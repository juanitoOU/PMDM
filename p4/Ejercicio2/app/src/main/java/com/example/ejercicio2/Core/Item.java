package com.example.ejercicio2.Core;


import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class Item {
    private String nombre;
    private String fecha;


    public Item (String n){ this.nombre = n;}

    public String getFecha(){return fecha;}

    public void setFecha( String f){this.fecha = f;}

    public String getNombre() { return nombre;}

    @Override
    public String toString()
    {
        return this.getNombre() + ". fecha.: " + this.getFecha();
    }
}
