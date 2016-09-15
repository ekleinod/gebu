package de.edgesoft.gebu.jaxb;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.edgesoft.edgeutils.commons.Info;
import de.edgesoft.edgeutils.commons.ext.VersionExt;
import de.edgesoft.edgeutils.files.JAXBFiles;
import de.edgesoft.gebu.jaxb.model.ContentModel;
import de.edgesoft.gebu.jaxb.model.EventModel;

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

	/** File name. */
	private static final String FILENAME = String.format("src/test/resources/%s.%s.xml", GebuTest.class.getPackage().getName(), GebuTest.class.getSimpleName().toLowerCase());

	/**
	 * Delete files.
	 * @throws Exception
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
	 * Rule for expected exception
	 */
	@Rule
	public ExpectedException exception = ExpectedException.none();

	/**
	 * Tests marshalling and unmarshalling.
	 */
	@Test
	public void testMarshalFile() throws Exception {

		Gebu gebu = new Gebu();

		Info info = new Info();

		info.setCreated(LocalDateTime.now().minusHours(2));
		info.setModified(LocalDateTime.now());
		info.setAppversion(new VersionExt("6.0.0"));
		info.setDocversion(new VersionExt("6.0.0"));
		info.setCreator(GebuTest.class.getCanonicalName());

		gebu.setInfo(info);

		ContentModel content = new ContentModel();
		gebu.setContent(content);

		EventModel event = new EventModel();
		event.setTitle("Johann Wolfgang von Goethe");
		event.setEventtype("Geburtstag");
		event.setDate(LocalDate.of(1749, 8, 28));
		event.setCategory("Dichter");
		content.getEvent().add(event);

		event = new EventModel();
		event.setTitle("Johann Wolfgang von Goethe");
		event.setEventtype("Todestag");
		event.setDate(LocalDate.of(1832, 2, 22));
		event.setCategory("Dichter");
		content.getEvent().add(event);

		event = new EventModel();
		event.setTitle("Friedrich Schiller");
		event.setEventtype("Geburtstag");
		event.setDate(LocalDate.of(1759, 11, 10));
		event.setCategory("Dichter");
		content.getEvent().add(event);

		event = new EventModel();
		event.setTitle("Charlotte von Lengefeld");
		event.setEventtype("Geburtstag");
		event.setDate(LocalDate.of(1766, 11, 22));
		content.getEvent().add(event);

		event = new EventModel();
		event.setTitle("Die Schillers");
		event.setEventtype("Hochzeitstag");
		event.setDate(LocalDate.of(1790, 2, 22));
		event.setCategory("Dichterehen");
		content.getEvent().add(event);

		JAXBFiles.marshal(new ObjectFactory().createGebu(gebu), FILENAME, null);



		Gebu readGebu = JAXBFiles.unmarshal(FILENAME, Gebu.class);

		Assert.assertEquals(5, readGebu.getContent().getEvent().size());
		Assert.assertEquals(LocalDate.of(1749, 8, 28), readGebu.getContent().getEvent().stream().findFirst().get().getDate());

	}

}

/* EOF */
