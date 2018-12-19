package com.example.edmol.webview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class manual extends AppCompatActivity {

    ImageView volver;
    RelativeLayout display2;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        textView1 = (TextView) findViewById(R.id.textView4);
        textView2 = (TextView) findViewById(R.id.txtManual1);
        textView3 = (TextView) findViewById(R.id.txtManual2);
        textView4 = (TextView) findViewById(R.id.txtManual3);
        textView5 = (TextView) findViewById(R.id.txtManual4);
        textView6 = (TextView) findViewById(R.id.txtManual5);
        textView7 = (TextView) findViewById(R.id.txtManual6);

        display2 = (RelativeLayout) findViewById(R.id.fondito);
        volver = (ImageView) findViewById(R.id.flechaAtras);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), home.class);
                startActivity(i);
            }
        });

        String fondoActual, tamanoActual;
        fondoActual = getIntent().getExtras().getString("fondoActual");
        tamanoActual = getIntent().getExtras().getString("tamanoActual");
        switch (fondoActual) {
            case "colorFondo1":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
                break;
            case "colorFondo2":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo2));
                break;
            case "colorFondo3":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo3));
                break;
            case "colorFondo4":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo4));
                break;
            default:
                Toast.makeText(this, "Algo malo pasó", Toast.LENGTH_SHORT).show();
                break;
        }
        switch (tamanoActual) {
            case "tamano25":
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                break;
            case "tamano30":
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                break;
            case "tamano35":
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                break;
            default:
                break;
        }
    }
}
