package com.tnevada.model.entidades;

import java.util.Date;

public class MensajeDTO {
    private String tipoDeOrigen;
    private String importancia;
    private String usuario;
    private Long usuarioLegajo;   
    private String usuarioNombre;
    private Date fecha;
    private String mensaje;
    private Long referencia;
    private String horaDeCarga;
    private String localDelUsuario;
    private Long visitadorLegajo;
    private String visitadorNombre;
    private String clasificacion;
    private Date fechaDeCompromiso;
    
    public MensajeDTO() {
        super();
    }

    public void setTipoDeOrigen(String tipoDeOrigen) {
        this.tipoDeOrigen = tipoDeOrigen;
    }

    public String getTipoDeOrigen() {
        return tipoDeOrigen;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuarioLegajo(Long usuarioLegajo) {
        this.usuarioLegajo = usuarioLegajo;
    }

    public Long getUsuarioLegajo() {
        return usuarioLegajo;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setReferencia(Long referencia) {
        this.referencia = referencia;
    }

    public Long getReferencia() {
        return referencia;
    }

    public void setHoraDeCarga(String horaDeCarga) {
        this.horaDeCarga = horaDeCarga;
    }

    public String getHoraDeCarga() {
        return horaDeCarga;
    }

    public void setLocalDelUsuario(String localDelUsuario) {
        this.localDelUsuario = localDelUsuario;
    }

    public String getLocalDelUsuario() {
        return localDelUsuario;
    }

    public void setVisitadorLegajo(Long visitadorLegajo) {
        this.visitadorLegajo = visitadorLegajo;
    }

    public Long getVisitadorLegajo() {
        return visitadorLegajo;
    }

    public void setVisitadorNombre(String visitadorNombre) {
        this.visitadorNombre = visitadorNombre;
    }

    public String getVisitadorNombre() {
        return visitadorNombre;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setFechaDeCompromiso(Date fechaDeCompromiso) {
        this.fechaDeCompromiso = fechaDeCompromiso;
    }

    public Date getFechaDeCompromiso() {
        return fechaDeCompromiso;
    }            
}
