
package com.proyectoFinal.homebanking.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException (String message){
        super(message);
    }
}
