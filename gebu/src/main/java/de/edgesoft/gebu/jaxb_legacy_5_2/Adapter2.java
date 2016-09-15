
package de.edgesoft.gebu.jaxb_legacy_5_2;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, LocalDate>
{


    public LocalDate unmarshal(String value) {
        return (de.edgesoft.gebu.model_legacy_5_2.LocalDateAdapter.unmarshal(value));
    }

    public String marshal(LocalDate value) {
        return (de.edgesoft.gebu.model_legacy_5_2.LocalDateAdapter.marshal(value));
    }

}
