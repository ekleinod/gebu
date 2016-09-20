
package de.edgesoft.gebu.jaxb;

import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, SimpleObjectProperty>
{


    public SimpleObjectProperty unmarshal(String value) {
        return (de.edgesoft.edgeutils.javafx.SimpleObjectPropertyLocalDateAdapter.unmarshal(value));
    }

    public String marshal(SimpleObjectProperty value) {
        return (de.edgesoft.edgeutils.javafx.SimpleObjectPropertyLocalDateAdapter.marshal(value));
    }

}
