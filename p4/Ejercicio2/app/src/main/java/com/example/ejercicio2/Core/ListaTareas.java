package com.example.ejercicio2.Core;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class ListaTareas extends Application {

    public void onCreate() {
        super.onCreate();
        this.items = new ArrayList<>();
    }

    public List<Item> getItemList(){return this.items;}

    public void addItem(String nombre, String fecha) {
        Item item = new Item(nombre);
        item.setFecha( fecha );
        this.items.add( item );
    }

    public void modifyItem(int pos, String nombre, String fecha) {
        Item item = new Item( nombre );
        item.setFecha( fecha );
        this.items.set( pos, item );
    }
    public void deleteItem(int pos) {
        this.items.remove(pos);
    }


    private List<Item> items;
    private int pos;
}
