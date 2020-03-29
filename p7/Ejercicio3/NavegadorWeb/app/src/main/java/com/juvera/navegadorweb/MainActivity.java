package com.juvera.navegadorweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;

import static android.bluetooth.BluetoothAssignedNumbers.GOOGLE;

public class MainActivity extends Activity {


    private WebView browser;
    private ProgressBar progressBar;
    private SearchView url;
    private String SEARCH_PATH = "/search?q=";
    private static final String DEBUG_TAG = "MainActivity";
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
        SearchView url = (SearchView) this.findViewById(R.id.url);
        url.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String consulta) {

                if (!consulta.trim().isEmpty()){
                    if (URLUtil.isValidUrl(consulta)){
                        //URL valida
                        browser.loadUrl(consulta);
                    }else {
                        //No es un URL válida
                        browser.loadUrl("http://google.com"+SEARCH_PATH+consulta);
                    }
                }

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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

        // gestión de los botones de navegación (final de carga de una
        // página)
        @Override
        public void onPageFinished(WebView view, String url)
        {
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
