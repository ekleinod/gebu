package de.edgesoft.gebu.controller;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import de.edgesoft.edgeutils.EdgeUtilsException;
import de.edgesoft.edgeutils.commons.Info;
import de.edgesoft.edgeutils.files.JAXBFiles;
import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Content;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.jaxb.ObjectFactory;
import de.edgesoft.gebu.model.AppModel;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.utils.Resources;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	 * Application icon.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static final Image ICON = Resources.loadImage("images/icon-32.png");

	/**
	 * App border pane.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private BorderPane appPane;

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
	 * Primary stage.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Stage primaryStage = null;

	/**
	 * Event overview controller.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private EventEditorController ctlEventOverview = null;

	/**
	 * Flag, if display (true) or editor (false) is shown.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private BooleanProperty isDisplay = null;


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

		isDisplay = new SimpleBooleanProperty();
		
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

        // bind menu/toolbar enabling to display kind
		mnuProgramDisplay.disableProperty().bind(isDisplay);
		mnuProgramEditor.disableProperty().bind(isDisplay.not());

		// hide unneeded menus/toolbars, disable menu items, so they cannot be used in display mode
		mnuFile.visibleProperty().bind(isDisplay.not());
		mnuFile.getItems().stream()
				.forEach(menuitem -> menuitem.disableProperty().bind(isDisplay));

		mnuStatistics.visibleProperty().bind(isDisplay.not());
		mnuStatistics.getItems().stream()
				.forEach(menuitem -> menuitem.disableProperty().bind(isDisplay));

		barMain.visibleProperty().bind(isDisplay.not());
		barMain.managedProperty().bind(barMain.visibleProperty());
		barMain.getItems().stream()
				.forEach(button -> button.disableProperty().bind(isDisplay));

	}

	/**
	 * Initializes the controller with things, that cannot be done during {@link #initialize()}.
	 * 
	 * @param thePrimaryStage primary stage
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void initController(final Stage thePrimaryStage) {

		primaryStage = thePrimaryStage;
		
        // set icon
		primaryStage.getIcons().add(ICON);

        // Show the scene containing the root layout.
        Scene scene = new Scene(appPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        // resize to last dimensions
    	primaryStage.setX(Double.parseDouble(Prefs.get(PrefKey.STAGE_X)));
    	primaryStage.setY(Double.parseDouble(Prefs.get(PrefKey.STAGE_Y)));

    	primaryStage.setWidth(Double.parseDouble(Prefs.get(PrefKey.STAGE_WIDTH)));
    	primaryStage.setHeight(Double.parseDouble(Prefs.get(PrefKey.STAGE_HEIGHT)));

		// if changed, save bounds to preferences
		primaryStage.xProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_X, Double.toString(newValue.doubleValue()));
		});
		primaryStage.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_WIDTH, Double.toString(newValue.doubleValue()));
		});
		primaryStage.yProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_Y, Double.toString(newValue.doubleValue()));
		});
		primaryStage.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_HEIGHT, Double.toString(newValue.doubleValue()));
		});

        // set handler for close requests (x-button of window)
		primaryStage.setOnCloseRequest(event -> {
        	event.consume();
        	handleProgramExit();
        });
		
		// finally, we can initialize the data 
		initData();

		// show correct pane
        if (AppModel.getData().getContent().getEvent().isEmpty()) {
        	handleProgramEditor();
        } else {
        	handleProgramDisplay();
        }

    }

	/**
	 * Initializes the data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void initData() {

		if (AppModel.getFilename().isEmpty()) {
			newData();
		} else {
			openData(Prefs.get(PrefKey.FILE));
		}

    }

	/**
	 * Creates new data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void newData() {

		de.edgesoft.gebu.jaxb.Gebu dtaGebu = new ObjectFactory().createGebu();

		Info info = new de.edgesoft.edgeutils.commons.ObjectFactory().createInfo();

		info.setCreated(LocalDateTime.now());
		info.setModified(LocalDateTime.now());
		info.setAppversion(Gebu.VERSION);
		info.setDocversion(Gebu.VERSION);
		info.setCreator(Gebu.class.getCanonicalName());

		dtaGebu.setInfo(info);

		Content content = new ObjectFactory().createContent();
		dtaGebu.setContent(content);
		
		AppModel.setData(dtaGebu);

		AppModel.setFilename(null);
		AppModel.setModified(false);
		AppModel.setLegacy(false);
		setAppTitle();
		
		if (ctlEventOverview != null) {
			ctlEventOverview.setTableItems();
		}

    }

	/**
	 * Sets the app title.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setAppTitle() {
		
		primaryStage.setTitle(String.format("Das Gebu-Programm%s%s",
				Prefs.get(PrefKey.FILE).isEmpty() ? "" : String.format(" - %s", Prefs.get(PrefKey.FILE)),
				AppModel.isModified() ? " *" : ""
				));

    }

	/**
	 * Loads data from the given file.
	 *
	 * @param theFilename filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void openData(final String theFilename) {

		try {

			de.edgesoft.gebu.jaxb.Gebu dtaGebu = JAXBFiles.unmarshal(theFilename, de.edgesoft.gebu.jaxb.Gebu.class);

			AppModel.setData(dtaGebu);
			AppModel.setLegacy(false);
			
			// legacy files?
			if (dtaGebu.getInfo() == null) {
				openLegacyData(theFilename);
			}

			if (ctlEventOverview != null) {
				ctlEventOverview.setTableItems();
			}

			AppModel.setFilename(theFilename);
			AppModel.setModified(false);
			setAppTitle();

		} catch (EdgeUtilsException e) {

	        AlertUtils.createAlert(AlertType.ERROR, primaryStage,
	        		"Datenfehler",
	        		"Ein Fehler ist beim Laden der Gebu-Daten aufgetreten.",
	        		MessageFormat.format("{0}\nDas Programm wird ohne Daten fortgeführt.", e.getMessage()))
	        .showAndWait();

	        newData();

		}

    }

	/**
	 * Loads and converts legacy data.
	 *
	 * @param theFilename filename
	 * 
	 * @throws EdgeUtilsException if loading or converting went wrong
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void openLegacyData(final String theFilename) throws EdgeUtilsException {

		newData();

		de.edgesoft.gebu.jaxb_legacy_5_2.Gebu dtaLegacy = JAXBFiles.unmarshal(theFilename, de.edgesoft.gebu.jaxb_legacy_5_2.Gebu.class);

		dtaLegacy.getData().getEvent().stream().forEach(
				event -> {
					
					Event newEvent = new ObjectFactory().createEvent();
					
					newEvent.setTitle(new SimpleStringProperty(event.getDescription()));
					newEvent.setDate(new SimpleObjectProperty<>(event.getDate()));
					newEvent.setEventtype(new SimpleStringProperty(event.getEventname()));
					newEvent.setCategory(new SimpleStringProperty((event.getCategory().equals("Keine") ? null : event.getCategory())));
					
					AppModel.getData().getContent().getEvent().add(newEvent);
					AppModel.setLegacy(false);

				}
				);

        AlertUtils.createAlert(AlertType.INFORMATION, primaryStage,
        		"Datenkonvertierung",
        		null,
        		"Die eingelesenen Daten stammen von einer alten Programmversion. Die Daten wurden eingelesen und konvertiert.\n\nFalls Sie die Daten speichern, werden diese im neuen Format gespeichert. Wenn Sie die Originaldatei behalten wollen, speichern Sie die Datei nicht oder unter einem anderen Namen ab.")
        .showAndWait();

    }

	/**
	 * Program menu display.
	 *
	 * Shows the event display.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleProgramDisplay() {
		
    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventDisplay");
    	AnchorPane eventDisplay = (AnchorPane) pneLoad.getKey();

        // Set event overview into the center of root layout.
        appPane.setCenter(eventDisplay);

        // Give the controller access to the app.
        EventDisplayController ctlEventDisplay = pneLoad.getValue().getController();
        ctlEventDisplay.initController(this);
        ctlEventDisplay.displayEvents(LocalDate.now());

        isDisplay.setValue(true);

	}

	/**
	 * Program menu editor.
	 *
	 * Shows the event overview.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleProgramEditor() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventEditor");
    	AnchorPane eventOverview = (AnchorPane) pneLoad.getKey();

        // Set event overview into the center of root layout.
        appPane.setCenter(eventOverview);

        // Give the controller access to the app.
        ctlEventOverview = pneLoad.getValue().getController();
        ctlEventOverview.initController(this);
        ctlEventOverview.setTableItems();

        isDisplay.setValue(false);

	}

	/**
	 * Program menu preferences.
	 *
	 * Opens the preferences edit dialog.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleProgramPreferences() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("PreferencesEditDialog");
    	AnchorPane preferencesDialog = (AnchorPane) pneLoad.getKey();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Einstellungen");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        Scene scene = new Scene(preferencesDialog);
        dialogStage.setScene(scene);

        // initialize controller
        PreferencesEditDialogController controller = pneLoad.getValue().getController();
        controller.setDialogStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        // reload display after changing preferences
        if (isDisplay.getValue() && controller.isOkClicked()) {
        	handleProgramDisplay();
        }

	}

	/**
	 * Program menu exit.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	public void handleProgramExit() {
		if (checkModified()) {
			Platform.exit();
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
			newData();
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
	        		new FileChooser.ExtensionFilter("Gebu-Dateien (*.gebu)", "*.gebu"),
	        		new FileChooser.ExtensionFilter("Gebu-5-Dateien (*.esx)", "*.esx"),
	        		new FileChooser.ExtensionFilter("Alle Dateien (*.*)", "*.*")
	        		);
	        if (!Prefs.get(PrefKey.PATH).isEmpty()) {
	        	fileChooser.setInitialDirectory(new File(Prefs.get(PrefKey.PATH)));
	        }

	        File file = fileChooser.showOpenDialog(primaryStage);

	        if (file != null) {
	            openData(file.getPath());
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
        if (Prefs.get(PrefKey.FILE).isEmpty() || AppModel.isLegacy()) {
        	handleFileSaveAs();
        } else {
        	saveData(Prefs.get(PrefKey.FILE));
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
        		new FileChooser.ExtensionFilter("Gebu-Dateien (*.gebu)", "*.gebu"),
        		new FileChooser.ExtensionFilter("Alle Dateien (*.*)", "*.*")
        		);
        if (!Prefs.get(PrefKey.PATH).isEmpty()) {
        	fileChooser.setInitialDirectory(new File(Prefs.get(PrefKey.PATH)));
        }

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
        	if (!file.getName().contains(".")) {
        		file = new File(String.format("%s.gebu", file.getPath()));
        	}
            saveData(file.getPath());
        }

    }

	/**
	 * Help menu about.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleHelpAbout() {

        StringBuilder sbText = new StringBuilder();

        sbText.append("Ein Qualitätsprodukt aus dem Hause \"edge-soft\".");
        sbText.append("\n\n");
        sbText.append("Verbesserungen, Fehler, Hinweise bitte per E-Mail an ekleinod@edgesoft.de.");
        sbText.append("\n");
        sbText.append("Alternativ kann auch eine Fehlermeldung bei github eröffnet werden: https://github.com/ekleinod/gebu/issues.");
        sbText.append("\n\n");
        sbText.append("Die Icons sind aus dem Papirus icon theme (https://github.com/PapirusDevelopmentTeam/papirus-icon-theme-gtk).");

        Alert alert = AlertUtils.createAlert(AlertType.INFORMATION, primaryStage,
        		"Über \"Das Gebu-Programm\"",
        		MessageFormat.format("Das Gebu-Programm Version {0}", Gebu.VERSION),
        		sbText.toString()
        		);

        alert.setGraphic(new ImageView(Resources.loadImage("images/icon-64.png")));
        alert.showAndWait();

    }

	/**
	 * Event menu statistics.
	 *
	 * Shows the event statistics.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleEventStatistics() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventStatistics");
    	AnchorPane eventStatistics = (AnchorPane) pneLoad.getKey();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ereignisstatistik");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        Scene scene = new Scene(eventStatistics);
        dialogStage.setScene(scene);

        // Set the events into the controller.
        EventStatisticsController controller = pneLoad.getValue().getController();
        controller.fillStatistics(AppModel.getData().getContent().getEvent());
        controller.setDialogStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.show();

    }

	/**
	 * Check if data is modified, show corresponding dialog, save data if needed.
	 *
	 * @return did user select continue (true) or cancel (false)?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private boolean checkModified() {

		boolean doContinue = true;

		if (AppModel.isModified() || AppModel.isLegacy()) {

	    	Alert alert = AlertUtils.createAlert(AlertType.CONFIRMATION, primaryStage,
	    			"Nicht gespeicherte Änderungen",
	    			"Sie haben Änderungen durchgeführt, die noch nicht gespeichert wurden.",
	    			"Wollen Sie die geänderten Daten speichern, nicht speichern oder wollen Sie den gesamten Vorgang abbrechen?");
	        
	        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

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

	/**
	 * Saves the data.
	 *
	 * @param theFilename filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void saveData(final String theFilename) {

		try {

			AppModel.getData().getInfo().setModified(LocalDateTime.now());
			AppModel.getData().getInfo().setAppversion(Gebu.VERSION);
			AppModel.getData().getInfo().setDocversion(Gebu.VERSION);
			AppModel.getData().getInfo().setCreator(Gebu.class.getCanonicalName());

			((ContentModel) AppModel.getData().getContent()).sortEvents();
			
			if (ctlEventOverview != null) {
				ctlEventOverview.setTableItems();
			}

			JAXBFiles.marshal(new ObjectFactory().createGebu(AppModel.getData()), theFilename, null);

			AppModel.setFilename(theFilename);
			AppModel.setModified(false);
			AppModel.setLegacy(false);

		} catch (EdgeUtilsException e) {

	        AlertUtils.createAlert(AlertType.ERROR, primaryStage,
	        		"Datenfehler",
	        		"Ein Fehler ist beim Speichern der Gebu-Daten aufgetreten.",
	        		MessageFormat.format("{0}\nDie Daten wurden nicht gespeichert.", e.getMessage()))
	        .showAndWait();

		}

		setAppTitle();

    }
	
	/**
	 * Returns primary stage.
	 * 
	 * @return primary stage
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

}

/* EOF */
