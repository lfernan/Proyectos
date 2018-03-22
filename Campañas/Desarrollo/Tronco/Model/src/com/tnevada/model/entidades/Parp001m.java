package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * To create ID generator sequence "PARP001M_ID_SEQ_GEN":
 * CREATE SEQUENCE "PARP001M_ID_SEQ_GEN" INCREMENT BY 50 START WITH 50;
 */
@Entity
@SequenceGenerator(name = "Parp001m_Id_Seq_Gen", sequenceName = "PARP001M_ID_SEQ_GEN", allocationSize = 50,
                   initialValue = 50)
public class Parp001m implements Serializable {
    private static final long serialVersionUID = 8574439875099110351L;
    @Column(name = "PA01_COD_ALF", nullable = false)
    private String pa01CodAlf;
    @Id
    @Column(name = "PA01_COD_PRV", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Parp001m_Id_Seq_Gen")
    private Integer pa01CodPrv;
    @Column(name = "PA01_DES_PRV", nullable = false)
    private String pa01DesPrv;
    @Column(name = "PA01_EST_PRV")
    private String pa01EstPrv;
    @Column(name = "PA01_FEC_ALT")
    private Integer pa01FecAlt;
    @Column(name = "PA01_FEC_BAJ")
    private Integer pa01FecBaj;

    public Parp001m() {
    }

    public Parp001m(String pa01CodAlf, Integer pa01CodPrv, String pa01DesPrv, String pa01EstPrv, Integer pa01FecAlt,
                    Integer pa01FecBaj) {
        this.pa01CodAlf = pa01CodAlf;
        this.pa01CodPrv = pa01CodPrv;
        this.pa01DesPrv = pa01DesPrv;
        this.pa01EstPrv = pa01EstPrv;
        this.pa01FecAlt = pa01FecAlt;
        this.pa01FecBaj = pa01FecBaj;
    }

    public String getPa01CodAlf() {
        return pa01CodAlf;
    }

    public void setPa01CodAlf(String pa01CodAlf) {
        this.pa01CodAlf = pa01CodAlf;
    }

    public Integer getPa01CodPrv() {
        return pa01CodPrv;
    }

    public String getPa01DesPrv() {
        return pa01DesPrv;
    }

    public void setPa01DesPrv(String pa01DesPrv) {
        this.pa01DesPrv = pa01DesPrv;
    }

    public String getPa01EstPrv() {
        return pa01EstPrv;
    }

    public void setPa01EstPrv(String pa01EstPrv) {
        this.pa01EstPrv = pa01EstPrv;
    }

    public Integer getPa01FecAlt() {
        return pa01FecAlt;
    }

    public void setPa01FecAlt(Integer pa01FecAlt) {
        this.pa01FecAlt = pa01FecAlt;
    }

    public Integer getPa01FecBaj() {
        return pa01FecBaj;
    }

    public void setPa01FecBaj(Integer pa01FecBaj) {
        this.pa01FecBaj = pa01FecBaj;
    }
}
