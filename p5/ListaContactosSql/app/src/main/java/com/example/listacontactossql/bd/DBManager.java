package com.example.listacontactossql.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

    public static final String DB_NOMBRE = "ListaContactos";
    public static final int DB_VERSION = 1;

    public static final String TABLA_CONTACTOS = "contactos";
    public static final String _id = "_id";
    public static final String CONTACTOS_COL_NOMBRE = "nombre";
    public static final String CONTACTOS_COL_APELLIDOS = "apellidos";
    public static final String CONTACTOS_COL_TELF = "telefono";
    public static final String CONTACTOS_COL_EMAIL = "email";

    public DBManager(Context context)
    {
        super( context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(  "DBManager",
                "Creando BBDD " + DB_NOMBRE + " v" + DB_VERSION);

        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS " + TABLA_CONTACTOS + "( "
                    + _id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CONTACTOS_COL_NOMBRE + " string(255) NOT NULL, "
                    + CONTACTOS_COL_APELLIDOS + " string(255) NOT NULL, "
                    + CONTACTOS_COL_TELF + " int  NOT NULL, "
                    + CONTACTOS_COL_EMAIL + " string(255)) ");

            db.setTransactionSuccessful();
        }
        catch(SQLException exc)
        {
            Log.e( "DBManager.onCreate", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(  "DBManager",
                "DB: " + DB_NOMBRE + ": v" + oldVersion + " -> v" + newVersion );

        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS " + TABLA_CONTACTOS );
            db.setTransactionSuccessful();
        }  catch(SQLException exc) {
            Log.e( "DBManager.onUpgrade", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }

    /** Devuelve todas los contactos en la BD
     * @return Un Cursor con los contactos. */
    public Cursor getContactos()
    {
        return this.getReadableDatabase().query( TABLA_CONTACTOS,
                null, null, null, null, null, null );
    }


    /** Inserta un nuevo item.
     * @param nombre El nombre del Contacto.
     * @param telefono El numero de telefono.
     * @param apellidos los apellidos del Contacto.
     * @param email El email del Contacto.
     * @return true si se pudo insertar (o modificar), false en otro caso.
     */
    public boolean insertaItem(String nombre , String apellidos, int telefono, String email)
    {
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( CONTACTOS_COL_NOMBRE, nombre );
        values.put( CONTACTOS_COL_APELLIDOS, apellidos );
        values.put( CONTACTOS_COL_TELF, telefono );
        values.put( CONTACTOS_COL_EMAIL, email );

        try {
            db.beginTransaction();
           cursor = db.query( TABLA_CONTACTOS,
                    null,
                    null,
                    null, null, null, null );

                db.insert( TABLA_CONTACTOS, null, values );

            db.setTransactionSuccessful();
            toret = true;
        } catch(SQLException exc)
        {
            Log.e( "DBManager.inserta", exc.getMessage() );
        }
        finally {
            if ( cursor != null ) {
                cursor.close();
            }

            db.endTransaction();
        }

        return toret;
    }


    public boolean editaItem (Integer id, String nombre , String apellidos, int telefono, String email){
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( _id, id );
        values.put( CONTACTOS_COL_NOMBRE, nombre );
        values.put( CONTACTOS_COL_APELLIDOS, apellidos );
        values.put( CONTACTOS_COL_TELF, telefono );
        values.put( CONTACTOS_COL_EMAIL, email );

        try {
            db.beginTransaction();

            db.update(TABLA_CONTACTOS,
                    values, _id + "=?", new String[]{String.valueOf(id)});

            db.setTransactionSuccessful();
            toret = true;
        } catch(SQLException exc)
        {
            Log.e( "DBManager.inserta", exc.getMessage() );
        }

        finally {
            if ( cursor != null ) {
                cursor.close();
            }

            db.endTransaction();
        }

        return toret;
    }


    /** Elimina un elemento de la base de datos
     * @param id El identificador del elemento.
     * @return true si se pudo eliminar, false en otro caso.
     */
    public boolean eliminaItem(Integer id)
    {
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete( TABLA_CONTACTOS, _id + "=?", new String[]{ String.valueOf(id) } );
            db.setTransactionSuccessful();
            toret = true;
        } catch(SQLException exc) {
            Log.e( "DBManager.elimina", exc.getMessage() );
        } finally {
            db.endTransaction();
        }

        return toret;
    }

}
