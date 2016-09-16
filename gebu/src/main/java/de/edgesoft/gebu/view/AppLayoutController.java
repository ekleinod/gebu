package de.edgesoft.gebu.view;

import java.io.File;

import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

/**
 * Controller for application layout.
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
public class AppLayoutController {

	/**
	 * Reference to application.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Gebu appGebu;

	/**
	 * Called by main application for reference to itself.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setGebuApp(final Gebu theApp) {
        appGebu = theApp;
    }

	/**
	 * File menu new.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleFileNew() {
		appGebu.newData();
	}

	/**
	 * File menu open.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleFileOpen() {
		
		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Gebu-Datei öffnen");
        fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("Gebu-Dateien (*.esx)", "*.esx"),
        		new FileChooser.ExtensionFilter("Alle Dateien (*.*)", "*.*")
        		);
        if (!Prefs.get(PrefKey.PATH).isEmpty()) {
        	fileChooser.setInitialDirectory(new File(Prefs.get(PrefKey.PATH)));
        }

        File file = fileChooser.showOpenDialog(appGebu.getPrimaryStage());

        if (file != null) {
            appGebu.openData(file.getPath());
        }
        
	}

	/**
	 * Program menu exit.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleProgramExit() {
		System.exit(0);
	}

}

/* EOF */
