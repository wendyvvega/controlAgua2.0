package com.example.edmol.webview;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class webView extends AppCompatActivity implements OnChartGestureListener,View.OnClickListener, OnChartValueSelectedListener, AdapterView.OnItemSelectedListener {
    private WebView webView;
    private LineChart chart;
    private HorizontalBarChart barraH;
    private ImageView flecha;
    private Spinner spFecha,spLitros,spTgraf;
    private String Url = "https://reports.zoho.com/open-view/1818177000000002051/2bec8a48eadcfe479fb9737b6b02038d";
    private RelativeLayout rl;
    final String[] meses = new String[] { "Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dec"};
    int seleccion;
    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rl = findViewById(R.id.layoutChart);
        setContentView(R.layout.activity_web_view);
        spFecha= findViewById(R.id.spFecha);
        spLitros = findViewById(R.id.spLitros);
        spTgraf = findViewById(R.id.spTChart);
        flecha = findViewById(R.id.flechaAtr);
        flecha.setOnClickListener(this);
        seleccion= spLitros.getSelectedItemPosition();

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.txtFechas,          android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFecha.setAdapter(adapter);
        spFecha.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayAdapter <CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.txtLitros,          android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLitros.setAdapter(adapter1);
        spLitros.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayAdapter <CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.txtTGraf,          android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTgraf.setAdapter(adapter2);
        spTgraf.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);






        BarChart barra = new BarChart(this);
        barra.setOnChartGestureListener(com.example.edmol.webview.webView.this);
        barra.setOnChartValueSelectedListener(com.example.edmol.webview.webView.this);
        barra.setDragEnabled(true);
        barra.setScaleEnabled(true);
        barra.setPinchZoom(true);




        //webView = findViewById(R.id.vistaWeb);



        grafLinea();
        barras();
    }


    public void grafLinea(){
      chart = findViewById(R.id.lineChart);
        Description desc = new Description();
        desc.setText("Gasto de agua");
        chart.setDescription(desc);
        chart.setOnChartGestureListener(com.example.edmol.webview.webView.this);
        chart.setOnChartValueSelectedListener(com.example.edmol.webview.webView.this);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        LineDataSet dataSet = new LineDataSet(getDataset(),"Visualiza el consumo de agua");
        dataSet.setHighlightEnabled(true); // allow highlighting for DataSet
        XAxis axisX = chart.getXAxis();
        axisX.setGranularity(1f);
        axisX.setValueFormatter(formatter);
        // set this to false to disable the drawing of highlight indicator (lines)
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setHighLightColor(Color.BLACK);
        dataSet.setDrawFilled(true);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Collections.sort(getDataset(), new EntryXComparator());
        ArrayList <ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        LineData lineData= new LineData(dataSets);
        //lineData.setValueFormatter(new EjeX(getEjeXValores()));
        chart.setData(lineData);
        chart.animateXY(5000,5000);
        lineData.setValueTextSize(18f);
        chart.invalidate();

    }
    public void barras(){
        barraH = findViewById(R.id.barChart);

        Description desc = new Description();
        desc.setText("Gasto de agua");
        barraH.setDescription(desc);
        //Opciones de interacción
        barraH.setOnChartValueSelectedListener(com.example.edmol.webview.webView.this);
        barraH.setOnChartGestureListener(com.example.edmol.webview.webView.this);
        barraH.setDragEnabled(true);
        barraH.setScaleEnabled(true);
        barraH.setPinchZoom(true);

        //Inicializar e introducir datos
        BarDataSet dataSet = new BarDataSet(getBarDataset(),"Agua gastada");
        dataSet.setColor(Color.rgb(7,169,234));
        dataSet.setHighLightAlpha(2);
        dataSet.setHighLightColor(Color.BLACK);
        //
        //Ordenar datos

        Collections.sort(getBarDataset(), new EntryXComparator());
        //Crear los datos para meterlos a la gráfica

       BarData data = new BarData(dataSet );
       XAxis axisX = barraH.getXAxis();
       axisX.setGranularity(1f);
       axisX.setValueFormatter(formatter);
        data.setBarWidth(0.9f);


       //Meter datos en la gráfica
       barraH.setData(data);
        barraH.animateXY(2000,2000);
        barraH.setFitBars(true);
        barraH.invalidate();



    }
    IAxisValueFormatter formatter = new IAxisValueFormatter() {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return meses[(int) value];
        }

        // we don't draw numbers, so no decimal digits needed

        public int getDecimalDigits() {  return 0; }
    };
    private ArrayList <BarEntry> getBarDataset(){
        ArrayList<BarEntry> entradas = new ArrayList<>();

        entradas.add(new BarEntry(0, 30f));
        entradas.add(new BarEntry(1, 80f));
        entradas.add(new BarEntry(2, 60f));
        entradas.add(new BarEntry(3, 50f));
        // gap of 2f
        entradas.add(new BarEntry(4, 70f));
        entradas.add(new BarEntry(5, 60f));


        return entradas;

    }
    private ArrayList<Entry> getDataset(){
        ArrayList<Entry> entradas = new ArrayList<Entry>();
        entradas.add(new Entry(0,215f));
        entradas.add(new Entry(1,36f));
        entradas.add(new Entry(2,478f));
        entradas.add(new Entry(3,120f));

        return  entradas;

    }
    private ArrayList<String> getEjeXValores(){
        ArrayList<String> EjeX= new ArrayList<String>();
        EjeX.add("Ene");
        EjeX.add("Feb");
        EjeX.add("Mar");
        EjeX.add("Abr");
        EjeX.add("May");
        EjeX.add("Jun");
        EjeX.add("Jul");
        EjeX.add("Ago");
        EjeX.add("Sept");
        EjeX.add("Oct");
        EjeX.add("Nov");
        EjeX.add("Dic");
        return EjeX;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view,int i , long l) {
        String item = adapterView.getItemAtPosition(i).toString();


        switch(item){
            case "Lineal":

                if(chart.getVisibility()==view.VISIBLE){
                    grafLinea();
                    return;
                }
                if(barraH.getVisibility() == view.VISIBLE){
                    barraH.setVisibility(View.GONE);
                    chart.setVisibility(view.VISIBLE);
                    grafLinea();

                }
                break;
            case "Barras-Horizontal":
                if(barraH.getVisibility()==view.VISIBLE){
                    return;
                }
                if(chart.getVisibility() == view.VISIBLE){
                     chart.setVisibility(View.GONE);
                    barraH.setVisibility(View.VISIBLE);
                    barras();
                }break;
           case "Litros":

               if(seleccion !=i && seleccion == 1 ){

               }else {
                   if (seleccion !=i && seleccion ==2){

                   }
               }
               seleccion=i;
                break;
            case "Galones":

                if(seleccion !=i && seleccion == 0 ){

                }else {
                    if (seleccion !=i && seleccion ==2){

                    }
                }
                seleccion=i;
                break;

            case "Metros Cúbicos":

                if(seleccion !=i && seleccion == 0 ){

                }else {
                    if (seleccion !=i && seleccion ==1){

                    }
                }
                seleccion=i;
                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.flechaAtr:
                    finish();
                break;
        }
    }

    private class EjeX implements IValueFormatter {
        private List<String> labels;
        public EjeX (List<String> labels){
            this.labels = labels;

        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            try {
                int index = (int) value;
                return this.labels.get(index);
            }catch (Exception e){
                return null;
            }
        }
    }
    public void llenarTabla(LineChart linea) {
        AdminBD bd = new AdminBD(this);
        SQLiteDatabase base = bd.getWritableDatabase();
        ArrayList<String> xNewData = new ArrayList<String>();
       /* Cursor c = bd.consultaFecha(base);
        int count = c.getCount();

        double[] values = new double[count];
        String[] fechas = new String[count];
        int[] colori = new int[count];

        for (int m = 0; m < count; m++) {
            c.moveToNext();
            fechas[m] = c.getString(0);
            values[m] = c.getDouble(1);
*/


            /*for (int i = 0; i < fechas.length; i++) {
                yVals1.add(new Entry((float) values[i], i));
            }
            ArrayList<String> xVals = new ArrayList<String>();//array legend

            for (int i = 0; i < fechas.length; i++) {
                xVals.add(fechas[m % fechas.length]);
            }*/

            // undo all highlights


        }
       /* c.close();
        bd.close();*/
    //}


    /*public ArrayList<String> queryXData(){


            for (fecha.moveToFirst(); !fecha.isAfterLast(); fecha.moveToNext()) {
            xNewData.add(fecha.getString(fecha.getColumnIndex(DAILY_DATE)));
        }
        cursor.close();
        return xNewData;
    }

    public ArrayList<Float> queryYData(){
        AdminBD bd= new AdminBD(this);
        SQLiteDatabase base = bd.getWritableDatabase();
        ArrayList<String> xNewData = new ArrayList<String>();
        Cursor litros = bd.consultaLitros(base);
        ArrayList<Float> yNewData = new ArrayList<Float>();
        String query = "SELECT " + DAILY_TOTAL + " FROM " + TABLE_DAILY_FRAG;
        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            yNewData.add(cursor.getFloat(cursor.getColumnIndex(DAILY_TOTAL)));
        }
        cursor.close();
        return yNewData;
    }*/

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

}