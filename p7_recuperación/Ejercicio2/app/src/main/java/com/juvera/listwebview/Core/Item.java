package com.juvera.listwebview.Core;

public class Item {
    private int codigo;
    private String nombre;
    private String cantidad;

    public Item(String codigo, String nombre,String cantidad){
        this.codigo= Integer.valueOf(codigo);
        this.nombre = nombre;
        this.cantidad=cantidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
