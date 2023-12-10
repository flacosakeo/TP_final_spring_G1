package com.proyectoFinal.homebanking.testsprueba;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Prueba {
    public static void main(String[] args) {
        System.out.println("Hello world!");

    }
    public static void testIsPositive(){
        Double doubleType = 1.3;
        Integer integerType = -7;
        Float floatType = -17.3F;
        Long longType = 3298891297878978978L;
        BigDecimal bigDecimalType = new BigDecimal(12323);

        /*        System.out.println(isPositive(doubleType));
        System.out.println(isPositive(integerType));
        System.out.println(isPositive(floatType));
        System.out.println(isPositive(longType));
        System.out.println(isPositive(bigDecimalType));*/

//        System.out.println(isPositive("A"));
    }

    public static <N extends Number> boolean isPositive(N number) {
        if (number instanceof Double || number instanceof Float || number instanceof Integer || number instanceof Long) {
            return ((Number) number).doubleValue() > 0;
        } else if (number instanceof BigDecimal) {
            return (((BigDecimal) number).compareTo(BigDecimal.ZERO) > 0);
        } else {
            throw new IllegalArgumentException("Tipo de número no compatible. Se esperaba Double, Float, Integer o Long.");
        }
    }
}