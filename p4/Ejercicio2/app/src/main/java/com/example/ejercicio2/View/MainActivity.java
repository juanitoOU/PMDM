package com.example.ejercicio2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ejercicio2.Core.Item;
import com.example.ejercicio2.Core.ListaTareas;
import com.example.ejercicio2.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    protected static final int CODIGO_EDICION_ITEM = 100;
    protected static final int CODIGO_ADICION_ITEM = 102;

    public static final String CfgFileName = "ejercicio2.cfg";
    public static final String EtqApp = "ejercicio2";

    ArrayList<Integer> posiciones = new ArrayList<Integer>();

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

                Intent subActividad = new Intent(MainActivity.this, ItemEditionActivity.class);

                subActividad.putExtra("pos", -1);
                MainActivity.this.startActivityForResult(subActividad, CODIGO_ADICION_ITEM);
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
            case R.id.delete: //ELIMINA
                final ListaTareas app = (ListaTareas) this.getApplication();
                app.deleteItem(pos);
                MainActivity.this.itemsAdapter.notifyDataSetChanged();
                toret=true;
                break;
            case R.id.edit: //EDITA
                Intent subActividad = new Intent(MainActivity.this, ItemEditionActivity.class);

                subActividad.putExtra("pos", pos);
                MainActivity.this.startActivityForResult(subActividad, CODIGO_EDICION_ITEM);
                toret = true;
                break;
        }
        return toret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );

        int size = Integer.valueOf(prefs.getString("tamaño","0"));
        final ListaTareas app = (ListaTareas) this.getApplication();

        for(int i = 0; i<size; i++) {
            String nombre = prefs.getString("nombre" + i, "0");
            String fecha = prefs.getString("fecha" + i, "0");
            Item e = new Item(nombre);
            e.setFecha(fecha);

            System.out.println(e.toString());
            app.addItem(nombre, fecha);

        }


        ListView lvItems = this.findViewById(R.id.lvItems);
        Button btInserta = this.findViewById(R.id.btAdd);
        this.registerForContextMenu(lvItems);
        lvItems.setLongClickable(true);
        // Lista

        this.itemsAdapter = new ArrayAdapter<Item>(
                this,
                android.R.layout.simple_selectable_list_item,
                app.getItemList());
        lvItems.setAdapter(this.itemsAdapter);


         // Inserta
        btInserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subActividad = new Intent( MainActivity.this, ItemEditionActivity.class );

                subActividad.putExtra( "pos", -1 );
                MainActivity.this.startActivityForResult( subActividad, CODIGO_ADICION_ITEM );
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_ADICION_ITEM
                && resultCode == Activity.RESULT_OK) {

            this.itemsAdapter.notifyDataSetChanged();

        }

        if (requestCode == CODIGO_EDICION_ITEM
                && resultCode == Activity.RESULT_OK) {

            this.itemsAdapter.notifyDataSetChanged();
        }

        return;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        final ListaTareas app = (ListaTareas) this.getApplication();
        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );

        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("tamaño", String.valueOf(app.getItemList().size()));

        for(int i=0;i<app.getItemList().size();i++){

            edit.putString("nombre" + i, String.valueOf(app.getItemList().get(i).getNombre()));
            edit.putString("fecha" + i, String.valueOf(app.getItemList().get(i).getFecha()));

        }

        edit.apply();




    }



    private ArrayAdapter<Item> itemsAdapter;
    private ArrayList<String> items;

}
