package com.tnevada.model.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * To create ID generator sequence "PARP008M_ID_SEQ_GEN":
 * CREATE SEQUENCE "PARP008M_ID_SEQ_GEN" INCREMENT BY 50 START WITH 50;
 */
@Entity
@SequenceGenerator(name = "Parp008m_Id_Seq_Gen", sequenceName = "PARP008M_ID_SEQ_GEN", allocationSize = 50,
                   initialValue = 50)
public class Parp008m implements Serializable {
    private static final long serialVersionUID = -7529385423261480351L;
    @Column(name = "PA08_ABR_ALF")
    private String pa08AbrAlf;
    @Column(name = "PA08_DES_CIV", nullable = false)
    private String pa08DesCiv;
    @Id
    @Column(name = "PA08_EST_CIV", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Parp008m_Id_Seq_Gen")
    private Integer pa08EstCiv;
    @Column(name = "PA08_FEC_ALT")
    private Integer pa08FecAlt;
    @Column(name = "PA08_FEC_BAJ")
    private Integer pa08FecBaj;
    @Column(name = "PA08_HAB_CIV")
    private String pa08HabCiv;

    public Parp008m() {
    }

    public Parp008m(String pa08AbrAlf, String pa08DesCiv, Integer pa08EstCiv, Integer pa08FecAlt, Integer pa08FecBaj,
                    String pa08HabCiv) {
        this.pa08AbrAlf = pa08AbrAlf;
        this.pa08DesCiv = pa08DesCiv;
        this.pa08EstCiv = pa08EstCiv;
        this.pa08FecAlt = pa08FecAlt;
        this.pa08FecBaj = pa08FecBaj;
        this.pa08HabCiv = pa08HabCiv;
    }

    public String getPa08AbrAlf() {
        return pa08AbrAlf;
    }

    public void setPa08AbrAlf(String pa08AbrAlf) {
        this.pa08AbrAlf = pa08AbrAlf;
    }

    public String getPa08DesCiv() {
        return pa08DesCiv;
    }

    public void setPa08DesCiv(String pa08DesCiv) {
        this.pa08DesCiv = pa08DesCiv;
    }

    public Integer getPa08EstCiv() {
        return pa08EstCiv;
    }

    public Integer getPa08FecAlt() {
        return pa08FecAlt;
    }

    public void setPa08FecAlt(Integer pa08FecAlt) {
        this.pa08FecAlt = pa08FecAlt;
    }

    public Integer getPa08FecBaj() {
        return pa08FecBaj;
    }

    public void setPa08FecBaj(Integer pa08FecBaj) {
        this.pa08FecBaj = pa08FecBaj;
    }

    public String getPa08HabCiv() {
        return pa08HabCiv;
    }

    public void setPa08HabCiv(String pa08HabCiv) {
        this.pa08HabCiv = pa08HabCiv;
    }
}
