
package de.edgesoft.gebu.jaxb_legacy_5_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.edgesoft.gebu.jaxb_legacy_5_2 package. 
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

    private final static QName _Gebu_QNAME = new QName("", "gebu");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.edgesoft.gebu.jaxb_legacy_5_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Gebu }
     * 
     */
    public Gebu createGebu() {
        return new Gebu();
    }

    /**
     * Create an instance of {@link Program }
     * 
     */
    public Program createProgram() {
        return new Program();
    }

    /**
     * Create an instance of {@link Data }
     * 
     */
    public Data createData() {
        return new Data();
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Gebu }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "gebu")
    public JAXBElement<Gebu> createGebu(Gebu value) {
        return new JAXBElement<Gebu>(_Gebu_QNAME, Gebu.class, null, value);
    }

}
