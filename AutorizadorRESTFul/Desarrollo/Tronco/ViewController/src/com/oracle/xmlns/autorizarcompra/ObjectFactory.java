
package com.oracle.xmlns.autorizarcompra;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.oracle.xmlns.autorizarcompra package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AutorizarCompraProcessRequestComercioDesc_QNAME =
        new QName("http://xmlns.oracle.com/AutorizarCompra", "comercioDesc");
    private final static QName _AutorizarCompraProcessRequestCvc_QNAME =
        new QName("http://xmlns.oracle.com/AutorizarCompra", "cvc");
    private final static QName _AutorizarCompraProcessRequestOmitePermiso_QNAME =
        new QName("http://xmlns.oracle.com/AutorizarCompra", "omitePermiso");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle.xmlns.autorizarcompra
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AutorizarCompraProcessRequest }
     *
     */
    public AutorizarCompraProcessRequest createAutorizarCompraProcessRequest() {
        return new AutorizarCompraProcessRequest();
    }

    /**
     * Create an instance of {@link AutorizarCompraProcessResponse }
     *
     */
    public AutorizarCompraProcessResponse createAutorizarCompraProcessResponse() {
        return new AutorizarCompraProcessResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/AutorizarCompra", name = "comercioDesc",
                    scope = AutorizarCompraProcessRequest.class)
    public JAXBElement<String> createAutorizarCompraProcessRequestComercioDesc(String value) {
        return new JAXBElement<String>(_AutorizarCompraProcessRequestComercioDesc_QNAME, String.class,
                                       AutorizarCompraProcessRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/AutorizarCompra", name = "cvc",
                    scope = AutorizarCompraProcessRequest.class)
    public JAXBElement<String> createAutorizarCompraProcessRequestCvc(String value) {
        return new JAXBElement<String>(_AutorizarCompraProcessRequestCvc_QNAME, String.class,
                                       AutorizarCompraProcessRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/AutorizarCompra", name = "omitePermiso",
                    scope = AutorizarCompraProcessRequest.class)
    public JAXBElement<Long> createAutorizarCompraProcessRequestOmitePermiso(Long value) {
        return new JAXBElement<Long>(_AutorizarCompraProcessRequestOmitePermiso_QNAME, Long.class,
                                     AutorizarCompraProcessRequest.class, value);
    }

}
