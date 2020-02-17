package com.example.listacontactossql.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.listacontactossql.R;
import com.example.listacontactossql.bd.DBManager;


public class ItemEditionActivity extends AppCompatActivity {


    Button btGuardar;
    Button btCancelar;
    EditText edNombre;
    EditText edApellidos;
    EditText edTelefono;
    EditText edEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_item_edition);

       btGuardar = this.findViewById(R.id.btGuardar);
       btCancelar = this.findViewById(R.id.btCancelar);
       edNombre = this.findViewById(R.id.edNombre);
       edApellidos = this.findViewById(R.id.edApellidos);
       edTelefono = this.findViewById(R.id.edTelefono);
       edEmail = this.findViewById(R.id.edEmail);

        final Intent datosEnviados = this.getIntent();
        final Integer id = datosEnviados.getExtras().getInt( "_id", -1 );
        final String nombre = datosEnviados.getExtras().getString( "nombre", "ERROR" );
        final String apellidos = datosEnviados.getExtras().getString( "apellidos", "ERROR" );
        final int telefono = datosEnviados.getExtras().getInt( "telefono", -1 );
        final String email = datosEnviados.getExtras().getString( "email", "ERROR" );


    edNombre.setText(nombre);
    edApellidos.setText(apellidos);
    edTelefono.setText(String.valueOf(telefono));
    edEmail.setText(email);


        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemEditionActivity.this.setResult( Activity.RESULT_CANCELED );
                ItemEditionActivity.this.finish();
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombre = edNombre.getText().toString();
                final String apellidos = edApellidos.getText().toString();
                final int telefono = Integer.parseInt( edTelefono.getText().toString() );
                final String email = edEmail.getText().toString();
                final Intent retData = new Intent();

                retData.putExtra( "_id", id );
                retData.putExtra( "nombre", nombre );
                retData.putExtra( "apellidos", apellidos );
                retData.putExtra( "telefono", telefono );
                retData.putExtra( "email", email );

                if(nombre != null && apellidos != null && telefono != 0){
                    ItemEditionActivity.this.setResult( Activity.RESULT_OK, retData );
                    ItemEditionActivity.this.finish();
                }
                else{
                    AlertDialog.Builder builder = new
                            AlertDialog.Builder(ItemEditionActivity.this);
                    builder.setTitle( "ERROR" );
                    builder.setMessage("Hay campos vacios");
                    builder.create().show();
                }



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
                btGuardar.setEnabled( edNombre.getText().toString().trim().length() > 0 && edApellidos.getText().toString().trim().length() > 0 && edTelefono.getText().toString().trim().length() > 0 );
            }
        });
        edApellidos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btGuardar.setEnabled( edNombre.getText().toString().trim().length() > 0 && edApellidos.getText().toString().trim().length() > 0 && edTelefono.getText().toString().trim().length() > 0 );
            }
        });
        edTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btGuardar.setEnabled( edNombre.getText().toString().trim().length() > 0 && edApellidos.getText().toString().trim().length() > 0 && edTelefono.getText().toString().trim().length() > 0 );
            }
        });
    }


}
