package com.example.watertraining.core;

public class Entrenamiento {
    private float min;
    private float distance;

    public Entrenamiento (){
    }

    public float getMinKm (){
        return this.min/this.distance;
    }

    public float getSegKm (){
        return (this.min*60)/this.distance;
    }

    public float getKmHour (){
        return this.distance/(this.min/60);
    }

    @Override
    public String toString(){
        return "Distancia = " + this.distance + " Km y Tiempo = " + this.min + " minutos";
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

}
