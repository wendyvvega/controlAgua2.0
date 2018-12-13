package com.example.edmol.webview;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class control extends AppCompatActivity /*implements TextWatcher*/ {
    RelativeLayout display2, auto_mood, manual_mood;
    TextView aguaTotal;
    TextView flujoAgua;
    ToggleButton btnModo;
    String txtCantidad;

    //variables para la conexion
    Handler bluetoothIn;
    EditText cantidad;
    ToggleButton botonPrender3, botonManual;
    final int handlerState = 0;                         //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    //private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    //private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        cantidad = findViewById(R.id.etAgua);
        botonManual = findViewById(R.id.botonPrender);
        display2 = (RelativeLayout) findViewById(R.id.Fondo);
        auto_mood = (RelativeLayout) findViewById(R.id.rlAutomatico);
        manual_mood = (RelativeLayout) findViewById(R.id.rlManual);
        aguaTotal = (TextView) findViewById(R.id.txtCantidad);
        flujoAgua = (TextView) findViewById(R.id.txtFlujo);
        btnModo = (ToggleButton) findViewById(R.id.btnMood);
        botonPrender3 = (ToggleButton) findViewById(R.id.botonPrender3);

        //aguaTotal.addTextChangedListener(this);

        String fondoActual;
        fondoActual = getIntent().getExtras().getString("fondoActual");
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

        btnModo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    auto_mood.setVisibility(CompoundButton.INVISIBLE);
                    manual_mood.setVisibility(CompoundButton.VISIBLE);
                } else {
                    auto_mood.setVisibility(CompoundButton.VISIBLE);
                    manual_mood.setVisibility(CompoundButton.INVISIBLE);
                }
            }
        });


    }
}