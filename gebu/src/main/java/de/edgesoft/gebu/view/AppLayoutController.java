package de.edgesoft.gebu.view;

import java.io.File;
import java.text.MessageFormat;

import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.model.EventModel;
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.utils.Resources;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
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
	 * Menu item program -> preferences.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuProgramPreferences;

	/**
	 * Menu item program -> quit.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuProgramQuit;

	/**
	 * Menu file.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Menu mnuFile;

	/**
	 * Menu item file -> new.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuFileNew;

	/**
	 * Menu item file -> open.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuFileOpen;

	/**
	 * Menu item file -> save.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuFileSave;

	/**
	 * Menu item file -> save as.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuFileSaveAs;

	/**
	 * Menu statistics.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Menu mnuStatistics;

	/**
	 * Menu item statistics -> data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuStatisticsData;

	/**
	 * Menu item help -> about.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private MenuItem mnuHelpAbout;

	/**
	 * Main toolbar.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ToolBar barMain;

	/**
	 * Button program -> quit.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnProgramQuit;

	/**
	 * Button file -> new.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnFileNew;

	/**
	 * Button file -> open.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnFileOpen;

	/**
	 * Button file -> save.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnFileSave;

	/**
	 * Button file -> save as.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnFileSaveAs;

	/**
	 * Button statistics -> data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnStatisticsData;

	/**
	 * Reference to application.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Gebu appGebu;


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
		mnuProgramDisplay.setGraphic(new ImageView(Resources.loadImage("icons/actions/view-calendar-birthday.png")));
		mnuProgramEditor.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-edit.png")));
		mnuProgramPreferences.setGraphic(new ImageView(Resources.loadImage("icons/actions/configure.png")));
		mnuProgramQuit.setGraphic(new ImageView(Resources.loadImage("icons/actions/application-exit.png")));

		mnuFileNew.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-new.png")));
		mnuFileOpen.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-open.png")));
		mnuFileSave.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-save.png")));
		mnuFileSaveAs.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-save-as.png")));

		mnuStatisticsData.setGraphic(new ImageView(Resources.loadImage("icons/actions/office-chart-bar.png")));

		mnuHelpAbout.setGraphic(new ImageView(Resources.loadImage("icons/actions/help-about.png")));

		// toolbar
		btnProgramQuit.setGraphic(new ImageView(Resources.loadImage("icons/actions/application-exit.png")));

		btnFileNew.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-new.png")));
		btnFileOpen.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-open.png")));
		btnFileSave.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-save.png")));
		btnFileSaveAs.setGraphic(new ImageView(Resources.loadImage("icons/actions/document-save-as.png")));

		btnStatisticsData.setGraphic(new ImageView(Resources.loadImage("icons/actions/office-chart-bar.png")));

	}

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

        // bind menu/toolbar enabling to display kind
		mnuProgramDisplay.disableProperty().bind(appGebu.isDisplay());
		mnuProgramEditor.disableProperty().bind(appGebu.isDisplay().not());

		// hide unneeded menus/toolbars, disable menu items, so they cannot be used in display mode
		mnuFile.visibleProperty().bind(appGebu.isDisplay().not());
		mnuFile.getItems().stream()
				.forEach(menuitem -> menuitem.disableProperty().bind(appGebu.isDisplay()));

		mnuStatistics.visibleProperty().bind(appGebu.isDisplay().not());
		mnuStatistics.getItems().stream()
				.forEach(menuitem -> menuitem.disableProperty().bind(appGebu.isDisplay()));

		barMain.visibleProperty().bind(appGebu.isDisplay().not());
		barMain.managedProperty().bind(barMain.visibleProperty());
		barMain.getItems().stream()
				.forEach(button -> button.disableProperty().bind(appGebu.isDisplay()));

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
	@FXML
	private void handleProgramExit() {
		if (appGebu.checkModified()) {
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
		if (appGebu.checkModified()) {
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

		if (appGebu.checkModified()) {

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
    public void handleFileSave() {
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
        	if (!file.getName().contains(".")) {
        		file = new File(String.format("%s.esx", file.getPath()));
        	}
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

        alert.setGraphic(new ImageView(Resources.loadImage("images/icon-64.png")));
        alert.setTitle("Über \"Das Gebu-Programm\"");
        alert.setHeaderText(MessageFormat.format("Das Gebu-Programm Version {0}", Gebu.VERSION));

        StringBuilder sbText = new StringBuilder();

        sbText.append("Ein Qualitätsprodukt aus dem Hause \"edge-soft\".");
        sbText.append("\n\n");
        sbText.append("Verbesserungen, Fehler, Hinweise bitte per E-Mail an ekleinod@edgesoft.de.");
        sbText.append("\n");
        sbText.append("Alternativ kann auch eine Fehlermeldung bei github eröffnet werden: https://github.com/ekleinod/gebu/issues.");
        sbText.append("\n\n");
        sbText.append("Die Icons sind aus dem Papirus icon theme (https://github.com/PapirusDevelopmentTeam/papirus-icon-theme-gtk).");

        alert.setContentText(sbText.toString());

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

}

/* EOF */
