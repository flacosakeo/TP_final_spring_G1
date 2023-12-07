package com.proyectoFinal.homebanking.exceptions;

public class TransferNotExistsException extends RuntimeException {

    public TransferNotExistsException(String message){
        super(message);
    }
}
