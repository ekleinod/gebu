package de.edgesoft.gebu.controller;

import java.io.IOException;
import java.io.StringWriter;
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

import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.utils.Resources;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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
	 * Bar chart overview.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private BarChart<String, Integer> chartOverview;

	/**
	 * Stacked bar chart overview.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private StackedBarChart<String, Integer> chartStacked;

	/**
	 * Pie chart event types.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private PieChart chartEventtypes;

	/**
	 * Details view.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private WebView viewDetails;

	/**
	 * Tab overview.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Tab tabOverview;

	/**
	 * Tab stacked.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Tab tabStacked;

	/**
	 * Tab event types.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Tab tabEventtypes;

	/**
	 * Tab view.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Tab tabDetails;

	/**
	 * OK button.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnOK;

	/**
	 * Reference to dialog stage.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Stage dialogStage;


	/**
	 * Sets dialog stage.
	 *
	 * @param theStage dialog stage
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setDialogStage(final Stage theStage) {
        dialogStage = theStage;
    }

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

		// icons
		btnOK.setGraphic(new ImageView(Resources.loadImage("icons/actions/dialog-ok-16.png")));

		tabOverview.setGraphic(new ImageView(Resources.loadImage("icons/actions/office-chart-bar.png")));
		tabStacked.setGraphic(new ImageView(Resources.loadImage("icons/actions/office-chart-bar-stacked.png")));
		tabEventtypes.setGraphic(new ImageView(Resources.loadImage("icons/actions/office-chart-pie.png")));
		tabDetails.setGraphic(new ImageView(Resources.loadImage("icons/actions/view-list-details.png")));

	}

	/**
	 * Fills statistics with event data.
	 *
	 * @param theEvents event data
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void fillStatistics(final List<Event> theEvents) {

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
			mapCounts.get(event.getEventtype().getValue()).get(((LocalDate) event.getDate().getValue()).getMonth()).incrementAndGet();
		});

		AtomicInteger iWholeCount = new AtomicInteger();
		Map<String, Object> mapContent = new HashMap<>();
		Map<String, Integer> mapStats = new HashMap<>();

		// output data
		mapCounts.entrySet().stream()
				.sorted(Comparator.comparing(typemap -> typemap.getKey(), Collator.getInstance()))
				.forEach(typemap -> {

					AtomicInteger iEventCount = new AtomicInteger();

					XYChart.Series<String, Integer> seriesOverview = new XYChart.Series<>();
					seriesOverview.setName(typemap.getKey());

					XYChart.Series<String, Integer> seriesStacked = new XYChart.Series<>();
					seriesStacked.setName(typemap.getKey());

					typemap.getValue().entrySet().stream()
							.sorted(Comparator.comparing(monthcount -> monthcount.getKey()))
									.forEach(monthcount -> {
										seriesOverview.getData().add(new XYChart.Data<>(monthcount.getKey().getDisplayName(TextStyle.FULL, Locale.getDefault()), monthcount.getValue().get()));
										seriesStacked.getData().add(new XYChart.Data<>(monthcount.getKey().getDisplayName(TextStyle.FULL, Locale.getDefault()), monthcount.getValue().get()));
										iEventCount.addAndGet(monthcount.getValue().get());
									});

					chartOverview.getData().add(seriesOverview);
					chartStacked.getData().add(seriesStacked);
					chartEventtypes.getData().add(new PieChart.Data(String.format("%s (%d)", typemap.getKey(), iEventCount.get()), iEventCount.get()));

					iWholeCount.addAndGet(iEventCount.get());

					mapStats.put(typemap.getKey(), iEventCount.get());

				});

		mapContent.put("stats", mapStats);
		mapContent.put("sum", iWholeCount);

		try {

			Template tplDisplay = Resources.loadStatisticsView();

			try (StringWriter wrtContent = new StringWriter()) {
				tplDisplay.process(mapContent, wrtContent);
				viewDetails.getEngine().loadContent(wrtContent.toString());
			}

		} catch (IOException | TemplateException e) {
            Gebu.logger.catching(e);
		}


    }

	/**
	 * Closes dialog.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleOk() {
        dialogStage.close();
    }

}

/* EOF */
