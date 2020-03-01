package com.juvera.androidcountry.core;

public class CountryInfo {
    public static CountryInfo INVALID = new CountryInfo( "", "", "", "");
    private static final String NotApplicable = "N/A";

    public CountryInfo(String pais, String provincia, String ciudad, String barrio){

        if ( pais == null
                || pais.isEmpty() )
        {
            pais = NotApplicable;
        }

        if ( provincia == null
                || provincia.isEmpty() )
        {
            provincia = NotApplicable;
        }

        if ( ciudad == null
                || ciudad.isEmpty() )
        {
            ciudad = NotApplicable;
        }

        if ( barrio == null
                || barrio.isEmpty() )
        {
            barrio = NotApplicable;
        }



        this.pais = pais.trim();
        this.provincia = provincia.trim();
        this.ciudad = ciudad.trim();
        this.barrio = barrio.trim();


    }

    public String getPais() {
        return pais;
    }


    public String getProvincia() {
        return provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getBarrio() {
        return barrio;
    }


 @Override
    public String toString(){
        return (this.pais + "" + this.provincia + "" + this.ciudad + "" + this.barrio);
    }



    private String pais;
    private String provincia;
    private String ciudad;
    private String barrio;

}
