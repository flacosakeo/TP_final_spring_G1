package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.InsufficientFoundsException;
import com.proyectoFinal.homebanking.exceptions.AccountNotFoundException;
import com.proyectoFinal.homebanking.exceptions.TransferNotExistsException;
import com.proyectoFinal.homebanking.exceptions.TransferNotFoundException;
import com.proyectoFinal.homebanking.mappers.TransferMapper;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.repositories.TransferRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;
    private AccountRepository accountRepository;
    
    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository){
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransferDTO> getTransfers(){
        List<Transfer> transfers = transferRepository.findAll();

        return transfers.stream()
                .map(TransferMapper::transferToDto)                
                .collect(Collectors.toList());
    }
    
    public TransferDTO getTransferById(Long id){
        Transfer transfer = transferRepository.findById(id).orElseThrow(() ->
            new TransferNotFoundException("Transferencia no encontrada id: " + id));
        return TransferMapper.transferToDto(transfer);
    }
    
    public String deleteTransfer(Long id){
        if (transferRepository.existsById(id)){
            transferRepository.deleteById(id);
            return "¡La transferencia con id " + id + " ha sido eliminada!";
        }else{
            throw new TransferNotExistsException("¡La transferencia con id " + id + " no existe!");
        }
    }

    @Transactional
    public TransferDTO createTransfer(TransferDTO dto){
        //verificar que las cuentas origen y destino existan
        Account originAccount = accountRepository.findById(dto.getOriginAccount())
                             .orElseThrow(()-> new AccountNotFoundException("Cuenta origen no existe, id: " + dto.getOriginAccount()));

        Account destinationAccount = accountRepository.findById(dto.getTargetAccount())
                             .orElseThrow(()-> new AccountNotFoundException("Cuenta destino no existe, id: " + dto.getTargetAccount()));
        
        //verifica si la cuenta origen tiene fondos
        if (originAccount.getMonto().compareTo(dto.getAmount()) < 0){
            throw new InsufficientFoundsException("Fondos insuficientes, id: " + dto.getOriginAccount());
        }
        
        //se hace la transferencia, se resta de la cuenta origen y se suma en la cuenta destino
        originAccount.setMonto(originAccount.getMonto().subtract(dto.getAmount()));
        destinationAccount.setMonto(destinationAccount.getMonto().add(dto.getAmount()));
        
        //guarda las cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);
        
        //crea la transferencia y la guarda en base de datos
        Transfer transfer = Transfer.builder()
                .amount(dto.getAmount())
                .originAccount(dto.getOriginAccount())
                .targetAccount(dto.getTargetAccount())
                .dateTime(LocalDateTime.now())
                .build();

        transfer = transferRepository.save(transfer);
        return TransferMapper.transferToDto(transfer);
    }

    // Creemos que una transferencia no deberia ser modificable, por el hecho de que queremos que quede registrado cada
    // movimiento. Nos parece poco etico que se pueda modificar una transferencia.
    // Si hace falta realizar una modificacion, por ejemplo si se transfirio erroneamente, deberia realizarse otra
    // transferencia con el mismo monto y id de las cuentas de origen y destino de forma invertida.
    // TODO (#Ref. 2): agregar atributo en entidad TRANSFER que indique quien realiza o el estado de la transferencia.
    public TransferDTO updateTransfer(Long id, TransferDTO dto){
        if(transferRepository.existsById(id)){
            Transfer transferToModify = transferRepository.findById(id).get();
            //logica del patch
            if(dto.getAmount() != null)
                transferToModify.setAmount(dto.getAmount());

            if(dto.getOriginAccount() != null)
                transferToModify.setOriginAccount(dto.getOriginAccount());

            if (dto.getTargetAccount() != null)
                transferToModify.setTargetAccount(dto.getTargetAccount());

            if (dto.getDateTime() != null)
                transferToModify.setDateTime(dto.getDateTime());

            Transfer transferModified = transferRepository.save(transferToModify);
            return TransferMapper.transferToDto(transferModified);
        }

        throw new TransferNotExistsException("La transferencia con id " + id + " no existe!");
    }
}

