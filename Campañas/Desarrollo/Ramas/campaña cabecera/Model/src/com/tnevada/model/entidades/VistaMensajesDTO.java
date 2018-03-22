package com.tnevada.model.entidades;

public class VistaMensajesDTO {
    private Long documento, 
        usuarioLegajo,         
        referencia, 
        visitadorLegajo,         
        campId, 
        clasificacionId;
    private String nombre, 
        tipoOrigen, 
        campania, 
        importancia, 
        usuario, 
        usuarioNombre, 
        horaCarga, 
        localUsuario, 
        visitadorNombre, 
        clasificacion, 
        mensaje, 
        tipoOrigenId,
        fecha,
        fechaCompromiso,
        tipoCampania;

    public void setTipoCampania(String tipoCampania) {
        this.tipoCampania = tipoCampania;
    }

    public String getTipoCampania() {
        return tipoCampania;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFechaCompromiso(String fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
    }

    public String getFechaCompromiso() {
        return fechaCompromiso;
    }

    public VistaMensajesDTO() {
        super();
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setUsuarioLegajo(Long usuarioLegajo) {
        this.usuarioLegajo = usuarioLegajo;
    }

    public Long getUsuarioLegajo() {
        return usuarioLegajo;
    }

    public void setReferancia(Long referancia) {
        this.referencia = referancia;
    }

    public Long getReferencia() {
        return referencia;
    }

    public void setVisitadorLegajo(Long visitadorLegajo) {
        this.visitadorLegajo = visitadorLegajo;
    }

    public Long getVisitadorLegajo() {
        return visitadorLegajo;
    }

    public void setCampId(Long campId) {
        this.campId = campId;
    }

    public Long getCampId() {
        return campId;
    }

    public void setClasificacionId(Long clasificacionId) {
        this.clasificacionId = clasificacionId;
    }

    public Long getClasificacionId() {
        return clasificacionId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTipoOrigen(String tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public String getTipoOrigen() {
        return tipoOrigen;
    }

    public void setCampania(String campania) {
        this.campania = campania;
    }

    public String getCampania() {
        return campania;
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

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setHoraCarga(String horaCarga) {
        this.horaCarga = horaCarga;
    }

    public String getHoraCarga() {
        return horaCarga;
    }

    public void setLocalUsuario(String localUsuario) {
        this.localUsuario = localUsuario;
    }

    public String getLocalUsuario() {
        return localUsuario;
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

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setTipoOrigenId(String tipoOrigenId) {
        this.tipoOrigenId = tipoOrigenId;
    }

    public String getTipoOrigenId() {
        return tipoOrigenId;
    }
}
