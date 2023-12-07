package com.proyectoFinal.homebanking.exceptions;

public class AccountsAreTheSameException extends RuntimeException {

    public AccountsAreTheSameException(String message){
        super(message);
    }
}
