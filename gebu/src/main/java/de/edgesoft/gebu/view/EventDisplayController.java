package de.edgesoft.gebu.view;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

import de.edgesoft.edgeutils.datetime.DateTimeUtils;
import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.utils.Resources;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

/**
 * Controller for event display scene.
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
public class EventDisplayController {

	/**
	 * Web view for events.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private WebView dspEvents;

	/**
	 * Reference to application.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Gebu appGebu;

	/**
	 * Displays events for given date.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void displayEvents(final LocalDate theDate) {

		int iInterval = Integer.parseInt(Prefs.get(PrefKey.INTERVAL));

		StringBuilder sbEvents = new StringBuilder();

		if (appGebu.getGebuData().getContent().getEvent().isEmpty()) {
			sbEvents.append(String.format("<tr><td colspan=\"4\">%s</td></tr>", "Es wurden noch keine Ereignisse eingegeben."));
		} else {

			String sTemp = getTableLines(
					theDate,
					((ContentModel) appGebu.getGebuData().getContent()).getSortedFilterEvents(theDate, -iInterval, -1),
					"past");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
				sbEvents.append("<tr class=\"empty\" />");
			}

			sTemp = getTableLines(
					theDate,
					((ContentModel) appGebu.getGebuData().getContent()).getSortedFilterEvents(theDate, 0, 0),
					"present");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
				sbEvents.append("<tr class=\"empty\" />");
			}

			sTemp = getTableLines(
					theDate,
					((ContentModel) appGebu.getGebuData().getContent()).getSortedFilterEvents(theDate, 1, iInterval),
					"future");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
			}

			if (sbEvents.length() == 0) {
				sbEvents.append(String.format("<tr><td colspan=\"4\">%s</td></tr>",
						MessageFormat.format("Im Intervall von &pm; {0} Tagen liegen keine Ereignisse.", Integer.parseInt(Prefs.get(PrefKey.INTERVAL)))));
			}
		}

		dspEvents.getEngine().loadContent(Resources.loadWebView().replace("**content**", String.format("<table class=\"display\">%s</table>", sbEvents)));

	}

	/**
	 * Returns table lines for a time.
	 *
	 * @param theDate date (for age computation)
	 * @param theEvents list of events
	 * @param theTime kind of time (past, present, future)
	 *
	 * @return table lines for time
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static String getTableLines(final LocalDate theDate, final List<Event> theEvents, final String theTime) {

		StringBuilder sbReturn = new StringBuilder();

		theEvents.stream()
				.forEach(event -> {
					sbReturn.append(String.format("<tr class=\"%s\">", theTime));
					LocalDate dteEvent = (LocalDate) event.getDate().getValue();
					sbReturn.append(String.format("<td>%s</td>", DateTimeUtils.formatDate(dteEvent)));
					sbReturn.append(String.format("<td>(%s)</td>", theDate.getYear() - dteEvent.getYear()));
					sbReturn.append(String.format("<td>%s</td>", event.getEventtype().getValue()));
					sbReturn.append(String.format("<td>%s</td>", event.getTitle().getValue()));
					sbReturn.append("</tr>");
				});

		return sbReturn.toString();
	}

	/**
	 * Called by main application for reference to itself.
	 *
	 * @param theApp reference to application
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setGebuApp(final Gebu theApp) {
        appGebu = theApp;
    }

	/**
	 * Opens edit dialog for new event.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void keyListener(KeyEvent event){
		if (event.getCode() == KeyCode.ESCAPE) {
			if (appGebu.checkModified()) {
				Platform.exit();
			}
			event.consume();
		}
	}

}

/* EOF */
