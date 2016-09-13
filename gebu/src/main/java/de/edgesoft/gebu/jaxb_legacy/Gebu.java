
package de.edgesoft.gebu.jaxb_legacy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import de.edgesoft.edgeutils.commons.ModelClass;


/**
 * <p>Java class for Gebu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Gebu">
 *   &lt;complexContent>
 *     &lt;extension base="{}ModelClass">
 *       &lt;sequence>
 *         &lt;element name="program" type="{}Program"/>
 *         &lt;element name="data" type="{}Data"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}base"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Gebu", propOrder = {
    "program",
    "data"
})
public class Gebu
    extends ModelClass
{

    @XmlElement(required = true)
    protected Program program;
    @XmlElement(required = true)
    protected Data data;
    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlSchemaType(name = "anyURI")
    protected String base;

    /**
     * Gets the value of the program property.
     * 
     * @return
     *     possible object is
     *     {@link Program }
     *     
     */
    public Program getProgram() {
        return program;
    }

    /**
     * Sets the value of the program property.
     * 
     * @param value
     *     allowed object is
     *     {@link Program }
     *     
     */
    public void setProgram(Program value) {
        this.program = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link Data }
     *     
     */
    public Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link Data }
     *     
     */
    public void setData(Data value) {
        this.data = value;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBase(String value) {
        this.base = value;
    }

}
