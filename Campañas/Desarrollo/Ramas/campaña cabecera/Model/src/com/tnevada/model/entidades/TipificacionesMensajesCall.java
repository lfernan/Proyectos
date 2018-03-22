package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TIPIFICACIONES_MEN")
public class TipificacionesMensajesCall implements Serializable {
    private static final long serialVersionUID = 2003756789483021732L;
    @Column(name = "TIPMEN_DESCRIPCION", length = 100)
    private String tipmenDescripcion;
    @Id
    @Column(name = "TIPMEN_ID", nullable = false)
    private Long tipmenId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipificacion", fetch=FetchType.LAZY)
    private List<TipificacionesMenDetalle> subTip;
    @Temporal(TemporalType.DATE)
    @Column(name = "TIPMEN_FECHA_BAJA")
    private Date tipmenFechaBaja;    
    @Column(name = "TIPMEN_ESTADO_TIPMEN")
    private String tipmenEstadoTipmen;
    
    public TipificacionesMensajesCall() {
    }

    public TipificacionesMensajesCall(String tipmenDescripcion, Long tipmenId, List<TipificacionesMenDetalle> subTip,
                                      Date tipmenFechaBaja, String tipmenEstadoTipmen) {
        super();
        this.tipmenDescripcion = tipmenDescripcion;
        this.tipmenId = tipmenId;
        this.subTip = subTip;
        this.tipmenFechaBaja = tipmenFechaBaja;
        this.tipmenEstadoTipmen = tipmenEstadoTipmen;
    }
    
    public void setTipmenFechaBaja(Date tipmenFechaBaja) {
        this.tipmenFechaBaja = tipmenFechaBaja;
    }

    public Date getTipmenFechaBaja() {
        return tipmenFechaBaja;
    }

    public void setTipmenEstadoTipmen(String tipmenEstadoTipmen) {
        this.tipmenEstadoTipmen = tipmenEstadoTipmen;
    }

    public String getTipmenEstadoTipmen() {
        return tipmenEstadoTipmen;
    }    

    public void setSubTip(List<TipificacionesMenDetalle> subTip) {
        this.subTip = subTip;
    }

    public List<TipificacionesMenDetalle> getSubTip() {
        return subTip;
    }    

    public String getTipmenDescripcion() {
        return tipmenDescripcion;
    }

    public void setTipmenDescripcion(String tipmenDescripcion) {
        this.tipmenDescripcion = tipmenDescripcion;
    }

    public Long getTipmenId() {
        return tipmenId;
    }

    public void setTipmenId(Long tipmenId) {
        this.tipmenId = tipmenId;
    }
}
