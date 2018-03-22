package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@IdClass(ParametrosPK.class)
public class Parametros implements Serializable {
    private static final long serialVersionUID = 4923347560888500719L;
    @Id
    @Column(name = "PAR_CODIGO", nullable = false, length = 50)
    private String parCodigo;
    @Column(name = "PAR_DESCRIPCION", length = 200)
    private String parDescripcion;
    @Id
    @Column(name = "PAR_VALOR", nullable = false, length = 100)
    private String parValor;

    public Parametros() {
    }

    public Parametros(String parCodigo, String parDescripcion, String parValor) {
        this.parCodigo = parCodigo;
        this.parDescripcion = parDescripcion;
        this.parValor = parValor;
    }

    public String getParCodigo() {
        return parCodigo;
    }

    public void setParCodigo(String parCodigo) {
        this.parCodigo = parCodigo;
    }

    public String getParDescripcion() {
        return parDescripcion;
    }

    public void setParDescripcion(String parDescripcion) {
        this.parDescripcion = parDescripcion;
    }

    public String getParValor() {
        return parValor;
    }

    public void setParValor(String parValor) {
        this.parValor = parValor;
    }
}
