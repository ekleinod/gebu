package de.edgesoft.gebu.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;

import de.edgesoft.gebu.jaxb.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
	private BarChart<String, Integer> chartStatistics;

	/**
	 * Event axis.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private CategoryAxis xAxis;

	/**
	 * Names of months.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private ObservableList<String> monthNames = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 *
	 * This method is automatically called after the fxml file has been loaded.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void initialize() {

        monthNames.addAll(Arrays.asList(DateFormatSymbols.getInstance().getMonths()));
        xAxis.setCategories(monthNames);
        
	}

	/**
	 * Sets events to show statistics for.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setEventData(final List<Event> theEvents) {
		
		// Count the number of people having their birthday in a specific month.
        int[] monthCounter = new int[12];
        for (Event event : theEvents) {
            int month = event.getDate().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        chartStatistics.getData().add(series);
        
    }

}

/* EOF */
