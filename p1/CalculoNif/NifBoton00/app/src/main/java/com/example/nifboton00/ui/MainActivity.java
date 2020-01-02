package com.example.nifboton00.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nifboton00.R;
import com.example.nifboton00.core.CalcularNif;

public class MainActivity extends AppCompatActivity {
    EditText edDni;
    TextView lblLetra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    edDni = findViewById(R.id.editableDni);
    lblLetra = findViewById(R.id.lblResult);
    }

    public void calculoDni(View view){
        String numero = edDni.getText().toString();
        int DNI = Integer.parseInt(numero);

        char letra = CalcularNif.asignarLetra(DNI);
    String letter = Character.toString(letra);

    lblLetra.setText(letter);
    }



}
