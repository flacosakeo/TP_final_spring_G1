package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.TransferNotExistsException;
import com.proyectoFinal.homebanking.mappers.TransferMapper;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.repositories.TransferRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    public List<TransferDTO> getTransfer(){
        List<Transfer> transfers = transferRepository.findAll();
        List<TransferDTO> transfersDTO=transfers.stream()
                .map(TransferMapper::transferToDto)                
                .collect(Collectors.toList());
        return transfersDTO;
    }
    
    public TransferDTO createTransfer(TransferDTO transferDTO){
        //accountDTO.setTipo(new Random (AccountType.values()));
        transferDTO.setDateTime(LocalDateTime.now());

//        accountRepository.existsByCbu(transferDTO.getOriginAccount());
//        accountRepository.existsByCbu(transferDTO.getTargetAccount());


        Transfer transfer = transferRepository.save(TransferMapper.dtoToTransfer(transferDTO));
        return TransferMapper.transferToDto(transfer);
    }
    
    public TransferDTO getTransferById(Long id){
        Transfer entity = transferRepository.findById(id).get();
        return TransferMapper.transferToDto(entity);
    }
    
    public String deleteTransfer(Long id){
        if (transferRepository.existsById(id)){
            transferRepository.deleteById(id);
            return "Transferencia Eliminada";
        }else{
            throw new TransferNotExistsException("¡La transferencia con id " + id + " no existe!");
        }       
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

        throw new TransferNotExistsException("¡La transferencia con id " + id + " no existe!");
    }
}
