package com.juvera.navegadorweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity {


    private WebView browser;
    private ProgressBar progressBar;
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        browser = (WebView)findViewById(R.id.webkit);

        //habilitamos javascript y el zoom
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setBuiltInZoomControls(true);


        browser.loadUrl("http://google.com");
        browser.setWebViewClient(new WebViewClient()
        {
            // evita que los enlaces se abran fuera nuestra app en el navegador de android
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }

        });
        url = (EditText) findViewById(R.id.url);
        url.setText("http://");

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        browser.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
               MainActivity.this.setProgress(progress * 1000);

                progressBar.incrementProgressBy(progress);

                if(progress == 100)
                {
                    progressBar.setVisibility(View.GONE);
                }
            }


        @Override
        public void onReceivedTitle(WebView view, String title)
        {
           MainActivity.this.setTitle("WebView demo: " + MainActivity.this.browser.getTitle());
        }

    });
}
    class WebViewClient extends android.webkit.WebViewClient
    {
        // gestión de los botones de navegación (inicio de carga de una página)
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            // mostramos la url de los enlaces seleccionados
            MainActivity.this.url.setText(url);

            ((Button) MainActivity.this.findViewById(R.id.buttonDetener)).setEnabled(true);
        }


        // gestión de los botones de navegación (final de carga de una
        // página)
        @Override
        public void onPageFinished(WebView view, String url)
        {
            ((Button) MainActivity.this.findViewById(R.id.buttonDetener)).setEnabled(false);
            Button botonAnterior = (Button) MainActivity.this.findViewById(R.id.buttonAnt);

            if (view.canGoBack())
            {
                botonAnterior.setEnabled(true);
            }
            else
            {
                botonAnterior.setEnabled(false);
            }

            Button botonSiguiente = (Button) MainActivity.this.findViewById(R.id.buttonSig);

            if (view.canGoForward())
            {
                botonSiguiente.setEnabled(true);
            }
            else
            {
                botonSiguiente.setEnabled(false);
            }
        }
        //gestión de errores
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(description).setPositiveButton("Aceptar", null).setTitle("onReceivedError");
            builder.show();
        }

    }



    // //////////BOTONES DE NAVEGACIÓN /////////

    public void ir(View view)
    {
        // oculta el teclado al pulsar el botón
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(url.getWindowToken(), 0);

        // he observado que si se pulsa "Ir" sin modificarse la url no se
        // ejecuta el método onPageStarted, así que nos aseguramos
        // que siempre que se cargue una url, aunque sea la que se está
        // mostrando, se active el botón "detener"
        ((Button) MainActivity.this.findViewById(R.id.buttonDetener)).setEnabled(true);

        browser.loadUrl(url.getText().toString());

    }

    public void anterior(View view)
    {
        browser.goBack();
    }

    public void siguiente(View view)
    {
        browser.goForward();
    }

    public void detener(View view)
    {
        browser.stopLoading();
    }



}
