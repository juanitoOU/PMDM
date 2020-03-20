package com.juvera.listacompraweb;

import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static com.juvera.listacompraweb.MainActivity.lista;

public class WebAppInterface {
    Context context;


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
    public void javaMethodGet(String val) {

        lista.add(val);
        Toast.makeText(context, val + "GUARDADO", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public ArrayList<String> javaMethodSet() {
        for(String s : lista){
            String val = s;
            Toast.makeText(context, val + "GUARDADO", Toast.LENGTH_SHORT).show();
        }

        return lista;
    }

}
