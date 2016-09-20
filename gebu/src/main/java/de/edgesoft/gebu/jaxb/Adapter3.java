
package de.edgesoft.gebu.jaxb;

import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter3
    extends XmlAdapter<String, SimpleStringProperty>
{


    public SimpleStringProperty unmarshal(String value) {
        return (de.edgesoft.edgeutils.javafx.SimpleStringPropertyAdapter.unmarshal(value));
    }

    public String marshal(SimpleStringProperty value) {
        return (de.edgesoft.edgeutils.javafx.SimpleStringPropertyAdapter.marshal(value));
    }

}
