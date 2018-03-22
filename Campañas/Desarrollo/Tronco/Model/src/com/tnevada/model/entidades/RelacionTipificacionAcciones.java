package com.tnevada.model.entidades;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RELACION_TIPIFICACION_ACCIONES")
public class RelacionTipificacionAcciones implements Serializable {
    private static final long serialVersionUID = 7741773735941854714L;
    @Id
    private Long id;
    @Column(length = 250)
    private String acciones;
    @Column(name = "ID_TIPIFICACION")
    private BigDecimal idTipificacion;
    @Column(name = "ID_TIPO_CAMP")
    private BigDecimal idTipoCamp;

    public RelacionTipificacionAcciones() {
    }


    public RelacionTipificacionAcciones(Long id, String acciones, BigDecimal idTipificacion, BigDecimal idTipoCamp) {
        super();
        this.id = id;
        this.acciones = acciones;
        this.idTipificacion = idTipificacion;
        this.idTipoCamp = idTipoCamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public BigDecimal getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(BigDecimal idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public BigDecimal getIdTipoCamp() {
        return idTipoCamp;
    }

    public void setIdTipoCamp(BigDecimal idTipoCamp) {
        this.idTipoCamp = idTipoCamp;
    }
}
