package com.juvera.listacompraweb;

import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.juvera.listacompraweb.BD.BDManager;

import java.util.ArrayList;

import static com.juvera.listacompraweb.MainActivity.lista;

public class WebAppInterface {
    Context context;
    BDManager bd = new BDManager(context);


    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        this.context = c;

    }

    /** Show a toast from the web page - muestra un mensaje desde la interfaz web
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText( context, toast, Toast.LENGTH_LONG ).show();
    }

    @JavascriptInterface
    public void add(String codigo, String nombre, String cantidad){

        Item item = new Item(codigo, nombre, cantidad);

        bd.add(item);
    }

    @JavascriptInterface
    public void delete(int c){
        bd.delete(c);
    }

    @JavascriptInterface
    public void edit(String c, String nombre, String cantidad){

        Item item = new Item(c, nombre, cantidad);

        bd.edit(item);

    }

    @JavascriptInterface
    public String getNames(){

        String bdAux = bd.getNames();

        return bdAux;
    }

    @JavascriptInterface
    public String getCantidades(){

        String bdAux = bd.getCantidades();

        return bdAux;
    }

    @JavascriptInterface
    public String getCodes(){

        String bdAux = bd.getCodes();

        return bdAux;
    }
}
