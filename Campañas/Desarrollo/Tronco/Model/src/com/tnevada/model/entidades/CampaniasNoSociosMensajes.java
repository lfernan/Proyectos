package com.tnevada.model.entidades;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAMPANIAS_NO_SOCIOS_MENSAJES")
public class CampaniasNoSociosMensajes implements Serializable{
    private static final long serialVersionUID = 2827477637115805929L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_no_socios_msj")
    @SequenceGenerator(name = "seq_no_socios_msj", sequenceName = "SEQ_NO_SOCIOS_MSJ", initialValue = 1,
                       allocationSize = 1)
    @Column(name = "CANSM_ID", nullable = false)
    private Long cansmId;
    @Column(name = "CANSM_CODIGO_ORIGEN", nullable = false, length = 1)
    private String cansmCodigoOrigen;
    @Column(name = "CANSM_FECHA", nullable = false)
    private Timestamp cansmFecha;
    @Temporal(TemporalType.DATE)
    @Column(name = "CANSM_FECHA_COMPROMISO")
    private Date cansmFechaCompromiso;
    @Column(name = "CANSM_MENSAJE", length = 2000)
    private String cansmMensaje;
    @Column(name = "CANSM_TIPIFICACION", nullable = false)
    private Long cansmTipificacion;
    @Column(name = "CANSM_USUARIO", nullable = false)
    private Long cansmUsuario;
    @ManyToOne
    @JoinColumn(name = "CANSM_CANS_ID")
    private CampaniasNoSocios campaniasNoSocios;

    public CampaniasNoSociosMensajes() {
    }

    public CampaniasNoSociosMensajes(Long cansmId, String cansmCodigoOrigen, Timestamp cansmFecha,
                                     Date cansmFechaCompromiso, String cansmMensaje, Long cansmTipificacion,
                                     Long cansmUsuario, CampaniasNoSocios campaniasNoSocios) {
        super();
        this.cansmId = cansmId;
        this.cansmCodigoOrigen = cansmCodigoOrigen;
        this.cansmFecha = cansmFecha;
        this.cansmFechaCompromiso = cansmFechaCompromiso;
        this.cansmMensaje = cansmMensaje;
        this.cansmTipificacion = cansmTipificacion;
        this.cansmUsuario = cansmUsuario;
        this.campaniasNoSocios = campaniasNoSocios;
    }

    public void setCansmId(Long cansmId) {
        this.cansmId = cansmId;
    }

    public Long getCansmId() {
        return cansmId;
    }

    public void setCansmCodigoOrigen(String cansmCodigoOrigen) {
        this.cansmCodigoOrigen = cansmCodigoOrigen;
    }

    public String getCansmCodigoOrigen() {
        return cansmCodigoOrigen;
    }

    public void setCansmFecha(Timestamp cansmFecha) {
        this.cansmFecha = cansmFecha;
    }

    public Timestamp getCansmFecha() {
        return cansmFecha;
    }

    public void setCansmFechaCompromiso(Date cansmFechaCompromiso) {
        this.cansmFechaCompromiso = cansmFechaCompromiso;
    }

    public Date getCansmFechaCompromiso() {
        return cansmFechaCompromiso;
    }

    public void setCansmMensaje(String cansmMensaje) {
        this.cansmMensaje = cansmMensaje;
    }

    public String getCansmMensaje() {
        return cansmMensaje;
    }

    public void setCansmTipificacion(Long cansmTipificacion) {
        this.cansmTipificacion = cansmTipificacion;
    }

    public Long getCansmTipificacion() {
        return cansmTipificacion;
    }

    public void setCansmUsuario(Long cansmUsuario) {
        this.cansmUsuario = cansmUsuario;
    }

    public Long getCansmUsuario() {
        return cansmUsuario;
    }

    public void setCampaniasNoSocios(CampaniasNoSocios campaniasNoSocios) {
        this.campaniasNoSocios = campaniasNoSocios;
    }

    public CampaniasNoSocios getCampaniasNoSocios() {
        return campaniasNoSocios;
    }
}
