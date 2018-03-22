package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAMPANIAS_BASE")
public class CampaniasBase implements Serializable {
    private static final long serialVersionUID = 8216365049912336181L;
    @Id
    @Column(name = "BASE_ID", nullable = false)
    private Long baseId;
    @Column(name = "BASE_ACTIVADO", length = 1)
    private String baseActivado;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "BASE_CAMP_ID")
    private Campanias campania;
    @Column(name = "BASE_CARTERA", length = 200)
    private String baseCartera;
    @Temporal(TemporalType.DATE)
    @Column(name = "BASE_FECHA_CARGA")
    private Date baseFechaCarga;
    @Temporal(TemporalType.DATE)
    @Column(name = "BASE_FECHA_GESTION")
    private Date baseFechaGestion;
    @Column(name = "BASE_GESTION", length = 1)
    private String baseGestion;    
    @Column(name = "BASE_IDENTIFICACION", length = 13)
    private String baseIdentificacion;
    @Column(name = "BASE_NOMBRE", length = 2000)
    private String baseNombre;
    @Column(name = "BASE_PERIODO")
    private Long basePeriodo;
    @Column(name = "BASE_USUARIO_GESTION")
    private Integer baseUsuarioGestion;
    @Column(name = "BASE_VIA_INGRESO")
    private Integer baseViaIngreso;
    @Column(name = "BASE_DATOS")
    private String baseDatos;
    
    public CampaniasBase(){
    }
    
    public CampaniasBase(Long baseId, String baseActivado, Campanias campania, String baseCartera, Date baseFechaCarga,
                         Date baseFechaGestion, String baseGestion, String baseIdentificacion, String baseNombre,
                         Long basePeriodo, Integer baseUsuarioGestion, Integer baseViaIngreso, String baseDatos) {
        super();
        this.baseId = baseId;
        this.baseActivado = baseActivado;
        this.campania = campania;
        this.baseCartera = baseCartera;
        this.baseFechaCarga = baseFechaCarga;
        this.baseFechaGestion = baseFechaGestion;
        this.baseGestion = baseGestion;
        this.baseIdentificacion = baseIdentificacion;
        this.baseNombre = baseNombre;
        this.basePeriodo = basePeriodo;
        this.baseUsuarioGestion = baseUsuarioGestion;
        this.baseViaIngreso = baseViaIngreso;
        this.baseDatos = baseDatos;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setBaseActivado(String baseActivado) {
        this.baseActivado = baseActivado;
    }

    public String getBaseActivado() {
        return baseActivado;
    }

    public void setCampania(Campanias campania) {
        this.campania = campania;
    }

    public Campanias getCampania() {
        return campania;
    }

    public void setBaseCartera(String baseCartera) {
        this.baseCartera = baseCartera;
    }

    public String getBaseCartera() {
        return baseCartera;
    }

    public void setBaseFechaCarga(Date baseFechaCarga) {
        this.baseFechaCarga = baseFechaCarga;
    }

    public Date getBaseFechaCarga() {
        return baseFechaCarga;
    }

    public void setBaseFechaGestion(Date baseFechaGestion) {
        this.baseFechaGestion = baseFechaGestion;
    }

    public Date getBaseFechaGestion() {
        return baseFechaGestion;
    }

    public void setBaseGestion(String baseGestion) {
        this.baseGestion = baseGestion;
    }

    public String getBaseGestion() {
        return baseGestion;
    }

    public void setBaseIdentificacion(String baseIdentificacion) {
        this.baseIdentificacion = baseIdentificacion;
    }

    public String getBaseIdentificacion() {
        return baseIdentificacion;
    }

    public void setBaseNombre(String baseNombre) {
        this.baseNombre = baseNombre;
    }

    public String getBaseNombre() {
        return baseNombre;
    }

    public void setBasePeriodo(Long basePeriodo) {
        this.basePeriodo = basePeriodo;
    }

    public Long getBasePeriodo() {
        return basePeriodo;
    }

    public void setBaseUsuarioGestion(Integer baseUsuarioGestion) {
        this.baseUsuarioGestion = baseUsuarioGestion;
    }

    public Integer getBaseUsuarioGestion() {
        return baseUsuarioGestion;
    }

    public void setBaseViaIngreso(Integer baseViaIngreso) {
        this.baseViaIngreso = baseViaIngreso;
    }

    public Integer getBaseViaIngreso() {
        return baseViaIngreso;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public String getBaseDatos() {
        return baseDatos;
    }
}
