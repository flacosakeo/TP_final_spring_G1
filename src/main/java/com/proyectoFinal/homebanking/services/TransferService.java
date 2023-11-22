package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.UserNotExistsException;
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
            throw new  UserNotExistsException("Transferencia no existe");
        }       
    }
    
    public TransferDTO updateTransfer(Long id, TransferDTO dto){
        if(repository.existsById(id)){
            Transfer transferToModify=repository.findById(id).get();
            //logica del patch
            if (dto.getMonto()!=null){
                transferToModify.setMonto(dto.getMonto());
            }
            if (dto.getId_cta_origen()!=null){
                transferToModify.setId_cta_origen(dto.getId_cta_origen());
            }
            if (dto.getId_cta_destino()!=null){
                transferToModify.setId_cta_destino(dto.getId_cta_destino());
            }
            if (dto.getFecha()!=null){
                transferToModify.setFecha(dto.getFecha());
            }
            repository.save(transferToModify);
            return TransferMapper.transferToDto(transferToModify);
        }
        return null;
    }
}
