
package com.proyectoFinal.homebanking.mappers;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {
    public static Account dtoToAccount(AccountDTO dto){
        Account account= new Account();
        account.setId_account(dto.getId());
        account.setTipo(dto.getTipo());
        account.setDueño(dto.getDueño());
        account.setCbu(dto.getCbu());
        account.setAlias(dto.getAlias());
        account.setMonto(dto.getMonto());
        return account;
    }
    public static AccountDTO accountToDto(Account account){
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId_account());
        dto.setTipo(account.getTipo());
        dto.setDueño(account.getDueño());
        dto.setCbu(account.getCbu());
        dto.setAlias(account.getAlias());
        dto.setMonto(account.getMonto());
        return dto;
    }
}
