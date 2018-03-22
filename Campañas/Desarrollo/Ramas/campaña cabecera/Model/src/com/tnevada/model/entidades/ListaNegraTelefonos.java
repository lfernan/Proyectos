package com.tnevada.model.entidades;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LISTA_NEGRA_TELEFONOS")
public class ListaNegraTelefonos implements Serializable {
    private static final long serialVersionUID = 5002993486872310976L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 50)
    private String telefono;

    public ListaNegraTelefonos() {
    }

    public ListaNegraTelefonos(BigDecimal id, String telefono) {
        this.id = id;
        this.telefono = telefono;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
