package com.juvera.androidcountry.core;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResponseParser {

    private static final String LOG_TAG = "ResponseParser";
    private static final String PAIS_NAME_TAG = "countryCode";
    private static final String PROVINCIA_TAG = "adminName1";
    private static final String CIUDAD_TAG = "adminName2";
    private static final String BARRIO_TAG = "placeName";


    /** Creates a new ResponseParser, given the InputStream from the connection */
    public ResponseParser(InputStream is)
    {
        this.parse( is );
    }

    /** @return a new DataDateTime object holding the info. */
    public CountryInfo getCountryInfo()
    {
        return this.data;
    }

    /** Parses the info from the given input stream. */
    private void parse(InputStream is)
    {
        String pais = "";
        String provincia = "";
        String ciudad = "";
        String barrio = "";
        String codigoPostal = "";

        try {
            Log.d(LOG_TAG, " in doInBackground(): querying");
            JSONObject json = new JSONObject( readAllStream( is ) );
            Log.d(LOG_TAG, " in doInBackground(): content fetched: " + json.toString( 4 ));

            // Get basic country info
            pais = json.getString( PAIS_NAME_TAG );
            provincia = json.getString( PROVINCIA_TAG );

            // ciudad y barrio info
            ciudad = json.getString( CIUDAD_TAG );
            barrio = json.getString( BARRIO_TAG );



            this.data = new CountryInfo( pais, provincia, ciudad, barrio );
        } catch(JSONException exc) {
            Log.e( LOG_TAG, " in parse(): " + exc.getMessage() );
            this.data = CountryInfo.INVALID;
        }
    }

    /** @return the whole stream contents in a single string. */
    private static String readAllStream(InputStream is)
    {
        BufferedReader reader = null;
        StringBuilder toret = new StringBuilder();
        String line;

        try {
            reader = new BufferedReader( new InputStreamReader( is ) );

            while( ( line = reader.readLine() ) != null ) {
                toret.append( line );
            }
        } catch (IOException e) {
            Log.e( LOG_TAG, " in getStringFromString(): error converting net input to string"  );
        } finally {
            Util.close( is, LOG_TAG, "readAllStream" );
        }

        return toret.toString();
    }



    private CountryInfo data;
}
