package com.juvera.listwebview.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.juvera.listwebview.Core.Item;
import com.juvera.listwebview.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static ArrayList<Item> lista ;
    WebView wvView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main);
        lista = new ArrayList<Item>();

        wvView = (WebView) this.findViewById( R.id.wvView );


        wvView.setWebChromeClient(new WebChromeClient() {
            //Other methods for your WebChromeClient here, if needed..

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        this.configureWebView( wvView, "http://www.google.es", 10 );
    }


    private void configureWebView(WebView wvView, String url, int defaultFontSize)
    {
        WebSettings webSettings = wvView.getSettings();

        webSettings.setBuiltInZoomControls( true );
        webSettings.setDefaultFontSize( defaultFontSize );

        // Enable javascript and make android code available from it as the Android object.
        webSettings.setJavaScriptEnabled( true );
        wvView.addJavascriptInterface( new WebAppInterface( this ), "Android" );

        // URLs are handled by this WebView,instead of launching a browser
        wvView.setWebViewClient( new WebViewClient() );

        // Load from a URL - remember to give the app the internet permission
        // wvView.loadUrl( url );

        // Load a HTML file from the assets subdir
        StringBuilder builder = new StringBuilder();
        InputStream in = null;
        try {
            String line;

            in = this.getAssets().open( "calc.html" );
            BufferedReader inf = new BufferedReader( new InputStreamReader( in ) );

            while( ( line = inf.readLine()) != null ) {
                builder.append( line );
            }
        } catch (IOException e) {
            builder.append( "<html><body><big>ERROR internal: loading asset</big></body></html>");
            Log.e( "main.configureWebView", "error loading asset 'calc.html'" );
        }
        finally {
            try {
                if ( in != null ) {
                    in.close();
                }
            } catch(IOException exc) {
                // ignored
            }
        }

        wvView.loadData( builder.toString(), "text/html", "utf-8" );
    }


}
