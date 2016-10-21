package de.edgesoft.gebu.controller;

import java.time.LocalDate;
import java.util.Objects;

import de.edgesoft.edgeutils.datetime.DateTimeUtils;
import de.edgesoft.gebu.Gebu;
import de.edgesoft.gebu.jaxb.Event;
import de.edgesoft.gebu.model.ContentModel;
import de.edgesoft.gebu.utils.Resources;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Controller for event edit dialog scene.
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
public class EventEditDialogController {

	/**
	 * Title text field label.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Label lblTitle;

	/**
	 * Title text field.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private TextField txtTitle;

	/**
	 * Date picker.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private DatePicker pickDate;

	/**
	 * Eventtype combobox.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ComboBox<String> cboEventtype;

	/**
	 * Category combobox.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ComboBox<String> cboCategory;

	/**
	 * OK button.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnOK;

	/**
	 * Cancel button.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Button btnCancel;

	/**
	 * Reference to dialog stage.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Stage dialogStage;

	/**
	 * Current event.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Event currentEvent;

	/**
	 * Reference to application.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Gebu appGebu;

	/**
	 * OK clicked?.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private boolean okClicked;


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

		// @todo until setting labelFor is fixed in FXML and Scenebuilder; set it programmatically
		// does not work either, strange
		lblTitle.setLabelFor(txtTitle);

		// set date picker date format
		pickDate.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate date) {
				if (date == null) {
					return "";
				}
				return DateTimeUtils.formatDate(date);
			}

			@Override
			public LocalDate fromString(String string) {
				return DateTimeUtils.parseDate(string);
			}
		});

		// enable ok button for valid entries only
		btnOK.disableProperty().bind(
				pickDate.valueProperty().isNull()
				.or(txtTitle.textProperty().isEmpty())
				.or(cboEventtype.valueProperty().isNull())
				.or(cboEventtype.valueProperty().asString().isEmpty())
				);

		// icons
		btnOK.setGraphic(new ImageView(Resources.loadImage("icons/actions/dialog-ok-16.png")));
		btnCancel.setGraphic(new ImageView(Resources.loadImage("icons/actions/dialog-cancel-16.png")));
		
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
	 * Sets dialog stage.
	 *
	 * @param theStage dialog stage
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setDialogStage(final Stage theStage) {
        dialogStage = theStage;
    }

	/**
	 * Sets event to be edited.
	 *
	 * @param theEvent edit event
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public void setEvent(Event theEvent) {

		Objects.requireNonNull(theEvent);

        txtTitle.setText(
        		(theEvent.getTitle() == null) ?
        				null :
        				theEvent.getTitle().getValue());
        pickDate.setValue(
        		(theEvent.getDate() == null) ?
        				null :
        				(LocalDate) theEvent.getDate().getValue());
        cboEventtype.setValue(
        		(theEvent.getEventtype() == null) ?
        				null :
        				theEvent.getEventtype().getValue());
        cboCategory.setValue(
        		(theEvent.getCategory() == null) ?
        				null :
        				theEvent.getCategory().getValue());

        currentEvent = theEvent;

		// fill event type and category boxes
		cboEventtype.setItems(FXCollections.observableArrayList(((ContentModel) appGebu.getGebuData().getContent()).getEventtypes()));
		cboCategory.setItems(FXCollections.observableArrayList(((ContentModel) appGebu.getGebuData().getContent()).getCategories()));

    }

	/**
	 * Returns if user clicked ok.
	 *
	 * @return did user click ok?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public boolean isOkClicked() {
        return okClicked;
    }

	/**
	 * Validates input, stores ok click, and closes dialog; does nothing for invalid input.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleOk() {
        okClicked = false;

    	if (currentEvent.getTitle() == null) {
    		currentEvent.setTitle(new SimpleStringProperty());
    	}
        currentEvent.getTitle().setValue(txtTitle.getText());

        if (currentEvent.getDate() == null) {
    		currentEvent.setDate(new SimpleObjectProperty<>());
    	}
        currentEvent.getDate().setValue(pickDate.getValue());

    	if (currentEvent.getEventtype() == null) {
    		currentEvent.setEventtype(new SimpleStringProperty());
    	}
        currentEvent.getEventtype().setValue(cboEventtype.getValue());

        if (currentEvent.getCategory() == null) {
    		currentEvent.setCategory(new SimpleStringProperty());
    	}
        currentEvent.getCategory().setValue(cboCategory.getValue());

        okClicked = true;
        dialogStage.close();

    }

	/**
	 * Stores non-ok click and closes dialog.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
    private void handleCancel() {
		okClicked = false;
        dialogStage.close();
    }

}

/* EOF */
