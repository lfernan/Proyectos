package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
public class Menp004m implements Serializable {
    private static final long serialVersionUID = 3674147523740532375L;
    @Column(name = "ME04_ACE_FCO")
    private String me04AceFco;
    @Column(name = "ME04_DES_TIP", nullable = false)
    private String me04DesTip;
    @Column(name = "ME04_EST_TIP")
    private String me04EstTip;
    @Column(name = "ME04_FEC_ALT")
    private Integer me04FecAlt;
    @Column(name = "ME04_FEC_BAJ")
    private Integer me04FecBaj;
    @Column(name = "ME04_TIP_ING")
    private String me04TipIng;
    @Id
    @Column(name = "ME04_TIP_MEN", nullable = false)
    private Integer me04TipMen;

    public Menp004m() {
    }

    public Menp004m(String me04AceFco, String me04DesTip, String me04EstTip, Integer me04FecAlt, Integer me04FecBaj,
                    String me04TipIng, Integer me04TipMen) {
        this.me04AceFco = me04AceFco;
        this.me04DesTip = me04DesTip;
        this.me04EstTip = me04EstTip;
        this.me04FecAlt = me04FecAlt;
        this.me04FecBaj = me04FecBaj;
        this.me04TipIng = me04TipIng;
        this.me04TipMen = me04TipMen;
    }

    public String getMe04AceFco() {
        return me04AceFco;
    }

    public void setMe04AceFco(String me04AceFco) {
        this.me04AceFco = me04AceFco;
    }

    public String getMe04DesTip() {
        return me04DesTip;
    }

    public void setMe04DesTip(String me04DesTip) {
        this.me04DesTip = me04DesTip;
    }

    public String getMe04EstTip() {
        return me04EstTip;
    }

    public void setMe04EstTip(String me04EstTip) {
        this.me04EstTip = me04EstTip;
    }

    public Integer getMe04FecAlt() {
        return me04FecAlt;
    }

    public void setMe04FecAlt(Integer me04FecAlt) {
        this.me04FecAlt = me04FecAlt;
    }

    public Integer getMe04FecBaj() {
        return me04FecBaj;
    }

    public void setMe04FecBaj(Integer me04FecBaj) {
        this.me04FecBaj = me04FecBaj;
    }

    public String getMe04TipIng() {
        return me04TipIng;
    }

    public void setMe04TipIng(String me04TipIng) {
        this.me04TipIng = me04TipIng;
    }

    public Integer getMe04TipMen() {
        return me04TipMen;
    }

    public void setMe04TipMen(Integer me04TipMen) {
        this.me04TipMen = me04TipMen;
    }
}
