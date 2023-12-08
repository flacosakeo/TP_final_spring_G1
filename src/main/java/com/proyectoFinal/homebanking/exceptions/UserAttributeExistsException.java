package com.proyectoFinal.homebanking.exceptions;

public class UserAttributeExistsException extends RuntimeException {

    public UserAttributeExistsException(String message) {
        super(message);
    }
}
