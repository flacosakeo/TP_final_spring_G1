
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
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

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
        Account entity = AccountServiceValidation.findAccountById(id);
        return AccountMapper.accountToDto(entity);
    }
    
    public String deleteAccount(Long id) throws EntityNotFoundException {
        if ( !AccountServiceValidation.existAccountById(id) ) {
            throw new EntityNotFoundException( NotificationMessage.accountNotFound(id) );
        }

        repository.deleteById(id);
        return NotificationMessage.accountSuccessfullyDeleted(id);
    }
    
    public AccountDTO updateAccount(Long id, AccountDTO dto) throws EntityNotFoundException {
        // Si llego hasta este punto, AccountService, es porque en el controlador ya se validaron
        // los atributos requeridos para esta modificacion
        if( !AccountServiceValidation.existAccountById(id) ) {
            throw new EntityNotFoundException( NotificationMessage.accountNotFound(id) );
        }

        // Solo se va a poder modificar el ALIAS de account
        Account accountToModify = AccountServiceValidation.findAccountById(id);

        // LÓGICA DEL PATCH
//            if (dto.getAccountType()!=null)
//                accountToModify.setTipo(dto.getTipo());

//            if (dto.getOwnerId()!=null)
//                accountToModify.setOwnerId(dto.getOwnerId());

//            if (dto.getCbu()!=null)
//                accountToModify.setCbu(dto.getCbu());

        accountToModify.setAlias(dto.getAlias());

        //if (dto.getAmount()!=null)
//                accountToModify.setAmount(dto.getAmount());

        Account accountSaved = repository.save(accountToModify);
        return AccountMapper.accountToDto(accountSaved);
    }

    public AccountDTO depositMoney(Long id, BigDecimal amount) throws EntityNotFoundException {
        if( !AccountServiceValidation.existAccountById(id) ) {
            throw new EntityNotFoundException( NotificationMessage.accountNotFound(id) );
        }

        Account accountToModify = AccountServiceValidation.findAccountById(id);

        // Se deposita el dinero en la cuenta
        accountToModify.setAmount(accountToModify.getAmount().add(amount));

        Account accountModified = repository.save(accountToModify);
        return AccountMapper.accountToDto(accountModified);
    }

    public AccountDTO extractMoney(Long id, BigDecimal amount) throws EntityNotFoundException, InsufficientFoundsException {
        if( !AccountServiceValidation.existAccountById(id) ) {
            throw new EntityNotFoundException(NotificationMessage.accountNotFound(id));
        }

        Account accountToModify = AccountServiceValidation.findAccountById(id);

        // Verifica si la cuenta origen tiene fondos
        if (accountToModify.getAmount().compareTo(amount) < 0){
            throw new InsufficientFoundsException( NotificationMessage.insufficientFounds(accountToModify.getId()));
        }

        // Se hace la transferencia. Se resta de la cuenta origen y se suma en la cuenta destino
        accountToModify.setAmount(accountToModify.getAmount().subtract(amount));

        Account accountModified = repository.save(accountToModify);
        return AccountMapper.accountToDto(accountModified);
    }

}
