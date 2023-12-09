package com.proyectoFinal.homebanking.tools;

import java.math.BigDecimal;

public class ErrorMessage {

    // region ------------------  TRANSFERS ERROR MESSAGES  ------------------
    // region  ------------  CONTROLLER  ------------
    public static String dniNotFound(String dni) {
        return "¡El DNI '" + dni + "' no es valido! ¡Debe contener 8 caracteres numericos!";
    }

    public static String invalidEmail(String email) {
        return "¡El EMAIL ingresado '" + email + "' no es valido! ¡Intente nuevamente!";
    }

    // endregion

    // region  ------------  SERVICE  ------------
    public static String transferNotFound(Long id) {
        return "¡La transferencia con ID '" + id + "' NO fue encontrada!";
    }

    public static String transferSuccessfullyDeleted(Long id) {
        return "¡La transferencia con ID '" + id + "' ha sido eliminada!";
    }

    public static String requiredAccount() {
        return "La cuenta de origen o la cuenta de destino es requerida.";
    }

    public static String requiredAmount() {
        return "Ingrese un monto";
    }

    public static String invalidAmount(BigDecimal amount) {
        return "Monto invalido: $" + amount + ". Debe ingresar un monto mayor a $0.";
    }
    //endregion
    // endregion

    // region ------------------  ACCOUNTS ERROR MESSAGES  ------------------
    public static String originAccountNotFound(Long id) {
        return "Cuenta origen no existe, ID: '" + id + "'";
    }

    public static String destinationAccountNotFound(Long id) {
        return "Cuenta destino no existe, ID: '" + id + "'";
    }

    public static String insufficientFounds(Long id) {
        return "Fondos insuficientes. ID: '" + id + "'";
    }

    public static String equalAccounts(Long idOriginAccount, Long idTargetAccount) {
        return "Ambas cuentas son iguales: '" + idOriginAccount + "', '" + idTargetAccount + "'";
    }

    public static String invalidPassword() {
        return "¡Contraseña incorrecta! Debe contener al menos 8 caracteres";
    }

    public static String invalidName() {
       return "¡Nombre invalido! Debe comenzar en mayúscula y tener al menos 2 letras. " +
               "Acepta nombres compuestos (hasta 3 palabras)";
    }

    public static String usernameInvalid() {
        return "¡Username invalido! Debe tener entre 4 y 8 caracteres. Solo se admiten letras o números";
    }
    // endregion

    // region ------------------  USERS ERROR MESSAGES  ------------------

    // endregion
}
