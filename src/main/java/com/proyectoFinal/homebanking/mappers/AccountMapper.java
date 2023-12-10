
package com.proyectoFinal.homebanking.mappers;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {
    public static Account dtoToAccount(AccountDTO dto){
        return Account.builder()
                .accountType(dto.getAccountType())
                .ownerId(dto.getOwnerId())
                .cbu(dto.getCbu())
                .alias(dto.getAlias())
                .amount(dto.getAmount())
                .build();
    }
    public static AccountDTO accountToDto(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .accountType(account.getAccountType())
                .ownerId(account.getOwnerId())
                .cbu(account.getCbu())
                .alias(account.getAlias())
                .amount(account.getAmount())
                .build();
    }
}