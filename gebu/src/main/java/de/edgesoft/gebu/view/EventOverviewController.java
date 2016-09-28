package de.edgesoft.gebu.view;

import java.time.LocalDate;

import de.edgesoft.edgeutils.datetime.DateTimeUtils;
import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.model.EventModel;
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
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
	 * Edit button.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnEdit;

	/**
	 * Delete button.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnDelete;

	/**
	 * Split pane.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private SplitPane pneSplit;

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

		// hook data to columns
		colTitle.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		colDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
		colEventtype.setCellValueFactory(cellData -> cellData.getValue().getEventtype());
		colCategory.setCellValueFactory(cellData -> cellData.getValue().getCategory());

		// format date column
		colDate.setCellFactory(column -> {
		    return new TableCell<EventModel, LocalDate>() {
		        @Override
		        protected void updateItem(LocalDate item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		                setText(null);
		            } else {
		                setText(DateTimeUtils.formatDate(item));
		            }
		        }
		    };
		});

		// set sorting of date column
		colDate.setComparator(EventModel.LOCALDATE);

		// clear event details
		showEventDetails(null);

		// listen to selection changes, show event
		tblEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEventDetails(newValue));

		// set divider position
		pneSplit.setDividerPositions(Double.parseDouble(Prefs.get(PrefKey.STAGE_SPLIT)));

		// if changed, save divider position to preferences
		pneSplit.getDividers().get(0).positionProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_SPLIT, Double.toString(newValue.doubleValue()));
		});

		// enabling edit/delete buttons only with selection
		btnEdit.disableProperty().bind(tblEvents.getSelectionModel().selectedItemProperty().isNull());
		btnDelete.disableProperty().bind(tblEvents.getSelectionModel().selectedItemProperty().isNull());

	}

	/**
	 * Called by main application for reference to itself.
	 *
	 * @param theApp reference to application
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setGebuApp(final Gebu theApp) {
        appGebu = theApp;
    }

	/**
	 * Sets events as table items.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setTableItems() {
		tblEvents.setItems(((ContentModel) appGebu.getGebuData().getContent()).getObservableEvents());
//		tblEvents.getSortOrder().setAll(colDate);
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

	        lblTitle.setText(
	        		(theEvent.getTitle() == null) ?
	        				null :
	        				theEvent.getTitle().getValue());
	        lblDate.setText(
	        		(theEvent.getDate() == null) ?
	        				null :
	        				DateTimeUtils.formatDate((LocalDate) theEvent.getDate().getValue()));
	        lblEventtype.setText(
	        		(theEvent.getEventtype() == null) ?
	        				null :
	        				theEvent.getEventtype().getValue());
	        lblCategory.setText(
	        		(theEvent.getCategory() == null) ?
	        				null :
	        				theEvent.getCategory().getValue());

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
				appGebu.setModified(true);
				appGebu.setAppTitle();
			}

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

	    	Alert alert = AlertUtils.createAlert(AlertType.CONFIRMATION);
	        alert.initOwner(appGebu.getPrimaryStage());

	        alert.setTitle("Bestätigung Ereignis löschen");
	        alert.setHeaderText("Soll das ausgewählte Ereignis gelöscht werden?");

	        alert.showAndWait()
	        		.filter(response -> response == ButtonType.OK)
	        		.ifPresent(response -> {
	        			tblEvents.getItems().remove(selectedIndex);
	        			appGebu.setModified(true);
	        			appGebu.setAppTitle();
	        			});

	    }

	}

}

/* EOF */
