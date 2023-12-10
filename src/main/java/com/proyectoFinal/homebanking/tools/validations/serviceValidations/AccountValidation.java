package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.tools.Utility;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.proyectoFinal.homebanking.tools.Utility.generadorCbu;
import static com.proyectoFinal.homebanking.tools.Utility.generateAlias;

@Component
public class AccountValidation {

    private static AccountRepository accountRepository;

    public static Boolean existByCbu(String cbu){ return accountRepository.existsByCbu(cbu);}
    public static Boolean existByAlias(String alias){ return accountRepository.existsByAlias(alias);}

    public AccountValidation(AccountRepository accountRepository){
        AccountValidation.accountRepository = accountRepository;
    }
    public static String generateValidCbu(){
        String cbu = "";
        do{
             cbu = Utility.generadorCbu();
        }while(existByCbu(cbu));
        return cbu;
    }
    public static String generateValidAlias(){
        String alias = "";
        do{
            alias = Utility.generateAlias();
        }while(existByAlias(alias));
        return alias;
    }





}
