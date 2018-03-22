
package consultamensaje.consultas.backing.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.oracle.webservices.internal.literal.Collection;


/**
 * <p>Java class for MensajePacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MensajePacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idReferencia" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="lista" type="{http://www.oracle.com/webservices/internal/literal}collection"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MensajePacket", propOrder = { "estado", "idReferencia", "lista", "mensaje" })
public class MensajePacket {

    @XmlElement(required = true, nillable = true)
    protected String estado;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long idReferencia;
    @XmlElement(required = true, nillable = true)
    protected Collection lista;
    @XmlElement(required = true, nillable = true)
    protected String mensaje;

    /**
     * Gets the value of the estado property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the idReferencia property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getIdReferencia() {
        return idReferencia;
    }

    /**
     * Sets the value of the idReferencia property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setIdReferencia(Long value) {
        this.idReferencia = value;
    }

    /**
     * Gets the value of the lista property.
     *
     * @return
     *     possible object is
     *     {@link Collection }
     *
     */
    public Collection getLista() {
        return lista;
    }

    /**
     * Sets the value of the lista property.
     *
     * @param value
     *     allowed object is
     *     {@link Collection }
     *
     */
    public void setLista(Collection value) {
        this.lista = value;
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

}
