package view.dto;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;

public class Datos {
    @FormParam("nombre")
    private String nombre;
    @FormParam("ocr")
    private Long ocr;
    @FormParam("dni")
    private Integer dni;
    @FormParam("cvc")
    private Integer cvc;
    @FormParam("expiracion")
    private Integer expiracion;
    @FormParam("token")
    private String token;
    @FormParam("importe") 
    private Double importe;
    @FormParam("tarjeta") 
    private Long tarjeta;
    @FormParam("comercio") 
    private Long comercio;
    @FormParam("plan") 
    private Integer plan;
    @FormParam("moneda") 
    private Integer moneda;
    @FormParam("terminal")
    private String terminal;
    @FormParam("codOperacion")
    private Long codOperacion;

    @HeaderParam("Content-Type")
    private String contentType;

    public Datos() {
        super();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setOcr(Long ocr) {
        this.ocr = ocr;
    }

    public Long getOcr() {
        return ocr;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Integer getDni() {
        return dni;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setExpiracion(Integer expiracion) {
        this.expiracion = expiracion;
    }

    public Integer getExpiracion() {
        return expiracion;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getImporte() {
        return importe;
    }

    public void setTarjeta(Long tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Long getTarjeta() {
        return tarjeta;
    }

    public void setComercio(Long comercio) {
        this.comercio = comercio;
    }

    public Long getComercio() {
        return comercio;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setMoneda(Integer moneda) {
        this.moneda = moneda;
    }

    public Integer getMoneda() {
        return moneda;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setCodOperacion(Long codOperacion) {
        this.codOperacion = codOperacion;
    }

    public Long getCodOperacion() {
        return codOperacion;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
