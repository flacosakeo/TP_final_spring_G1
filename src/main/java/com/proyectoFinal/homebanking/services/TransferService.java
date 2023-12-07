package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.TransferNotExistsException;
import com.proyectoFinal.homebanking.mappers.TransferMapper;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.TransferRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired
    private TransferRepository repository;    
    
    public List<TransferDTO> getTransfer(){
        List<Transfer> transfers = repository.findAll();
        List<TransferDTO> transfersDTO=transfers.stream()
                .map(TransferMapper::transferToDto)                
                .collect(Collectors.toList());
        return transfersDTO;
    }
    
    public TransferDTO createTransfer(TransferDTO transferDTO){
        //accountDTO.setTipo(new Random (AccountType.values()));
        Transfer transfer = repository.save(TransferMapper.dtoToTransfer(transferDTO));
        return TransferMapper.transferToDto(transfer);
    }
    
    public TransferDTO getTransferById(Long id){
        Transfer entity = repository.findById(id).get();
        return TransferMapper.transferToDto(entity);
    }
    
    public String deleteTransfer(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Transferencia Eliminada";
        }else{
            throw new TransferNotExistsException("¡La transferencia con id " + id + " no existe!");
        }       
    }

    // Decicimos no implementar la modificacion de una transferencia. Ya que creemos que una transferencia no deberia
    // ser modificable, por el hecho de que queremos que quede registrado cada movimiento.
    // Si hace falta realizar una modificacion, por ejemplo si se transfirio erroneamente, deberia realizarse otra
    // transferencia con el mismo monto y id de la cuentas de origen y destino de forma invertida.
    // Nos parece poco etico que se pueda modificar una transferencia.
    // TODO (#Ref. 2): agregar atributo en entidad TRANSFER que indique quien realiza o el estado de la transferencia.
    /*public TransferDTO updateTransfer(Long id, TransferDTO dto){
        if(repository.existsById(id)){
            Transfer transferToModify = repository.findById(id).get();
            //logica del patch
            if(dto.getAmount() != null)
                transferToModify.setAmount(dto.getAmount());

            if(dto.getSourceAccount() != null)
                transferToModify.setSourceAccount(dto.getSourceAccount());

            if (dto.getTargetAccount() != null)
                transferToModify.setTargetAccount(dto.getTargetAccount());

            if (dto.getDateTime() != null)
                transferToModify.setDateTime(dto.getDateTime());

            Transfer transferModified = repository.save(transferToModify);
            return TransferMapper.transferToDto(transferModified);
        }

        throw new TransferNotExistsException("¡La transferencia con id " + id + " no existe!");
    }*/
}
