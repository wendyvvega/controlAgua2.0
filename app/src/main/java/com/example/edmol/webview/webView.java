package com.example.edmol.webview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class webView extends AppCompatActivity implements OnChartGestureListener,View.OnClickListener, OnChartValueSelectedListener, AdapterView.OnItemSelectedListener {
    private WebView webView;
    private LineChart chart;
    private HorizontalBarChart barraH;
    private ImageView flecha;
    private Spinner spFecha,spLitros,spTgraf;

    private String Url = "https://reports.zoho.com/open-view/1818177000000002051/2bec8a48eadcfe479fb9737b6b02038d";
    private RelativeLayout rl;
    private  XAxis axisX;
    private LineDataSet dataSetL;
    private BarDataSet dataSetB;
    private final ArrayList<String> xVals = new ArrayList<String>();
    private  ArrayList<Float> litrosNormales = new ArrayList<>();
    private  ArrayList<Float> datosGalones = new ArrayList<>();
    private  ArrayList<Float> datosMc = new ArrayList<>();
    private ArrayList<String> xNewData = new ArrayList<String>();
    private ArrayList<BarEntry> entradaBarraLitros = new ArrayList<>();
    private ArrayList<BarEntry> entradaBarraGalon = new ArrayList<>();
    private ArrayList<BarEntry> entradaBarraMc = new ArrayList<>();
    private ArrayList<Entry> entradaLinea = new ArrayList<>();
    private ArrayList<Entry> entradaLineaGalon = new ArrayList<>();
    private ArrayList<Entry> entradaLineaMc = new ArrayList<>();
    private RadioButton rMes,rDia,rHora,rLitro,rGalon,rMC;
public void limpiarArreglos(){
    xVals.clear();
    litrosNormales.clear();
    datosGalones.clear();
    datosMc.clear();
    entradaLinea.clear();
    entradaBarraLitros.clear();
    entradaLineaGalon.clear();
    entradaLineaMc.clear();
    entradaBarraGalon.clear();
    entradaBarraMc.clear();
}
    private  AdminBD bd = new AdminBD(this);
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
        rMes= findViewById(R.id.rMes);
        rDia = findViewById(R.id.rDia);
        rHora=findViewById(R.id.rHora);
        rLitro = findViewById(R.id.rLitro);
        rGalon=findViewById(R.id.rGalon);
        rMC=findViewById(R.id.rMC);

        spTgraf = findViewById(R.id.spTChart);
        flecha = findViewById(R.id.flechaAtr);
        flecha.setOnClickListener(this);
        barraH = findViewById(R.id.barChart);
        chart = findViewById(R.id.lineChart);

        ArrayAdapter <CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.txtTGraf,          android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTgraf.setAdapter(adapter2);
        spTgraf.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);






        BarChart barra = new BarChart(this);
        barra.setOnChartGestureListener(com.example.edmol.webview.webView.this);

        barra.setOnChartValueSelectedListener(com.example.edmol.webview.webView.this);
        barra.setDragEnabled(true);
        barra.setScaleEnabled(true);
        barra.setPinchZoom(true);




        //webView = findViewById(R.id.vistaWeb);



        //grafLinea();
        //barras();
    }


    public void grafLinea(int ln){
      
        Description desc = new Description();
        desc.setText("Gasto de agua en promedio");
        chart.setDescription(desc);
        chart.setOnChartGestureListener(com.example.edmol.webview.webView.this);
        chart.setOnChartValueSelectedListener(com.example.edmol.webview.webView.this);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        switch (ln){
            case 0:
                mostrarLineaMes();
                break;
            case 1:
                mostrarLineaDia();
                break;
            case 2:mostrarLineaHora();
            break;
        }



    }
    public void mostrarLineaMes(){

        obtenerMes();
        obtenerLitrosMes();
        ListIterator <Float> k= litrosNormales.listIterator();
        ListIterator <String>s = xNewData.listIterator();
        Float h=0f;
        String l="";

        while(k.hasNext()){
            h =  k.next();
            entradaLinea.add(new Entry(k.nextIndex(),h));
        }
        while(s.hasNext()){
            l=s.next();
            xVals.add(l);
        }


        cfgLineaFecha(xVals);


    }

    public void mostrarLineaDia(){

        obtenerDia();
        obtenerLitrosDia();
        ListIterator <Float> k= litrosNormales.listIterator();
        ListIterator <String>s = xNewData.listIterator();
        Float h=0f;
        String l="";

        while(k.hasNext()){
            h =  k.next();
            entradaLinea.add(new Entry(k.nextIndex(),h));
        }
        while(s.hasNext()){
            l=s.next();
            xVals.add(l);
        }


        cfgLineaFecha(xVals);



    }
    public void mostrarLineaHora(){

        obtenerHora();
        obtenerLitrosHora();
        ListIterator <Float> k= litrosNormales.listIterator();
        ListIterator <String>s = xNewData.listIterator();
        Float h=0f;
        String l="";

        while(k.hasNext()){
            h =  k.next();
            entradaLinea.add(new Entry(k.nextIndex(),h));
        }
        while(s.hasNext()){
            l=s.next();
            xVals.add(l);
        }


        cfgLineaFecha(xVals);

    }
    public void cfgLineaFecha(final ArrayList <String> xVals ){

        //Inicializar e introducir datos

        axisX = chart.getXAxis();
        axisX.setGranularity(1f);
        axisX.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int intValue = (int)value;
                return (xVals.size() > intValue && intValue >= 0) ? xVals.get(intValue) : "";
            }
            public int getDecimalDigits() {  return 0; }
        });

        chart.animateXY(5000,5000);
        chart.notifyDataSetChanged();
        chart.invalidate();

    }

    public void cfgLinea( final ArrayList <Entry> entrada){

        dataSetL = new LineDataSet(entrada,"Agua gastada en promedio");
        dataSetL.setHighlightEnabled(true); // allow highlighting for DataSet
        // set this to false to disable the drawing of highlight indicator (lines)
        dataSetL.setDrawHighlightIndicators(true);
        dataSetL.setHighLightColor(Color.BLACK);
        dataSetL.setDrawFilled(true);
        dataSetL.setColors(ColorTemplate.COLORFUL_COLORS);
        Collections.sort(entrada, new EntryXComparator());
        LineData lineData= new LineData(dataSetL);
        //Ver como pasar de parámtero un data para meter dataset dinámicos desde
        //El switch por fecha
        chart.setData(lineData);
        chart.animateXY(5000,5000);
        lineData.setValueTextSize(18f);
        lineData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();

    }
    
    /***********************************************************************************/
    public void barras(int k){
       

        Description desc = new Description();
        desc.setText("Gasto de agua en promedio");
        barraH.setDescription(desc);
        //Opciones de interacción
        barraH.setOnChartValueSelectedListener(com.example.edmol.webview.webView.this);
        barraH.setOnChartGestureListener(com.example.edmol.webview.webView.this);
        barraH.setDragEnabled(true);
        barraH.setScaleEnabled(true);
        barraH.setPinchZoom(true);
        switch (k){
            case 0:
                mostrarBarraMes();
                break;
            case 1:
                mostrarBarraDia();
                break;
            case 2:mostrarBarraHora();
            break;
        }


    }
    IAxisValueFormatter formatter = new IAxisValueFormatter() {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return meses[(int) value];
        }

        // we don't draw numbers, so no decimal digits needed

        public int getDecimalDigits() {  return 0; }
    };
/*************************************Convertir de litros a **************************************************************************/
    public ArrayList<Float> litroGalon(){


            ListIterator <Float> k= litrosNormales.listIterator();
            Float h=0f;
            while(k.hasNext()){
                h =  k.next();
               datosGalones.add(h);
            }
            Log.d("NewData",String.valueOf(litrosNormales));
            return datosGalones;

    }


    public ArrayList<Float> litrosMc(){

        ListIterator <Float> k= litrosNormales.listIterator();
        Float h=0f;

        while(k.hasNext()){
            h =  k.next();
           datosMc.add(h);
        }
        Log.d("NewData",String.valueOf(litrosNormales));
        return datosMc;

    }
    /*************************************Convertir de litros a **************************************************************************/
    public void mostrarLitrosMc(){

        ListIterator <Float> k= litrosMc().listIterator();
        Float h=0f;

        while(k.hasNext()){
            h =  k.next();
            entradaBarraMc.add(new BarEntry(k.nextIndex(),h/1000f));
        }
    }
    public void mostrarLitrosGalonBarra(){

        ListIterator <Float> k= litroGalon().listIterator();
        Float h=0f;

        while(k.hasNext()){
            h =  k.next();
            entradaBarraGalon.add(new BarEntry(k.nextIndex(),h/3.785f));
        }
    }

    /**********************************Convertir lineal**********************************************************/
    public void mostrarLitrosMcLinea(){

        ListIterator <Float> k= litrosMc().listIterator();
        Float h=0f;

        while(k.hasNext()){
            h =  k.next();
            entradaLineaMc.add(new Entry(k.nextIndex(),h/1000f));
        }
    }
    public void mostrarLitrosGalonBarraLinea(){

        ListIterator <Float> k= litroGalon().listIterator();
        Float h=0f;

        while(k.hasNext()){
            h =  k.next();
            entradaLineaGalon.add(new Entry(k.nextIndex(),h/3.785f));
        }
    }

    /**********************************Convertir lineal**********************************************************/



        // we don't draw numbers, so no decimal digits needed
        /****************************Insertar datos por primera vez Barras**********************************************/
       public void mostrarBarraMes(){

           obtenerMes();
           obtenerLitrosMes();
           ListIterator <Float> k= litrosNormales.listIterator();
           ListIterator <String>s = xNewData.listIterator();
           Float h=0f;
           String l="";

           while(k.hasNext()){
               h =  k.next();
               entradaBarraLitros.add(new BarEntry(k.nextIndex(),h));
           }
           while(s.hasNext()){
               l=s.next();
               xVals.add(l);
           }


           cfgBarraFecha(xVals);

       }
       public void limpiarBarra(){
           if(!barraH.isEmpty()){

        barraH.clearValues();}
        barraH.notifyDataSetChanged();
        barraH.clear();
        barraH.invalidate();
    }
    public void limpiarLinea(){
           if (!chart.isEmpty()){
        chart.clearValues();}
        chart.notifyDataSetChanged();
        chart.clear();
        chart.invalidate();
    }
    public void mostrarBarraDia(){


        obtenerDia();
        obtenerLitrosDia();
        ListIterator <Float> k= litrosNormales.listIterator();
        ListIterator <String>s = xNewData.listIterator();
        Float h=0f;
        String l="";

        while(k.hasNext()){
            h =  k.next();
            entradaBarraLitros.add(new BarEntry(k.nextIndex(),h));
        }
        while(s.hasNext()){
            l=s.next();
            xVals.add(l);
        }


        cfgBarraFecha(xVals);

    }
    public void mostrarBarraHora(){

        obtenerHora();
        obtenerLitrosHora();
        ListIterator <Float> k= litrosNormales.listIterator();
        ListIterator <String>s = xNewData.listIterator();
        Float h=0f;
        String l="";

        while(k.hasNext()){
            h =  k.next();
            entradaBarraLitros.add(new BarEntry(k.nextIndex(),h));
        }
        while(s.hasNext()){
            l=s.next();
            xVals.add(l);
        }


        cfgBarraFecha(xVals);

    }



       public void cfgBarra(ArrayList <BarEntry> entradaBarra){

           //Inicializar e introducir datos
           dataSetB = new BarDataSet(entradaBarra,"Agua gastada en promedio");
           dataSetB.setColor(Color.rgb(7,169,234));
           dataSetB.setHighLightAlpha(2);
           dataSetB.setHighLightColor(Color.BLACK);
           //
           //Ordenar datos

           Collections.sort(entradaBarra, new EntryXComparator());
           //Crear los datos para meterlos a la gráfica

           BarData data = new BarData(dataSetB);

           data.setBarWidth(0.6f);


           //Meter datos en la gráfica
           barraH.setData(data);
           barraH.animateXY(2000,2000);
           barraH.setFitBars(true);
           data.notifyDataChanged();
           barraH.notifyDataSetChanged();
           barraH.invalidate();

       }
    public void cfgBarraFecha(final ArrayList <String> xVals ){

        //Inicializar e introducir datos

        axisX = barraH.getXAxis();
        axisX.setGranularity(1f);
        axisX.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int intValue = (int)value;
                return (xVals.size() > intValue && intValue >= 0) ? xVals.get(intValue) : "";
            }
            public int getDecimalDigits() {  return 0; }
        });

        barraH.animateXY(2000,2000);
        barraH.setFitBars(true);
        barraH.notifyDataSetChanged();
        barraH.invalidate();

    }



   /*private ArrayList<Entry> getDataset(){




        entradaBarraLitros.add(new Entry(1,36f));
        entradaBarraLitros.add(new Entry(2,478f));
        entradaBarraLitros.add(new Entry(3,120f));

        return  entradaBarraLitros;

    }*/


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view,int i , long l) {

        String item = (String) adapterView.getSelectedItem();


        switch(item){
            case "Lineal":

                    chart.setVisibility(View.VISIBLE);
                    barraH.setVisibility(View.GONE);
                    if(rMes.isChecked()) {
                        if(!chart.isEmpty()){
                            limpiarArreglos();
                            limpiarLinea();
                           }

                        grafLinea(0);
                    }
                    if(rDia.isChecked()) {
                        if(!chart.isEmpty()){
                            limpiarArreglos();
                            limpiarLinea();
                           }
                        grafLinea(1);
                    }
                    if(rHora.isChecked()) {
                        if(!chart.isEmpty()){
                            limpiarArreglos();
                            limpiarLinea();
                            }
                        grafLinea(2);
                    }
                if(rLitro.isChecked()) {
                    if(!chart.isEmpty()){
                        limpiarArreglos();
                        limpiarLinea();
                       }
                    cfgLinea(entradaLinea);
                }
                if(rGalon.isChecked()) {
                    if(!chart.isEmpty()){
                        limpiarArreglos();
                        limpiarLinea();
                        }
                    mostrarLitrosGalonBarraLinea();
                    cfgLinea(entradaLineaGalon);
                }
                if(rMC.isChecked()) {
                        if(!chart.isEmpty()){
                    limpiarArreglos();
                    limpiarLinea();
                 }
                    mostrarLitrosMcLinea();
                    cfgLinea(entradaLineaMc);
                }


            case "Barras-Horizontal":


                     chart.setVisibility(View.GONE);
                    barraH.setVisibility(View.VISIBLE);
                if(rMes.isChecked()) {
                    if(!barraH.isEmpty()){
                    limpiarArreglos();
                    limpiarBarra();
                    }
                    barras(0);
                }
                if(rDia.isChecked()) {
                    if(!barraH.isEmpty()){
                        limpiarArreglos();
                        limpiarBarra();
                       }
                    barras(1);
                }
                if(rHora.isChecked()) {
                    if(!barraH.isEmpty()){
                        limpiarArreglos();
                        limpiarBarra();
                       }
                    barras(2);
                }
                if(rLitro.isChecked()) {
                    if(!barraH.isEmpty()){
                        limpiarArreglos();
                        limpiarBarra();
                       }
                    cfgBarra(entradaBarraLitros);
                }
                if(rGalon.isChecked()) {
                    if(!barraH.isEmpty()){
                        limpiarArreglos();
                        limpiarBarra();
                       }
                    mostrarLitrosGalonBarra();
                    cfgBarra(entradaBarraGalon);
                }
                if(rMC.isChecked()) {
                    if(!barraH.isEmpty()){
                        limpiarArreglos();
                        limpiarBarra();
                    }

                    mostrarLitrosMc();
                    cfgBarra(entradaBarraMc);
                }
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

public ArrayList <String> obtenerDia(){
        AdminBD bd = new AdminBD(this);
        SQLiteDatabase base = bd.getWritableDatabase();

        Cursor c = bd.consultaDia(base);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            xNewData.add(c.getString(c.getColumnIndex("dia")));
        }
        c.close();
        return xNewData;

    }
    public ArrayList <String> obtenerHora(){
        AdminBD bd = new AdminBD(this);
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor c = bd.consultaHora(base);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            xNewData.add(c.getString(c.getColumnIndex("hora")));
        }
        c.close();
        return xNewData;

    }
    public ArrayList <String> obtenerMes() {
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor c = bd.consultaMes(base);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            xNewData.add(c.getString(c.getColumnIndex("mes")));
        }
        c.close();
        return xNewData;
    }
    public ArrayList<Float> obtenerLitrosMes(){
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor cursor = bd.consultaMes(base);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            litrosNormales.add(cursor.getFloat(cursor.getColumnIndex("Litro")));
        }
        cursor.close();
        return litrosNormales;
    }
    public ArrayList<Float> obtenerLitrosDia(){
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor cursor = bd.consultaDia(base);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            litrosNormales.add(cursor.getFloat(cursor.getColumnIndex("Litro")));
        }
        cursor.close();
        return litrosNormales;
    }
    public ArrayList<Float> obtenerLitrosHora(){
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor cursor = bd.consultaHora(base);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            litrosNormales.add(cursor.getFloat(cursor.getColumnIndex("Litro")));
        }
        cursor.close();
        return litrosNormales;
    }





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
        ArrayList<Float> litrosNormales = new ArrayList<Float>();
        String query = "SELECT " + DAILY_TOTAL + " FROM " + TABLE_DAILY_FRAG;
        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            litrosNormales.add(cursor.getFloat(cursor.getColumnIndex(DAILY_TOTAL)));
        }
        cursor.close();
        return litrosNormales;
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