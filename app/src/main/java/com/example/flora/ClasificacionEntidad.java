package com.example.flora;

public class ClasificacionEntidad {
    private Integer codigo;
    private String nombre_cientifico;
    private String nombre_comun;
    private String reino;
    private String division;
    private String clase;
    private String orden;

    private String familia;
    private String genero;
    private String especie;

    public ClasificacionEntidad() {
    }

    public ClasificacionEntidad(Integer codigo, String nombre_cientifico, String nombre_comun, String reino, String division, String clase, String orden, String familia, String genero, String especie) {
        this.codigo = codigo;
        this.nombre_cientifico = nombre_cientifico;
        this.nombre_comun = nombre_comun;
        this.reino = reino;
        this.division = division;
        this.clase = clase;
        this.orden = orden;
        this.familia = familia;
        this.genero = genero;
        this.especie = especie;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public String getReino() {
        return reino;
    }

    public void setReino(String reino) {
        this.reino = reino;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
