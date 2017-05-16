package de.edgesoft.gebu.utils;

import de.edgesoft.gebu.controller.AppLayoutController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Utility methods for alerts.
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
public class AlertUtils {

	/**
	 * Creates and initializes an alert.
	 *
	 * This method just encapsulates the tiresome setting of
	 * the icon and resizing the alert to fit the text.
	 *
	 * @param theAlertType alert type
	 * @param theOwner alert owner
	 * @param theTitle alert title
	 * @param theHeader alert header text
	 * @param theContent alert content (null allowed for empty content)
	 * @return created alert
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Alert createAlert(final AlertType theAlertType, final Stage theOwner,
			final String theTitle, final String theHeader, final String theContent) {

        Alert alert = new Alert(theAlertType);

        // set owning stage
        alert.initOwner(theOwner);

        // display all text and resize to height
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // set alert icon
        switch (theAlertType) {
			case CONFIRMATION:
				alert.setGraphic(new ImageView(Resources.loadImage("icons/status/dialog-question.png")));
				break;
			case ERROR:
				alert.setGraphic(new ImageView(Resources.loadImage("icons/status/dialog-error.png")));
				break;
			case INFORMATION:
				alert.setGraphic(new ImageView(Resources.loadImage("icons/status/dialog-information.png")));
				break;
			case NONE:
				alert.setGraphic(new ImageView(Resources.loadImage("icons/status/image-missing.png")));
				break;
			case WARNING:
				alert.setGraphic(new ImageView(Resources.loadImage("icons/status/dialog-warning.png")));
				break;
		}

        // set window icon
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(AppLayoutController.ICON);

        // set texts
        alert.setTitle(theTitle);
        alert.setHeaderText(theHeader);
        alert.setContentText(theContent);

        return alert;

    }

}

/* EOF */
