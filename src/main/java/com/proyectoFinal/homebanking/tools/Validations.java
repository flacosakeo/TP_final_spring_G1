package com.proyectoFinal.homebanking.tools;

import com.proyectoFinal.homebanking.exceptions.AccountsAreTheSameException;

import java.util.Objects;

public class Validations {

    //Iba a inyectar el accountRepository pero me pide que la clase Validations sea un java bean
    //Al inyectar aca el accountRepository iba evitar inyectarlo en el transferService
/*    @Autowired
    private AccountRepository accountRepository;

    public static boolean validateIfAccountExistsByCBU(Long cbu){
        boolean valid = false;
        if(accountRepository.existsByCBU(cbu)){
            valid = true;
        };



        return valid;
    }*/

    public static boolean validateIfAccountsAreEqual(Long origin, Long target){
        boolean valid = false;

        if(!Objects.equals(origin, target)){
            valid = true;
        }else{
            throw new AccountsAreTheSameException("Ambas cuentas son iguales: " + origin + ", " + target);
        }
        return valid;
    }

}
