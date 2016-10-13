package de.edgesoft.gebu;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.edgesoft.edgeutils.EdgeUtilsException;
import de.edgesoft.edgeutils.commons.Info;
import de.edgesoft.edgeutils.commons.Version;
import de.edgesoft.edgeutils.commons.ext.VersionExt;
import de.edgesoft.edgeutils.files.JAXBFiles;
import de.edgesoft.gebu.jaxb.Content;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.jaxb.ObjectFactory;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.utils.Resources;
import de.edgesoft.gebu.view.AppLayoutController;
import de.edgesoft.gebu.view.EventDisplayController;
import de.edgesoft.gebu.view.EventEditDialogController;
import de.edgesoft.gebu.view.EventOverviewController;
import de.edgesoft.gebu.view.EventStatisticsController;
import de.edgesoft.gebu.view.PreferencesEditDialogController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Gebu application.
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
public class Gebu extends Application {

	/** Central logger for all classes. */
	public static final Logger logger = LogManager.getLogger(Gebu.class.getPackage().getName());

	/** Program and doc version. */
	public static final Version VERSION = new VersionExt("6.0.0 beta 2");

	/**
	 * Application icon.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static final Image ICON = Resources.loadImage("images/icon-32.png");

	/**
	 * Gebu event data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private de.edgesoft.gebu.jaxb.Gebu dtaGebu = null;

	/**
	 * Primary stage.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Stage stgPrimary = null;

	/**
	 * Pane: main application layout container.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private BorderPane pneAppLayout = null;

	/**
	 * App controller.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private AppLayoutController ctlApp = null;

	/**
	 * Event overview controller.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private EventOverviewController ctlEventOverview = null;

	/**
	 * Flag, if data is modified.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private boolean isModified = false;

	/**
	 * Flag, if display (true) or editor (false) is shown.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private BooleanProperty isDisplay = null;

	/**
     * Returns the main stage.
     *
     * @return primary stage
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public Stage getPrimaryStage() {
        return stgPrimary;
    }

	/**
     * Is data modified?.
     *
     * @return Is data modified?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public boolean isModified() {
        return isModified;
    }

	/**
     * Sets modified flag.
     *
     * @param modified data modified?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public void setModified(final boolean modified) {
        isModified = modified;
    }

	/**
     * Returns gebu data.
     *
     * @return gebu data
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public de.edgesoft.gebu.jaxb.Gebu getGebuData() {
        return dtaGebu;
    }

	/**
	 * Starts the application.
	 *
	 * @param args command line arguments
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Constructor.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public Gebu() {
		isDisplay = new SimpleBooleanProperty();
	}

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param primaryStage primary stage
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
	@Override
	public void start(Stage primaryStage) {

		showSplashScreen();

		stgPrimary = primaryStage;
        stgPrimary.setTitle("Das Gebu-Programm");

        // set icon
        stgPrimary.getIcons().add(ICON);

        initAppLayout();

        initData();

        if (dtaGebu.getContent().getEvent().isEmpty()) {
        	showEventOverview();
        } else {
        	showEventDisplay();
        }

	}

	/**
	 * Initializes the application layout.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void initAppLayout() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("AppLayout");
        pneAppLayout = (BorderPane) pneLoad.getKey();

        // Show the scene containing the root layout.
        Scene scene = new Scene(pneAppLayout);
        stgPrimary.setScene(scene);
        stgPrimary.show();

        // Give the controller access to the main app.
        ctlApp = pneLoad.getValue().getController();
        ctlApp.setGebuApp(this);

        // resize to last dimensions
    	stgPrimary.setX(Double.parseDouble(Prefs.get(PrefKey.STAGE_X)));
    	stgPrimary.setY(Double.parseDouble(Prefs.get(PrefKey.STAGE_Y)));

    	stgPrimary.setWidth(Double.parseDouble(Prefs.get(PrefKey.STAGE_WIDTH)));
    	stgPrimary.setHeight(Double.parseDouble(Prefs.get(PrefKey.STAGE_HEIGHT)));

		// if changed, save bounds to preferences
		stgPrimary.xProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_X, Double.toString(newValue.doubleValue()));
		});
		stgPrimary.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_WIDTH, Double.toString(newValue.doubleValue()));
		});
		stgPrimary.yProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_Y, Double.toString(newValue.doubleValue()));
		});
		stgPrimary.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_HEIGHT, Double.toString(newValue.doubleValue()));
		});

    }

	/**
	 * Initializes the data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void initData() {

		if (Prefs.get(PrefKey.FILE).isEmpty()) {
			newData();
		} else {
			openData(Prefs.get(PrefKey.FILE));
		}

    }

	/**
	 * Sets the file name.
	 *
	 * @param theFilename filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static void setFilename(final String theFilename) {

		if (theFilename == null) {
			Prefs.put(PrefKey.FILE, "");
		} else {
			Prefs.put(PrefKey.FILE, theFilename);
			Prefs.put(PrefKey.PATH, Paths.get(theFilename).getParent().toString());
		}

    }

	/**
	 * Sets the app title.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setAppTitle() {

		if (Prefs.get(PrefKey.FILE).isEmpty()) {
			stgPrimary.setTitle("Das Gebu-Programm");
		} else {
			stgPrimary.setTitle(MessageFormat.format("Das Gebu-Programm - {0}", Prefs.get(PrefKey.FILE)));
		}

		if (isModified()) {
			stgPrimary.setTitle(String.format("%s *", stgPrimary.getTitle()));
		}

    }

	/**
	 * Creates new data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void newData() {

		dtaGebu = new ObjectFactory().createGebu();

		Info info = new de.edgesoft.edgeutils.commons.ObjectFactory().createInfo();

		info.setCreated(LocalDateTime.now());
		info.setModified(LocalDateTime.now());
		info.setAppversion(VERSION);
		info.setDocversion(VERSION);
		info.setCreator(Gebu.class.getCanonicalName());

		dtaGebu.setInfo(info);

		Content content = new ObjectFactory().createContent();
		dtaGebu.setContent(content);

		if (ctlEventOverview != null) {
			ctlEventOverview.setTableItems();
		}

		setFilename(null);
		setModified(false);
		setAppTitle();

    }

	/**
	 * Loads the data.
	 *
	 * @param theFilename filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void openData(final String theFilename) {

		try {

			dtaGebu = JAXBFiles.unmarshal(theFilename, de.edgesoft.gebu.jaxb.Gebu.class);

			// legacy files?
			if (dtaGebu.getInfo() == null) {
				openLegacyData(theFilename);
				if (dtaGebu == null) {
					throw new EdgeUtilsException("Die Daten konnten nicht korrekt eingelesen werden.");
				}
			}

			if (ctlEventOverview != null) {
				ctlEventOverview.setTableItems();
			}

			setFilename(theFilename);
			setModified(false);
			setAppTitle();

		} catch (EdgeUtilsException e) {

	        Alert alert = AlertUtils.createAlert(AlertType.ERROR);
	        alert.initOwner(stgPrimary);

	        alert.setTitle("Datenfehler");
	        alert.setHeaderText("Ein Fehler ist beim Laden der Gebu-Daten aufgetreten.");
	        alert.setContentText(MessageFormat.format("{0}\nDas Programm wird ohne Daten fortgeführt.", e.getMessage()));

	        alert.showAndWait();

	        newData();

		}

    }

	/**
	 * Loads and converts legacy data.
	 *
	 * @param theFilename filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void openLegacyData(final String theFilename) {

		dtaGebu = null;

		try {

			de.edgesoft.gebu.jaxb_legacy_5_2.Gebu dtaLegacy = JAXBFiles.unmarshal(theFilename, de.edgesoft.gebu.jaxb_legacy_5_2.Gebu.class);

			newData();
			dtaLegacy.getData().getEvent().stream().forEach(
					event -> {
						Event newEvent = new ObjectFactory().createEvent();
						newEvent.setTitle(new SimpleStringProperty(event.getDescription()));
						newEvent.setDate(new SimpleObjectProperty<>(event.getDate()));
						newEvent.setEventtype(new SimpleStringProperty(event.getEventname()));
						newEvent.setCategory(new SimpleStringProperty((event.getCategory().equals("Keine") ? null : event.getCategory())));
						dtaGebu.getContent().getEvent().add(newEvent);
					}
					);

	        Alert alert = AlertUtils.createAlert(AlertType.INFORMATION);
	        alert.initOwner(stgPrimary);

	        alert.setTitle("Datenkonvertierung");
	        alert.setHeaderText(null);
	        alert.setContentText("Die eingelesenen Daten stammen von einer alten Programmversion. Die Daten wurden eingelesen und konvertiert.\n\nFalls Sie die Daten speichern, werden diese im neuen Format gespeichert. Wenn Sie die Originaldatei behalten wollen, speichern Sie die Datei nicht oder unter einem anderen Namen ab.");

	        alert.showAndWait();

		} catch (EdgeUtilsException e) {
			dtaGebu = null;
		}

    }

	/**
	 * Saves the data.
	 *
	 * @param theFilename filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void saveData(final String theFilename) {

		try {

			dtaGebu.getInfo().setModified(LocalDateTime.now());

			((ContentModel) dtaGebu.getContent()).sortEvents();

			JAXBFiles.marshal(new ObjectFactory().createGebu(dtaGebu), theFilename, null);

			setFilename(theFilename);
			setModified(false);

		} catch (EdgeUtilsException e) {

	        Alert alert = AlertUtils.createAlert(AlertType.ERROR);
	        alert.initOwner(stgPrimary);

	        alert.setTitle("Datenfehler");
	        alert.setHeaderText("Ein Fehler ist beim Speichern der Gebu-Daten aufgetreten.");
	        alert.setContentText(MessageFormat.format("{0}\nDie Daten wurden nicht gespeichert.", e.getMessage()));

	        alert.showAndWait();

		}

		setAppTitle();

    }

	/**
	 * Shows the event display.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void showEventDisplay() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventDisplay");
    	AnchorPane eventDisplay = (AnchorPane) pneLoad.getKey();

        // Set event overview into the center of root layout.
        pneAppLayout.setCenter(eventDisplay);

        // Give the controller access to the app.
        EventDisplayController ctlEventDisplay = pneLoad.getValue().getController();
        ctlEventDisplay.setGebuApp(this);
        ctlEventDisplay.displayEvents(LocalDate.now());

        isDisplay.setValue(true);

    }

	/**
	 * Shows the event overview.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void showEventOverview() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventOverview");
    	AnchorPane eventOverview = (AnchorPane) pneLoad.getKey();

        // Set event overview into the center of root layout.
        pneAppLayout.setCenter(eventOverview);

        // Give the controller access to the app.
        ctlEventOverview = pneLoad.getValue().getController();
        ctlEventOverview.setGebuApp(this);
        ctlEventOverview.setTableItems();

        isDisplay.setValue(false);

    }

	/**
	 * Opens the event edit dialog.
	 *
	 * If the user clicks OK, the changes are saved into the provided event object and true is returned.
	 *
	 * @param theEvent the event to be edited
	 * @return true if the user clicked OK, false otherwise.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public boolean showEventEditDialog(Event theEvent) {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventEditDialog");
    	AnchorPane editDialog = (AnchorPane) pneLoad.getKey();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ereignis editieren");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stgPrimary);

        Scene scene = new Scene(editDialog);
        dialogStage.setScene(scene);

        // Set the event into the controller.
        EventEditDialogController controller = pneLoad.getValue().getController();
        controller.setGebuApp(this);
        controller.setDialogStage(dialogStage);
        controller.setEvent(theEvent);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();

	}

	/**
	 * Opens the preferences edit dialog.
	 *
	 * If the user clicks OK, the changes are saved into the provided event object and true is returned.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void showPreferencesEditDialog() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("PreferencesEditDialog");
    	AnchorPane preferencesDialog = (AnchorPane) pneLoad.getKey();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Einstellungen");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stgPrimary);

        Scene scene = new Scene(preferencesDialog);
        dialogStage.setScene(scene);

        // initialize controller
        PreferencesEditDialogController controller = pneLoad.getValue().getController();
        controller.setDialogStage(dialogStage);
        controller.setGebuApp(this);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        if (isDisplay.getValue() && controller.isOkClicked()) {
        	showEventDisplay();
        }

	}

	/**
	 * Show the event statistics.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void showEventStatistics() {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventStatistics");
    	AnchorPane eventStatistics = (AnchorPane) pneLoad.getKey();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ereignisstatistik");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stgPrimary);

        Scene scene = new Scene(eventStatistics);
        dialogStage.setScene(scene);

        // Set the events into the controller.
        EventStatisticsController controller = pneLoad.getValue().getController();
        controller.fillStatistics(dtaGebu.getContent().getEvent());
        controller.setDialogStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.show();

	}

	/**
	 * Is app in display mode?
	 *
	 * @return display mode (true) or editor mode (false)
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public BooleanProperty isDisplay() {
		return isDisplay;
	}

	/**
	 * Shows splash screen.
	 *
	 * Inspired by https://gist.github.com/jewelsea/2305098
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void showSplashScreen() {

		// Load splash screen.
		Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("SplashScreen");
		final AnchorPane pane = (AnchorPane) pneLoad.getKey();

		// Create and fill splash screen stage.
		Stage stage = new Stage();
		stage.initModality(Modality.NONE);
		stage.setAlwaysOnTop(true);
		stage.initStyle(StageStyle.TRANSPARENT);

		Scene scene = new Scene(pane, new Color(1, 1, 1, .5));
		stage.setScene(scene);

		// define task, that waits 4 seconds, then returns null, i.e. succeeds
		// if needed, some progress bar output could be defined here
		final Task<Void> splashTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread.sleep(4000);
				return null;
			}
		};

		// add listener to succeed state of task, then fade out
		splashTask.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), pane);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> stage.hide());
                fadeSplash.play();
            }
        });

		// show splash screen, then start fading task
		stage.show();
		new Thread(splashTask).start();

	}

	/**
	 * Check if data is modified, show corresponding dialog, save data if needed.
	 *
	 * @return continue?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public boolean checkModified() {

		boolean doContinue = true;

		if (isModified()) {

	    	Alert alert = AlertUtils.createAlert(AlertType.CONFIRMATION);
	        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

	        alert.setTitle("Nicht gespeicherte Änderungen");
	        alert.setHeaderText("Sie haben Änderungen durchgeführt, die noch nicht gespeichert wurden.");
	        alert.setContentText("Wollen Sie die geänderten Daten speichern, nicht speichern oder wollen Sie den gesamten Vorgang abbrechen?");

	        Optional<ButtonType> result = alert.showAndWait();
	        if (result.isPresent()) {
    			if (result.get() == ButtonType.YES) {
    				ctlApp.handleFileSave();
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
