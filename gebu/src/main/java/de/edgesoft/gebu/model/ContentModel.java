package de.edgesoft.gebu.model;

import java.text.Collator;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    /**
     * Returns filtered list of events.
     *
     * Filters all events within the boundaries, including the boundaries.
     *
     * @param theDate base date
     * @param theLowerBound lower bound
     * @param theUpperBound upper bound
     *
     * @return list of events within boundaries, empty if there are none
     *
	 * @version 6.0.0
	 * @since 6.0.0
     */
	public List<Event> getSortedFilterEvents(final LocalDate theDate, final int theLowerBound, final int theUpperBound) {
		return getEvent().stream()
				.filter(ev -> {
					LocalDate dteEvent = ((LocalDate) ev.getDate().getValue());

					int iDateDayOfYear = theDate.getDayOfYear();
					int iEventDayOfYear = dteEvent.getDayOfYear();

					int iLowerBound = iDateDayOfYear + theLowerBound;
					int iUpperBound = iDateDayOfYear + theUpperBound;

					// year change, date is january, event is december
					if ((theDate.getMonth() == Month.JANUARY) && (dteEvent.getMonth() == Month.DECEMBER)) {
						if ((iEventDayOfYear >= 1) && (iEventDayOfYear <= iUpperBound)) {
							return true;
						}
						iLowerBound = dteEvent.lengthOfYear() + iLowerBound;
						if ((iEventDayOfYear >= iLowerBound) && (iEventDayOfYear <= dteEvent.lengthOfYear())) {
							return true;
						}
						return false;
					}

					// year change, date is december, event is january
					if ((theDate.getMonth() == Month.DECEMBER) && (dteEvent.getMonth() == Month.JANUARY)) {
						if ((iEventDayOfYear >= iLowerBound) && (iEventDayOfYear <= theDate.lengthOfYear())) {
							return true;
						}
						iUpperBound = iUpperBound - theDate.lengthOfYear();
						if ((iEventDayOfYear >= 1) && (iEventDayOfYear <= iUpperBound)) {
							return true;
						}
						return false;
					}

					// no year change
					if (theDate.isLeapYear() != dteEvent.isLeapYear()) {
						if (theDate.isLeapYear() && (dteEvent.getMonth().ordinal() >= Month.MARCH.ordinal())) {
							iEventDayOfYear++;
						}
						if (dteEvent.isLeapYear() && (theDate.getMonth().ordinal() >= Month.MARCH.ordinal())) {
							iLowerBound++;
							iUpperBound++;
						}
					}

					return ((iEventDayOfYear >= iLowerBound) && (iEventDayOfYear <= iUpperBound));

				})
				.sorted(EventModel.DATE_INTERVAL_TITLE)
				.collect(Collectors.toList());
	}

    /**
     * Returns list of used event types.
     *
     * @return list of used event types
     *
	 * @version 6.0.0
	 * @since 6.0.0
     */
	public List<String> getEventtypes() {

		Set<String> setReturn = new HashSet<>();

		getEvent().stream()
				.forEach(event -> {
					if ((event.getEventtype() != null) && (event.getEventtype().getValue() != null) && !event.getEventtype().getValue().isEmpty()) {
						setReturn.add(event.getEventtype().getValue());
					}
				});

		return setReturn.stream()
				.sorted(Collator.getInstance())
				.collect(Collectors.toList());

	}

    /**
     * Returns list of used categories.
     *
     * @return list of used categories
     *
	 * @version 6.0.0
	 * @since 6.0.0
     */
	public List<String> getCategories() {

		Set<String> setReturn = new HashSet<>();

		getEvent().stream()
				.forEach(event -> {
					if ((event.getCategory() != null) && (event.getCategory().getValue() != null) && !event.getCategory().getValue().isEmpty()) {
						setReturn.add(event.getCategory().getValue());
					}
				});

		return setReturn.stream()
				.sorted(Collator.getInstance())
				.collect(Collectors.toList());

	}

}

/* EOF */
