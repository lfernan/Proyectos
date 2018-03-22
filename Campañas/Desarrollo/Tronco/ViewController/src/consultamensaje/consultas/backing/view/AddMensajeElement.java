
package consultamensaje.consultas.backing.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="dni" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codUsu" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ocod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="odes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="icod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ides" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ccod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cdes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cfc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idCampania" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="fComp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
         "dni", "codUsu", "ocod", "odes", "icod", "ides", "ccod", "cdes", "cfc", "mensaje", "idCampania", "fComp"
    })
@XmlRootElement(name = "addMensajeElement")
public class AddMensajeElement {

    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long dni;
    @XmlElement(required = true, nillable = true)
    protected String codUsu;
    @XmlElement(required = true, nillable = true)
    protected String ocod;
    @XmlElement(required = true, nillable = true)
    protected String odes;
    @XmlElement(required = true, nillable = true)
    protected String icod;
    @XmlElement(required = true, nillable = true)
    protected String ides;
    @XmlElement(required = true, nillable = true)
    protected String ccod;
    @XmlElement(required = true, nillable = true)
    protected String cdes;
    @XmlElement(required = true, nillable = true)
    protected String cfc;
    @XmlElement(required = true, nillable = true)
    protected String mensaje;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long idCampania;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fComp;

    /**
     * Gets the value of the dni property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getDni() {
        return dni;
    }

    /**
     * Sets the value of the dni property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setDni(Long value) {
        this.dni = value;
    }

    /**
     * Gets the value of the codUsu property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCodUsu() {
        return codUsu;
    }

    /**
     * Sets the value of the codUsu property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCodUsu(String value) {
        this.codUsu = value;
    }

    /**
     * Gets the value of the ocod property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOcod() {
        return ocod;
    }

    /**
     * Sets the value of the ocod property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOcod(String value) {
        this.ocod = value;
    }

    /**
     * Gets the value of the odes property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOdes() {
        return odes;
    }

    /**
     * Sets the value of the odes property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOdes(String value) {
        this.odes = value;
    }

    /**
     * Gets the value of the icod property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIcod() {
        return icod;
    }

    /**
     * Sets the value of the icod property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIcod(String value) {
        this.icod = value;
    }

    /**
     * Gets the value of the ides property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdes() {
        return ides;
    }

    /**
     * Sets the value of the ides property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdes(String value) {
        this.ides = value;
    }

    /**
     * Gets the value of the ccod property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCcod() {
        return ccod;
    }

    /**
     * Sets the value of the ccod property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCcod(String value) {
        this.ccod = value;
    }

    /**
     * Gets the value of the cdes property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCdes() {
        return cdes;
    }

    /**
     * Sets the value of the cdes property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCdes(String value) {
        this.cdes = value;
    }

    /**
     * Gets the value of the cfc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCfc() {
        return cfc;
    }

    /**
     * Sets the value of the cfc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCfc(String value) {
        this.cfc = value;
    }

    /**
     * Gets the value of the mensaje property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the idCampania property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getIdCampania() {
        return idCampania;
    }

    /**
     * Sets the value of the idCampania property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setIdCampania(Long value) {
        this.idCampania = value;
    }

    /**
     * Gets the value of the fComp property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFComp() {
        return fComp;
    }

    /**
     * Sets the value of the fComp property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setFComp(XMLGregorianCalendar value) {
        this.fComp = value;
    }

}
