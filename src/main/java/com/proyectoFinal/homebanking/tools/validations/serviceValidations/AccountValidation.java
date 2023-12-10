package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.tools.Utility;
import org.springframework.stereotype.Component;

@Component
public class AccountValidation {

    private static AccountRepository accountRepository;

    public AccountValidation(AccountRepository accountRepository){
        AccountValidation.accountRepository = accountRepository;
    }
    public static String generateValidCbu(){
        String cbu = "";
        do{
             cbu = Utility.generadorCbu();
        }while(AccountValidation.accountRepository.existsByCbu(cbu));
        return cbu;
    }


}
