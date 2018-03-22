package com.tnevada.model.entidades;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LISTA_NEGRA_TELEFONOS")
public class ListaNegraTelefonos implements Serializable {
    private static final long serialVersionUID = 5002993486872310976L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 50)
    private String telefono;    
    @Temporal(TemporalType.DATE)
    @Column
    private Date fecha;

    public ListaNegraTelefonos() {
    }

    public ListaNegraTelefonos(BigDecimal id, String telefono, Date fecha) {
        super();
        this.id = id;
        this.telefono = telefono;
        this.fecha = fecha;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }
}
