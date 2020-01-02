package com.example.nifboton00.core;

public class CalcularNif {
    public static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static char asignarLetra (int DNI){

        return NIF_STRING_ASOCIATION.charAt(DNI % 23);
    }
}
