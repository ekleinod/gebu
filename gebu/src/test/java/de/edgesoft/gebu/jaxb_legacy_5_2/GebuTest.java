package de.edgesoft.gebu.jaxb_legacy_5_2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.edgesoft.edgeutils.files.JAXBFiles;

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
	 * Tests marshalling.
	 */
	@Test
	public void testMarshalFile() throws Exception {

		Gebu gebu = new Gebu();
		
		Program program = new Program();
		program.setName(GebuTest.class.getCanonicalName());
		program.setVersion("6.0.0");
		
		gebu.setProgram(program);
		
		Data data = new Data();
		gebu.setData(data);
		
		Event event = new Event();
		event.setDescription("Johann Wolfgang von Goethe");
		event.setEventname("Geburtstag");
		event.setDate(LocalDate.of(1749, 8, 28));
		event.setCategory("Dichter");
		data.getEvent().add(event);
		
		event = new Event();
		event.setDescription("Johann Wolfgang von Goethe");
		event.setEventname("Todestag");
		event.setDate(LocalDate.of(1832, 2, 22));
		event.setCategory("Dichter");
		data.getEvent().add(event);
		
		event = new Event();
		event.setDescription("Friedrich Schiller");
		event.setEventname("Geburtstag");
		event.setDate(LocalDate.of(1759, 11, 10));
		event.setCategory("Dichter");
		data.getEvent().add(event);
		
		event = new Event();
		event.setDescription("Charlotte von Lengefeld");
		event.setEventname("Geburtstag");
		event.setDate(LocalDate.of(1766, 11, 22));
		data.getEvent().add(event);
		
		event = new Event();
		event.setDescription("Die Schillers");
		event.setEventname("Hochzeitstag");
		event.setDate(LocalDate.of(1790, 2, 22));
		event.setCategory("Dichterehen");
		data.getEvent().add(event);
		
		JAXBFiles.marshal(new ObjectFactory().createGebu(gebu), FILENAME, null);


		
		Gebu readGebu = JAXBFiles.unmarshal(FILENAME, Gebu.class);
		
		Assert.assertEquals(5, readGebu.getData().getEvent().size());
		Assert.assertEquals(LocalDate.of(1749, 8, 28), readGebu.getData().getEvent().stream().findFirst().get().getDate());

	}

}

/* EOF */
