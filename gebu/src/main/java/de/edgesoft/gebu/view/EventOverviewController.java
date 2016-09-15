package de.edgesoft.gebu.view;

import java.time.LocalDate;

import de.edgesoft.edgeutils.datetime.DateTimeUtils;
import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.jaxb.model.ContentModel;
import de.edgesoft.gebu.jaxb.model.EventModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for event overview scene.
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
public class EventOverviewController {

	/**
	 * Table view.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private TableView<Event> tblEvents;

	/**
	 * Title column.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private TableColumn<EventModel, String> colTitle;

	/**
	 * Date column.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private TableColumn<EventModel, LocalDate> colDate;

	/**
	 * Eventtype column.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private TableColumn<EventModel, String> colEventtype;

	/**
	 * Category column.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private TableColumn<EventModel, String> colCategory;

	/**
	 * Title label.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Label lblTitle;

	/**
	 * Date label.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Label lblDate;

	/**
	 * Eventtype label.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Label lblEventtype;

	/**
	 * Category label.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Label lblCategory;

	/**
	 * Reference to application.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Gebu appGebu;

	/**
	 * Default constructor, called before initialize.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public EventOverviewController() {
		// nothing special yet
	}

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

		// hook data to columns
		colTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		colDate.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
		colEventtype.setCellValueFactory(cellData -> cellData.getValue().getEventtypeProperty());
		colCategory.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());

		// clear event details
		showEventDetails(null);

		// listen to selection changes, show event
		tblEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEventDetails(newValue));
	}

	/**
	 * Called by main application for reference to itself.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setGebuApp(final Gebu theApp) {
        appGebu = theApp;

        // Add observable list data to the table
        tblEvents.setItems(((ContentModel) appGebu.getGebuData().getContent()).getObservableEvents());
        Gebu.logger.debug(String.format("Table loaded with %d items.", tblEvents.getItems().size()));
    }

	/**
	 * Shows selected event data in detail window.
	 *
	 * @param theEvent event (null if none is selected)
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private void showEventDetails(final Event theEvent) {

	    if (theEvent == null) {

	        lblTitle.setText("");
	        lblDate.setText("");
	        lblEventtype.setText("");
	        lblCategory.setText("");

	    } else {

	        lblTitle.setText(theEvent.getTitle());
	        lblDate.setText(DateTimeUtils.formatDate(theEvent.getDate()));
	        lblEventtype.setText(theEvent.getEventtype());
	        lblCategory.setText(theEvent.getCategory());

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
		}

	}

	/**
	 * Opens edit dialog for editing selected event.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleEditEvent() {

		EventModel editEvent = (EventModel) tblEvents.getSelectionModel().getSelectedItem();

	    if (editEvent != null) {

			if (appGebu.showEventEditDialog(editEvent)) {
				showEventDetails(editEvent);
			}

	    } else {

	    	Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(appGebu.getPrimaryStage());
	        alert.setTitle("Keine Auswahl");
	        alert.setHeaderText("Kein Ereignis zum Ändern ausgewählt");
	        alert.setContentText("Bitte wählen Sie das zu ändernde Ereignis in der Tabelle aus.");

	        alert.showAndWait();

	    }

	}

	/**
	 * Deletes selected event from list.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private void handleDeleteEvent() {

	    int selectedIndex = tblEvents.getSelectionModel().getSelectedIndex();

	    if (selectedIndex >= 0) {

	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.initOwner(appGebu.getPrimaryStage());
	        alert.setTitle("Bestätigung Ereignis löschen");
	        alert.setHeaderText("Soll das ausgewählte Eregnis gelöscht werden?");

	        alert.showAndWait()
	        		.filter(response -> response == ButtonType.OK)
	        		.ifPresent(response -> tblEvents.getItems().remove(selectedIndex));
	    } else {

	    	Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(appGebu.getPrimaryStage());
	        alert.setTitle("Keine Auswahl");
	        alert.setHeaderText("Kein Ereignis zum Löschen ausgewählt");
	        alert.setContentText("Bitte wählen Sie das zu löschende Ereignis in der Tabelle aus.");

	        alert.showAndWait();

	    }

	}

}

/* EOF */
