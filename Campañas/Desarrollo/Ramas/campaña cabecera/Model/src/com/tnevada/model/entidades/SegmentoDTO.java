package com.tnevada.model.entidades;


public class SegmentoDTO {
    private Long dni, baseId, nroRef;
    private String local, pp, pp1;
    
    public SegmentoDTO() {
        super();
    }

    public SegmentoDTO(Long dni, Long baseId, String local, String pp, String pp1, Long nroRef) {
        super();
        this.dni = dni;
        this.baseId = baseId;
        this.local = local;
        this.pp = pp;
        this.pp1 = pp1;
        this.nroRef = nroRef;
    }

    public void setNroRef(Long nroRef) {
        this.nroRef = nroRef;
    }

    public Long getNroRef() {
        return nroRef;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public Long getDni() {
        return dni;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocal() {
        return local;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getPp() {
        return pp;
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1;
    }

    public String getPp1() {
        return pp1;
    }
}
