
package de.edgesoft.gebu.jaxb_legacy;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Integer>
{


    public Integer unmarshal(String value) {
        return (javax.xml.bind.DatatypeConverter.parseInt(value));
    }

    public String marshal(Integer value) {
        if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printInt(value));
    }

}
