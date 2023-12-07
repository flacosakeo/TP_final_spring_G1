package com.proyectoFinal.homebanking.mappers;

import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {
    public static Transfer dtoToTransfer(TransferDTO dto){
        return Transfer.builder()
                .amount(dto.getAmount())
                .originAccount(dto.getOriginAccount())
                .targetAccount(dto.getTargetAccount())
                .dateTime(dto.getDateTime())
                .build();
    }
    
    public static TransferDTO transferToDto(Transfer transfer){
        return TransferDTO.builder()
                .id(transfer.getId())
                .amount(transfer.getAmount())
                .originAccount(transfer.getOriginAccount())
                .targetAccount(transfer.getTargetAccount())
                .dateTime(transfer.getDateTime())
                .build();
    }
}
