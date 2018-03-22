package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.Set;

public class BaseFiltradaDTO implements Serializable{
    @SuppressWarnings("compatibility:-2136555916386252738")
    private static final long serialVersionUID = 1501029376984806351L;
    private boolean incluir;
    private Set documentos;
    private String descripcion,archivo;
    private int cantidad;
    
    public BaseFiltradaDTO() {
        super();
    }

    public BaseFiltradaDTO(boolean incluir, Set documentos, String descripcion, String archivo, int cantidad) {
        super();
        this.incluir = incluir;
        this.documentos = documentos;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.cantidad = cantidad;
    }

    public void setIncluir(boolean incluir) {
        this.incluir = incluir;
    }

    public boolean isIncluir() {
        return incluir;
    }

    public void setDocumentos(Set documentos) {
        this.documentos = documentos;
    }

    public Set getDocumentos() {
        return documentos;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }
}
