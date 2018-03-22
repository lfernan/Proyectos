package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_CAMPANIAS")
public class TipoCampanias implements Serializable {
    private static final long serialVersionUID = 7004700646131848665L;
    @Column(name = "TIPOCAMP_DESCRIPCION", length = 1000)
    private String tipocampDescripcion;
    @Id
    @Column(name = "TIPOCAMP_ID", nullable = false)
    private Long tipocampId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoCampanias")
    private List<Campanias> campanias;

    public void setCampanias(List<Campanias> campanias) {
        this.campanias = campanias;
    }

    public List<Campanias> getCampanias() {
        return campanias;
    }

    public TipoCampanias() {
    }

    public TipoCampanias(String tipocampDescripcion, Long tipocampId) {
        this.tipocampDescripcion = tipocampDescripcion;
        this.tipocampId = tipocampId;
    }

    public String getTipocampDescripcion() {
        return tipocampDescripcion;
    }

    public void setTipocampDescripcion(String tipocampDescripcion) {
        this.tipocampDescripcion = tipocampDescripcion;
    }

    public Long getTipocampId() {
        return tipocampId;
    }

    public void setTipocampId(Long tipocampId) {
        this.tipocampId = tipocampId;
    }
}
