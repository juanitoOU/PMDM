package com.example.ejerciciowatertraining.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejerciciowatertraining.R;
import com.example.ejerciciowatertraining.core.Entrenamiento;
import com.example.ejerciciowatertraining.core.ListaEntrenamientos;

public class EntrenamientoEditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_edition);

        final Button btGuardar = (Button) this.findViewById(R.id.btGuardar);
        final Button btCancelar = (Button) this.findViewById(R.id.btCancelar);
        final EditText edDistancia = (EditText) this.findViewById(R.id.pistaDistancia);
        final EditText edTiempo = (EditText) this.findViewById(R.id.pistaTiempo);

        final ListaEntrenamientos app = (ListaEntrenamientos) this.getApplication();

        Intent datosEnviados = this.getIntent();

        final int pos = datosEnviados.getExtras().getInt( "pos" );

        float min = 0;
        float distance = 0;

        if(pos >= 0){
            min = app.getItemList().get( pos ).getMin();
            distance = app.getItemList().get( pos ).getDistance();
        }

        edTiempo.setText(Float.toString(min));
        edDistancia.setText((Float.toString(distance)));


        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntrenamientoEditionActivity.this.setResult( Activity.RESULT_CANCELED );
                EntrenamientoEditionActivity.this.finish();
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final float min = Float.parseFloat(edTiempo.getText().toString());
                final float distance = Float.parseFloat( edDistancia.getText().toString() );
                if ( pos >= 0 ) {
                    app.modifyEntrenamiento( pos, min, distance );
                } else {
                    app.addEntrenamiento( min, distance );
                }
                EntrenamientoEditionActivity.this.setResult( Activity.RESULT_OK );
                EntrenamientoEditionActivity.this.finish();
            }
        });
        btGuardar.setEnabled( false );



        edTiempo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float min = 0;

                try {
                    min = Float.parseFloat( edTiempo.getText().toString() );
                } catch(NumberFormatException exc) {
                    Log.w( "ItemEditionActivity", "edtiempo no puede ser convertido a número" );
                }

                btGuardar.setEnabled( min > 0 );
            }
        });

        edDistancia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float distance = 0;

                try {
                    distance = Float.parseFloat( edDistancia.getText().toString() );
                } catch(NumberFormatException exc) {
                    Log.w( "ItemEditionActivity", "edDistancia no puede ser convertido a número" );
                }

                btGuardar.setEnabled( distance > 0 );
            }
        });
    }
}
