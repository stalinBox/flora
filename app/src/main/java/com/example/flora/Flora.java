package com.example.flora;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Flora {

    private String nombre_cientifico;
    private String nombre_comun;
    private String ecosistema;
    private String tipo_clima;
    private String uso;
    private String geolocalizacion;
    private String imagen;

    public Flora() {
    }

    public Flora(String nombre_cientifico, String imagen){
        this.nombre_cientifico = nombre_cientifico;
        this.imagen = imagen;
    }

    public Flora(String nombre_cientifico, String nombre_comun, String ecosistema, String tipo_clima, String uso, String geolocalizacion, String imagen) {
        this.nombre_cientifico = nombre_cientifico;
        this.nombre_comun = nombre_comun;
        this.ecosistema = ecosistema;
        this.tipo_clima = tipo_clima;
        this.uso = uso;
        this.geolocalizacion = geolocalizacion;
        this.imagen = imagen;
    }

    public String getNombre_cientifico() {
        return nombre_cientifico;
    }

    public void setNombre_cientifico(String nombre_cientifico) {
        this.nombre_cientifico = nombre_cientifico;
    }

    public String getNombre_comun() {
        return nombre_comun;
    }

    public void setNombre_comun(String nombre_comun) {
        this.nombre_comun = nombre_comun;
    }

    public String getEcosistema() {
        return ecosistema;
    }

    public void setEcosistema(String ecosistema) {
        this.ecosistema = ecosistema;
    }

    public String getTipo_clima() {
        return tipo_clima;
    }

    public void setTipo_clima(String tipo_clima) {
        this.tipo_clima = tipo_clima;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(String geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

