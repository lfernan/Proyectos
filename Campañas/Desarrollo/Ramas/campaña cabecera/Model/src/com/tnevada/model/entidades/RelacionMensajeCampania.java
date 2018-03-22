package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RELACION_MENSAJE_CAMPANIA")
public class RelacionMensajeCampania implements Serializable {
    private static final long serialVersionUID = 7293225996718647669L;
    @Column(name = "REL_CAMP_ID")
    private Long relCampId;
    @Id
    @Column(name = "REL_ID", nullable = false)
    private Long relId;
    @Column(name = "REL_ME01_NRO_DOC")
    private Integer relMe01NroDoc;
    @Column(name = "REL_ME01_NRO_REF")
    private Integer relMe01NroRef;
    @Column(name = "REL_TELEFONO", length = 50)
    private String relTelefono;

    public RelacionMensajeCampania() {
    }

    public RelacionMensajeCampania(Long relCampId, Long relId, Integer relMe01NroDoc, Integer relMe01NroRef,
                                   String relTelefono) {
        this.relCampId = relCampId;
        this.relId = relId;
        this.relMe01NroDoc = relMe01NroDoc;
        this.relMe01NroRef = relMe01NroRef;
        this.relTelefono = relTelefono;
    }

    public Long getRelCampId() {
        return relCampId;
    }

    public void setRelCampId(Long relCampId) {
        this.relCampId = relCampId;
    }

    public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
    }

    public Integer getRelMe01NroDoc() {
        return relMe01NroDoc;
    }

    public void setRelMe01NroDoc(Integer relMe01NroDoc) {
        this.relMe01NroDoc = relMe01NroDoc;
    }

    public Integer getRelMe01NroRef() {
        return relMe01NroRef;
    }

    public void setRelMe01NroRef(Integer relMe01NroRef) {
        this.relMe01NroRef = relMe01NroRef;
    }

    public String getRelTelefono() {
        return relTelefono;
    }

    public void setRelTelefono(String relTelefono) {
        this.relTelefono = relTelefono;
    }
}
