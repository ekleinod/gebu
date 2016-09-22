package de.edgesoft.gebu.jaxb;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.edgesoft.edgeutils.commons.Info;
import de.edgesoft.edgeutils.commons.ext.VersionExt;
import de.edgesoft.edgeutils.files.JAXBFiles;
import de.edgesoft.gebu.model.EventModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Unit test for Gebu.
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
public class GebuTest {

	/** 
	 * File name. 
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static final String FILENAME = String.format("src/test/resources/%s.%s.xml", GebuTest.class.getPackage().getName(), GebuTest.class.getSimpleName().toLowerCase());

	/** 
	 * Gebu data. 
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static Gebu testData = null;

	/**
	 * Delete files.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@Before
	public void deleteFiles() {
		try {
			Files.deleteIfExists(Paths.get(FILENAME));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Create test data.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@Before
	public void createTestData() {
		
		ObjectFactory factory = new ObjectFactory();

		testData = factory.createGebu();

		Info info = new de.edgesoft.edgeutils.commons.ObjectFactory().createInfo();

		info.setCreated(LocalDateTime.now().minusHours(2));
		info.setModified(LocalDateTime.now());
		info.setAppversion(new VersionExt("6.0.0"));
		info.setDocversion(new VersionExt("6.0.0"));
		info.setCreator(GebuTest.class.getCanonicalName());

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

	}

	/**
	 * Rule for expected exception
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@Rule
	public ExpectedException exception = ExpectedException.none();

	/**
	 * Tests marshalling and unmarshalling.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@Test
	public void testMarshalFile() throws Exception {
		
		JAXBFiles.marshal(new ObjectFactory().createGebu(testData), FILENAME, null);

		Gebu readGebu = JAXBFiles.unmarshal(FILENAME, Gebu.class);

		Assert.assertEquals(12, readGebu.getContent().getEvent().size());
		Assert.assertEquals(LocalDate.of(1749, 8, 28), readGebu.getContent().getEvent().stream().findFirst().get().getDate().getValue());

	}

	/**
	 * Tests sorting.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@Test
	public void testSorting() {
		
		// unsorted
		System.out.println("unsorted");
		testData.getContent().getEvent().stream()
				.map(Event::getDate)
				.forEach(System.out::println);
		System.out.println();

		// sorted
		System.out.println("sorted");
		testData.getContent().getEvent().stream()
				.sorted(EventModel.DATE_TITLE)
				.map(Event::getDate)
				.forEach(System.out::println);
		System.out.println();

		// first element
		Assert.assertEquals(LocalDate.of(1995, 1, 3), testData.getContent().getEvent().stream().sorted(EventModel.DATE_TITLE).findFirst().get().getDate().getValue());
		
		// last element
		Assert.assertEquals(LocalDate.of(1991, 12, 31), ((Event) Arrays.asList(testData.getContent().getEvent().stream().sorted(EventModel.DATE_TITLE).toArray()).get(11)).getDate().getValue());

	}

}

/* EOF */
