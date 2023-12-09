package com.proyectoFinal.homebanking.tools.validations;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerValidation {


    /**
     * Método genérico que verifica que el número pasado por párametro sea positivo.
     * @param number de tipo <N>. El parametro number acepta objetos que hereden de la clase Number
     * @return boolean. Retorna True si el número es positivo.
     */
    public static <N extends Number> boolean isPositive(N number) {
        if (number instanceof Double || number instanceof Float || number instanceof Integer || number instanceof Long) {
            return  number.doubleValue() > 0;
        } else if (number instanceof BigDecimal) {
            return (((BigDecimal) number).compareTo(BigDecimal.ZERO) > 0);
        } else {
            throw new IllegalArgumentException("Tipo de número no compatible. Se esperaba Double, Float, Integer o Long.");
        }
    }

    // Valida que el dni tenga 8 digitos
    public static Boolean dniNumberDigitsIsValid(String dni) {
        // DNI a verificar viene por el parametro. Si no se pasa tal atributo, no se analiza mas nada.
        // Es decir que si se pasara un null se romperia en el matcher si se deja pasar tal atributo.
        if(dni == null) return false;

        // Patrón para verificar que el DNI contenga exactamente 8 dígitos
        String regex = "\\d{8}";

        // Compilar el patrón
        Pattern pattern = Pattern.compile(regex);

        // Crear un Matcher con el DNI
        Matcher matcher = pattern.matcher(dni);

        // Verificar si el DNI cumple con el patrón y retornar el resultado
        return matcher.matches();
    }

    public static Boolean emailIsValid(String email){
        if(email == null) return false;

        Pattern pattern = Pattern.compile("[^@ ]+@[^@ .]+\\.[a-z]{2,}(\\.[a-z]{2})?");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static Boolean passwordIsValid(String password){
        if(password == null) return false;

        Pattern pattern = Pattern.compile(".{8}");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    // El name debe comenzar en mayuscula y tener al menos 2 letras. Acepta nombres compuestos(hasta 3 palabras)
    public static Boolean nameIsValid(String name) {
        if(name == null) return false;

        Pattern pattern = Pattern.compile("([A-Z][a-z]+ ?){1,3}");
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    // Valida que el username tenga al entre 4 y 8 caracteres. Solo admite letras o numeros.
    public static Boolean usernameIsValid(String username) {
        if(username == null) return false;

        Pattern pattern = Pattern.compile("\\w{4,8}");
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }
}
