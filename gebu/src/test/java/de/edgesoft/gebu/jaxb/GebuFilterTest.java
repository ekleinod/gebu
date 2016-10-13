package de.edgesoft.gebu.jaxb;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.edgesoft.edgeutils.datetime.DateTimeUtils;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.model.EventModel;

/**
 * Unit test for filtering gebu data.
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
@RunWith(Parameterized.class)
public class GebuFilterTest {

	/** 
	 * Gebu data. 
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static Gebu testData = null;

	/** 
	 * Arguments for test calls.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private List<Object> lstArguments = null;
	
	/**
	 * Results for tests.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private List<Object> lstResults = null;
	
	/**
	 * Create test data.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@BeforeClass
	public static void createTestData() {
		testData = GebuTestData.getTestData();
		
		System.out.println("unfiltered");
		testData.getContent().getEvent().stream()
				.sorted(EventModel.DATE_TITLE)
				.map(Event::getDate)
				.forEach(System.out::println);
		System.out.println();

	}

	/**
	 * Constructor, settings the arguments.
	 * 
	 * @param theTestName test name (for junit output only)
	 * @param theArguments test arguments
	 * @param theResults test results
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public GebuFilterTest(String theTestName, List<Object> theArguments, List<Object> theResults) {
		lstArguments = (theArguments == null) ? null : new ArrayList<>(theArguments);
		lstResults = (theResults == null) ? null : new ArrayList<>(theResults);
	}
	
	
	/**
	 * Definition of test arguments.
	 * 
	 * @return collection of arguments.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@Parameters(name="Testcase ''{0}'', Index {index}")
	public static Collection<Object[]> arguments() {
	
		List<Object[]> lstArguments = new ArrayList<>();
		List<Object> arguments = null;
		List<Object> results = null;
		String name = null;
		LocalDate date = null;
		
		//
		date = LocalDate.of(2015, 3, 1);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(3);
		results.add(LocalDate.of(1790, 2, 22));
		results.add(2);
		results.add(LocalDate.of(1995, 3, 1));
		results.add(0);
		results.add(null);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2016, 3, 1);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(2);
		results.add(LocalDate.of(1996, 2, 28));
		results.add(2);
		results.add(LocalDate.of(1995, 3, 1));
		results.add(0);
		results.add(null);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2016, 1, 3);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(6);
		results.add(LocalDate.of(1996, 12, 27));
		results.add(1);
		results.add(LocalDate.of(1995, 1, 3));
		results.add(1);
		results.add(LocalDate.of(1992, 1, 4));
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2015, 1, 3);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2016, 1, 1);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(7);
		results.add(LocalDate.of(1996, 12, 26));
		results.add(1);
		results.add(LocalDate.of(1995, 1, 1));
		results.add(2);
		results.add(LocalDate.of(1995, 1, 3));
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2015, 1, 1);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2016, 1, 8);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(3);
		results.add(LocalDate.of(1995, 1, 1));
		results.add(0);
		results.add(null);
		results.add(0);
		results.add(null);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2015, 1, 8);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2015, 12, 24);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(0);
		results.add(null);
		results.add(2);
		results.add(LocalDate.of(1992, 12, 24));
		results.add(7);
		results.add(LocalDate.of(1996, 12, 26));
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2016, 12, 24);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2015, 12, 27);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(4);
		results.add(LocalDate.of(1992, 12, 24));
		results.add(2);
		results.add(LocalDate.of(1996, 12, 27));
		results.add(5);
		results.add(LocalDate.of(1990, 12, 28));
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(2016, 12, 27);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(1972, 8, 29);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(1);
		results.add(LocalDate.of(1749, 8, 28));
		results.add(0);
		results.add(null);
		results.add(0);
		results.add(null);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(1971, 8, 29);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(1982, 2, 1);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		results = new ArrayList<>();
		results.add(0);
		results.add(null);
		results.add(0);
		results.add(null);
		results.add(0);
		results.add(null);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		//
		date = LocalDate.of(1980, 2, 1);
		name = DateTimeUtils.formatDate(date);
		arguments = new ArrayList<>();
		arguments.add(date);
		
		lstArguments.add(new Object[]{name, arguments, results});
		
		return lstArguments;
	}
	
	/**
	 * Tests filtering.
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@Test
	public void testFiltering() {
		
		LocalDate testDate = (LocalDate) lstArguments.get(0);
		System.out.println(MessageFormat.format("previous 7 days not including {0}", DateTimeUtils.formatDate(testDate)));
		List<Event> lstFiltered = ((ContentModel) testData.getContent()).getSortedFilterEvents(testDate, -7, -1);
		lstFiltered.stream()
				.map(Event::getDate)
				.forEach(System.out::println);
		System.out.println();

		Assert.assertEquals(lstResults.get(0), lstFiltered.size());
		if (lstResults.get(1) != null) {
			Assert.assertEquals(lstResults.get(1), lstFiltered.get(0).getDate().getValue());
		}

		System.out.println(MessageFormat.format("exactly {0}", DateTimeUtils.formatDate(testDate)));
		lstFiltered = ((ContentModel) testData.getContent()).getSortedFilterEvents(testDate, 0, 0);
		lstFiltered.stream()
				.map(Event::getDate)
				.forEach(System.out::println);
		System.out.println();

		Assert.assertEquals(lstResults.get(2), lstFiltered.size());
		if (lstResults.get(3) != null) {
			Assert.assertEquals(lstResults.get(3), lstFiltered.get(0).getDate().getValue());
		}

		System.out.println(MessageFormat.format("next 7 days not including {0}", DateTimeUtils.formatDate(testDate)));
		lstFiltered = ((ContentModel) testData.getContent()).getSortedFilterEvents(testDate, 1, 7);
		lstFiltered.stream()
				.map(Event::getDate)
				.forEach(System.out::println);
		System.out.println();

		Assert.assertEquals(lstResults.get(4), lstFiltered.size());
		if (lstResults.get(5) != null) {
			Assert.assertEquals(lstResults.get(5), lstFiltered.get(0).getDate().getValue());
		}

	}

}

/* EOF */
