package com.proyectoFinal.homebanking.mappers;

import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {
    public static Transfer dtoToTransfer(TransferDTO dto){
        return Transfer.builder()
                .amount(dto.getAmount())
                .originAccountId(dto.getOriginAccountId())
                .targetAccountId(dto.getTargetAccountId())
                .dateTime(dto.getDateTime())
                .build();
    }
    
    public static TransferDTO transferToDto(Transfer transfer){
        return TransferDTO.builder()
                .id(transfer.getId())
                .amount(transfer.getAmount())
                .originAccountId(transfer.getOriginAccountId())
                .targetAccountId(transfer.getTargetAccountId())
                .dateTime(transfer.getDateTime())
                .build();
    }
}
