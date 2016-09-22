package de.edgesoft.gebu.model;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Comparator;

import de.edgesoft.gebu.jaxb.Event;

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

	/** 
	 * Comparator date. 
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static final Comparator<Event> DATE = new Comparator<Event>() {

		/**
		 * (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 * 
		 * Creates new dates in year 1996 and compares them - easiest solution.
		 * 
		 * @todo is this an elegant solution?
		 */
		@Override
		public int compare(Event e1, Event e2) {
			LocalDate d1 = LocalDate.of(1996, ((LocalDate) e1.getDate().getValue()).getMonth(), ((LocalDate) e1.getDate().getValue()).getDayOfMonth());
			LocalDate d2 = LocalDate.of(1996, ((LocalDate) e2.getDate().getValue()).getMonth(), ((LocalDate) e2.getDate().getValue()).getDayOfMonth());
			return d1.compareTo(d2);
		}

	};
	
	/** 
	 * Comparator date, then title. 
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static final Comparator<Event> DATE_TITLE = DATE.thenComparing(event -> event.getTitle().getValue(), Collator.getInstance());

}

/* EOF */
