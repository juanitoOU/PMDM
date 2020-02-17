package com.example.listacontactossql.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.listacontactossql.R;
import com.example.listacontactossql.bd.DBManager;

public class MainActivity extends AppCompatActivity {

    protected static final int CODIGO_EDICION_ITEM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvLista = this.findViewById( R.id.lvLista );
        Button btInserta = this.findViewById( R.id.btInserta );

        // Inserta
        btInserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lanzaEditor(  null, "", "", 0, "" );
            }
        });
        this.registerForContextMenu( lvLista );
        this.gestorDB = new DBManager( this.getApplicationContext() );
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Configurar lista
        final ListView lvLista = this.findViewById( R.id.lvLista );

        this.adaptadorDB = new SimpleCursorAdapter(
                this,
                R.layout.lvlista_item,
                null,
                new String[] { DBManager.CONTACTOS_COL_NOMBRE, DBManager.CONTACTOS_COL_APELLIDOS, DBManager.CONTACTOS_COL_TELF,DBManager.CONTACTOS_COL_EMAIL },
                new int[] { R.id.lvLista_Item_Nombre,R.id.lvLista_Item_Apellidos, R.id.lvLista_Item_Telefono, R.id.lvLista_Item_Email}
        );

        lvLista.setAdapter( this.adaptadorDB );
        this.actualizaContactos();

    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.gestorDB.close();
        this.adaptadorDB.getCursor().close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.main_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;
        switch( menuItem.getItemId() ) {
            case R.id.opAdd:
                MainActivity.this. lanzaEditor(  null, "", "", null, "" );
                toret = true;
                break;

        }
        return toret;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu( menu, v, menuInfo );

        if ( v.getId() == R.id.lvLista ) {
            this.getMenuInflater().inflate( R.menu.lista_menu_contextual, menu );
        }

        return;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        boolean toret = super.onContextItemSelected(item);
        int pos = ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position;
        Cursor cursor = this.adaptadorDB.getCursor();

        switch ( item.getItemId() ) {
            case R.id.item_contextual_elimina:
                if ( cursor.moveToPosition( pos ) ) {
                    this.delete(pos);
                    toret = true;
                } else {
                    String msg = this.getString( R.string.msgNoPos ) + ": " + pos;
                    Log.e( "context_elimina", msg );
                    Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
                }

                break;
            case R.id.item_contextual_modifica:
                if ( cursor.moveToPosition( pos ) ) {
                    final Integer id = cursor.getInt( 0 );
                    final String nombre = cursor.getString( 1 );
                    final String apellidos = cursor.getString( 2 );
                    final int telefono = cursor.getInt( 3 );
                    final String email = cursor.getString( 4 );

                    lanzaEditor( id, nombre, apellidos, telefono, email );

                    toret = true;
                } else {
                    String msg = this.getString( R.string.msgNoPos ) + ": " + pos;
                    Log.e( "context_modifica", msg );
                    Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
                }

                break;
        }

        return toret;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent retData)
    {
        if ( resultCode == Activity.RESULT_OK
                && requestCode == CODIGO_EDICION_ITEM )
        {
            Integer id = retData.getExtras().getInt( "_id", -1 );
            String nombre = retData.getExtras().getString( "nombre", "ERROR" );
            String apellidos = retData.getExtras().getString( "apellidos", "ERROR" );
            int telefono = retData.getExtras().getInt( "telefono", -1 );
            String email = retData.getExtras().getString( "email", "ERROR" );

            if (id <= 0){
                this.gestorDB.insertaItem( nombre, apellidos, telefono, email );
            } else {
                this.gestorDB.editaItem(id, nombre, apellidos, telefono, email  );
            }

            this.actualizaContactos();

        }

        return;
    }

    private void delete (final int pos){

            Cursor cursor = this.adaptadorDB.getCursor();
            final int id = cursor.getInt( 0 );



            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Eliminado");
            builder.setMessage("Seguro que quieres borrar el elemento ?");
            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gestorDB.eliminaItem( id );
                    actualizaContactos();

                }
            });
            builder.setNegativeButton("Cancelar", null);
            builder.create().show();
        }



    private void actualizaContactos()
    {
        this.adaptadorDB.changeCursor( this.gestorDB.getContactos() );
    }

    private void lanzaEditor(Integer id, String nombre, String apellidos, Integer telefono, String email)
    {
        Intent subActividad = new Intent( MainActivity.this, ItemEditionActivity.class );

        subActividad.putExtra( "_id", id );
        subActividad.putExtra( "nombre", nombre );
        subActividad.putExtra( "apellidos", apellidos );
        subActividad.putExtra( "telefono", telefono );
        subActividad.putExtra( "email", email );
        MainActivity.this.startActivityForResult( subActividad, CODIGO_EDICION_ITEM );
    }
    private DBManager gestorDB;
    private SimpleCursorAdapter adaptadorDB;
}
