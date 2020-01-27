package com.example.ejerciciowatertraining.core;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class ListaEntrenamientos extends Application {

    public void onCreate() {
        super.onCreate();
        this.entrenamientos = new ArrayList<>();
    }

    public List<Entrenamiento> getItemList() {
        return this.entrenamientos;
    }

    public void addEntrenamiento(float min, float distance) {
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setMin( min );
        entrenamiento.setDistance( distance );
        this.entrenamientos.add( entrenamiento );
    }
    public void modifyEntrenamiento(int pos, float min, float distance ) {
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setMin( min );
        entrenamiento.setDistance( distance );
        this.entrenamientos.set( pos, entrenamiento );
    }
    public void deleteEntrenamiento(int pos){
        this.entrenamientos.remove( pos );
    }


    private List<Entrenamiento> entrenamientos;
    private int pos;
}
