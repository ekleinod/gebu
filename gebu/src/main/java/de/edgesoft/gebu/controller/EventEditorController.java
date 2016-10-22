package de.edgesoft.gebu.controller;

import java.time.LocalDate;
import java.util.Map;

import de.edgesoft.edgeutils.datetime.DateTimeUtils;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.model.AppModel;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.model.EventModel;
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import de.edgesoft.gebu.utils.Resources;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
public class EventEditorController {

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
	 * New button.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnNew;

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
	 * Main app controller.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private AppLayoutController appController = null;
	

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

		// set "empty data" text
		Label lblPlaceholder = new Label("Es wurden noch keine Ereignisse eingegeben. Geben Sie Ereignisse ein oder öffnen Sie eine vorhandene Datei über \"Datei->öffnen\"");
		lblPlaceholder.setWrapText(true);
		tblEvents.setPlaceholder(lblPlaceholder);

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

		// enabling edit/delete buttons only with selection
		btnEdit.disableProperty().bind(tblEvents.getSelectionModel().selectedItemProperty().isNull());
		btnDelete.disableProperty().bind(tblEvents.getSelectionModel().selectedItemProperty().isNull());

		// set divider position
		pneSplit.setDividerPositions(Double.parseDouble(Prefs.get(PrefKey.STAGE_SPLIT)));

		// if changed, save divider position to preferences
		pneSplit.getDividers().get(0).positionProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			Prefs.put(PrefKey.STAGE_SPLIT, Double.toString(newValue.doubleValue()));
		});

		// icons
		btnNew.setGraphic(new ImageView(Resources.loadImage("icons/actions/list-add-16.png")));
		btnEdit.setGraphic(new ImageView(Resources.loadImage("icons/actions/edit-16.png")));
		btnDelete.setGraphic(new ImageView(Resources.loadImage("icons/actions/list-remove-16.png")));
		
	}

	/**
	 * Initializes the controller with things, that cannot be done during {@link #initialize()}.
	 * 
	 * @param theAppController app controller
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void initController(final AppLayoutController theAppController) {

		appController = theAppController;
		
	}
		
	/**
	 * Sets events as table items.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setTableItems() {
		if (AppModel.getData() != null) {
			tblEvents.setItems(((ContentModel) AppModel.getData().getContent()).getObservableEvents());
		} else {
			tblEvents.setItems(null);
		}
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
		if (showEventEditDialog(newEvent)) {
			((ContentModel) AppModel.getData().getContent()).getObservableEvents().add(newEvent);
			AppModel.setModified(true);
			appController.setAppTitle();
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

			if (showEventEditDialog(editEvent)) {
				showEventDetails(editEvent);
				AppModel.setModified(true);
				appController.setAppTitle();
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

	    	Alert alert = AlertUtils.createAlert(AlertType.CONFIRMATION, appController.getPrimaryStage(),
	    			"Bestätigung Ereignis löschen",
	    			"Soll das ausgewählte Ereignis gelöscht werden?",
	    			null);

	        alert.showAndWait()
	        		.filter(response -> response == ButtonType.OK)
	        		.ifPresent(response -> {
	        			tblEvents.getItems().remove(selectedIndex);
	        			AppModel.setModified(true);
	        			appController.setAppTitle();
	        			});

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
	private boolean showEventEditDialog(Event theEvent) {

    	Map.Entry<Pane, FXMLLoader> pneLoad = Resources.loadPane("EventEditDialog");
    	AnchorPane editDialog = (AnchorPane) pneLoad.getKey();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(appController.getPrimaryStage());
        dialogStage.setTitle("Ereignis editieren");

        Scene scene = new Scene(editDialog);
        dialogStage.setScene(scene);

        // Set the event into the controller.
        EventEditDialogController controller = pneLoad.getValue().getController();
        controller.setDialogStage(dialogStage);
        controller.setEvent(theEvent);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();

	}

}

/* EOF */
