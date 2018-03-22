package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Consulta implements Serializable {
    private static final long serialVersionUID = 4627571598873517444L;
    @Column(name = "CONS_CONSULTA")
    private String consConsulta;
    @Column(name = "CONS_DESCRIP", length = 4000)
    private String consDescrip;
    @Id
    @Column(name = "CONS_ID", nullable = false, length = 40)
    private String consId;
    @Column(name = "CONS_NOMBRE", length = 200)
    private String consNombre;
    @Column(name = "CONS_SECTOR", length = 40)
    private String consSector;
    @Column(name = "CONS_TIENESQL")
    private Integer consTienesql;
    @Column(name = "CONS_TIPO", length = 20)
    private String consTipo;

    public Consulta() {
    }

    public Consulta(String consConsulta, String consDescrip, String consId, String consNombre, String consSector,
                    Integer consTienesql, String consTipo) {
        this.consConsulta = consConsulta;
        this.consDescrip = consDescrip;
        this.consId = consId;
        this.consNombre = consNombre;
        this.consSector = consSector;
        this.consTienesql = consTienesql;
        this.consTipo = consTipo;
    }

    public String getConsConsulta() {
        return consConsulta;
    }

    public void setConsConsulta(String consConsulta) {
        this.consConsulta = consConsulta;
    }

    public String getConsDescrip() {
        return consDescrip;
    }

    public void setConsDescrip(String consDescrip) {
        this.consDescrip = consDescrip;
    }

    public String getConsId() {
        return consId;
    }

    public void setConsId(String consId) {
        this.consId = consId;
    }

    public String getConsNombre() {
        return consNombre;
    }

    public void setConsNombre(String consNombre) {
        this.consNombre = consNombre;
    }

    public String getConsSector() {
        return consSector;
    }

    public void setConsSector(String consSector) {
        this.consSector = consSector;
    }

    public Integer getConsTienesql() {
        return consTienesql;
    }

    public void setConsTienesql(Integer consTienesql) {
        this.consTienesql = consTienesql;
    }

    public String getConsTipo() {
        return consTipo;
    }

    public void setConsTipo(String consTipo) {
        this.consTipo = consTipo;
    }
}
