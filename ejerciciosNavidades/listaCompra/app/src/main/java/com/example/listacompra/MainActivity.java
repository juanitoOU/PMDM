package com.example.listacompra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final String CfgFileName = "listaCompra.cfg";
    public static final String EtqApp = "listaCompra";

    ArrayList<Integer> posiciones = new ArrayList<Integer>();
    String aux="";
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> items;

//    MENU PRINCIPAL

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.main_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean toret = false;
        switch (menuItem.getItemId()) {
            case R.id.Add:
                MainActivity.this.addItem();
                toret = true;
                break;
        }
        return toret;
    }

//      MENU CONTEXTUAL
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View
            view, ContextMenu.ContextMenuInfo cmi) {
        if (view.getId() == R.id.lvitems) {
            this.getMenuInflater().inflate(R.menu.context_menu,
                    contextMenu);
            contextMenu.setHeaderTitle(R.string.app_name);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        boolean toret = false;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        final int pos = info.position;
        switch( menuItem.getItemId() ) {
            case R.id.editar:
                final EditText editText = new EditText(MainActivity.this);
                String texto = MainActivity.this.items.get(pos).toString();
                final String[] partes = texto.split(Pattern.quote("(") );
                editText.setText(partes[0]);
                if(pos>=0){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Modificar");
                    builder.setView(editText);
                    builder.setNegativeButton("Cancelar",null);
                    builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            introducirFecha2(editText.getText().toString(),pos,partes[1]);
                        }
                    });
                    builder.create().show();
                }
                toret = true;
                break;
            case R.id.eliminar:
                MainActivity.this.removeItem(pos);
                toret = true;
                break;
        }
        return toret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.items = new ArrayList<String>();
        Button btAdd = (Button) this.findViewById(R.id.btAdd);
        ListView lvItems = (ListView) this.findViewById(R.id.lvitems);
        this.registerForContextMenu(lvItems);

        this.itemsAdapter = new ArrayAdapter<String>(
                this.getApplicationContext(),
                android.R.layout.simple_selectable_list_item,
                this.items);
        lvItems.setAdapter( this.itemsAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.addItem();
            }
        });
    }



//      METODO PARA GUARDAR EL ESTADO DE LA APLICACION, SIEMPRE QUE NO LA DESTRUYAMOS DE GOLPE(APAGAR EL DISPOSITIVO)
    private void saveState(){
        try (FileOutputStream f = this.openFileOutput( CfgFileName, Context.MODE_PRIVATE ) )
        {
            PrintStream cfg = new PrintStream( f );

            int i = 0;
            for(String s: this.items) {
                cfg.println(this.items.get(i) );
                i++;

            }

            cfg.close();
        }
        catch(IOException exc) {
            Log.e( EtqApp, "Error saving state" );
        }


        System.out.println( "Saved state..." );
    }


//      METODO PARARECUPERAR EL ESTADO DE LA APLICACIÓN, SIEMPRE QUE NO LA HAYAMOS DESTRUIDO DE GOLPE(APAGAR EL DISPOSITIVO)
    private void loadState() {
        try (FileInputStream f = this.openFileInput( CfgFileName  ) )
        {
            BufferedReader cfg = new BufferedReader( new InputStreamReader( f ) );

            this.items.clear();
            String line = cfg.readLine();
            while( line != null ) {

                items.add(line);

                line = cfg.readLine();
            }

            cfg.close();
        }
        catch (IOException exc)
        {
            Log.e( EtqApp, "Error loading state" );
        }
        System.out.println( "Loaded state..." );
    }


    @Override
    public void onResume() {
        super.onResume();
        this.loadState();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.saveState();
    }





//      ELIMINAR ELEMENTO PASÁNDOLE UNA POSICIÓN DEL ARRAYLIST<STRING> ITEMS
    private void removeItem (int pos){
        MainActivity.this.items.remove(pos);
        MainActivity.this.itemsAdapter.notifyDataSetChanged();
    }


//      AÑADIR UN ELEMENTO A LA LISTA
    private void addItem (){

        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
        final EditText edText = new EditText( this );
        builder.setTitle( "Nueva tarea" );
        builder.setMessage( "Que desea Apuntar" );
        builder.setView( edText );
        builder.setPositiveButton( "+",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!edText.getText().toString().isEmpty()){
                    introducirFecha(edText.getText().toString());
                } else {
                    vacio();
                }
            }
        });
        builder.setNegativeButton( "Cancelar", null );
        builder.create().show();
    }


//      COMPROBAR QUE NO SE INSERTAN ELEMENTOS VACIOS EN LA LISTA
    private void vacio (){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder( this );
        builder.setTitle("Tarea vacia");
        builder.setMessage( "No es posible añadir una tarea vacia" );
        builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.addItem();
            }
        });
        builder.setNegativeButton("Aceptar",null);
        builder.create().show();
    }


//      SELECCIONAR LA FECHA DE CADUCIDAD DEL ELEMENTO AL AÑADIR UNO NUEVO
   private void introducirFecha(final String texto){
        Calendar now = Calendar.getInstance();

        int anho=now.get(Calendar.YEAR);
        int mes= now.get(Calendar.MONTH);
        int dia = now.get(Calendar.DATE);

        final DatePickerDialog date = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                aux = day + "/" + (month + 1) + "/" + year;
                MainActivity.this.itemsAdapter.add(texto + "(" + aux + ")");
            }
        },
                anho,mes,dia
        );
        date.show();
   }


//        MODIFICA LA FECHA EXISTENTE , SE USA PARA EDITAR UN ELEMENTO
    private void introducirFecha2 (final String texto,final int pos, String texto2){
        String[] partes = texto2.split("/");
        int a,b,c;
        a=Integer.valueOf(partes[0]);
        b=Integer.valueOf(partes[1]);
        String c2 = partes[2].substring(0,partes[2].length()-1);
        c=Integer.valueOf(c2);
        final DatePickerDialog date = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                aux = day + "/" + (month + 1) + "/" + year;
                MainActivity.this.items.set(pos,texto + " (" + aux +")");
                MainActivity.this.itemsAdapter.notifyDataSetChanged();
            }
        },
                c,b,a

        );
        date.show();
    }


//      COMPRUEBA SI HAY ELEMeNTOS CADUCADOS
    private void caducados(int aux){
        posiciones.clear();
        Calendar now = Calendar.getInstance();
        int anho= now.get(Calendar.YEAR);
        int mes= now.get(Calendar.MONTH);
        int dia = now.get(Calendar.DATE);

        for (int i = aux; i<items.size();i++){
            String[] partes = items.get(i).split(Pattern.quote("(") );
            String[] partes2 = partes[1].split("/");
            int a,b,c;
            a=Integer.valueOf(partes2[0]);
            System.out.println("+++++++" + a + "--------");
            b=Integer.valueOf(partes2[1]);
            System.out.println("+++++++" + b + "--------");
            String c2="";
            for(int j=0;j<partes2[2].length()-1;j++){
                c2=c2 + partes2[2].charAt(j);
            }
            c=Integer.valueOf(c2);
            System.out.println("+++++++" + c + "--------");
            if (c<anho){
                System.out.println("+++++++" + "Entro anho" + "--------");
                posiciones.add(i);
            } else if(b<mes){
                System.out.println("+++++++" + "Entro mes" + "--------");
                posiciones.add(i);
            } else if(a<dia){
                System.out.println("+++++++" + "Entro dia" + "--------");
                posiciones.add(i);
            }
        }
        System.out.println("+++++++" + posiciones.toString() + "--------");
        alerta(0);
    }


//      ALERTA CUANDO FINALIZA EL PLAZO ESTABLECIDO PARA UNA TAREA
    private void alerta(final int aux){
        System.out.println("aux="+ aux);
        System.out.println("Tamaño = "+ posiciones.size());
        if (aux<posiciones.size()){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder( this );
            builder.setTitle("Tarea Caducada");
            String[] texto =  MainActivity.this.items.get(posiciones.get(aux)).split(Pattern.quote("(") );
            builder.setMessage( "Su tarea \"" + texto[0] + "\" ha caducado.");
            builder.setPositiveButton("Borrar Tarea", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    removeItem(posiciones.get(aux));
                    caducados(posiciones.get(aux));
                }
            });
            builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alerta(aux+1);
                }
            });
            builder.create().show();
        }
    }



}
