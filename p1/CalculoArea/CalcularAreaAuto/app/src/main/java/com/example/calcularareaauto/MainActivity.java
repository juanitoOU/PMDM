package com.example.calcularareaauto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etLargo;
    EditText etAncho;
    TextView tvLargo;
    TextView tvAncho;
    TextView tvArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etLargo = findViewById(R.id.editText1);
        etAncho = findViewById(R.id.editText2);
        
        etLargo.setOnKeyListener(new View.OnKeyListener() {
                                     public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                                         MainActivity.this.calculoArea();
                                         return false;
                                     }
                                 });
        etAncho.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                MainActivity.this.calculoArea();
                return false;
            }
        });
        etLargo = findViewById(R.id.editText1);
        etAncho = findViewById(R.id.editText2);
        tvArea = findViewById(R.id.textView3);
    }

    public void calculoArea() {

        try{
            String largo = etLargo.getText().toString();
            String ancho = etAncho.getText().toString();
            float num1 = Float.parseFloat(largo);
            float num2 = Float.parseFloat(ancho);
            float resultado = num1 * num2 ;

            String area = String.valueOf(resultado);
            tvArea.setText(area+" m2");
        }catch (NumberFormatException fmt){
            tvArea.setText("introduzca los datos");
        }


    }
}
