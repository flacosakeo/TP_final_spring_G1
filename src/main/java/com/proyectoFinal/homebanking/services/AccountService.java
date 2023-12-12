
package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.InsufficientFoundsException;
import com.proyectoFinal.homebanking.mappers.AccountMapper;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.proyectoFinal.homebanking.tools.NotificationMessage;
import com.proyectoFinal.homebanking.tools.validations.serviceValidations.AccountServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    
    public List<AccountDTO> getAccount() {
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::accountToDto)                
                .collect(Collectors.toList());
    }

    public AccountDTO createAccount(AccountDTO dto) throws EntityNotFoundException {
        /*for(int i=1; i<4; i++){
            List<AccountAlias> alias = AccountAlias.values()[new Random().nextInt(AccountAlias.values().length)];
        }*/
        AccountServiceValidation.validateCreateAccountDTO(dto);

        dto.setCbu(AccountServiceValidation.generateValidCbu());
        dto.setAlias(AccountServiceValidation.generateValidAlias());
        dto.setAmount(BigDecimal.ZERO);

        Account account = repository.save(AccountMapper.dtoToAccount(dto));
        return AccountMapper.accountToDto(account);
    }
    
    public AccountDTO getAccountById(Long id) throws EntityNotFoundException {
        Account entity = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(NotificationMessage.accountNotFound(id)));
        return AccountMapper.accountToDto(entity);
    }
    
    public String deleteAccount(Long id) throws EntityNotFoundException {
        if ( !AccountServiceValidation.existAccountById(id) ) {
            throw new EntityNotFoundException( NotificationMessage.accountNotFound(id) );
        }

        repository.deleteById(id);
        return NotificationMessage.accountSuccessfullyDeleted(id);
    }
    
    public Object updateAccount(Long id, AccountDTO dto){
    //Account cbuValidate = repository.findByCbu(dto.getCbu());
    //if(cbuValidate==null){
        //Solo se va a poder modificar el ALIAS de account
        if(repository.existsById(id)){
            Account accountToModify = repository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException(NotificationMessage.accountNotFound(id)));

            // LÓGICA DEL PATCH
//            if (dto.getAccountType()!=null)
//                accountToModify.setTipo(dto.getTipo());

//            if (dto.getOwnerId()!=null)
//                accountToModify.setOwnerId(dto.getOwnerId());

//            if (dto.getCbu()!=null)
//                accountToModify.setCbu(dto.getCbu());

            if (dto.getAlias() != null)
                accountToModify.setAlias(dto.getAlias());

//            if (dto.getAmount()!=null)
//                accountToModify.setAmount(dto.getAmount());

            repository.save(accountToModify);
            return AccountMapper.accountToDto(accountToModify);
        }

        return null;
    }

    public AccountDTO depositMoney(Long id, BigDecimal amount){
        if(repository.existsById(id)){
            Account accountToModify = repository.findById(id).orElseThrow( () ->
                    new EntityNotFoundException(NotificationMessage.accountNotFound(id)));

            accountToModify.setAmount(accountToModify.getAmount().add(amount));

            Account accountModified = repository.save(accountToModify);
            return AccountMapper.accountToDto(accountModified);
        }
        throw new EntityNotFoundException(NotificationMessage.accountNotFound(id));
    }

    public AccountDTO extractMoney(Long id, BigDecimal amount) {
        if(repository.existsById(id)){
            Account accountToModify = repository.findById(id).orElseThrow( () ->
                    new EntityNotFoundException(NotificationMessage.accountNotFound(id)));

            //verifica si la cuenta origen tiene fondos

            if (accountToModify.getAmount().compareTo(amount) < 0){
                throw new InsufficientFoundsException( NotificationMessage.insufficientFounds(accountToModify.getId()));
            }

            //se hace la transferencia, se resta de la cuenta origen y se suma en la cuenta destino
            accountToModify.setAmount(accountToModify.getAmount().subtract(amount));

            Account accountModified = repository.save(accountToModify);
            return AccountMapper.accountToDto(accountModified);
        }
        throw new EntityNotFoundException(NotificationMessage.accountNotFound(id));
    }

}
