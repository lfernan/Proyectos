package com.tnevada.model.entidades;

import java.io.Serializable;


public class MensajeDTO implements Serializable{
    @SuppressWarnings("compatibility:5005991436518378257")
    private static final long serialVersionUID = 1L;
    private String tipoDeOrigen;
    private String importancia;
    private String usuario;
    private Long usuarioLegajo;   
    private String usuarioNombre;
    private String fecha;
    private String mensaje;
    private Long referencia;
    private String horaDeCarga;
    private String localDelUsuario;
    private Long visitadorLegajo;
    private String visitadorNombre;
    private String clasificacion;
    private String fechaDeCompromiso;
    
    public MensajeDTO() {
        super();
    }

    public MensajeDTO(String tipoDeOrigen, String importancia, String usuario, Long usuarioLegajo, String usuarioNombre,
                      String fecha, String mensaje, Long referencia, String horaDeCarga, String localDelUsuario,
                      Long visitadorLegajo, String visitadorNombre, String clasificacion, String fechaDeCompromiso) {
        super();
        this.tipoDeOrigen = tipoDeOrigen;
        this.importancia = importancia;
        this.usuario = usuario;
        this.usuarioLegajo = usuarioLegajo;
        this.usuarioNombre = usuarioNombre;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.referencia = referencia;
        this.horaDeCarga = horaDeCarga;
        this.localDelUsuario = localDelUsuario;
        this.visitadorLegajo = visitadorLegajo;
        this.visitadorNombre = visitadorNombre;
        this.clasificacion = clasificacion;
        this.fechaDeCompromiso = fechaDeCompromiso;
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

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
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

    public void setFechaDeCompromiso(String fechaDeCompromiso) {
        this.fechaDeCompromiso = fechaDeCompromiso;
    }

    public String getFechaDeCompromiso() {
        return fechaDeCompromiso;
    }
}
