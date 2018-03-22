package com.tnevada.model.entidades;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Sucursales")
public class Sucursales implements Serializable {
    @Column(name="SUC_ACTIVA", nullable = false)
    private String sucActiva;
    @Column(name="SUC_COD_POSTAL", nullable = false, length = 10)
    private String sucCodPostal;
    @Column(name="SUC_DENOMINACION", nullable = false, length = 100)
    private String sucDenominacion;
    @Column(name="SUC_DOMICILIO", nullable = false, length = 100)
    private String sucDomicilio;
    @Column(name="SUC_FECHA_ALTA", nullable = false)
    private Timestamp sucFechaAlta;
    @Column(name="SUC_FECHA_BAJA")
    private Timestamp sucFechaBaja;
    @Column(name="SUC_FECHA_FIN")
    private Timestamp sucFechaFin;
    @Column(name="SUC_FECHA_INICIO", nullable = false)
    private Timestamp sucFechaInicio;
    @Column(name="SUC_FECHA_ULT_MOD")
    private Timestamp sucFechaUltMod;
    @Id
    @Column(name="SUC_ID", nullable = false)
    private Long sucId;
    @Column(name="SUC_ID_SUB_CUENTA", nullable = false, length = 12)
    private String sucIdSubCuenta;
    @Column(name="SUC_IP", length = 15)
    private String sucIp;
    @Column(name="SUC_NRO_VIEJO", nullable = false)
    private Long sucNroViejo;
    @Column(name="SUC_NUMERO", nullable = false)
    private Long sucNumero;
    @Column(name="SUC_PROVINCIA", nullable = false)
    private String sucProvincia;
    @Column(name="SUC_SUB_CUENTA_PADRE", nullable = false, length = 12)
    private String sucSubCuentaPadre;
    @Column(name="SUC_TELEFONO", length = 20)
    private String sucTelefono;
    @Column(name="SUC_TIPO", nullable = false, length = 1)
    private String sucTipo;
    @Column(name="SUC_USUARIO_ALTA", nullable = false, length = 10)
    private String sucUsuarioAlta;
    @Column(name="SUC_USUARIO_BAJA", length = 10)
    private String sucUsuarioBaja;
    @Column(name="SUC_USUARIO_ULT_MOD", length = 10)
    private String sucUsuarioUltMod;

    public Sucursales() {
    }

    public Sucursales(String sucActiva, String sucCodPostal,
                      String sucDenominacion, String sucDomicilio,
                      Timestamp sucFechaAlta, Timestamp sucFechaBaja,
                      Timestamp sucFechaFin, Timestamp sucFechaInicio,
                      Timestamp sucFechaUltMod, Long sucId,
                      String sucIdSubCuenta, String sucIp, Long sucNroViejo,
                      Long sucNumero, String sucProvincia,
                      String sucSubCuentaPadre, String sucTelefono,
                      String sucTipo, String sucUsuarioAlta,
                      String sucUsuarioBaja, String sucUsuarioUltMod) {
        this.sucActiva = sucActiva;
        this.sucCodPostal = sucCodPostal;
        this.sucDenominacion = sucDenominacion;
        this.sucDomicilio = sucDomicilio;
        this.sucFechaAlta = sucFechaAlta;
        this.sucFechaBaja = sucFechaBaja;
        this.sucFechaFin = sucFechaFin;
        this.sucFechaInicio = sucFechaInicio;
        this.sucFechaUltMod = sucFechaUltMod;
        this.sucId = sucId;
        this.sucIdSubCuenta = sucIdSubCuenta;
        this.sucIp = sucIp;
        this.sucNroViejo = sucNroViejo;
        this.sucNumero = sucNumero;
        this.sucProvincia = sucProvincia;
        this.sucSubCuentaPadre = sucSubCuentaPadre;
        this.sucTelefono = sucTelefono;
        this.sucTipo = sucTipo;
        this.sucUsuarioAlta = sucUsuarioAlta;
        this.sucUsuarioBaja = sucUsuarioBaja;
        this.sucUsuarioUltMod = sucUsuarioUltMod;
    }

    public String getSucActiva() {
        return sucActiva;
    }

    public void setSucActiva(String sucActiva) {
        this.sucActiva = sucActiva;
    }

    public String getSucCodPostal() {
        return sucCodPostal;
    }

    public void setSucCodPostal(String sucCodPostal) {
        this.sucCodPostal = sucCodPostal;
    }

    public String getSucDenominacion() {
        return sucDenominacion;
    }

    public void setSucDenominacion(String sucDenominacion) {
        this.sucDenominacion = sucDenominacion;
    }

    public String getSucDomicilio() {
        return sucDomicilio;
    }

    public void setSucDomicilio(String sucDomicilio) {
        this.sucDomicilio = sucDomicilio;
    }

    public Timestamp getSucFechaAlta() {
        return sucFechaAlta;
    }

    public void setSucFechaAlta(Timestamp sucFechaAlta) {
        this.sucFechaAlta = sucFechaAlta;
    }

    public Timestamp getSucFechaBaja() {
        return sucFechaBaja;
    }

    public void setSucFechaBaja(Timestamp sucFechaBaja) {
        this.sucFechaBaja = sucFechaBaja;
    }

    public Timestamp getSucFechaFin() {
        return sucFechaFin;
    }

    public void setSucFechaFin(Timestamp sucFechaFin) {
        this.sucFechaFin = sucFechaFin;
    }

    public Timestamp getSucFechaInicio() {
        return sucFechaInicio;
    }

    public void setSucFechaInicio(Timestamp sucFechaInicio) {
        this.sucFechaInicio = sucFechaInicio;
    }

    public Timestamp getSucFechaUltMod() {
        return sucFechaUltMod;
    }

    public void setSucFechaUltMod(Timestamp sucFechaUltMod) {
        this.sucFechaUltMod = sucFechaUltMod;
    }

    public Long getSucId() {
        return sucId;
    }

    public void setSucId(Long sucId) {
        this.sucId = sucId;
    }

    public String getSucIdSubCuenta() {
        return sucIdSubCuenta;
    }

    public void setSucIdSubCuenta(String sucIdSubCuenta) {
        this.sucIdSubCuenta = sucIdSubCuenta;
    }

    public String getSucIp() {
        return sucIp;
    }

    public void setSucIp(String sucIp) {
        this.sucIp = sucIp;
    }

    public Long getSucNroViejo() {
        return sucNroViejo;
    }

    public void setSucNroViejo(Long sucNroViejo) {
        this.sucNroViejo = sucNroViejo;
    }

    public Long getSucNumero() {
        return sucNumero;
    }

    public void setSucNumero(Long sucNumero) {
        this.sucNumero = sucNumero;
    }

    public String getSucProvincia() {
        return sucProvincia;
    }

    public void setSucProvincia(String sucProvincia) {
        this.sucProvincia = sucProvincia;
    }

    public String getSucSubCuentaPadre() {
        return sucSubCuentaPadre;
    }

    public void setSucSubCuentaPadre(String sucSubCuentaPadre) {
        this.sucSubCuentaPadre = sucSubCuentaPadre;
    }

    public String getSucTelefono() {
        return sucTelefono;
    }

    public void setSucTelefono(String sucTelefono) {
        this.sucTelefono = sucTelefono;
    }

    public String getSucTipo() {
        return sucTipo;
    }

    public void setSucTipo(String sucTipo) {
        this.sucTipo = sucTipo;
    }

    public String getSucUsuarioAlta() {
        return sucUsuarioAlta;
    }

    public void setSucUsuarioAlta(String sucUsuarioAlta) {
        this.sucUsuarioAlta = sucUsuarioAlta;
    }

    public String getSucUsuarioBaja() {
        return sucUsuarioBaja;
    }

    public void setSucUsuarioBaja(String sucUsuarioBaja) {
        this.sucUsuarioBaja = sucUsuarioBaja;
    }

    public String getSucUsuarioUltMod() {
        return sucUsuarioUltMod;
    }

    public void setSucUsuarioUltMod(String sucUsuarioUltMod) {
        this.sucUsuarioUltMod = sucUsuarioUltMod;
    }
}
