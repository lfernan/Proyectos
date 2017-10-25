
package com.oracle.xmlns.autorizarcompra;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="codigoError" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codigoAutorizacion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codigoError", "codigoAutorizacion", "observacion" })
@XmlRootElement(name = "AutorizarCompraProcessResponse")
public class AutorizarCompraProcessResponse {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codigoError;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long codigoAutorizacion;
    @XmlElement(required = true, nillable = true)
    protected String observacion;

    /**
     * Gets the value of the codigoError property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getCodigoError() {
        return codigoError;
    }

    /**
     * Sets the value of the codigoError property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setCodigoError(Integer value) {
        this.codigoError = value;
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
     * Gets the value of the observacion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Sets the value of the observacion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setObservacion(String value) {
        this.observacion = value;
    }

}
