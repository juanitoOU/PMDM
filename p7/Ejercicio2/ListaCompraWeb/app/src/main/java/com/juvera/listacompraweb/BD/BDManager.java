package com.juvera.listacompraweb.BD;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.juvera.listacompraweb.Item;

public class BDManager extends SQLiteOpenHelper {

    public static final String BD_NOMBRE="ListaCompra";
    public static final int DB_VERSION = 1;
    public static final String ID="id";
    public static final String NAME = "name";
    public static final String CANTIDAD="cantidad";
    public static final String TABLA_ITEMS ="listaCompra";


    public BDManager(Context c){
        super(c, BD_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("DBManager", "Creando BD " + BD_NOMBRE + " v" + DB_VERSION);
        try{
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_ITEMS + "(" + ID + " int PRIMARY KEY," + NAME + " string(60) NOT NULL," + CANTIDAD + " int NOT NULL)");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBManager", "DB" + BD_NOMBRE + ": v" + oldVersion + "-> v" + newVersion);
        try{
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_ITEMS);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        this.onCreate(db);
        db.close();
    }

    public String getCodes(){
        String codes ="";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ID + " FROM " + TABLA_ITEMS, null);

        while(cursor.moveToNext()){
            codes = codes + cursor.getInt(0) + ".";
        }

        cursor.close();

        if (codes.length()!=0){
            codes = codes.substring(0, codes.length()-1);
        }


        System.out.println("+++++++++" + codes);

        return codes;
    }

    public String getNames(){
        String names = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + NAME + " FROM " + TABLA_ITEMS, null);

        while (cursor.moveToNext()){
            names= names +  cursor.getString(0) + "." ;
        }

        cursor.close();
        if (names.length()!=0){
            names = names.substring(0, names.length()-1);
        }


        System.out.println("----------" + names);
        return names;
    }

    public String getCantidades(){
        String cantidades = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + CANTIDAD + " FROM " + TABLA_ITEMS, null);

        while (cursor.moveToNext()){
            cantidades= cantidades + cursor.getString(0) + ".";
        }

        cursor.close();
        if (cantidades.length()!=0){
            cantidades = cantidades.substring(0, cantidades.length()-1);
        }

        return cantidades;
    }


    public boolean add(Item item){
        Cursor cursor=null;
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try{
            db.beginTransaction();
            db.execSQL("INSERT OR IGNORE INTO " + TABLA_ITEMS + "(" + ID + ", " + NAME + ", " + CANTIDAD + ") VALUES(?,?,?)", new String[]{String.valueOf(item.getCodigo()), item.getNombre(), item.getCantidad()});
            db.setTransactionSuccessful();
            toret=true;
        } finally {
            db.endTransaction();
        }
        db.close();
        return toret;
    }
    public boolean delete(int code){
        boolean toret = false;

        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TABLA_ITEMS + " WHERE " + ID + " = ?", new String[]{String.valueOf(code)});
            db.setTransactionSuccessful();
            toret = true;
        } finally {
            db.endTransaction();
        }
        return toret;
    }
    public boolean edit(Item item){
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(NAME, item.getNombre());
        valores.put(CANTIDAD, item.getCantidad());

        try{
            db.beginTransaction();
            db.update(TABLA_ITEMS, valores, ID + "=?", new String[]{Integer.toString(item.getCodigo())});
            db.setTransactionSuccessful();
            toret=true;
        } finally {
            db.endTransaction();
        }

        return toret;
    }
}
