package com.tnevada.view.backing.mensajes;

import java.io.Serializable;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;

public class MensajeNsDTO implements Serializable, Comparable<MensajeNsDTO>{
    @SuppressWarnings("compatibility:-7546578710084076023")
    private static final long serialVersionUID = 1L;
    protected String visitadorNombre;    
    protected Date fechaDeCompromiso;    
    protected Long visitadorLegajo;    
    protected Timestamp fecha;    
    protected String usuario;    
    protected String localDelUsuario;    
    protected String mensaje;
    protected Long referencia;    
    protected String usuarioNombre;    
    protected String importancia;    
    protected String clasificacion;    
    protected String tipoDeOrigen;    
    protected String horaDeCarga;    
    protected Long usuarioLegajo;    
    protected String producto;
    protected Long id;
    
    public MensajeNsDTO() {
        super();
    }

    public void setVisitadorNombre(String visitadorNombre) {
        this.visitadorNombre = visitadorNombre;
    }

    public String getVisitadorNombre() {
        return visitadorNombre;
    }

    public void setFechaDeCompromiso(Date fechaDeCompromiso) {
        this.fechaDeCompromiso = fechaDeCompromiso;
    }

    public Date getFechaDeCompromiso() {
        return fechaDeCompromiso;
    }

    public void setVisitadorLegajo(Long visitadorLegajo) {
        this.visitadorLegajo = visitadorLegajo;
    }

    public Long getVisitadorLegajo() {
        return visitadorLegajo;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setLocalDelUsuario(String localDelUsuario) {
        this.localDelUsuario = localDelUsuario;
    }

    public String getLocalDelUsuario() {
        return localDelUsuario;
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

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setTipoDeOrigen(String tipoDeOrigen) {
        this.tipoDeOrigen = tipoDeOrigen;
    }

    public String getTipoDeOrigen() {
        return tipoDeOrigen;
    }

    public void setUsuarioLegajo(Long usuarioLegajo) {
        this.usuarioLegajo = usuarioLegajo;
    }

    public Long getUsuarioLegajo() {
        return usuarioLegajo;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFechaFormateada() {
        if(fecha!=null){            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            return sdf.format(fecha);
        }else{
            return "";
        }
    }
    
    public String getFechaCompFormateada() {
        if(fechaDeCompromiso!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fechaDeCompromiso);
        }else{
            return "";
        }
    }
    
    public String getHoraDeCarga(){
        if(fecha!=null){            
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(fecha);
        }else{
            return "";
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MensajeNsDTO)) {
            return false;
        }
        final MensajeNsDTO other = (MensajeNsDTO)object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public int compareTo(MensajeNsDTO mensajeNsDTO) {
        if (this.getId() > mensajeNsDTO.getId()) {
            return -1;
        }else if(this.getId() == mensajeNsDTO.getId()){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Id:"+this.id+"-Hora:"+this.getHoraDeCarga()+"-Mensaje:"+this.getMensaje();
    }
}
