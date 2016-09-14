package de.edgesoft.gebu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.edgesoft.edgeutils.EdgeUtilsException;
import de.edgesoft.edgeutils.files.JAXBFiles;
import de.edgesoft.gebu.view.EventOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
		// this is for debugging only, run GebuTest in order to create test file
		try {
			dtaGebu = JAXBFiles.unmarshal("src/test/resources/de.edgesoft.gebu.jaxb.gebutest.xml", de.edgesoft.gebu.jaxb.Gebu.class);
		} catch (EdgeUtilsException e) {
			e.printStackTrace();
		}
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

        initAppLayout();

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
            
        } catch (IOException e) {
            e.printStackTrace();
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

}

/* EOF */
