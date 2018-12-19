package com.example.edmol.webview;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    private ImageView control, ajustes, grafica, manual;
    RelativeLayout display2;
    TextView display;
    String fondoActual, tamanoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        display = (TextView) findViewById(R.id.seleccioneOpcion);

        control = (ImageView) findViewById(R.id.controlAgua);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openControl();
            }
        });

        ajustes = (ImageView) findViewById(R.id.imgAjustes);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAjustes();
            }
        });

        grafica = (ImageView) findViewById(R.id.graficaAgua);
        grafica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrafica();
            }
        });

        manual = (ImageView) findViewById(R.id.imgProblema);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openManual();
            }
        });

        display2 = (RelativeLayout) findViewById(R.id.Fondo);
        display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
        fondoActual="colorFondo1";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String fondoSeleccionado = data.getStringExtra("fondoSeleccionado").toString();
                String tamanoSeleccionado = data.getStringExtra("tamanoSelecionado").toString();
                //Toast.makeText(this, tamanoSeleccionado, Toast.LENGTH_SHORT).show();
                fondoActual = fondoSeleccionado;
                tamanoActual = tamanoSeleccionado;
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
                switch (tamanoSeleccionado) {
                    case "tamano25":
                        display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                        break;
                    case "tamano30":
                        display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        break;
                    case "tamano35":
                        display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                        break;
                }
            }

        }
    }

    public void openControl() {
        Intent c = new Intent(getApplicationContext(), control.class);
        c.putExtra("fondoActual",fondoActual);
        c.putExtra("tamanoActual",tamanoActual);
        startActivityForResult(c,1);
    }

    public void openManual() {
        Intent m = new Intent(getApplicationContext(), manual.class);
        m.putExtra("fondoActual",fondoActual);
        m.putExtra("tamanoActual",tamanoActual);
        startActivityForResult(m,1);
    }

    public void openAjustes() {
        Intent a = new Intent(getApplicationContext(), ajustes.class);
        a.putExtra("fondoActual",fondoActual);
        startActivityForResult(a,1);
    }

    public void openGrafica() {
        Intent g = new Intent(this, webView.class);
        startActivity(g);
    }
}