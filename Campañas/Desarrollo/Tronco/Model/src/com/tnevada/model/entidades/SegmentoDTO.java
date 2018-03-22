package com.tnevada.model.entidades;


public class SegmentoDTO extends NoSociosDTO{
    @SuppressWarnings("compatibility:1015933293148338195")
    private static final long serialVersionUID = 1L;
    private Long dni, baseId, nroRef, tipoCamp;
    private String local, pp, pp1;
    
    public SegmentoDTO() {
        super();
    }
    
    public SegmentoDTO(Long dni) {
        super();
        this.dni = dni;
    }
    
    public SegmentoDTO(Long dni, Long baseId, Long nroRef, Long tipoCamp, String local, String pp, String pp1) {
        super();
        this.dni = dni;
        this.baseId = baseId;
        this.nroRef = nroRef;
        this.tipoCamp = tipoCamp;
        this.local = local;
        this.pp = pp;
        this.pp1 = pp1;
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

    public void setNroRef(Long nroRef) {
        this.nroRef = nroRef;
    }

    public Long getNroRef() {
        return nroRef;
    }

    public void setTipoCamp(Long tipoCamp) {
        this.tipoCamp = tipoCamp;
    }

    public Long getTipoCamp() {
        return tipoCamp;
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
