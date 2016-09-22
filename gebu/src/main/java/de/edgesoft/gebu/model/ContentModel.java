package de.edgesoft.gebu.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlTransient;

import de.edgesoft.gebu.jaxb.Content;
import de.edgesoft.gebu.jaxb.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model extension for model class Content.
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
public class ContentModel extends Content {
	
    /**
     * Observable list of events (singleton). 
     * 
	 * @version 6.0.0
	 * @since 6.0.0
     */
	@XmlTransient
	private ObservableList<Event> observableEvents = null;
	
    /**
     * Returns observable list of events. 
     * 
     * @return observable list of events
	 * 	
	 * @version 6.0.0
	 * @since 6.0.0
     */
	public ObservableList<Event> getObservableEvents() {
		if (observableEvents == null) {
			observableEvents = FXCollections.observableArrayList(getEvent());
		}
		return observableEvents;
	}

    /**
     * Sorts events. 
     * 
	 * @version 6.0.0
	 * @since 6.0.0
     */
	public void sortEvents() {
		List<Event> lstSorted = getEvent().stream().sorted(EventModel.DATE_TITLE).collect(Collectors.toList());
		getEvent().clear();
		getEvent().addAll(lstSorted);
	}

}

/* EOF */
