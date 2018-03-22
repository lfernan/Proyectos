package com.tnevada.model.entidades;

import java.io.Serializable;

import java.util.List;

public class NoSociosDTO implements Serializable{
    @SuppressWarnings("compatibility:4988716976502157715")
    private static final long serialVersionUID = 1L;
    private Long cansId;
    private String cansApellidos;
    private String cansCondicionLaboral;    
    private String cansDomicilio;    
    private String cansEmail;    
    private String cansEstadoCivil;    
    private String cansFechaNacimiento;    
    private String cansIngresoPromedio;    
    private String cansNombres;    
    private String cansNroDocumento;    
    private String cansNroTel1;
    private String cansNroTel2;    
    private String cansNroTel3;    
    private String cansNroTel4;    
    private String cansProvincia;    
    private String cansSexo;    
    private String cansSucursal;    
    private String cansTipoDocumento;
    private List<MensajeDTO> mensajes;
    
    public NoSociosDTO() {
        super();
    }

    public NoSociosDTO(Long cansId, String cansApellidos, String cansCondicionLaboral, String cansDomicilio,
                       String cansEmail, String cansEstadoCivil, String cansFechaNacimiento, String cansIngresoPromedio,
                       String cansNombres, String cansNroDocumento, String cansNroTel1, String cansNroTel2,
                       String cansNroTel3, String cansNroTel4, String cansProvincia, String cansSexo,
                       String cansSucursal, String cansTipoDocumento, List<MensajeDTO> mensajes) {
        super();
        this.cansId = cansId;
        this.cansApellidos = cansApellidos;
        this.cansCondicionLaboral = cansCondicionLaboral;
        this.cansDomicilio = cansDomicilio;
        this.cansEmail = cansEmail;
        this.cansEstadoCivil = cansEstadoCivil;
        this.cansFechaNacimiento = cansFechaNacimiento;
        this.cansIngresoPromedio = cansIngresoPromedio;
        this.cansNombres = cansNombres;
        this.cansNroDocumento = cansNroDocumento;
        this.cansNroTel1 = cansNroTel1;
        this.cansNroTel2 = cansNroTel2;
        this.cansNroTel3 = cansNroTel3;
        this.cansNroTel4 = cansNroTel4;
        this.cansProvincia = cansProvincia;
        this.cansSexo = cansSexo;
        this.cansSucursal = cansSucursal;
        this.cansTipoDocumento = cansTipoDocumento;
        this.mensajes = mensajes;
    }

    public void setCansId(Long cansId) {
        this.cansId = cansId;
    }

    public Long getCansId() {
        return cansId;
    }

    public void setCansApellidos(String cansApellidos) {
        this.cansApellidos = cansApellidos;
    }

    public String getCansApellidos() {
        return cansApellidos;
    }

    public void setCansCondicionLaboral(String cansCondicionLaboral) {
        this.cansCondicionLaboral = cansCondicionLaboral;
    }

    public String getCansCondicionLaboral() {
        return cansCondicionLaboral;
    }

    public void setCansDomicilio(String cansDomicilio) {
        this.cansDomicilio = cansDomicilio;
    }

    public String getCansDomicilio() {
        return cansDomicilio;
    }

    public void setCansEmail(String cansEmail) {
        this.cansEmail = cansEmail;
    }

    public String getCansEmail() {
        return cansEmail;
    }

    public void setCansEstadoCivil(String cansEstadoCivil) {
        this.cansEstadoCivil = cansEstadoCivil;
    }

    public String getCansEstadoCivil() {
        return cansEstadoCivil;
    }

    public void setCansFechaNacimiento(String cansFechaNacimiento) {
        this.cansFechaNacimiento = cansFechaNacimiento;
    }

    public String getCansFechaNacimiento() {
        return cansFechaNacimiento;
    }

    public void setCansIngresoPromedio(String cansIngresoPromedio) {
        this.cansIngresoPromedio = cansIngresoPromedio;
    }

    public String getCansIngresoPromedio() {
        return cansIngresoPromedio;
    }

    public void setCansNombres(String cansNombres) {
        this.cansNombres = cansNombres;
    }

    public String getCansNombres() {
        return cansNombres;
    }

    public void setCansNroDocumento(String cansNroDocumento) {
        this.cansNroDocumento = cansNroDocumento;
    }

    public String getCansNroDocumento() {
        return cansNroDocumento;
    }

    public void setCansNroTel1(String cansNroTel1) {
        this.cansNroTel1 = cansNroTel1;
    }

    public String getCansNroTel1() {
        return cansNroTel1;
    }

    public void setCansNroTel2(String cansNroTel2) {
        this.cansNroTel2 = cansNroTel2;
    }

    public String getCansNroTel2() {
        return cansNroTel2;
    }

    public void setCansNroTel3(String cansNroTel3) {
        this.cansNroTel3 = cansNroTel3;
    }

    public String getCansNroTel3() {
        return cansNroTel3;
    }

    public void setCansNroTel4(String cansNroTel4) {
        this.cansNroTel4 = cansNroTel4;
    }

    public String getCansNroTel4() {
        return cansNroTel4;
    }

    public void setCansProvincia(String cansProvincia) {
        this.cansProvincia = cansProvincia;
    }

    public String getCansProvincia() {
        return cansProvincia;
    }

    public void setCansSexo(String cansSexo) {
        this.cansSexo = cansSexo;
    }

    public String getCansSexo() {
        return cansSexo;
    }

    public void setCansSucursal(String cansSucursal) {
        this.cansSucursal = cansSucursal;
    }

    public String getCansSucursal() {
        return cansSucursal;
    }

    public void setCansTipoDocumento(String cansTipoDocumento) {
        this.cansTipoDocumento = cansTipoDocumento;
    }

    public String getCansTipoDocumento() {
        return cansTipoDocumento;
    }

    public void setMensajes(List<MensajeDTO> mensajes) {
        this.mensajes = mensajes;
    }

    public List<MensajeDTO> getMensajes() {
        return mensajes;
    }
}
