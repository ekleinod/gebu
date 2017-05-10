package de.edgesoft.gebu.controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.edgesoft.edgeutils.datetime.DateTimeUtils;
import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.model.AppModel;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.model.EventModel;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.utils.Resources;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

/**
 * Controller for event display scene.
 *
 * ## Legal stuff
 *
 * Copyright 2016-2017 Ekkart Kleinod <ekleinod@edgesoft.de>
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
	 * Main app controller.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private AppLayoutController appController = null;


	/**
	 * Initializes the controller with things, that cannot be done during {@link #initialize()}.
	 *
	 * @param theAppController app controller
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void initController(final AppLayoutController theAppController) {

		appController = theAppController;

	}

	/**
	 * Displays events for given date.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void displayEvents(final LocalDate theDate) {

		int iInterval = Integer.parseInt(Prefs.get(PrefKey.INTERVAL));

		Map<String, Object> mapContent = new HashMap<>();

		for (PrefKey key : PrefKey.values()) {
			mapContent.put(key.toString().toLowerCase(), Prefs.get(key));
		}

		mapContent.put("noevents", false);
		mapContent.put("noeventsininterval", false);

		if (AppModel.getData().getContent().getEvent().isEmpty()) {
			mapContent.put("noevents", true);
		} else {

			Map<String, List<EventModel>> mapTimeKinds = new HashMap<>();
			mapContent.put("time_kinds", mapTimeKinds);

			String sTemp = getTableLines(
					theDate,
					((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, -iInterval, -1),
					"past");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
				sbEvents.append("<tr class=\"empty\" />");
			}

			sTemp = getTableLines(
					theDate,
					((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, 0, 0),
					"present");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
				sbEvents.append("<tr class=\"empty\" />");
			}

			sTemp = getTableLines(
					theDate,
					((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, 1, iInterval),
					"future");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
			}

			if (sbEvents.length() == 0) {
				sbEvents.append(String.format("<tr><td>%s</td></tr>",
						MessageFormat.format("Im Intervall von &pm; {0} Tagen liegen keine Ereignisse.", Integer.parseInt(Prefs.get(PrefKey.INTERVAL)))));
			}
		}

		try {

			Template tplDisplay = new Template("display", new StringReader(Resources.loadWebView()), new Configuration(Configuration.VERSION_2_3_26));

			try (StringWriter wrtContent = new StringWriter()) {
				tplDisplay.process(mapContent, wrtContent);
				dspEvents.getEngine().loadContent(wrtContent.toString());
			}

		} catch (IOException | TemplateException e) {
            Gebu.logger.catching(e);
		}

	}

	/**
	 * Displays events for given date.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void displayEventsOld(final LocalDate theDate) {

		int iInterval = Integer.parseInt(Prefs.get(PrefKey.INTERVAL));

		StringBuilder sbEvents = new StringBuilder();

		if (AppModel.getData().getContent().getEvent().isEmpty()) {
			sbEvents.append(String.format("<tr><td>%s</td></tr>", "Es wurden noch keine Ereignisse eingegeben."));
		} else {

			String sTemp = getTableLines(
					theDate,
					((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, -iInterval, -1),
					"past");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
				sbEvents.append("<tr class=\"empty\" />");
			}

			sTemp = getTableLines(
					theDate,
					((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, 0, 0),
					"present");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
				sbEvents.append("<tr class=\"empty\" />");
			}

			sTemp = getTableLines(
					theDate,
					((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, 1, iInterval),
					"future");

			if (!sTemp.isEmpty()) {
				sbEvents.append(sTemp);
			}

			if (sbEvents.length() == 0) {
				sbEvents.append(String.format("<tr><td>%s</td></tr>",
						MessageFormat.format("Im Intervall von &pm; {0} Tagen liegen keine Ereignisse.", Integer.parseInt(Prefs.get(PrefKey.INTERVAL)))));
			}
		}

		try {

			Map<String, String> mapContent = new HashMap<>();
			mapContent.put("content", String.format("<table class=\"display\">%s</table>", sbEvents));

			Template tplDisplay = new Template("display", new StringReader(Resources.loadWebView()), new Configuration(Configuration.VERSION_2_3_26));

			try (StringWriter wrtContent = new StringWriter()) {
				tplDisplay.process(mapContent, wrtContent);
				dspEvents.getEngine().loadContent(wrtContent.toString());
			}

		} catch (IOException | TemplateException e) {
            Gebu.logger.catching(e);
		}

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

					if (Boolean.parseBoolean(Prefs.get(PrefKey.DISPLAY_CATEGORIES))) {
						sbReturn.append(String.format("<td>%s</td>", event.getCategory().getValue()));
					}

					sbReturn.append("</tr>");
				});

		return sbReturn.toString();
	}

	/**
	 * Handles pressing of ESC, closes app.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void keyListener(KeyEvent event){
		if (event.getCode() == KeyCode.ESCAPE) {
			appController.handleProgramExit();
			// this code is reached only, if program was not exited, thus consume event
			event.consume();
		}
	}

}

/* EOF */
