package de.edgesoft.gebu.view;

import java.io.File;
import java.text.MessageFormat;
import java.util.Optional;

import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.model.EventModel;
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
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
	 * Menu item program -> display.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuProgramDisplay;

	/**
	 * Menu item program -> editor.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuProgramEditor;

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

        // set handler for close requests (x-button of window)
        appGebu.getPrimaryStage().setOnCloseRequest(event -> {
        	event.consume();
        	handleProgramExit();
        });

        // initialize menu
		mnuProgramDisplay.disableProperty().bind(appGebu.isDisplay());
		mnuProgramEditor.disableProperty().bind(appGebu.isDisplay().not());

    }

	/**
	 * Program menu display.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleProgramDisplay() {
		appGebu.showEventDisplay();
	}

	/**
	 * Program menu editor.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleProgramEditor() {
		appGebu.showEventOverview();
	}

	/**
	 * Program menu preferences.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleProgramPreferences() {
		appGebu.showPreferencesEditDialog();
	}

	/**
	 * Program menu exit.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@SuppressWarnings("static-method")
	@FXML
	private void handleProgramExit() {
		if (checkModified()) {
			Platform.exit();
		}
	}

	/**
	 * Opens edit dialog for new event.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleNewEvent() {

		EventModel newEvent = new EventModel();
		if (appGebu.showEventEditDialog(newEvent)) {
			((ContentModel) appGebu.getGebuData().getContent()).getObservableEvents().add(newEvent);
			appGebu.setModified(true);
			appGebu.setAppTitle();
		}

	}

	/**
	 * File menu new.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleFileNew() {
		if (checkModified()) {
			appGebu.newData();
		}
	}

	/**
	 * File menu open.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleFileOpen() {

		if (checkModified()) {

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

	}

	/**
	 * File menu save.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleFileSave() {
        if (Prefs.get(PrefKey.FILE).isEmpty()) {
        	handleFileSaveAs();
        } else {
        	appGebu.saveData(Prefs.get(PrefKey.FILE));
        }
    }

	/**
	 * File menu save as.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleFileSaveAs() {

		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Gebu-Datei speichern");
        fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("Gebu-Dateien (*.esx)", "*.esx"),
        		new FileChooser.ExtensionFilter("Alle Dateien (*.*)", "*.*")
        		);
        if (!Prefs.get(PrefKey.PATH).isEmpty()) {
        	fileChooser.setInitialDirectory(new File(Prefs.get(PrefKey.PATH)));
        }

        File file = fileChooser.showSaveDialog(appGebu.getPrimaryStage());

        if (file != null) {
            appGebu.saveData(file.getPath());
        }

    }

	/**
	 * Help menu about.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@SuppressWarnings("static-method")
	@FXML
    private void handleHelpAbout() {

        Alert alert = AlertUtils.createAlert(AlertType.INFORMATION);

        alert.setGraphic(new ImageView("file:src/main/resources/images/icon-64.png"));
        alert.setTitle("Das Gebu-Programm");
        alert.setHeaderText(MessageFormat.format("Über \"Das Gebu-Programm\" {0}", Gebu.VERSION));
        alert.setContentText("Ein Qualitätsprodukt aus dem Hause \"edge-soft\".\n\nBei Fehlern bitte eine E-Mail an ekleinod@edgesoft.de senden.");

        alert.showAndWait();

    }

	/**
	 * Event menu statistics.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleEventStatistics() {
        appGebu.showEventStatistics();
    }

	/**
	 * Check if data is modified, show corresponding dialog, save data if needed.
	 *
	 * @return continue?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private boolean checkModified() {

		boolean doContinue = true;

		if (appGebu.isModified()) {

	    	Alert alert = AlertUtils.createAlert(AlertType.CONFIRMATION);
//	        alert.initOwner(getPrimaryStage());
	        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

	        alert.setTitle("Nicht gespeicherte Änderungen");
	        alert.setHeaderText("Sie haben Änderungen durchgeführt, die noch nicht gespeichert wurden.");
	        alert.setContentText("Wollen Sie die geänderten Daten speichern, nicht speichern oder wollen Sie den gesamten Vorgang abbrechen?");

	        Optional<ButtonType> result = alert.showAndWait();
	        if (result.isPresent()) {
    			if (result.get() == ButtonType.YES) {
    				handleFileSave();
    				doContinue = true;
    			}
    			if (result.get() == ButtonType.NO) {
    				doContinue = true;
    			}
    			if (result.get() == ButtonType.CANCEL) {
    				doContinue = false;
    			}
	        }

		}

		return doContinue;

	}

}

/* EOF */
