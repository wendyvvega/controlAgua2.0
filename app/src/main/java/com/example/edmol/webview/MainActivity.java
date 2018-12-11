package com.example.edmol.webview;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity  {

    private static int TIMEOUT=3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeI= new Intent(MainActivity.this,home.class);
                startActivity(homeI);
                finish();
            }
        },TIMEOUT);

    }


}
