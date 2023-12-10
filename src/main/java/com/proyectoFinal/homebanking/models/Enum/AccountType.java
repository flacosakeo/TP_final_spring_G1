package com.proyectoFinal.homebanking.models.Enum;

public enum AccountType {
    SAVINGS_BANK("Caja de ahorro"),
    DOLLAR_SAVINGS_BANK("Caja de ahorro en dolares"),
    CURRENT_ACCOUNT("Cuenta corriente");

    private final String name;


    AccountType(String name) {
        this.name = name;
    }
}
