package de.edgesoft.gebu;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDateTime;

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
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.view.AppLayoutController;
import de.edgesoft.gebu.view.EventEditDialogController;
import de.edgesoft.gebu.view.EventOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	public static final Version VERSION = new VersionExt("6.0.0");
	
	/**
	 * Application icon.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static final Image ICON = new Image("file:src/main/resources/images/icon-32.png");

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
		// nothing to do so far
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

		stgPrimary = primaryStage;
        stgPrimary.setTitle("Das Gebu-Programm");

        // set icon
        this.stgPrimary.getIcons().add(ICON);

        initAppLayout();

        initData();

        showEventOverview();
	}

	/**
	 * Initializes the application layout.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void initAppLayout() {

        try {
            // Load app layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Gebu.class.getResource("view/AppLayout.fxml"));
            pneAppLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(pneAppLayout);
            stgPrimary.setScene(scene);
            stgPrimary.show();
            
            // Give the controller access to the main app.
            AppLayoutController controller = loader.getController();
            controller.setGebuApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
	private void setFilename(final String theFilename) {

		if (theFilename == null) {
			stgPrimary.setTitle("Das Gebu-Programm");
			Prefs.put(PrefKey.FILE, "");
		} else {
			stgPrimary.setTitle(MessageFormat.format("Das Gebu-Programm - {0}", theFilename));
			Prefs.put(PrefKey.FILE, theFilename);
			Prefs.put(PrefKey.PATH, Paths.get(theFilename).getParent().toString());
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
		
		setFilename(null);
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
			
			setFilename(theFilename);
			
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
	public void openLegacyData(final String theFilename) {
		
		dtaGebu = null;
		
		try {
			
			de.edgesoft.gebu.jaxb_legacy_5_2.Gebu dtaLegacy = JAXBFiles.unmarshal(theFilename, de.edgesoft.gebu.jaxb_legacy_5_2.Gebu.class);
			
			newData();
			dtaLegacy.getData().getEvent().stream().forEach(
					event -> {
						Event newEvent = new ObjectFactory().createEvent();
						newEvent.setTitle(event.getDescription());
						newEvent.setDate(event.getDate());
						newEvent.setEventtype(event.getEventname());
						newEvent.setCategory(event.getCategory());
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
			
			JAXBFiles.marshal(new ObjectFactory().createGebu(dtaGebu), theFilename, null);
			
			setFilename(theFilename);
			
		} catch (EdgeUtilsException e) {
			
	        Alert alert = AlertUtils.createAlert(AlertType.ERROR);
	        alert.initOwner(stgPrimary);

	        alert.setTitle("Datenfehler");
	        alert.setHeaderText("Ein Fehler ist beim Speichern der Gebu-Daten aufgetreten.");
	        alert.setContentText(MessageFormat.format("{0}\nDie Daten wurden nicht gespeichert.", e.getMessage()));

	        alert.showAndWait();
	        
		}
		
    }

	/**
	 * Shows the event overview.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void showEventOverview() {
        try {

            // Load event overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Gebu.class.getResource("view/EventOverview.fxml"));
            AnchorPane eventOverview = (AnchorPane) loader.load();

            // Set event overview into the center of root layout.
            pneAppLayout.setCenter(eventOverview);

            // Give the controller access to the app.
            EventOverviewController controller = loader.getController();
            controller.setGebuApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

	    try {

	        // Load dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Gebu.class.getResource("view/EventEditDialog.fxml"));
	        AnchorPane editDialog = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Ereignis editieren");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stgPrimary);

	        Scene scene = new Scene(editDialog);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EventEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setEvent(theEvent);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();

	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }

	}

}

/* EOF */
