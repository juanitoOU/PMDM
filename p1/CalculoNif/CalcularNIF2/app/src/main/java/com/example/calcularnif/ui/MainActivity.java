package com.example.calcularnif.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.calcularnif.core.Calcularnif;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText edDni = (EditText) this.findViewById(R.id.editableDni);
        edDni.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                MainActivity.this.calcula();
                return false;
            }
        });
    }
    protected void calcula(){

        EditText edDni = (EditText) this.findViewById(R.id.editableDni);
        TextView lblLetra = (TextView) this.findViewById(R.id.lblResult);

        try{
            int dni = Integer.parseInt(edDni.getText().toString() );
            char letra = Calcularnif.asignarLetra(dni);
            lblLetra.setText(Character.toString(letra) );
        } catch(NumberFormatException fmt)
        {
            lblLetra.setText(R.string.label_default_result);
        }
        return;
}

}
