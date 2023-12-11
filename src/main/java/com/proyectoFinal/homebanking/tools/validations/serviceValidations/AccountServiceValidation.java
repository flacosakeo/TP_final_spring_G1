package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.tools.NotificationMessage;
import com.proyectoFinal.homebanking.tools.Utility;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceValidation {

    private static AccountRepository repository;

    public AccountServiceValidation(AccountRepository accountRepository) {
        AccountServiceValidation.repository = accountRepository;
    }

    public static Account findAccountById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.accountNotFound(id)) );
    }

    public static Account findOriginAccountById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.originAccountNotFound(id)) );
    }

    public static Account findTargetAccountById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.targetAccountNotFound(id)) );
    }

    public static Boolean existAccountById(Long id) {
        return repository.existsById(id);
    }

    public static Boolean existByCbu(String cbu) {
        return repository.existsByCbu(cbu);
    }

    public static Boolean existByAlias(String alias) {
        return repository.existsByAlias(alias);
    }

    public static String generateValidCbu() {
        String cbu = "";
        do{
             cbu = Utility.generadorCbu();
        }while(existByCbu(cbu));
        return cbu;
    }

    public static String generateValidAlias() {
        String alias = "";
        do{
            alias = Utility.generateAlias();
        }while(existByAlias(alias));
        return alias;
    }





}
