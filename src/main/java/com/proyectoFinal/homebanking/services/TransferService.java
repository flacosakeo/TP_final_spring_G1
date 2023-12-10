package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.AccountsAreTheSameException;
import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.InsufficientFoundsException;
import com.proyectoFinal.homebanking.mappers.TransferMapper;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.repositories.TransferRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.proyectoFinal.homebanking.tools.ErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    
    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransferDTO> getTransfers() {
        List<Transfer> transfers = transferRepository.findAll();

        return transfers.stream()
                .map(TransferMapper::transferToDto)                
                .collect(Collectors.toList());
    }
    
    public TransferDTO getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(ErrorMessage.transferNotFound(id)));

        return TransferMapper.transferToDto(transfer);
    }
    
    public String deleteTransfer(Long id) {
        if (transferRepository.existsById(id)) {
            transferRepository.deleteById(id);
            return ErrorMessage.transferSuccessfullyDeleted(id);
        } else {
            throw new EntityNotFoundException(ErrorMessage.transferNotFound(id));
        }
    }

    @Transactional
    public TransferDTO createTransfer(TransferDTO dto) {

        Long originAccountId = dto.getOriginAccountId();
        Long targetAccountId = dto.getTargetAccountId();

        //verifica si ambas cuentas son iguales
        if(Objects.equals(originAccountId, targetAccountId)) {
            throw new AccountsAreTheSameException(ErrorMessage.equalAccounts(originAccountId, targetAccountId));
        }

        //verificar que las cuentas origen y destino existan
        Account originAccount = accountRepository.findById( originAccountId )
                             .orElseThrow( () -> new EntityNotFoundException( ErrorMessage.originAccountNotFound(
                                     originAccountId )));

        Account destinationAccount = accountRepository.findById( targetAccountId )
                             .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.destinationAccountNotFound(
                                     targetAccountId )));
        
        // Verifica si la cuenta origen tiene fondos
        if (originAccount.getAmount().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFoundsException( ErrorMessage.insufficientFounds( originAccountId ));
        }
        
        // Se hace la transferencia, se resta de la cuenta origen y se suma en la cuenta destino
        originAccount.setAmount( originAccount.getAmount().subtract(dto.getAmount()) );
        destinationAccount.setAmount( destinationAccount.getAmount().add( dto.getAmount()) );
        
        // Guarda las cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);
        
        // Crea la transferencia y la guarda en base de datos
        dto.setDateTime( LocalDateTime.now() );
        Transfer transfer = TransferMapper.dtoToTransfer(dto);

        transfer = transferRepository.save(transfer);
        return TransferMapper.transferToDto(transfer);
    }

    // Creemos que una transferencia no deberia ser modificable, por el hecho de que queremos que quede registrado cada
    // movimiento. Nos parece poco etico que se pueda modificar una transferencia.
    // Si hace falta realizar una modificacion, por ejemplo si se transfirio erroneamente, deberia realizarse otra
    // transferencia con el mismo monto y id de las cuentas de origen y destino de forma invertida.
    // TODO (#Ref. 2): agregar atributo en entidad TRANSFER que indique quien realiza o el estado de la transferencia.
    public TransferDTO updateTransfer(Long id, TransferDTO dto) {
        if(transferRepository.existsById(id)) {
            Long originAccountId = dto.getOriginAccountId();
            Long targetAccountId = dto.getTargetAccountId();

            Transfer transferToModify = transferRepository.findById(id).orElseThrow( () ->
                    new EntityNotFoundException( ErrorMessage.transferNotFound(id) ));

            // LÓGICA DEL PATCH
            if(dto.getAmount() != null)
                transferToModify.setAmount(dto.getAmount());

            if(originAccountId != null)
                transferToModify.setOriginAccountId(originAccountId);

            if (targetAccountId != null)
                transferToModify.setTargetAccountId(targetAccountId);

            if (dto.getDateTime() != null)
                transferToModify.setDateTime(dto.getDateTime());

            Transfer transferModified = transferRepository.save(transferToModify);
            return TransferMapper.transferToDto(transferModified);
        }
        throw new EntityNotFoundException(ErrorMessage.transferNotFound(id));
    }
}

