package de.edgesoft.gebu.view;

import de.edgesoft.gebu.jaxb.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
		// nothing to do for now
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

        currentEvent = theEvent;

        txtTitle.setText(theEvent.getTitle());
        txtTitle.setPromptText("Titel eingeben");
        pickDate.setValue(theEvent.getDate());
        cboEventtype.setValue(theEvent.getEventtype());
        cboEventtype.setPromptText("Ereignisart eingeben oder auswählen");
        cboCategory.setValue(theEvent.getCategory());
        cboCategory.setPromptText("Kategorie eingeben oder auswählen");

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
        if (isInputValid()) {

            currentEvent.setTitle(txtTitle.getText());
            currentEvent.setDate(pickDate.getValue());
            currentEvent.setEventtype(cboEventtype.getValue());
            currentEvent.setCategory(cboCategory.getValue());

            okClicked = true;
            dialogStage.close();
        }
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

	/**
	 * Validates input, shows error message for invalid input.
	 *
	 * @return is input valid?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private boolean isInputValid() {

        StringBuilder sbErrorMessage = new StringBuilder();

        if ((txtTitle.getText() == null) || txtTitle.getText().trim().isEmpty()) {
            sbErrorMessage.append("Kein gültiger Titel!\n");
        }

        if (pickDate.getValue() == null) {
            sbErrorMessage.append("Kein gültiges Datum!\n");
        }

        if ((cboEventtype.getValue() == null) || cboEventtype.getValue().trim().isEmpty()) {
            sbErrorMessage.append("Keine gültige Ereignisart!\n");
        }

        if (sbErrorMessage.length() == 0) {
            return true;
        } else {

            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ungültige Eingaben");
            alert.setHeaderText("Bitte korrigieren Sie die fehlerhaften Eingaben.");
            alert.setContentText(sbErrorMessage.toString());

            alert.showAndWait();

            return false;

        }
    }

}

/* EOF */
