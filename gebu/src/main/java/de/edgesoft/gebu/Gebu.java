package de.edgesoft.gebu;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.edgesoft.edgeutils.commons.Version;
import de.edgesoft.edgeutils.commons.ext.VersionExt;
import de.edgesoft.gebu.controller.AppLayoutController;
import de.edgesoft.gebu.utils.Resources;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

	/** 
	 * Central logger for all classes. 
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static final Logger logger = LogManager.getLogger(Gebu.class.getPackage().getName());

	/** 
	 * Program and doc version. 
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static final Version VERSION = new VersionExt("6.0.0 beta 2");

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

		// load app layout and controller, then delegate control to controller
    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("AppLayout");
        ((AppLayoutController) pneLoad.getValue().getController()).initAppLayout(primaryStage);

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

}

/* EOF */
