package com.proyectoFinal.homebanking.tools;

import java.math.BigDecimal;

public class Validations {


    /**
     * @author Florencia
     * Método genérico que verifica que el número pasado por párametro sea positivo.
     * @param number de tipo <N>. El parametro number acepta objetos que hereden de la clase Number
     * @return boolean. Retorna True si el número es positivo.
     */
    public static <N extends Number> boolean isPositive(N number) {
        if (number instanceof Double || number instanceof Float || number instanceof Integer || number instanceof Long) {
            return ((Number) number).doubleValue() > 0;
        } else if (number instanceof BigDecimal) {
            return (((BigDecimal) number).compareTo(BigDecimal.ZERO) > 0);
        } else {
            throw new IllegalArgumentException("Tipo de número no compatible. Se esperaba Double, Float, Integer o Long.");
        }
    }

    public static String validateTransferAccountId(Long originAccount, Long targetAccount){
        if(!Validations.isPositive(originAccount) && (!Validations.isPositive(targetAccount))){
            return "Cuenta destino y cuenta de origen invalidas.";
        }else if(!Validations.isPositive(originAccount)){
            return "Ingrese una cuenta de origen valida";
        }else{
            return "Ingrese una cuenta de destino valida";
        }
    }
}
