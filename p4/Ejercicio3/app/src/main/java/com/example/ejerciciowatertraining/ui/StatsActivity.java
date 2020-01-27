package com.example.ejerciciowatertraining.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejerciciowatertraining.R;
import com.example.ejerciciowatertraining.core.ListaEntrenamientos;

import java.text.DecimalFormat;

public class StatsActivity extends AppCompatActivity {
    DecimalFormat formato = new DecimalFormat("#0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_entrenamiento_stats );

        final EditText etStats = (EditText) this.findViewById(R.id.stats);
        final Button btCancelar = (Button) this.findViewById( R.id.btCancelar );
        final ListaEntrenamientos app = (ListaEntrenamientos) this.getApplication();

        Intent datosEnviados = this.getIntent();

        final int pos = datosEnviados.getExtras().getInt( "pos" );

        app.getItemList();
        float totalKm = 0;
        float totalMinKm = 0;
        for (int i = 0; i < app.getItemList().size(); i++) {
            totalKm = totalKm + app.getItemList().get(i).getDistance();
            totalMinKm = totalMinKm + app.getItemList().get(i).getMinKm();
        }
        totalMinKm = totalMinKm / app.getItemList().size();
        etStats.setText("Usted a recorrido un total de " + formato.format(totalKm) + " Km entre todos sus entrenamientos y además tiene una media de " + formato.format(totalMinKm) + " minutos por cada Km.");

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               StatsActivity.this.setResult( Activity.RESULT_CANCELED );
                StatsActivity.this.finish();
            }
        });

    }
}
