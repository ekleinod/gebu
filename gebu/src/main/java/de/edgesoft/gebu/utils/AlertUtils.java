package de.edgesoft.gebu.utils;

import de.edgesoft.gebu.Gebu;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	 * @return created alert
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Alert createAlert(final AlertType theAlertType) {
		
        Alert alert = new Alert(theAlertType);
        
        // display all text and resize to height
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        
        // set icon
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Gebu.ICON);
        
        return alert;
        
    }

}

/* EOF */
