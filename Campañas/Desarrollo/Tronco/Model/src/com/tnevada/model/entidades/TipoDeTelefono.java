package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARP011M")
public class TipoDeTelefono implements Serializable {
    private static final long serialVersionUID = 2803712275358141301L;
    @Id
    @Column(name="PA11_COD_TEL", nullable = false)
    private Integer codigo;
    @Column(name="PA11_DES_COD", nullable = false)
    private String descripcion;
    @Column(name="PA11_EST_COD")
    private String estado;
    @Column(name="PA11_FEC_ALT")
    private Long fechaAlta;
    @Column(name="PA11_FEC_BAJ")
    private Long fechaBaja;
    @Column(name="PA11_SOL_NOM")
    private String solicitaNombre;
    @Column(name="PA11_TIPO_ENTIDAD")
    private String tipoEntidad;
    @Column(name="PA11_TIP_TEL")
    private String tipoTelefono;

    public TipoDeTelefono() {
    }

    public TipoDeTelefono(Integer codigo, String descripcion, String estado, Long fechaAlta, Long fechaBaja,
                          String solicitaNombre, String tipoEntidad, String tipoTelefono) {
        super();
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.solicitaNombre = solicitaNombre;
        this.tipoEntidad = tipoEntidad;
        this.tipoTelefono = tipoTelefono;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaBaja(Long fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Long getFechaBaja() {
        return fechaBaja;
    }

    public void setSolicitaNombre(String solicitaNombre) {
        this.solicitaNombre = solicitaNombre;
    }

    public String getSolicitaNombre() {
        return solicitaNombre;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoTelefono(String tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    public String getTipoTelefono() {
        return tipoTelefono;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
