
package com.proyectoFinal.homebanking.mappers;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {
    public static Account dtoToAccount(AccountDTO dto){
        return Account.builder()
                .tipo(dto.getTipo())
                .dueño(dto.getDueño())
                .cbu(dto.getCbu())
                .alias(dto.getAlias())
                .monto(dto.getMonto())
                .build();
    }
    public static AccountDTO accountToDto(Account account){
        return AccountDTO.builder()
                .id(account.getId_account())
                .tipo(account.getTipo())
                .dueño(account.getDueño())
                .cbu(account.getCbu())
                .alias(account.getAlias())
                .monto(account.getMonto())
                .build();
    }
}