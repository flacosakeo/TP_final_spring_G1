package com.proyectoFinal.homebanking.tools;

public class Utility {

    public static String generadorCbu(){
        int i=1;
        String cadena="";
        while (i<24){
            int randomNum = (int)(Math.random() * 10);
            //cbu.add(randomNum);
            cadena += String.valueOf(randomNum);
            //String cbuCadena=cadena;
            i++;
            //System.out.print(cbuCadena);
        }
        return cadena;
    }


}
