package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.InsufficientFoundsException;
import com.proyectoFinal.homebanking.exceptions.AccountNotFoundException;
import com.proyectoFinal.homebanking.exceptions.TransferNotFoundException;
import com.proyectoFinal.homebanking.mappers.TransferMapper;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.repositories.TransferRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    @Autowired
    private TransferRepository repository;
    private AccountRepository accountRepository;
    
    public TransferService(TransferRepository repository, AccountRepository accountRepository){
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    
    public List<TransferDTO> getTransfer(){
        List<Transfer> transfers = repository.findAll();
        List<TransferDTO> transfersDTO=transfers.stream()
                .map(TransferMapper::transferToDto)                
                .collect(Collectors.toList());
        return transfersDTO;
    }
    
    public TransferDTO getTransferById(Long id){
        Transfer transfer = repository.findById(id).orElseThrow(()->
        new TransferNotFoundException("Transferencia no encontrada id: "+id));
        return TransferMapper.transferToDto(transfer);
    }
    
    public String deleteTransfer(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Transferencia Eliminada";
        }else{
            return "No se ha eliminado";
        }
    }
    
    public TransferDTO updateTransfer(Long id, TransferDTO dto){
            Transfer transfer=repository.findById(id).orElseThrow(()->
            new TransferNotFoundException("No se encontro el id: "+id));
            Transfer transferToModify=TransferMapper.dtoToTransfer(dto);
            transferToModify.setId_transfer(transfer.getId_transfer());
            return TransferMapper.transferToDto(transferToModify);
    }
    
    @Transactional
    public TransferDTO createTransfer(TransferDTO transferDTO){
        //verificar que las cuentas origen y destino existan
        Account cta_origen = accountRepository.findById(transferDTO.getId_cta_origen())
                             .orElseThrow(()-> new AccountNotFoundException("Cuenta origen no existe, id: "
                                                                            +transferDTO.getId_cta_origen()));
        Account cta_destino = accountRepository.findById(transferDTO.getId_cta_destino())
                             .orElseThrow(()-> new AccountNotFoundException("Cuenta destino no existe, id: "
                                                                            +transferDTO.getId_cta_destino()));
        
        //verifica si la cuenta origen tiene fondos
        if (cta_origen.getMonto().compareTo(transferDTO.getMonto())<0){
            throw new InsufficientFoundsException("Fondos insufucuentes, id: "+transferDTO.getId_cta_origen());
        }
        
        //se hace la transferencia, se resta de la cuenta origen y se suma en la cuenta destino
        cta_origen.setMonto(cta_origen.getMonto().subtract(transferDTO.getMonto()));
        cta_destino.setMonto(cta_destino.getMonto().add(transferDTO.getMonto()));
        
        //guarda las cuentas actualizadas
        accountRepository.save(cta_origen);
        accountRepository.save(cta_destino);
        
        //crea la transferencia y la gusrada en base de datos
        Transfer transfer = new Transfer();
        Date fecha = new Date();
        transfer.setFecha(fecha);
        transfer.setId_cta_origen(cta_origen.getId_account());
        transfer.setId_cta_destino(cta_destino.getId_account());
        transfer.setMonto(transferDTO.getMonto());
        transfer = repository.save(transfer);
        return TransferMapper.transferToDto(transfer);
    }
}

