package model.entities;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TOKEN_OPERACIONES")
public class TokenOperaciones implements Serializable {
    private static final long serialVersionUID = -455883897250612835L;
    @Column(name = "TKO_FECHA_GENERACION")
    private Timestamp tkoFechaGeneracion;
    @Column(name = "TKO_FECHA_USO")
    private Timestamp tkoFechaUso;
    @Column(name = "TKO_FECHA_VENCIMIENTO")
    private Timestamp tkoFechaVencimiento;
    @Id
    @Column(name = "TKO_ID", nullable = false)
    private String tkoId;
    @Column(name = "TKO_IDENTIFICADOR")
    private String tkoIdentificador;

    public TokenOperaciones() {
    }

    public TokenOperaciones(Timestamp tkoFechaGeneracion, Timestamp tkoFechaUso, Timestamp tkoFechaVencimiento,
                            String tkoId, String tkoIdentificador) {
        this.tkoFechaGeneracion = tkoFechaGeneracion;
        this.tkoFechaUso = tkoFechaUso;
        this.tkoFechaVencimiento = tkoFechaVencimiento;
        this.tkoId = tkoId;
        this.tkoIdentificador = tkoIdentificador;
    }

    public Timestamp getTkoFechaGeneracion() {
        return tkoFechaGeneracion;
    }

    public void setTkoFechaGeneracion(Timestamp tkoFechaGeneracion) {
        this.tkoFechaGeneracion = tkoFechaGeneracion;
    }

    public Timestamp getTkoFechaUso() {
        return tkoFechaUso;
    }

    public void setTkoFechaUso(Timestamp tkoFechaUso) {
        this.tkoFechaUso = tkoFechaUso;
    }

    public Timestamp getTkoFechaVencimiento() {
        return tkoFechaVencimiento;
    }

    public void setTkoFechaVencimiento(Timestamp tkoFechaVencimiento) {
        this.tkoFechaVencimiento = tkoFechaVencimiento;
    }

    public String getTkoId() {
        return tkoId;
    }

    public void setTkoId(String tkoId) {
        this.tkoId = tkoId;
    }

    public String getTkoIdentificador() {
        return tkoIdentificador;
    }

    public void setTkoIdentificador(String tkoIdentificador) {
        this.tkoIdentificador = tkoIdentificador;
    }
}
