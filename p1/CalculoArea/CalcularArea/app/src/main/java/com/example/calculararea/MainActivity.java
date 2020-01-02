package com.example.calculararea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Float.parseFloat;

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
        tvLargo = findViewById(R.id.textView1);
        tvAncho = findViewById(R.id.textView2);
        tvArea = findViewById(R.id.textView3);
    }

    public void calculoArea(View view){
        String largo = etLargo.getText().toString();
        String ancho = etAncho.getText().toString();
       try{
            float num1 = Float.parseFloat(largo);
            float num2 = Float.parseFloat(ancho);
            float resultado = num1 * num2 ;

            String area = String.valueOf(resultado);
            tvArea.setText(area+" m2");
        }catch (NumberFormatException fmt){
           tvArea.setText("introduzca bien los datos");
       }


    }

}
