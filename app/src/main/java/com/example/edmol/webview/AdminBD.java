package com.example.edmol.webview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AdminBD extends SQLiteOpenHelper{
    final static int bd_version = 1;
    final static String bd_nombre = "bd_registros.db";
    final static String tb_nombre = "tb_registros";
    final static String campo_id = "id";
    final static String campo_fecha = "fecha";
        final static String campo_litros_consumidos = "litros_consumidos";
    final static String campo_modo = "modo"; //MANUAL O AUTOMATICO
    final static String campo_caudal = "caudal";
    final static String crear_tabla = "CREATE TABLE IF NOT EXISTS "+tb_nombre+" ("+campo_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +campo_fecha+" DATETIME DEFAULT (CURRENT_TIMESTAMP) NOT NULL, "+campo_litros_consumidos+" REAL NOT NULL, "+campo_modo+" TEXT NOT NULL, "+campo_caudal+" TEXT);";
    final static String consulta_litros= "SELECT printf(\"%.2f\",(AVG("+campo_litros_consumidos+"))) AS Litro FROM "+tb_nombre+";";
    final static String consulta_galones= "SELECT printf(\"%.2f\",(AVG(litros_consumidos)/3.785)) AS Galon From "+tb_nombre+";";
    final static String consulta_mc= "SELECT printf(\"%.2f\",(AVG(litros_consumidos)/1000)) AS MetroCubico  "+tb_nombre+";";
    final static String consulta_hora= "SELECT strftime('%H',DATETIME("+campo_fecha+"))hora,printf(\"%.2f\",(AVG("+campo_litros_consumidos+"))) as Litro FROM "+tb_nombre+" GROUP BY strftime ('%H',"+campo_fecha+");";

    final static String consulta_mes= "SELECT strftime('%m',DATETIME("+campo_fecha+",'localtime'))mes, printf(\"%.2f\",(AVG("+campo_litros_consumidos+"))) as Litro FROM "+tb_nombre+" GROUP BY strftime ('%m',"+campo_fecha+");";
    final static String consulta_dia= "SELECT strftime('%d',DATETIME("+campo_fecha+",'localtime'))dia, printf(\"%.2f\",(AVG("+campo_litros_consumidos+"))) as Litro FROM "+tb_nombre+" GROUP BY strftime ('%d',"+campo_fecha+");";


    final static String consulta_tabla = "SELECT * FROM "+tb_nombre+";";
    ArrayList <ObjDatos> lista;
    ObjDatos ob;

    public AdminBD(Context context) {
        super(context, bd_nombre, null, bd_version);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(crear_tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {

    }

    public void insertarRegistro(SQLiteDatabase bd, String fecha, Float litros_consumidos, String modo, String caudal) {
        final String insertar = "INSERT INTO "+tb_nombre+" ("+campo_fecha+", "
                +campo_litros_consumidos+", "+campo_modo+", "+campo_caudal+") VALUES ('"+fecha+"','"
                +litros_consumidos+"','"+modo+"','"+caudal+"');";
        bd.execSQL(insertar);
    }

    public Cursor consulta(SQLiteDatabase bd) {
        Cursor cursor = bd.rawQuery(consulta_tabla, null);
        return cursor;
    }


    public  Cursor consultaMes(SQLiteDatabase bd){
        Cursor cursorLitros = bd.rawQuery(consulta_mes,null);
        return cursorLitros;
    }
    public  Cursor consultaDia(SQLiteDatabase bd){
        Cursor cursorLitros = bd.rawQuery(consulta_dia,null);
        return cursorLitros;
    }

    public Cursor consultarLitos (SQLiteDatabase bd){
        Cursor c = bd.rawQuery(consulta_litros,null);
        return c;

    }


    public  Cursor consultaHora(SQLiteDatabase bd){
        Cursor cursorFecha = bd.rawQuery(consulta_hora,null);
                return cursorFecha;
    }


    public void modificarRegistro(SQLiteDatabase bd, int id, String fecha, String litros_consumidos, String modo, String caudal) {
        final String modificar = "UPDATE "+tb_nombre+" SET "+campo_fecha+ "='"+fecha+"', "
                +campo_litros_consumidos+"='"+litros_consumidos+"', "
                +campo_modo+"='"+modo+"', "
                +campo_caudal+"='"+caudal+"' WHERE "+campo_id+"="+ id + ";";
        bd.execSQL(modificar);
    }

    public void eliminarRegistro(SQLiteDatabase bd, int id) {
        final String eliminar = "DELETE FROM "+tb_nombre+" WHERE "+campo_id+"="+id+";";
        bd.execSQL(eliminar);
    }

    public ArrayList <ObjDatos> obtener(){
        return lista;
    }
    public ArrayList <String> queymesData(){

       ArrayList<String> mgetData = new ArrayList<>();
       String query = consulta_mes;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            mgetData.add(c.getString(c.getColumnIndex(campo_fecha)));
        }
        c.close();
        return mgetData;

    }
    public ArrayList <String> queyhoraData(){

        ArrayList<String> ygetData = new ArrayList<>();
        String query = consulta_hora;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ygetData.add(c.getString(c.getColumnIndex(campo_fecha)));
        }
        c.close();
        return ygetData;


    }
    public ArrayList <String> queydiaData(){

        ArrayList<String> ygetData = new ArrayList<>();
        String query = consulta_dia;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ygetData.add(c.getString(c.getColumnIndex(campo_fecha)));
        }
        c.close();
        return ygetData;
    }
    public ArrayList <Float> queyLitrosData(){

        ArrayList<Float> ygetData = new ArrayList<>();
        String query = consulta_dia;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ygetData.add(c.getFloat(c.getColumnIndex(campo_fecha)));
        }
        c.close();
        return ygetData;

    }

}
