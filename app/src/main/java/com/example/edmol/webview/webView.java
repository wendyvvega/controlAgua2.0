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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
    private RadioGroup rGFecha,rGLitros;

    private String Url = "https://reports.zoho.com/open-view/1818177000000002051/2bec8a48eadcfe479fb9737b6b02038d";
    private RelativeLayout rl;
    private  XAxis axisX,axisXL;
    private LineDataSet dataSetL;

    private final ArrayList<String> xVals = new ArrayList<String>();
    private  ArrayList<Float> litrosNormales = new ArrayList<>();
    private  ArrayList<Float> datosMc = new ArrayList<>();
    private ArrayList<String> xNewData = new ArrayList<String>();
    private ArrayList<BarEntry> entradaBarraLitros = new ArrayList<>();
    private ArrayList<BarEntry> entradaBarraMc = new ArrayList<>();
    private ArrayList<Entry> entradaLinea = new ArrayList<>();
    private ArrayList<Entry> entradaLineaMc = new ArrayList<>();
    private RadioButton rMes,rDia,rHora;
    private CheckBox cMc;
    private LineData lineData;
    private  BarData data;
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

        rGFecha = findViewById(R.id.rGFecha);

        spTgraf = findViewById(R.id.spTChart);
        flecha = findViewById(R.id.flechaAtr);
        flecha.setOnClickListener(this);
        barraH = findViewById(R.id.barChart);
        chart = findViewById(R.id.lineChart);

        barraH.setNoDataText("Selecciona un filtro para mostrar los datos");
        barraH.invalidate();
        chart.setNoDataText("Selecciona un filtro para mostrar los datos");
        chart.invalidate();
        ArrayAdapter <CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.txtTGraf,          android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTgraf.setAdapter(adapter2);
        spTgraf.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);




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


        cfgLineaMes();


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
    public void cfgLineaMes(){

        //Inicializar e introducir datos
        axisXL = chart.getXAxis();

        axisXL.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.fitScreen();
        chart.getAxisLeft().setEnabled(true);
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        chart.getAxisRight().setDrawAxisLine(true);
        chart.getAxisRight().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(true);
        chart.getXAxis().setDrawGridLines(true);
        axisXL.setAvoidFirstLastClipping(true);

        axisXL.setGranularity(1f);

        chart.animateXY(5000,5000);
        chart.notifyDataSetChanged();
        chart.invalidate();

    }
    public void cfgLineaFecha(final ArrayList <String> xVals ){

        //Inicializar e introducir datos
        axisXL = chart.getXAxis();

        axisXL.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.fitScreen();
        chart.getAxisLeft().setEnabled(true);
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        chart.getAxisRight().setDrawAxisLine(true);
        chart.getAxisRight().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(true);
        chart.getXAxis().setDrawGridLines(true);
        axisXL.setAvoidFirstLastClipping(true);
        axisXL.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String val = "";
                try {
                    val = xVals.get((int) value);
                    axisXL.setGranularity(1f);
                } catch (IndexOutOfBoundsException e) {
                    axis.setGranularityEnabled(false);
                }
                return val;



            }
        });
        axisXL.setGranularity(1f);

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
       lineData= new LineData(dataSetL);
        //Ver como pasar de parámtero un data para meter dataset dinámicos desde
        //El switch por fecha
        chart.setData(lineData);

        chart.setOnChartValueSelectedListener(this);
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
        barraH.setScaleEnabled(false);
        barraH.setPinchZoom(true);
        barraH.setDrawValueAboveBar(true);




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
    IAxisValueFormatter formatterMes = new IAxisValueFormatter() {
        String[] arreglo = xVals.toArray(new String[xVals.size()]);

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            for(String h:arreglo){
                return meses[Integer.parseInt(h)];
            }
            return "";

        }

        // we don't draw numbers, so no decimal digits needed

        public int getDecimalDigits() {  return 0; }
    };
/*************************************Convertir de litros a **************************************************************************/



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


    /**********************************Convertir lineal**********************************************************/
    public void mostrarLitrosMcLinea(){

        ListIterator <Float> k= litrosMc().listIterator();
        Float h=0f;

        while(k.hasNext()){
            h =  k.next();
            entradaLineaMc.add(new Entry(k.nextIndex(),h/1000f));
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
           cfgBarra(entradaBarraLitros);
           cfgBarraFechaMes();

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

        cfgBarra(entradaBarraLitros);
        cfgBarraFecha();


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

        cfgBarra(entradaBarraLitros);
        cfgBarraFecha();


    }



       public void cfgBarra(ArrayList <BarEntry> entradaBarra){
           BarDataSet dataSetB;
           //Inicializar e introducir datos
           dataSetB = new BarDataSet(entradaBarra,"Agua gastada en promedio");
           dataSetB.setColors(ColorTemplate.COLORFUL_COLORS);
           dataSetB.setHighLightAlpha(2);
           dataSetB.setHighLightColor(Color.BLACK);
           dataSetB.setHighlightEnabled(true);

           //
           //Ordenar datos

           Collections.sort(entradaBarra, new EntryXComparator());
           //Crear los datos para meterlos a la gráfica
           data= new BarData(dataSetB);

           data.setBarWidth(0.3f);


           //Meter datos en la gráfica
           barraH.setData(data);
           barraH.setOnChartValueSelectedListener(this);
           barraH.animateXY(2000,2000);
           barraH.setFitBars(true);
           data.notifyDataChanged();
           barraH.notifyDataSetChanged();
           barraH.invalidate();

       }

    public void cfgBarraFechaMes(){

        //Inicializar e introducir datos

        axisX = barraH.getXAxis();
        axisX.setAvoidFirstLastClipping(true);
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barraH);
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(barraH); // For bounds control
        barraH.setMarker(mv);
        barraH.fitScreen();
        barraH.setAutoScaleMinMaxEnabled(true);
        barraH.setDrawValueAboveBar(true);
        axisX.setGranularity(1f);
        axisX.setPosition(XAxis.XAxisPosition.TOP);
        axisX.setDrawGridLines(false);
        axisX.setValueFormatter(formatterMes);
       /* axisX.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String[] weekArr;
                weekArr = xVals.toArray(new String[0]);
                return weekArr[(int) 0];

            }
        });*/

        // axisX.setValueFormatter(formatter);
        axisX.setLabelCount(entradaBarraLitros.size());

        barraH.animateXY(2000,2000);
        barraH.setFitBars(true);
        barraH.notifyDataSetChanged();
        barraH.invalidate();

    }
    public void cfgBarraFecha(){

        //Inicializar e introducir datos

        axisX = barraH.getXAxis();
        axisX.setAvoidFirstLastClipping(true);
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barraH);
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(barraH); // For bounds control
        barraH.setMarker(mv);
       barraH.fitScreen();
       barraH.setAutoScaleMinMaxEnabled(true);
       barraH.setDrawValueAboveBar(true);
        axisX.setGranularity(1f);
       axisX.setPosition(XAxis.XAxisPosition.TOP);
        axisX.setDrawGridLines(false);

          /*  axisX.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    if (((int) value) < xVals.size()) {
                        weekArr= xVals.toArray(new String[(int)value-1]);
                        for(String a : weekArr ){
                            return a;
                        }


                    }

                             weekArr= xVals.toArray(new String[(int)value]);

                    return "";

                }
            });*/

                axisX.setValueFormatter(formatterMes);
                //axisX.setLabelCount(entradaBarraLitros.size());

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
        rGFecha.clearCheck();

        switch(item){
            case "Lineal":

                    chart.setVisibility(View.VISIBLE);
                    barraH.setVisibility(View.GONE);

                    // rGFecha.check(R.id.rMes);

                rGFecha.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.rMes:
                                if (!chart.isEmpty() ) {

                                    xVals.clear();

                                    litrosNormales.clear();
                                    datosMc.clear();
                                    entradaLinea.clear();
                                    entradaLineaMc.clear();
                                    //entradaBarraLitros.clear();
                                    xNewData.clear();
                                   // entradaBarraMc.clear();
                                    grafLinea(0);
                                    cfgLineaMes();
                                } else {
                                    grafLinea(0);
                                    cfgLineaMes();
                                }
                                break;
                            case R.id.rDia:
                                if (!chart.isEmpty()) {
                                    xVals.clear();

                                    litrosNormales.clear();
                                    datosMc.clear();
                                    entradaLinea.clear();
                                    entradaLineaMc.clear();
                                    xNewData.clear();
                                    grafLinea(1);
                                    cfgLinea(entradaLinea);
                                } else {
                                    grafLinea(1);
                                    cfgLinea(entradaLinea);
                                }
                                break;
                            case R.id.rHora:
                                if (!chart.isEmpty()) {
                                    xVals.clear();

                                    litrosNormales.clear();
                                    datosMc.clear();
                                    xNewData.clear();
                                    entradaLinea.clear();
                                    entradaLineaMc.clear();
                                    grafLinea(2);
                                    cfgLinea(entradaLinea);
                                } else {
                                    grafLinea(2);
                                    cfgLinea(entradaLinea);
                                }
                                break;
                        }}
                });

                break;




            case "Barras-Horizontal":
                chart.setVisibility(View.GONE);
                barraH.setVisibility(View.VISIBLE);
                rGFecha.clearCheck();

             //   rGFecha.check(R.id.rMes);


                rGFecha.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.rMes:
                                if (!barraH.isEmpty()) {
                                    xNewData.clear();
                                    xVals.clear();

                                    litrosNormales.clear();
                                    datosMc.clear();
                                    entradaBarraLitros.clear();
                                    entradaBarraMc.clear();
                                    barras(0);

                                    //cfgBarra(entradaBarraLitros);
                                } else {
                                    barras(0);
                                    //cfgBarra(entradaBarraLitros);
                                }
                                break;
                            case R.id.rDia:
                                if (!barraH.isEmpty()) {
                                    xVals.clear();
                                    xNewData.clear();

                                    litrosNormales.clear();
                                    datosMc.clear();
                                    entradaBarraLitros.clear();
                                    entradaBarraMc.clear();
                                    barras(1);


                                } else {
                                    barras(1);


                                }
                                break;
                            case R.id.rHora:
                                if (!barraH.isEmpty()) {
                                    xVals.clear();
                                    xNewData.clear();

                                    litrosNormales.clear();
                                    datosMc.clear();
                                    entradaBarraLitros.clear();
                                    entradaBarraMc.clear();
                                    barras(2);


                                } else {
                                    barras(2);


                                }
                                break;
                        }
                        int id=group.getCheckedRadioButtonId();
                        rMes = findViewById(id);
                        rDia = findViewById(id);
                        rHora = findViewById(id);


                         /*   if(checkedId==rMes.getId()) {

                            }else{
                                if(checkedId==rDia.getId()) {

                                    if (!barraH.isEmpty()) {

                                        limpiarBarra();
                                        barras(1);
                                    }
                                    barras(1);
                                }else{
                                    if(checkedId==rHora.getId()){
                                        if (!barraH.isEmpty()) {
                                            limpiarBarra();
                                        }
                                        barras(2);
                                    }
                                }
                            }*/
                    }

                });  break;}}



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
/*****************************Llenar label****************************/
public void obtenerDia(){
        AdminBD bd = new AdminBD(this);
        SQLiteDatabase base = bd.getWritableDatabase();

        Cursor c = bd.consultaDia(base);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            xNewData.add(c.getString(c.getColumnIndex("dia")));
        }
        c.close();

    }
    public void obtenerHora(){
        AdminBD bd = new AdminBD(this);
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor c = bd.consultaHora(base);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            xNewData.add(c.getString(c.getColumnIndex("hora")));
        }
        c.close();

    }
    public void obtenerMes() {
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor c = bd.consultaMes(base);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            xNewData.add(c.getString(c.getColumnIndex("mes")));
        }
        c.close();
    }
    /*****************************Llenar label****************************/
    /*****************************Llenar ArrayLitros**********************/
    public void  obtenerLitrosMes(){
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor cursor = base.rawQuery("SELECT  printf(\"%.2f\",(AVG(litros_consumidos))) AS Litro from tb_registros group by strftime('%m',fecha)",null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            litrosNormales.add(cursor.getFloat(cursor.getColumnIndex("Litro")));
        }
        cursor.close();

    }
    public void obtenerLitrosDia(){
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor cursor = bd.consultaDia(base);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            litrosNormales.add(cursor.getFloat(cursor.getColumnIndex("Litro")));
        }
        cursor.close();

    }
    public void obtenerLitrosHora(){
        SQLiteDatabase base = bd.getWritableDatabase();
        Cursor cursor = bd.consultaHora(base);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            litrosNormales.add(cursor.getFloat(cursor.getColumnIndex("Litro")));
        }
        cursor.close();

    }
    /*****************************Llenar ArrayLitros**********************/





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