package com.juvera.androidcountry.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juvera.androidcountry.R;
import com.juvera.androidcountry.core.CountryInfo;
import com.juvera.androidcountry.core.HttpFetcher;
import com.juvera.androidcountry.core.Observer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements Observer{

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onPause() {
        super.onPause();

        if ( this.timer != null ) {
            this.timer.cancel();
            this.timer.purge();
        }

        return;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        this.setStatus( R.string.status_init );

        final Button btnBuscar = this.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final TextView lblCP = findViewById( R.id.editText1 );
                HttpFetcher.codigoPostal = lblCP.toString();
                if ( chkConnectivity() ) {
                    TimerTask taskFetchTime = new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                new HttpFetcher(MainActivity.this ).execute( new URL( HttpFetcher.COUNTRY_URL ) );
                            } catch(MalformedURLException e)
                            {
                                Log.e( "Timer.run", e.getMessage() );
                                MainActivity.this.setStatus( R.string.status_incorrect_url );
                            }
                        }
                    };

                    // Program the task for every 20 seconds from now on.
                    timer = new Timer();
                    timer.schedule( taskFetchTime, 0, 20000 );
                }
            }
        });


        return;
    }

    public void setCountryInfo(CountryInfo data)
    {

        final TextView lblPais = this.findViewById( R.id.txtPais );
        final TextView lblProvincia = this.findViewById( R.id.txtProvincia );
        final TextView lblCiudad = this.findViewById( R.id.txtCiudad );
        final TextView lblBarrio = this.findViewById( R.id.txtBarrio );


        lblPais.setText( data.getPais() );
        lblProvincia.setText( data.getProvincia() );
        lblCiudad.setText( data.getCiudad() );
        lblBarrio.setText( data.getBarrio() );

    }


    public void setStatus(int msgId)
    {
        final TextView lblStatus = this.findViewById( R.id.lblStatus );

        lblStatus.setText( msgId );
    }

    public void setDefaultValues()
    {
        final String notAvailable = this.getString( R.string.status_not_available );

        this.setCountryInfo( CountryInfo.INVALID );
        this.setStatus( R.string.status_error );
    }


//    public void search(){
//        final CountryInfo info = new CountryInfo();
//        Context context = MainActivity.this.getApplicationContext();
//        LinearLayout ly = new LinearLayout(context);
//        ly.setOrientation(LinearLayout.VERTICAL);
//        final EditText distancia = new EditText(context);
//    }





    private boolean chkConnectivity()
    {
        Log.d( LOG_TAG, "checking connectivity" );
        ConnectivityManager connMgr =
                (ConnectivityManager)  this.getSystemService( Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        boolean connected = ( networkInfo != null && networkInfo.isConnected() );

        if ( !connected ) {
            this.setStatus( R.string.status_not_connected );
        }

        return connected;
    }

    private Timer timer;
}
