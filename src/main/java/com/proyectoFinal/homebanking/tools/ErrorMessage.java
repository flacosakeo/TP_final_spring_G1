package com.proyectoFinal.homebanking.tools;

public class ErrorMessage {

    public static String transferNotFound(Long id) {
        return "¡La transferencia con ID '" + id + "' NO fue encontrada!";
    }

    public static String transferSuccessfullyDeleted(Long id) {
        return "¡La transferencia con ID '" + id + "' ha sido eliminada!";
    }

    public static String originAccountNotFound(Long id) {
        return "Cuenta origen no existe, ID: '" + id + "'";
    }

    public static String destinationAccountNotFound(Long id) {
        return "Cuenta destino no existe, ID: '" + id + "'";
    }

    public static String insufficientFounds(Long id) {
        return "Cuenta destino no existe, ID: '" + id + "'";
    }


    public static String accountNotFound(Long id) { return "¡La cuenta con ID '" + id + "' NO fue encontrada!";}
}
