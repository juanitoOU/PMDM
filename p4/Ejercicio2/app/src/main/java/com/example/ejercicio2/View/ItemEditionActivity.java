package com.example.ejercicio2.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejercicio2.Core.ListaTareas;
import com.example.ejercicio2.R;

import java.util.Calendar;

public class ItemEditionActivity extends AppCompatActivity {

    DatePickerDialog date;
    Button btFecha;
    TextView lblFecha;
    Button btGuardar;
    Button btCancelar;
    EditText edNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edition);

        btGuardar = this.findViewById(R.id.btGuardar);
        btCancelar = this.findViewById(R.id.btCancelar);
        edNombre = this.findViewById(R.id.edNombre);
        lblFecha= this.findViewById((R.id.textView1));
        btFecha= findViewById(R.id.btfecha);

        final ListaTareas app = (ListaTareas) this.getApplication();

        Intent datosEnviados = this.getIntent();
        final int pos = datosEnviados.getExtras().getInt("pos");

        String nombre= "";
        String fecha="";

        if ( pos >= 0 ) {
            nombre = app.getItemList().get( pos ).getNombre();
            fecha = app.getItemList().get( pos ).getFecha();
        }
        edNombre.setText( nombre );
        lblFecha.setText(fecha);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemEditionActivity.this.setResult( Activity.RESULT_CANCELED );
                ItemEditionActivity.this.finish();
            }
        });



       lblFecha.setInputType(InputType.TYPE_NULL);
        btFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                date = new DatePickerDialog(ItemEditionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                lblFecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                date.show();
            }
        });
        btGuardar= findViewById(R.id.btGuardar);
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = edNombre.getText().toString();
                final String fecha = lblFecha.getText().toString();
                lblFecha.setText("Selected Date: "+ lblFecha.getText());
                if ( pos >= 0 ) {
                    app.modifyItem( pos, nombre, fecha );
                } else {
                    app.addItem( nombre, fecha );
                }
                if(nombre!=null || fecha!= null){
                ItemEditionActivity.this.setResult( Activity.RESULT_OK );
                }else{

                }
                ItemEditionActivity.this.finish();
            }
        });
        btGuardar.setEnabled( false );

        edNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btGuardar.setEnabled( edNombre.getText().toString().trim().length() > 0 );
            }
        });

    }
}