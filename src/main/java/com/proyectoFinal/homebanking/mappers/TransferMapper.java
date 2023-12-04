package com.proyectoFinal.homebanking.mappers;

import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {
    public Transfer dtoToTransfer(TransferDTO dto){
        return Transfer.builder()
                .id_transfer(dto.getId_transfer())
                .monto(dto.getMonto())
                .fecha(dto.getFecha())
                .id_cta_origen(dto.getId_cta_origen())
                .id_cta_destino(dto.getId_cta_destino())
                .build();
        /*Transfer transfer= new Transfer();
        transfer.setId_transfer(dto.getId_transfer());
        transfer.setMonto(dto.getMonto());
        transfer.setId_cta_origen(dto.getId_cta_origen());
        transfer.setId_cta_destino(dto.getId_cta_destino());
        transfer.setFecha(dto.getFecha());
        return transfer;*/
    }
    
    public TransferDTO transferToDto(Transfer transfer){
        return TransferDTO.builder()
                .id_transfer(transfer.getId_transfer())
                .monto(transfer.getMonto())
                .fecha(transfer.getFecha())
                .id_cta_origen(transfer.getId_cta_origen())
                .id_cta_destino(transfer.getId_cta_destino())
                .build();
        /*TransferDTO dto = new TransferDTO();
        dto.setId_transfer(transfer.getId_transfer());
        dto.setMonto(transfer.getMonto());
        dto.setId_cta_origen(transfer.getId_cta_origen());
        dto.setId_cta_destino(transfer.getId_cta_destino());
        dto.setFecha(transfer.getFecha());
        return dto;*/
    }   
}
