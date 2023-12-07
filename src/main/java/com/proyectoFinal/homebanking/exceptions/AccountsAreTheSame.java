package com.proyectoFinal.homebanking.exceptions;

public class AccountsAreTheSame extends RuntimeException {

    public AccountsAreTheSame(String message){
        super(message);
    }
}
