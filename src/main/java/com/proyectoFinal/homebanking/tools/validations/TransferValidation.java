package com.proyectoFinal.homebanking.tools.validations;

public class TransferValidation {

    public static String validateTransferAccountId(Long originAccount, Long targetAccount){
        if(!ControllerValidation.isPositive(originAccount) && (!ControllerValidation.isPositive(targetAccount))){
            return "Cuenta destino y cuenta de origen invalidas.";
        }else if(!ControllerValidation.isPositive(originAccount)){
            return "Ingrese una cuenta de origen valida";
        }else{
            return "Ingrese una cuenta de destino valida";
        }
    }
}
