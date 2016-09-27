package de.edgesoft.gebu.view;

import java.text.Collator;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import de.edgesoft.gebu.jaxb.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

/**
 * Controller for event statistics scene.
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
public class EventStatisticsController {

	/**
	 * Bar chart.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private BarChart<String, Integer> chartOverview;

	/**
	 * Bar chart.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private StackedBarChart<String, Integer> chartStacked;

	/**
	 * Event axis.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private CategoryAxis xAxis;

	/**
	 * Sets events to show statistics for.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setEventData(final List<Event> theEvents) {

		// compute statistics data
		Map<String, Map<Month, AtomicInteger>> mapCounts = new HashMap<>();

		theEvents.forEach(event -> {
			mapCounts.computeIfAbsent(event.getEventtype().getValue(), eventtype -> {
				Map<Month, AtomicInteger> mapMonthCounts = new HashMap<>();
				for (Month month : Month.values()) {
					mapMonthCounts.put(month, new AtomicInteger());
				}
				return mapMonthCounts;
			});
			mapCounts.get(event.getEventtype().getValue()).get(((LocalDate) event.getDate().getValue()).getMonth()).addAndGet(1);
		});

		// output data
		mapCounts.entrySet().stream()
				.sorted(Comparator.comparing(typemap -> typemap.getKey(), Collator.getInstance()))
				.forEach(typemap -> {
		
					XYChart.Series<String, Integer> seriesOverview = new XYChart.Series<>();
					seriesOverview.setName(typemap.getKey());
		
					XYChart.Series<String, Integer> seriesStacked = new XYChart.Series<>();
					seriesStacked.setName(typemap.getKey());
		
					typemap.getValue().entrySet().stream()
							.sorted(Comparator.comparing(monthcount -> monthcount.getKey()))
									.forEach(monthcount -> {
										seriesOverview.getData().add(new XYChart.Data<>(monthcount.getKey().getDisplayName(TextStyle.FULL, Locale.getDefault()), monthcount.getValue().get()));
										seriesStacked.getData().add(new XYChart.Data<>(monthcount.getKey().getDisplayName(TextStyle.FULL, Locale.getDefault()), monthcount.getValue().get()));
									});
		
					chartOverview.getData().add(seriesOverview);
					chartStacked.getData().add(seriesStacked);
		
				});
				
    }

}

/* EOF */
