package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPANIAS_SEGMENTACION_BASE")
@IdClass(CampaniasSegmentacionBasePK.class)
public class CampaniasSegmentacionBase implements Serializable {
    private static final long serialVersionUID = 7304743455770393125L;
    @Id
    @Column(name = "CASEB_BASE_ID", nullable = false)
    private Long baseId;
    @Id
    @Column(name = "CASEB_CASE_ID", nullable = false)
    private Long segId;

    public CampaniasSegmentacionBase() {
    }
    
    public CampaniasSegmentacionBase(Long baseId, Long segId) {
        super();
        this.baseId = baseId;
        this.segId = segId;
    }


    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setSegId(Long segId) {
        this.segId = segId;
    }

    public Long getSegId() {
        return segId;
    }
}
