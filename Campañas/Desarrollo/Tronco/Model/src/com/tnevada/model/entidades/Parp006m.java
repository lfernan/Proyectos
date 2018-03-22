package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * To create ID generator sequence "PARP006M_ID_SEQ_GEN":
 * CREATE SEQUENCE "PARP006M_ID_SEQ_GEN" INCREMENT BY 50 START WITH 50;
 */
@Entity
@SequenceGenerator(name = "Parp006m_Id_Seq_Gen", sequenceName = "PARP006M_ID_SEQ_GEN", allocationSize = 50,
                   initialValue = 50)
public class Parp006m implements Serializable {
    private static final long serialVersionUID = -1137752712868120451L;
    @Column(name = "PA06_COD_SEX")
    private String pa06CodSex;
    @Column(name = "PA06_DES_COR")
    private String pa06DesCor;
    @Column(name = "PA06_DES_TIP", nullable = false)
    private String pa06DesTip;
    @Column(name = "PA06_EST_DOC")
    private String pa06EstDoc;
    @Column(name = "PA06_FEC_ALT")
    private Integer pa06FecAlt;
    @Column(name = "PA06_FEC_BAJ")
    private Integer pa06FecBaj;
    @Id
    @Column(name = "PA06_TIP_DOC", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Parp006m_Id_Seq_Gen")
    private Integer pa06TipDoc;

    public Parp006m() {
    }

    public Parp006m(String pa06CodSex, String pa06DesCor, String pa06DesTip, String pa06EstDoc, Integer pa06FecAlt,
                    Integer pa06FecBaj, Integer pa06TipDoc) {
        this.pa06CodSex = pa06CodSex;
        this.pa06DesCor = pa06DesCor;
        this.pa06DesTip = pa06DesTip;
        this.pa06EstDoc = pa06EstDoc;
        this.pa06FecAlt = pa06FecAlt;
        this.pa06FecBaj = pa06FecBaj;
        this.pa06TipDoc = pa06TipDoc;
    }

    public String getPa06CodSex() {
        return pa06CodSex;
    }

    public void setPa06CodSex(String pa06CodSex) {
        this.pa06CodSex = pa06CodSex;
    }

    public String getPa06DesCor() {
        return pa06DesCor;
    }

    public void setPa06DesCor(String pa06DesCor) {
        this.pa06DesCor = pa06DesCor;
    }

    public String getPa06DesTip() {
        return pa06DesTip;
    }

    public void setPa06DesTip(String pa06DesTip) {
        this.pa06DesTip = pa06DesTip;
    }

    public String getPa06EstDoc() {
        return pa06EstDoc;
    }

    public void setPa06EstDoc(String pa06EstDoc) {
        this.pa06EstDoc = pa06EstDoc;
    }

    public Integer getPa06FecAlt() {
        return pa06FecAlt;
    }

    public void setPa06FecAlt(Integer pa06FecAlt) {
        this.pa06FecAlt = pa06FecAlt;
    }

    public Integer getPa06FecBaj() {
        return pa06FecBaj;
    }

    public void setPa06FecBaj(Integer pa06FecBaj) {
        this.pa06FecBaj = pa06FecBaj;
    }

    public Integer getPa06TipDoc() {
        return pa06TipDoc;
    }

}
