package com.example.edmol.webview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ajustes extends AppCompatActivity {
    private ArrayList<coloresItem> mColoresList;
    private coloresAdapter cAdapter;
    private ArrayList<tamanoItem> mTamanoList;
    private tamanoAdapter eAdapter;
    TextView display;
    RelativeLayout display2;
    String fondoSeleccionado = "";
    String tamanoSelecionado = "";
    String fondoActual;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        display = (TextView) findViewById(R.id.txtControl);
        display2 = (RelativeLayout) findViewById(R.id.Fondo);
        colorList();
        tamanoList();


        Spinner spinnerColores = findViewById(R.id.spinnerColor);
        Spinner spinnerTamano = findViewById(R.id.spinnerTamano);

        cAdapter = new coloresAdapter(this, mColoresList);
        eAdapter = new tamanoAdapter(this, mTamanoList);


        spinnerColores.setAdapter(cAdapter);
        spinnerTamano.setAdapter(eAdapter);

        save = (Button) findViewById(R.id.btnGuardar);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCambios();
            }
        });

        spinnerColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coloresItem clickItem = (coloresItem) parent.getItemAtPosition(position);
                String clickNombre = clickItem.getColoresNombres();

                if (position == 0) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
                    fondoSeleccionado = "colorFondo1";
                }
                if (position == 1) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo2));
                    fondoSeleccionado = "colorFondo2";
                }
                if (position == 2) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo3));
                    fondoSeleccionado = "colorFondo3";
                }
                if (position == 3) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo4));
                    fondoSeleccionado = "colorFondo4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTamano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tamanoItem clickItem = (tamanoItem) parent.getItemAtPosition(position);
                String clickNombre = clickItem.getTamanoNombres();

                if (position == 0) {
                    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                    tamanoSelecionado = "tamano25";
                }
                if (position == 1) {
                    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                    tamanoSelecionado = "tamano30";
                }
                if (position == 2) {
                    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                    tamanoSelecionado = "tamano35";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void guardarCambios() {
        Intent back = new Intent();
        switch (fondoSeleccionado) {
            case "colorFondo1":
                back.putExtra("fondoSeleccionado", fondoSeleccionado);
            case "colorFondo2":
                back.putExtra("fondoSeleccionado", fondoSeleccionado);
            case "colorFondo3":
                back.putExtra("fondoSeleccionado", fondoSeleccionado);
            case "colorFondo4":
                back.putExtra("fondoSeleccionado", fondoSeleccionado);
        }

        switch (tamanoSelecionado){
            case "tamano25":
                back.putExtra("tamanoSelecionado", tamanoSelecionado);
            case "tamano30":
                back.putExtra("tamanoSelecionado", tamanoSelecionado);
            case "tamano35":
                back.putExtra("tamanoSelecionado", tamanoSelecionado);
        }
        setResult(Activity.RESULT_OK, back);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        guardarCambios();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String fondoSeleccionado = data.getStringExtra("fondoSeleccionado").toString();
                fondoActual = fondoSeleccionado;
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
            }

        }
    }

    private void colorList() {
        mColoresList = new ArrayList<>();
        mColoresList.add(new coloresItem("Azul", R.drawable.paleta_colores));
        mColoresList.add(new coloresItem("Verde", R.drawable.paleta_colores));
        mColoresList.add(new coloresItem("Ambar", R.drawable.paleta_colores));
        mColoresList.add(new coloresItem("Gris", R.drawable.paleta_colores));
    }

    private void tamanoList() {
        mTamanoList = new ArrayList<>();
        mTamanoList.add(new tamanoItem("Pequeño", R.drawable.tamano_texto));
        mTamanoList.add(new tamanoItem("Mediano", R.drawable.tamano_texto));
        mTamanoList.add(new tamanoItem("Grande", R.drawable.tamano_texto));
    }
}