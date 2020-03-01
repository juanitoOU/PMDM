package com.juvera.conexionwifi;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.provider.Settings;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    //Declaramos los componentes necesarios para mostrar la información de la conexión de red
    private TextView estadoTV;
    private TextView pingTV;
    private TextView ipTV;
    private TextView velocidadTV;
    private TextView conexionInalambricaTV;
    private TextView estadoDatos;
    private  Switch simpleSwitch;



    //Clase que nos proporcionada la gestión de todos los aspectos de una conexión Wifi
    private WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initiate a Switch
        simpleSwitch = (Switch) findViewById(R.id.simpleWifiSwitch);
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String statusSwitch;
                if (isChecked) {
                    statusSwitch = simpleSwitch.getTextOn().toString();
                    changeStateWifiOn();
                    Toast.makeText(getApplicationContext(), "Switch :" + statusSwitch, Toast.LENGTH_LONG).show(); // display the current state for switch's

                } else {
                    statusSwitch = simpleSwitch.getTextOff().toString();
                    changeStateWifiOff();
                    Toast.makeText(getApplicationContext(), "Switch :" + statusSwitch, Toast.LENGTH_LONG).show(); // display the current state for switch's

                }
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //Enlazamos los componentes con los recursos definidos a nivel de layout.
        estadoTV = (TextView)findViewById(R.id.txtEstado);
        pingTV = (TextView)findViewById(R.id.txtPing);
        ipTV = (TextView)findViewById(R.id.txtIP);
        velocidadTV = (TextView)findViewById(R.id.txtVelocidad);
        conexionInalambricaTV = (TextView)findViewById(R.id.txtNombre);
        estadoDatos = (TextView)findViewById(R.id.txtEstadoDatos);
        wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

    }

    //Evento On Click que reiniciará los valores de cada componente TextView definido.
    public void reiniciarValores(View view)
    {
        try
        {
            estadoTV.setTextColor(0xFF000000);
            estadoTV.setText(R.string.sin_estado);
            pingTV.setTextColor(0xFF000000);
            pingTV.setText(R.string.sin_ping);
            ipTV.setTextColor(0xFF000000);
            ipTV.setText(R.string.sin_ip);
            velocidadTV.setTextColor(0xFF000000);
            velocidadTV.setText(R.string.sin_velocidad);
            conexionInalambricaTV.setTextColor(0xFF000000);
            conexionInalambricaTV.setText(R.string.sin_nombre);
            estadoDatos.setText(R.string.sin_estado);
            estadoDatos.setTextColor(0xFF000000);
        }catch(Exception ex)
        {
            ex.getMessage();
        }
    }

    //Evento On Click que muestra si hay una conexión establecida mediante WIFI
    public void mostrarEstado(View view)
    {
        try
        {

            //Método que devuelve un valor de verdadero o falso si se ha establecido la conexión Wifi.
            boolean estadoWifi = wifi.isWifiEnabled();
            String estado_conexion;
            if(estadoWifi == true)
            {
                estadoTV.setTextColor(0xFF0000FF);
                estado_conexion = "Conectado!!!";
                estadoTV.setText(estado_conexion);
                Toast.makeText(this, "Estado Conexión: " + estado_conexion, Toast.LENGTH_LONG).show();
            }else
            {
                estadoTV.setTextColor(0xFF0000FF);
                estado_conexion = "Desconectado!!!";
                estadoTV.setText(estado_conexion);
                Toast.makeText(this, "Estado Conexión: " + estado_conexion, Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex)
        {
            ex.getMessage();
        }
    }


    //Evento On Click que comprueba si el dispositivo responde con la IP Local establecida
    public void hacerPing(View view)
    {
        try
        {
            //Método que devuelve un valor de verdadero o falso si responde a la IP Local asignada.
            boolean ip_ping = wifi.pingSupplicant();
            if(ip_ping == true)
            {
                pingTV.setTextColor(0xFF0000FF);
                pingTV.setText("IP con conectividad TCP/IP");
                Toast.makeText(this, "IP con conectividad TCP/IP", Toast.LENGTH_LONG).show();
            }
            else
            {
                pingTV.setTextColor(0xFF0000FF);
                pingTV.setText("IP sin conectividad TCP/IP");
                Toast.makeText(this, "IP sin conectividad TCP/IP", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex)
        {
            ex.getMessage();
        }
    }

    //Evento On Click que muestra la IP Local asignada al dispositivo Android.
    public void mostrarIP(View view)
    {
        try
        {
            @SuppressWarnings("deprecation")
            //Método que devuelve el valor de la IP Local asignada al dispositivo. No soporta IPv6
                    String ip_local =  Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());
            ipTV.setTextColor(0xFF0000FF);
            ipTV.setText(ip_local);
            Toast.makeText(this, "IP Local: " + ip_local, Toast.LENGTH_LONG).show();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Evento On Click que muestra la velocidad de transmisión de datos entre el router y el dispositivo Android
    public void mostrarVelocidad(View view)
    {
        try
        {
            //Método que devuelve el valor de la conexión Wifi, si está activa (si no está activa muestra -1Mbps)
            WifiInfo info = wifi.getConnectionInfo();
            String puertoDatos = String .valueOf(info.getLinkSpeed());
            velocidadTV.setTextColor(0xFF0000FF);
            velocidadTV.setText(puertoDatos + "Mbps");
            Toast.makeText(this, "Velocidad de Red: " + puertoDatos + "Mbps", Toast.LENGTH_LONG).show();

        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    //Evento On CLick encargado de mostrar el identificador de la red Wifi a la que se ha conectado.
    public void mostrarNombreConexion(View view)
    {
        try
        {
            //Método que devuelve el identificador de servicio de la red Wifi 802.11
            String red = wifi.getConnectionInfo().getSSID();

            if(red == null)
            {
                conexionInalambricaTV.setTextColor(0xFF0000FF);
                conexionInalambricaTV.setText("Sin Red");
                Toast.makeText(this, "Prueba de red: " + red, Toast.LENGTH_LONG).show();
            }else
            {
                conexionInalambricaTV.setTextColor(0xFF0000FF);
                conexionInalambricaTV.setText(red);
                Toast.makeText(this, "Prueba de red: " + red, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
    }
    //Evento On Click que muestra si hay una conexión establecida mediante DATOS
    public void mostrarEstadoDatos(View view)
    {
        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        String estado_conexion;

        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
                estadoDatos.setTextColor(0xFF0000FF);
                estado_conexion = "Conectado!!!";
                estadoDatos.setText(estado_conexion);
                Toast.makeText(this, "Estado Conexión: " + estado_conexion, Toast.LENGTH_LONG).show();
            }
            else{
                estadoDatos.setTextColor(0xFF0000FF);
                estado_conexion = "Desconectado";
                estadoDatos.setText(estado_conexion);
                Toast.makeText(this, "Estado Conexión: " + estado_conexion, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.wifi_settings:
                if (id == R.id.wifi_settings) {
                    irAjustesWifi();
                    return true;
                }
                break;
            case R.id.wifi_on:
                if (id == R.id.wifi_on) {
                    changeStateWifiOn();
                    return true;
                }
                break;
            case R.id.wifi_off:
                if (id == R.id.wifi_off) {
                    changeStateWifiOff();
                    return true;
                }
                break;
             }
        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

 //Evento necesario para ir a ajustes del dispositivo y activar wifi
    public void irAjustesWifi(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Configuracion");
        alertDialog.setMessage("Por favor, active el WIFI");
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setPositiveButton("Ajustes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);

                startActivityForResult(intent, 0);
            }
        });
        alertDialog.show();
    }



    public void changeStateWifiOn(){


        simpleSwitch.setChecked(true);
        wifi.setWifiEnabled(true);

        //Método que devuelve un valor de verdadero o falso si se ha establecido la conexión Wifi.
        boolean estadoWifi = wifi.isWifiEnabled();
        String estado_conexion;
        estadoTV.setTextColor(0xFF0000FF);
        estado_conexion = "Conectado!!!";
        estadoTV.setText(estado_conexion);
        Toast.makeText(this, "Estado Conexión: " + estado_conexion, Toast.LENGTH_LONG).show();


    }
    public void changeStateWifiOff(){
        wifi.setWifiEnabled(false);


        simpleSwitch.setChecked(false);
        //Método que devuelve un valor de verdadero o falso si se ha establecido la conexión Wifi.
        boolean estadoWifi = wifi.isWifiEnabled();
        String estado_conexion;
        estadoTV.setTextColor(0xFF0000FF);
        estado_conexion = "Desconectado!!!";
        estadoTV.setText(estado_conexion);
        Toast.makeText(this, "Estado Conexión: " + estado_conexion, Toast.LENGTH_LONG).show();


    }


}
