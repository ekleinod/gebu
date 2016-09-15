package de.edgesoft.gebu.model;

import java.time.LocalDate;

import de.edgesoft.gebu.jaxb.Event;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model extension for model class Event.
 * 
 * This is for a great part an attempt to use property classes for JavaFX integration.
 * Maybe a better approach would be to write adapters, who knows. 
 *
 * ## Legal stuff
 *
 * Copyright 2016-2016 Ekkart Kleinod <ekleinod@edgesoft.de>
 *
 * This file is part of "Das Gebu-Programm".
 *
 * "Das Gebu-Programm" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * "Das Gebu-Programm" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with "Das Gebu-Programm".  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Ekkart Kleinod
 * @version 6.0.0
 * @since 6.0.0
 */
public class EventModel extends Event {

	// undocumented property code (self explanatory to a certain extent)
    private StringProperty titleProperty = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    private StringProperty eventtypeProperty = new SimpleStringProperty();
    private StringProperty categoryProperty = new SimpleStringProperty();
    
    public StringProperty getTitleProperty() {
        titleProperty.set(getTitle());
        return titleProperty;
    }

    public ObjectProperty<LocalDate> getDateProperty() {
    	dateProperty.set(getDate());
    	return dateProperty;
    }
    
    public StringProperty getEventtypeProperty() {
    	eventtypeProperty.set(getEventtype());
        return eventtypeProperty;
    }

    public StringProperty getCategoryProperty() {
    	categoryProperty.set(getCategory());
        return categoryProperty;
    }
	// end of undocumented property code

}

/* EOF */
