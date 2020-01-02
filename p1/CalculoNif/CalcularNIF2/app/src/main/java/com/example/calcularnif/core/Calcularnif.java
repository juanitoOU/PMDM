package com.example.calcularnif.core;

public class Calcularnif {
    public static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static char asignarLetra (int DNI){

        return NIF_STRING_ASOCIATION.charAt(DNI % 23);
    }

}
