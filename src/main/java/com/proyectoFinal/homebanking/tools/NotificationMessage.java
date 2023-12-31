package com.proyectoFinal.homebanking.tools;

import java.math.BigDecimal;

public class NotificationMessage {

    // region ------------------  GENERALS ERROR MESSAGES  ------------------
    public static String allAttributesAreNull() {
        return "¡No se envio ningún atributo!";
    }

    public static String nullAttributes() {
        return "¡Uno o varios de los atributos enviados son nulos!";
    }
    //endregion

    public static String illegalArgument() {
        return "Tipo de número no compatible. Se esperaba Double, Float, Integer o Long.";
    }

    public static String attributeNotRequired(String attribute) {
        return "¡'" + attribute + "' no es requerido!";
    }

    // region ------------------  TRANSFERS ERROR MESSAGES  ------------------
    // region  ------------  CONTROLLER  ------------
    public static String requiredAccounts() {
        return "Cuenta origen y cuenta de destino son requeridas";
    }

    public static String invalidAccounts() {
        return "Cuenta origen y cuenta de destino invalidas";
    }

    public static String originAccountRequired() {
        return "La cuenta de origen es requerida";
    }

    public static String invalidOriginAccount() {
        return "Ingrese una cuenta de origen valida";
    }
    public static String targetAccountRequired() {
        return "La cuenta de destino es requerida";
    }

    public static String invalidTargetAccount() {
        return "Ingrese una cuenta de destino valida";
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

    public static String transferNotFoundAndNullAttributes(Long id) {
        return "La transferencia con id '" + id + "' NO fue encontrada! Y uno o varios atributos son nulos";
    }
    //endregion
    // endregion

    // region ------------------  ACCOUNTS ERROR MESSAGES  ------------------
    // region  ------------  CONTROLLER  ------------
    public static String ownerIdRequired() {
        return "El ID del titular de la cuenta es requerido";
    }

    public static String accountTypeRequired() {
        return "El 'accountType' es requerido";
    }

    public static String invalidOwnerId() {
        return "¡Ingrese una 'ownerId' valido!";
    }
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

    public static String targetAccountNotFound(Long id) {
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
        return "¡El DNI '" + dni + "' no es valido! ¡Debe contener 8 caracteres numericos!";
    }

    public static String invalidEmail(String email) {
        return "¡El EMAIL ingresado '" + email + "' no es valido! ¡Intente nuevamente!";
    }

    public static String invalidPassword() {
        return "¡Contraseña inválida! Debe contener al menos 8 caracteres";
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
        return "¡Email " + email + " ya registrado!";
    }

    public static String userDniExists(String dni) {
        return "¡Ya existe un usuario con el DNI: " + dni + "!";
    }

    public static String userUsernameExists(String email) {
        return "¡Ya existe un usuario con el USERNAME: '" + email + "'!";
    }

    public static String userDeleted() {
        return "¡El usuario fue eliminado existosamente!";
    }

    public static String userNotFoundAndNullAttributes(Long id) {
        return "El usuario con ID '" + id + "' NO fue encontrado! Y uno o varios atributos son nulos";
    }

    public static String requiredAlias() {
        return "¡Alias es requerido!";
    }
    // endregion

    // endregion
}
