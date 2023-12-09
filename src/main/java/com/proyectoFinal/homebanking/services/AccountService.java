
package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.InsufficientFoundsException;
import com.proyectoFinal.homebanking.mappers.AccountMapper;
import com.proyectoFinal.homebanking.mappers.TransferMapper;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.models.Enum.AccountAlias;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.proyectoFinal.homebanking.tools.ErrorMessage;
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

    public Object createAccount(AccountDTO accountDTO){
        /*for(int i=1; i<4; i++){
            List<AccountAlias> alias = AccountAlias.values()[new Random().nextInt(AccountAlias.values().length)];
        }*/
        accountDTO.setAlias(AccountAlias.values()[new Random().nextInt(AccountAlias.values().length)]);
        //accountDTO.setTipo (AccountType.values()[new Random().nextInt(AccountType.values().length)]);
        accountDTO.setMonto(BigDecimal.ZERO);
        Account cbuValidate = repository.findByCbu(accountDTO.getCbu());
        if (cbuValidate==null){
            Account account = repository.save(AccountMapper.dtoToAccount(accountDTO));
            return AccountMapper.accountToDto(account);
        }else{
            return "Cbu ya existe";
        }
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
            return "Cuenta no existe";
        }       
    }
    
    public Object updateAccount(Long id, AccountDTO dto){
    //Account cbuValidate = repository.findByCbu(dto.getCbu());
    //if(cbuValidate==null){
        if(repository.existsById(id)){
            Account accountToModify=repository.findById(id).get();
            //logica del patch
            if (dto.getTipo()!=null){
                //accountToModify.setTipo(dto.getTipo());
            }
            if (dto.getDueño()!=null){
                accountToModify.setDueño(dto.getDueño());
            }
            if (dto.getCbu()!=null){
                //accountToModify.setCbu(dto.getCbu());
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

    //}else{
    //    return "Cbu ya existe";
    //}
        return null;
    }

    public AccountDTO depositMoney(Long id, BigDecimal amount){
        if(repository.existsById(id)){
            Account accountToModify = repository.findById(id).orElseThrow( () ->
                    new EntityNotFoundException(ErrorMessage.accountNotFound(id)));

            //TODO: dejo estos comentarios para luego realizar las validaciones
//            if (accountToModify.getMonto() == null) {
//                accountToModify.setMonto(BigDecimal.ZERO);
//            }
//            if(amount == null){
//                amount = BigDecimal.ZERO;
//            }

            accountToModify.setMonto(accountToModify.getMonto().add(amount));

            Account accountModified = repository.save(accountToModify);
            return AccountMapper.accountToDto(accountModified);
        }
        throw new EntityNotFoundException(ErrorMessage.accountNotFound(id));
    }

    public AccountDTO extractMoney(Long id, BigDecimal amount){
        if(repository.existsById(id)){
            Account accountToModify = repository.findById(id).orElseThrow( () ->
                    new EntityNotFoundException(ErrorMessage.accountNotFound(id)));

            //verifica si la cuenta origen tiene fondos
            if (accountToModify.getMonto().compareTo(amount) < 0){
                throw new InsufficientFoundsException( ErrorMessage.insufficientFounds(accountToModify.getId_account()));
            }

            //se hace la transferencia, se resta de la cuenta origen y se suma en la cuenta destino
            accountToModify.setMonto(accountToModify.getMonto().subtract(amount));

            Account accountModified = repository.save(accountToModify);
            return AccountMapper.accountToDto(accountModified);
        }
        throw new EntityNotFoundException(ErrorMessage.accountNotFound(id));

    }

}
