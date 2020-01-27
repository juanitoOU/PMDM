package com.example.ejerciciowatertraining.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ejerciciowatertraining.R;
import com.example.ejerciciowatertraining.core.Entrenamiento;
import com.example.ejerciciowatertraining.core.ListaEntrenamientos;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EtqApp = "ejercicio3";
    public static final String CfgFileName = "ejercicio3.cfg";
    protected static final int CODIGO_EDICION_ITEM = 100;
    protected static final int CODIGO_ADICION_ITEM = 102;
    protected static final int CODIGO_STATS_ITEM = 103;




    private ArrayAdapter<Entrenamiento> adaptadorItems;
    DecimalFormatSymbols dfs;
    DecimalFormat formato = new DecimalFormat("#0.00");
    final ListaEntrenamientos app = (ListaEntrenamientos) this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );

        int size = Integer.valueOf(prefs.getString("tamaño","0"));
        final ListaEntrenamientos app = (ListaEntrenamientos) this.getApplication();

        for(int i = 0; i<size; i++) {
            String distancia = prefs.getString("distancia" + i, "0");
            String tiempo = prefs.getString("tiempo" + i, "0");
            Entrenamiento e = new Entrenamiento();
            e.setDistance(Float.valueOf(distancia));
            e.setMin(Float.valueOf(tiempo));
            System.out.println(e.toString());
            app.addEntrenamiento(Float.valueOf(distancia), Float.valueOf(tiempo));

        }

        ListView lvLista = (ListView) this.findViewById(R.id.lista);
        Button btInserta = (Button) this.findViewById(R.id.btAñadir);



        // LISTA
        this.adaptadorItems = new ArrayAdapter<Entrenamiento>(
                this,
                android.R.layout.simple_selectable_list_item,
                app.getItemList());
        lvLista.setAdapter(this.adaptadorItems);

        this.registerForContextMenu(lvLista);
        lvLista.setLongClickable(true);

        // INSERTA
        btInserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subActividad = new Intent( MainActivity.this, EntrenamientoEditionActivity.class );

                subActividad.putExtra( "pos", -1 );
                MainActivity.this.startActivityForResult( subActividad, CODIGO_ADICION_ITEM );
            }
        });

    }
    @Override
    public void onPause()
    {
        super.onPause();
        final ListaEntrenamientos app = (ListaEntrenamientos) this.getApplication();
        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );

        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("tamaño", String.valueOf(app.getItemList().size()));

        for(int i=0;i<app.getItemList().size();i++){

            edit.putString("distancia" + i, String.valueOf(app.getItemList().get(i).getDistance()));
            edit.putString("tiempo" + i, String.valueOf(app.getItemList().get(i).getMin()));

        }

        edit.apply();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean toret = false;
        switch (menuItem.getItemId()) {
            case R.id.opAdd: // Inserta

                Intent subActividad = new Intent(MainActivity.this, EntrenamientoEditionActivity.class);
                subActividad.putExtra("pos", -1);
                MainActivity.this.startActivityForResult(subActividad, CODIGO_ADICION_ITEM);

                toret = true;
                break;

            case R.id.opStats:
                subActividad = new Intent(MainActivity.this,StatsActivity.class);
                subActividad.putExtra("pos", -1);
                MainActivity.this.startActivityForResult(subActividad, CODIGO_STATS_ITEM);

                toret = true;
                break;


        }
        return toret;
    }

    @Override
    public void onCreateContextMenu(ContextMenu cm, View v, ContextMenu.ContextMenuInfo cmi) {
        this.getMenuInflater().inflate(R.menu.context_menu, cm);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        boolean toret = false;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        int pos = info.position;
        switch (menuItem.getItemId()) {

            case R.id.delete:
                final ListaEntrenamientos app = (ListaEntrenamientos) this.getApplication();
                app.deleteEntrenamiento(pos);
                MainActivity.this.adaptadorItems.notifyDataSetChanged();
                toret=true;
                break;

            case R.id.edit:  // Modifica

                Intent subActividad = new Intent(MainActivity.this, EntrenamientoEditionActivity.class);

                subActividad.putExtra("pos", pos);
                MainActivity.this.startActivityForResult(subActividad, CODIGO_EDICION_ITEM);

                toret = true;


                break;
        }
        return toret;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_ADICION_ITEM
                && resultCode == Activity.RESULT_OK) {

            this.adaptadorItems.notifyDataSetChanged();
        }

        if (requestCode == CODIGO_EDICION_ITEM
                && resultCode == Activity.RESULT_OK) {

            this.adaptadorItems.notifyDataSetChanged();
        }


        return;
    }












}
