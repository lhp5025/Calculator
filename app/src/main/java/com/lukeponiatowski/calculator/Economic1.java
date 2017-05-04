package com.lukeponiatowski.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
/*
    Economic1 -  Activity for hosting the web-views that are used for economic calculations
*/
public class Economic1 extends ChildActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic1);
        ///Setup WebView
        WebView window = (WebView)findViewById(R.id.webView); //get WebView from layout
        window.setWebChromeClient(new WebChromeClient());
        window.getSettings().setJavaScriptEnabled(true);
        window.loadUrl("file:///android_asset/economic1.html"); //load file
    }
}
