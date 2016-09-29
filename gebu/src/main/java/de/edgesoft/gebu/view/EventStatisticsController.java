package de.edgesoft.gebu.view;

import java.nio.file.Paths;
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

import de.edgesoft.edgeutils.files.FileAccess;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.web.WebView;

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
		StringBuilder sbDetails = new StringBuilder();
		sbDetails.append("<table>");

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

					sbDetails.append("<tr>");
					sbDetails.append(String.format("<th>%s</th>", typemap.getKey()));
					sbDetails.append(String.format("<td>%d</td>", iEventCount.get()));
					sbDetails.append("</tr>");
					iWholeCount.addAndGet(iEventCount.get());

				});

		sbDetails.append("<tr>");
		sbDetails.append(String.format("<th>%s</th>", "Summe"));
		sbDetails.append(String.format("<td>%d</td>", iWholeCount.get()));
		sbDetails.append("</tr>");

		sbDetails.append("</table>");

		String sContent;
		try {
			sContent = FileAccess.readFile(Paths.get("src/main/resources/webview.html")).toString();
		} catch (Exception e) {
			sContent = "**content**";
			e.printStackTrace();
		}

		sContent = sContent
				.replace("**past foreground**", Prefs.get(PrefKey.PAST_FOREGROUND))
				.replace("**past fontsize**", Prefs.get(PrefKey.PAST_FONTSIZE))
				.replace("**past background**", Prefs.get(PrefKey.PAST_BACKGROUND))

				.replace("**present foreground**", Prefs.get(PrefKey.PRESENT_FOREGROUND))
				.replace("**present fontsize**", Prefs.get(PrefKey.PRESENT_FONTSIZE))
				.replace("**present background**", Prefs.get(PrefKey.PRESENT_BACKGROUND))

				.replace("**future foreground**", Prefs.get(PrefKey.FUTURE_FOREGROUND))
				.replace("**future fontsize**", Prefs.get(PrefKey.FUTURE_FONTSIZE))
				.replace("**future background**", Prefs.get(PrefKey.FUTURE_BACKGROUND))

				.replace("**content**", String.format("<table>%s</table>", sbDetails))
				;

		viewDetails.getEngine().loadContent(sContent);

    }

}

/* EOF */
