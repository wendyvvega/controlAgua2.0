package com.example.edmol.webview;

public class ObjDatos {
    int id;
    float litros;

    public ObjDatos() {

    }

    public ObjDatos( float litros, String fecha) {

        this.litros = litros;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLitros() {
        return litros;
    }

    public void setLitros(float litros) {
        this.litros = litros;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    String fecha;


}
