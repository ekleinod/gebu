package de.edgesoft.gebu.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.model.AppModel;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.utils.Resources;
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

		mapContent.put("compare_date", theDate);

		mapContent.put("noevents", AppModel.getData().getContent().getEvent().isEmpty());

		if (!AppModel.getData().getContent().getEvent().isEmpty()) {

			Map<String, List<Event>> mapTimeKinds = new HashMap<>();

			mapTimeKinds.put("past", ((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, -iInterval, -1));
			mapTimeKinds.put("present", ((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, 0, 0));
			mapTimeKinds.put("future", ((ContentModel) AppModel.getData().getContent()).getSortedFilterEvents(theDate, 1, iInterval));

			mapContent.put("time_kinds", mapTimeKinds);

			mapContent.put("noeventsininterval", mapTimeKinds.get("past").isEmpty() && mapTimeKinds.get("present").isEmpty() && mapTimeKinds.get("future").isEmpty());

		}

		try {

			Template tplDisplay = Resources.loadEventView();

			try (StringWriter wrtContent = new StringWriter()) {
				tplDisplay.process(mapContent, wrtContent);
				dspEvents.getEngine().loadContent(wrtContent.toString());
			}

		} catch (IOException | TemplateException e) {
            Gebu.logger.catching(e);
		}

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
