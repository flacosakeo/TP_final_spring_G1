
package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.UserNotExistsException;
import com.proyectoFinal.homebanking.mappers.AccountMapper;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.models.Enum.AccountType;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    
    public List<AccountDTO> getAccount(){
        List<Account> accounts = repository.findAll();
        List<AccountDTO> accountsDTO=accounts.stream()
                .map(AccountMapper::accountToDto)                
                .collect(Collectors.toList());
        return accountsDTO;
    }
    
    public AccountDTO createAccount(AccountDTO accountDTO){
        accountDTO.setTipo (AccountType.values()[new Random().nextInt(AccountType.values().length)]);
        accountDTO.setMonto(0.0);
        Account account = repository.save(AccountMapper.dtoToAccount(accountDTO));
        return AccountMapper.accountToDto(account);
    }
    
    public AccountDTO getAccountById(Long id){
        Account entity = repository.findById(id).get();
        return AccountMapper.accountToDto(entity);
    }
    
    public String deleteAccount(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Eliminado";
        }else{
            throw new  UserNotExistsException("Cuenta no existe");
        }       
    }
    
    public AccountDTO updateAccount(Long id, AccountDTO dto){
        if(repository.existsById(id)){
            Account accountToModify=repository.findById(id).get();
            //logica del patch
            if (dto.getTipo()!=null){
                accountToModify.setTipo(dto.getTipo());
            }
            if (dto.getDueño()!=null){
                accountToModify.setDueño(dto.getDueño());
            }
            if (dto.getCbu()!=null){
                accountToModify.setCbu(dto.getCbu());
            }
            if (dto.getAlias()!=null){
                accountToModify.setAlias(dto.getAlias());
            }
            if (dto.getMonto()!=null){
                accountToModify.setMonto(dto.getMonto());
            }
            repository.save(accountToModify);
            return AccountMapper.accountToDto(accountToModify);
        }
        return null;
    }
        
}
