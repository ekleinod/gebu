package de.edgesoft.gebu.jaxb;

import java.time.LocalDate;
import java.time.LocalDateTime;

import de.edgesoft.edgeutils.commons.Info;
import de.edgesoft.edgeutils.commons.ext.VersionExt;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Data for Gebu teste.
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
public class GebuTestData {

	/**
	 * Returns test data.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Gebu getTestData() {
		
		ObjectFactory factory = new ObjectFactory();

		Gebu testData = factory.createGebu();

		Info info = new de.edgesoft.edgeutils.commons.ObjectFactory().createInfo();

		info.setCreated(LocalDateTime.now().minusHours(2));
		info.setModified(LocalDateTime.now());
		info.setAppversion(new VersionExt("6.0.0"));
		info.setDocversion(new VersionExt("6.0.0"));
		info.setCreator(GebuTestData.class.getCanonicalName());

		testData.setInfo(info);

		Content content = factory.createContent();
		testData.setContent(content);

		Event event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Johann Wolfgang von Goethe"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1749, 8, 28)));
		event.setCategory(new SimpleStringProperty("Dichter"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Johann Wolfgang von Goethe"));
		event.setEventtype(new SimpleStringProperty("Todestag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1832, 2, 22)));
		event.setCategory(new SimpleStringProperty("Dichter"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Friedrich Schiller"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1759, 11, 10)));
		event.setCategory(new SimpleStringProperty("Dichter"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Charlotte von Lengefeld"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1766, 11, 22)));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Die Schillers"));
		event.setEventtype(new SimpleStringProperty("Hochzeitstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1790, 2, 22)));
		event.setCategory(new SimpleStringProperty("Dichterehen"));
		content.getEvent().add(event);
		
		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 1"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1990, 12, 28)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 2"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1991, 12, 31)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 3"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1995, 1, 3)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 4"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1996, 3, 1)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 5"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1996, 2, 29)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 6"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1996, 2, 28)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 17"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1995, 3, 1)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 8"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1995, 1, 1)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 9"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1996, 12, 31)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 10"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1996, 12, 27)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 11"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1996, 12, 26)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 12"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1995, 12, 27)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 13"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1995, 12, 26)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);

		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 14"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1992, 1, 4)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);
		
		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 16"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1992, 12, 24)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);
		
		event = factory.createEvent();
		event.setTitle(new SimpleStringProperty("Test 17"));
		event.setEventtype(new SimpleStringProperty("Geburtstag"));
		event.setDate(new SimpleObjectProperty<>(LocalDate.of(1991, 12, 24)));
		event.setCategory(new SimpleStringProperty("Tests"));
		content.getEvent().add(event);
		
		return testData;

	}

}

/* EOF */
