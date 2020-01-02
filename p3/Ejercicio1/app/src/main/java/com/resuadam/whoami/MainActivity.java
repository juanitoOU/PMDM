package com.resuadam.whoami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.resuadam.whoami.R;
import com.resuadam.whoami.DatosPersonales;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final EditText edNombre = (EditText) this.findViewById( R.id.edNombre );
        final EditText edApellidos = (EditText) this.findViewById( R.id.edApellidos );
        final EditText edEmail = (EditText) this.findViewById( R.id.edEmail );
        final EditText edDireccion = (EditText) this.findViewById( R.id.edDireccion );
        final EditText edTelefono = (EditText) this.findViewById( R.id.edTelefono );

        edNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonalesExtendidos.setNombre( edNombre.getText().toString() );
            }
        });

        edApellidos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonalesExtendidos.setApellidos( edApellidos.getText().toString() );
            }
        });

        edDireccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonalesExtendidos.setDireccion( edDireccion.getText().toString() );
            }
        });

        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonalesExtendidos.setEmail( edEmail.getText().toString() );
            }
        });
        edTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonalesExtendidos.setTelefono( edTelefono.getText().toString() );
            }
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );
        String nombre = prefs.getString( "nombre", "" );
        String apellidos = prefs.getString( "apellidos", "" );
        String direccion = prefs.getString( "direccion", "" );
        String email = prefs.getString( "email", "" );
        String telefono = prefs.getString( "telefono", "" );

        this.datosPersonalesExtendidos = new DatosPersonalesExtendidos( nombre, apellidos, email, direccion, telefono);

        final EditText edNombre = (EditText) this.findViewById( R.id.edNombre );
        final EditText edApellidos = (EditText) this.findViewById( R.id.edApellidos );
        final EditText edEmail = (EditText) this.findViewById( R.id.edEmail );
        final EditText edDireccion = (EditText) this.findViewById( R.id.edDireccion );
        final EditText edTelefono = (EditText) this.findViewById( R.id.edTelefono );

        edNombre.setText( this.datosPersonalesExtendidos.getNombre() );
        edApellidos.setText( this.datosPersonalesExtendidos.getApellidos() );
        edDireccion.setText( this.datosPersonalesExtendidos.getDireccion() );
        edEmail.setText( this.datosPersonalesExtendidos.getEmail() );
        edTelefono.setText( this.datosPersonalesExtendidos.getTelefono() );

    }


    @Override
    public void onPause()
    {
        super.onPause();

        SharedPreferences.Editor edit = this.getPreferences( Context.MODE_PRIVATE ).edit();

        edit.putString( "nombre", this.datosPersonalesExtendidos.getNombre() );
        edit.putString( "apellidos", this.datosPersonalesExtendidos.getApellidos() );
        edit.putString( "direccion", this.datosPersonalesExtendidos.getDireccion() );
        edit.putString( "email", this.datosPersonalesExtendidos.getEmail() );
        edit.putString( "telefono", this.datosPersonalesExtendidos.getTelefono() );
        edit.apply();
    }

    private DatosPersonalesExtendidos datosPersonalesExtendidos;
}