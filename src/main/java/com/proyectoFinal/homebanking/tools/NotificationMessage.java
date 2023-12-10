package com.proyectoFinal.homebanking.tools;

import java.math.BigDecimal;

public class NotificationMessage {

    // region ------------------  TRANSFERS ERROR MESSAGES  ------------------
    // region  ------------  CONTROLLER  ------------
    public static String requiredAccount() {
        return "La cuenta de origen o la cuenta de destino es requerida.";
    }

    public static String requiredAmount() {
        return "Ingrese un monto";
    }

    public static String invalidAmount(BigDecimal amount) {
        return "Monto invalido: $" + amount + ". Debe ingresar un monto mayor a $0.";
    }
    // endregion

    // region  ------------  SERVICE  ------------
    public static String transferNotFound(Long id) {
        return "¡La transferencia con ID '" + id + "' NO fue encontrada!";
    }

    public static String transferSuccessfullyDeleted(Long id) {
        return "¡La transferencia con ID '" + id + "' ha sido eliminada!";
    }
    //endregion
    // endregion

    // region ------------------  ACCOUNTS ERROR MESSAGES  ------------------
    // region  ------------  CONTROLLER  ------------
    // endregion

    // region  ------------  SERVICE  ------------
    public static String accountNotFound(Long id) {
        return "¡La cuenta con ID '" + id + "' NO fue encontrada!";
    }

    public static String accountSuccessfullyDeleted(Long id) {
        return "¡La cuenta con ID '" + id + "' ha sido eliminada!";
    }

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

    // endregion
    // endregion

    // region ------------------  USERS ERROR MESSAGES  ------------------
    // region  ------------  CONTROLLER  ------------
    public static String dniNotFound(String dni) {
        return "¡El DNI '" + dni.toUpperCase() + "' no es valido! ¡Debe contener 8 caracteres numericos!";
    }

    public static String invalidEmail(String email) {
        return "¡El EMAIL ingresado '" + email.toUpperCase() + "' no es valido! ¡Intente nuevamente!";
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

    // region  ------------  SERVICE  ------------
    public static String userNotFound(Long id) {
        return "¡El usuario con ID '" + id + "' NO fue encontrado!";
    }

    public static String userEmailAttributeExists(String email) {
        return "¡Email " + email.toUpperCase() + " ya registrado!";
    }

    public static String userDniExists(String dni) {
        return "¡Ya existe un usuario con el DNI: " + dni + "!";
    }

    public static String userUsernameExists(String email) {
        return "¡Ya existe un usuario con el USERNAME: '" + email.toUpperCase() + "'!";
    }

    public static String userDeleted() {
        return "¡El usuario fue eiminado existosamente!";
    }

    public static String userNotFoundAndNullAttributes(Long id) {
        return "El usuario con id '" + id + "' NO fue encontrado! Y uno o varios atributos son nulos";
    }

    public static String userNullAttributes() {
        return "¡Uno o varios de los atributos enviados son nulos!";
    }
    // endregion
    // endregion
}
