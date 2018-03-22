package com.tnevada.model.entidades;

import java.io.Serializable;

public class ServiciosProductosDTO implements Serializable {
    @SuppressWarnings("compatibility:5186344545577610192")
    private static final long serialVersionUID = 1L;
    private String producto,estado,accion,url,color;

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    }
