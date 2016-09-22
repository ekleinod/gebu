package de.edgesoft.gebu.jaxb;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.edgesoft.edgeutils.files.JAXBFiles;
import de.edgesoft.gebu.model.EventModel;

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
	@BeforeClass
	public static void deleteFiles() {
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
	@BeforeClass
	public static void createTestData() {
		testData = GebuTestData.getTestData();
	}

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

		Assert.assertEquals(21, readGebu.getContent().getEvent().size());
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
		Assert.assertEquals(LocalDate.of(1995, 1, 1), testData.getContent().getEvent().stream().sorted(EventModel.DATE_TITLE).findFirst().get().getDate().getValue());
		
		// last element
		List<Event> lstEvents = testData.getContent().getEvent().stream().sorted(EventModel.DATE_TITLE).collect(Collectors.toList());
		Assert.assertEquals(LocalDate.of(1996, 12, 31), lstEvents.get(lstEvents.size() - 1).getDate().getValue());

	}

}

/* EOF */
