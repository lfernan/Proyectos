package com.tnevada.model.entidades;


public class CampaniasSegmentacionBasePK {
    private Long baseId;
    private Long segId;

    public CampaniasSegmentacionBasePK() {
        super();
    }

    public CampaniasSegmentacionBasePK(Long baseId, Long segId) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CampaniasSegmentacionBasePK)) {
            return false;
        }
        final CampaniasSegmentacionBasePK other = (CampaniasSegmentacionBasePK)object;
        if (!(baseId == null ? other.baseId == null : baseId.equals(other.baseId))) {
            return false;
        }
        if (!(segId == null ? other.segId == null : segId.equals(other.segId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((baseId == null) ? 0 : baseId.hashCode());
        result = PRIME * result + ((segId == null) ? 0 : segId.hashCode());
        return result;
    }
}
