
package com.oracle.xmlns.autorizarcompra;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tarjeta" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="comercio" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="plan" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="moneda" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="terminal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nroTransaccion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="diasDiferimiento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="legajo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tipoAutorizador" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="marcaLecturaPorBandaMagnetica" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="marcaTransaccionOffLine" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="marcaLlamadaConTarjeta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codigoAutorizacion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="omitePermiso" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="cvc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comercioDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
         "tarjeta", "comercio", "fecha", "importe", "plan", "moneda", "terminal", "nroTransaccion", "diasDiferimiento",
         "legajo", "tipoAutorizador", "marcaLecturaPorBandaMagnetica", "marcaTransaccionOffLine",
         "marcaLlamadaConTarjeta", "codigoAutorizacion", "omitePermiso", "cvc", "comercioDesc"
    })
@XmlRootElement(name = "AutorizarCompraProcessRequest")
public class AutorizarCompraProcessRequest {

    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long tarjeta;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long comercio;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double importe;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer plan;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer moneda;
    @XmlElement(required = true, nillable = true)
    protected String terminal;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long nroTransaccion;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer diasDiferimiento;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer legajo;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer tipoAutorizador;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer marcaLecturaPorBandaMagnetica;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer marcaTransaccionOffLine;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer marcaLlamadaConTarjeta;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long codigoAutorizacion;
    @XmlElementRef(name = "omitePermiso", namespace = "http://xmlns.oracle.com/AutorizarCompra",
                   type = JAXBElement.class, required = false)
    protected JAXBElement<Long> omitePermiso;
    @XmlElementRef(name = "cvc", namespace = "http://xmlns.oracle.com/AutorizarCompra", type = JAXBElement.class,
                   required = false)
    protected JAXBElement<String> cvc;
    @XmlElementRef(name = "comercioDesc", namespace = "http://xmlns.oracle.com/AutorizarCompra",
                   type = JAXBElement.class, required = false)
    protected JAXBElement<String> comercioDesc;

    /**
     * Gets the value of the tarjeta property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getTarjeta() {
        return tarjeta;
    }

    /**
     * Sets the value of the tarjeta property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setTarjeta(Long value) {
        this.tarjeta = value;
    }

    /**
     * Gets the value of the comercio property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getComercio() {
        return comercio;
    }

    /**
     * Sets the value of the comercio property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setComercio(Long value) {
        this.comercio = value;
    }

    /**
     * Gets the value of the fecha property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the importe property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getImporte() {
        return importe;
    }

    /**
     * Sets the value of the importe property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setImporte(Double value) {
        this.importe = value;
    }

    /**
     * Gets the value of the plan property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getPlan() {
        return plan;
    }

    /**
     * Sets the value of the plan property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setPlan(Integer value) {
        this.plan = value;
    }

    /**
     * Gets the value of the moneda property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMoneda() {
        return moneda;
    }

    /**
     * Sets the value of the moneda property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMoneda(Integer value) {
        this.moneda = value;
    }

    /**
     * Gets the value of the terminal property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * Sets the value of the terminal property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTerminal(String value) {
        this.terminal = value;
    }

    /**
     * Gets the value of the nroTransaccion property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getNroTransaccion() {
        return nroTransaccion;
    }

    /**
     * Sets the value of the nroTransaccion property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setNroTransaccion(Long value) {
        this.nroTransaccion = value;
    }

    /**
     * Gets the value of the diasDiferimiento property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getDiasDiferimiento() {
        return diasDiferimiento;
    }

    /**
     * Sets the value of the diasDiferimiento property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setDiasDiferimiento(Integer value) {
        this.diasDiferimiento = value;
    }

    /**
     * Gets the value of the legajo property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getLegajo() {
        return legajo;
    }

    /**
     * Sets the value of the legajo property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setLegajo(Integer value) {
        this.legajo = value;
    }

    /**
     * Gets the value of the tipoAutorizador property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getTipoAutorizador() {
        return tipoAutorizador;
    }

    /**
     * Sets the value of the tipoAutorizador property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setTipoAutorizador(Integer value) {
        this.tipoAutorizador = value;
    }

    /**
     * Gets the value of the marcaLecturaPorBandaMagnetica property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMarcaLecturaPorBandaMagnetica() {
        return marcaLecturaPorBandaMagnetica;
    }

    /**
     * Sets the value of the marcaLecturaPorBandaMagnetica property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMarcaLecturaPorBandaMagnetica(Integer value) {
        this.marcaLecturaPorBandaMagnetica = value;
    }

    /**
     * Gets the value of the marcaTransaccionOffLine property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMarcaTransaccionOffLine() {
        return marcaTransaccionOffLine;
    }

    /**
     * Sets the value of the marcaTransaccionOffLine property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMarcaTransaccionOffLine(Integer value) {
        this.marcaTransaccionOffLine = value;
    }

    /**
     * Gets the value of the marcaLlamadaConTarjeta property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMarcaLlamadaConTarjeta() {
        return marcaLlamadaConTarjeta;
    }

    /**
     * Sets the value of the marcaLlamadaConTarjeta property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMarcaLlamadaConTarjeta(Integer value) {
        this.marcaLlamadaConTarjeta = value;
    }

    /**
     * Gets the value of the codigoAutorizacion property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    /**
     * Sets the value of the codigoAutorizacion property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setCodigoAutorizacion(Long value) {
        this.codigoAutorizacion = value;
    }

    /**
     * Gets the value of the omitePermiso property.
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *
     */
    public JAXBElement<Long> getOmitePermiso() {
        return omitePermiso;
    }

    /**
     * Sets the value of the omitePermiso property.
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *
     */
    public void setOmitePermiso(JAXBElement<Long> value) {
        this.omitePermiso = value;
    }

    /**
     * Gets the value of the cvc property.
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getCvc() {
        return cvc;
    }

    /**
     * Sets the value of the cvc property.
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setCvc(JAXBElement<String> value) {
        this.cvc = value;
    }

    /**
     * Gets the value of the comercioDesc property.
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getComercioDesc() {
        return comercioDesc;
    }

    /**
     * Sets the value of the comercioDesc property.
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setComercioDesc(JAXBElement<String> value) {
        this.comercioDesc = value;
    }

}
