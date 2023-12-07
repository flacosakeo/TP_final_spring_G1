package com.proyectoFinal.homebanking.exceptions;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String message){
        super(message);
    }
}
