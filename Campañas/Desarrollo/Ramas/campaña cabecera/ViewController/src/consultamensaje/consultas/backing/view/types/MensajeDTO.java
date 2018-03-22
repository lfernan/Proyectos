
package consultamensaje.consultas.backing.view.types;

import com.tnevada.model.util.Util;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MensajeDTO complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MensajeDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="visitadorNombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaDeCompromiso" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="visitadorLegajo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localDelUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="usuarioNombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="importancia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="clasificacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoDeOrigen" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="horaDeCarga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usuarioLegajo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MensajeDTO", propOrder = {
         "visitadorNombre", "fechaDeCompromiso", "visitadorLegajo", "fecha", "usuario", "localDelUsuario", "mensaje",
         "referencia", "usuarioNombre", "importancia", "clasificacion", "tipoDeOrigen", "horaDeCarga", "usuarioLegajo"
    })
public class MensajeDTO implements Serializable{
    @SuppressWarnings("compatibility:-5000485634873905138")
    private static final long serialVersionUID = 1L;

    @XmlElement(required = true, nillable = true)
    protected String visitadorNombre;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaDeCompromiso;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long visitadorLegajo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElement(required = true, nillable = true)
    protected String usuario;
    @XmlElement(required = true, nillable = true)
    protected String localDelUsuario;
    @XmlElement(required = true, nillable = true)
    protected String mensaje;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long referencia;
    @XmlElement(required = true, nillable = true)
    protected String usuarioNombre;
    @XmlElement(required = true, nillable = true)
    protected String importancia;
    @XmlElement(required = true, nillable = true)
    protected String clasificacion;
    @XmlElement(required = true, nillable = true)
    protected String tipoDeOrigen;
    @XmlElement(required = true, nillable = true)
    protected String horaDeCarga;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long usuarioLegajo;

    /**
     * Gets the value of the visitadorNombre property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVisitadorNombre() {
        return visitadorNombre;
    }

    /**
     * Sets the value of the visitadorNombre property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVisitadorNombre(String value) {
        this.visitadorNombre = value;
    }

    /**
     * Gets the value of the fechaDeCompromiso property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFechaDeCompromiso() {
        return fechaDeCompromiso;
    }

    /**
     * Sets the value of the fechaDeCompromiso property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setFechaDeCompromiso(XMLGregorianCalendar value) {
        this.fechaDeCompromiso = value;
    }

    /**
     * Gets the value of the visitadorLegajo property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getVisitadorLegajo() {
        return visitadorLegajo;
    }

    /**
     * Sets the value of the visitadorLegajo property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setVisitadorLegajo(Long value) {
        this.visitadorLegajo = value;
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
     * Gets the value of the usuario property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the localDelUsuario property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLocalDelUsuario() {
        return localDelUsuario;
    }

    /**
     * Sets the value of the localDelUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLocalDelUsuario(String value) {
        this.localDelUsuario = value;
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
     * Gets the value of the referencia property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getReferencia() {
        return referencia;
    }

    /**
     * Sets the value of the referencia property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setReferencia(Long value) {
        this.referencia = value;
    }

    /**
     * Gets the value of the usuarioNombre property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    /**
     * Sets the value of the usuarioNombre property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUsuarioNombre(String value) {
        this.usuarioNombre = value;
    }

    /**
     * Gets the value of the importancia property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getImportancia() {
        return importancia;
    }

    /**
     * Sets the value of the importancia property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setImportancia(String value) {
        this.importancia = value;
    }

    /**
     * Gets the value of the clasificacion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getClasificacion() {
        return clasificacion;
    }

    /**
     * Sets the value of the clasificacion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setClasificacion(String value) {
        this.clasificacion = value;
    }

    /**
     * Gets the value of the tipoDeOrigen property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTipoDeOrigen() {
        return tipoDeOrigen;
    }

    /**
     * Sets the value of the tipoDeOrigen property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTipoDeOrigen(String value) {
        this.tipoDeOrigen = value;
    }

    /**
     * Gets the value of the horaDeCarga property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getHoraDeCarga() {
        return horaDeCarga;
    }

    /**
     * Sets the value of the horaDeCarga property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setHoraDeCarga(String value) {
        this.horaDeCarga = value;
    }

    /**
     * Gets the value of the usuarioLegajo property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getUsuarioLegajo() {
        return usuarioLegajo;
    }

    /**
     * Sets the value of the usuarioLegajo property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setUsuarioLegajo(Long value) {
        this.usuarioLegajo = value;
    }

    public String getFechaFormateada() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, fecha.getYear());
        cal.set(Calendar.MONTH, fecha.getMonth() - 1);
        cal.set(Calendar.DATE, fecha.getDay());
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    
    public String getHoraFormateada(){
        return Util.formatearHora(Long.valueOf(horaDeCarga));
    }
}
