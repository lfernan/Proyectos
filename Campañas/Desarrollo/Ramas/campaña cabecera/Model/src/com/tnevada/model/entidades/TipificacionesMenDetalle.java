package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TIPIFICACIONES_MEN_DETALLE")
public class TipificacionesMenDetalle implements Serializable {
    private static final long serialVersionUID = 1489986714838104182L;
    @Column(name = "TIPDET_AGENDAR")
    private Integer tipdetAgendar;
    @Column(name = "TIPDET_DESCRIPCION", length = 100)
    private String tipdetDescripcion;
    @Id
    @Column(name = "TIPDET_ID", nullable = false)
    private Long tipdetId;
    @Column(name = "TIPDET_RELLAMAR")
    private Integer tipdetRellamar;
    /*@Column(name = "TIPDET_TIPMEN_ID")
    private Long tipdetTipmenId;*/
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TIPDET_TIPMEN_ID")
    private TipificacionesMensajesCall tipificacion;
    @Temporal(TemporalType.DATE)
    @Column(name = "TIPDET_FECHA_ALTA")
    private Date tipdetFechaAlta;
    @Temporal(TemporalType.DATE)
    @Column(name = "TIPDET_FECHA_BAJA")
    private Date tipdetFechaBaja;
    @Column(name = "TIPDET_ESTADO_TIPDET")
    private String tipdetEstadoTipdet;
    
    public TipificacionesMenDetalle() {
    }

    public TipificacionesMenDetalle(Integer tipdetAgendar, String tipdetDescripcion, Long tipdetId,
                                    Integer tipdetRellamar, TipificacionesMensajesCall tipificacion,
                                    Date tipdetFechaAlta, Date tipdetFechaBaja, String tipdetEstadoTipdet) {
        super();
        this.tipdetAgendar = tipdetAgendar;
        this.tipdetDescripcion = tipdetDescripcion;
        this.tipdetId = tipdetId;
        this.tipdetRellamar = tipdetRellamar;
        this.tipificacion = tipificacion;
        this.tipdetFechaAlta = tipdetFechaAlta;
        this.tipdetFechaBaja = tipdetFechaBaja;
        this.tipdetEstadoTipdet = tipdetEstadoTipdet;
    }

    public void setTipdetFechaAlta(Date tipdetFechaAlta) {
        this.tipdetFechaAlta = tipdetFechaAlta;
    }

    public Date getTipdetFechaAlta() {
        return tipdetFechaAlta;
    }

    public void setTipdetFechaBaja(Date tipdetFechaBaja) {
        this.tipdetFechaBaja = tipdetFechaBaja;
    }

    public Date getTipdetFechaBaja() {
        return tipdetFechaBaja;
    }

    public void setTipdetEstadoTipdet(String tipdetEstadoTipdet) {
        this.tipdetEstadoTipdet = tipdetEstadoTipdet;
    }

    public String getTipdetEstadoTipdet() {
        return tipdetEstadoTipdet;
    }    
    
    public void setTipificacion(TipificacionesMensajesCall tipificacion) {
        this.tipificacion = tipificacion;
    }

    public TipificacionesMensajesCall getTipificacion() {
        return tipificacion;
    }    

    public Integer getTipdetAgendar() {
        return tipdetAgendar;
    }

    public void setTipdetAgendar(Integer tipdetAgendar) {
        this.tipdetAgendar = tipdetAgendar;
    }

    public String getTipdetDescripcion() {
        return tipdetDescripcion;
    }

    public void setTipdetDescripcion(String tipdetDescripcion) {
        this.tipdetDescripcion = tipdetDescripcion;
    }

    public Long getTipdetId() {
        return tipdetId;
    }

    public void setTipdetId(Long tipdetId) {
        this.tipdetId = tipdetId;
    }

    public Integer getTipdetRellamar() {
        return tipdetRellamar;
    }

    public void setTipdetRellamar(Integer tipdetRellamar) {
        this.tipdetRellamar = tipdetRellamar;
    }

    /*public Long getTipdetTipmenId() {
        return tipdetTipmenId;
    }

    public void setTipdetTipmenId(Long tipdetTipmenId) {
        this.tipdetTipmenId = tipdetTipmenId;
    }*/
}
