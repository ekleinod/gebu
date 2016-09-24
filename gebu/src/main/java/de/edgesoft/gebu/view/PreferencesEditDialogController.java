package de.edgesoft.gebu.view;

import de.edgesoft.edgeutils.javafx.ColorUtils;
import de.edgesoft.gebu.utils.AlertUtils;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Controller for preferences edit dialog scene.
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
public class PreferencesEditDialogController {

	/**
	 * Interval spinner.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Spinner<Integer> spnInterval;

	/**
	 * Fontsize spinner past.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Spinner<Integer> spnPastFontsize;

	/**
	 * Foreground color picker past.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ColorPicker pckPastForeground;

	/**
	 * Backgroud color picker past.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ColorPicker pckPastBackground;

	/**
	 * Fontsize spinner present.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Spinner<Integer> spnPresentFontsize;

	/**
	 * Foreground color picker present.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ColorPicker pckPresentForeground;

	/**
	 * Backgroud color picker present.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ColorPicker pckPresentBackground;

	/**
	 * Fontsize spinner future.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private Spinner<Integer> spnFutureFontsize;

	/**
	 * Foreground color picker future.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ColorPicker pckFutureForeground;

	/**
	 * Backgroud color picker future.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	@FXML
	private ColorPicker pckFutureBackground;

	/**
	 * Reference to dialog stage.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private Stage dialogStage;

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

		spnInterval.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 365, Integer.valueOf(Prefs.get(PrefKey.INTERVAL))));

		spnPastFontsize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 20, Integer.valueOf(Prefs.get(PrefKey.PAST_FONTSIZE))));
		pckPastForeground.setValue(Color.web(Prefs.get(PrefKey.PAST_FOREGROUND)));
		pckPastBackground.setValue(Color.web(Prefs.get(PrefKey.PAST_BACKGROUND)));

		spnPresentFontsize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 20, Integer.valueOf(Prefs.get(PrefKey.PRESENT_FONTSIZE))));
		pckPresentForeground.setValue(Color.web(Prefs.get(PrefKey.PRESENT_FOREGROUND)));
		pckPresentBackground.setValue(Color.web(Prefs.get(PrefKey.PRESENT_BACKGROUND)));

		spnFutureFontsize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 20, Integer.valueOf(Prefs.get(PrefKey.FUTURE_FONTSIZE))));
		pckFutureForeground.setValue(Color.web(Prefs.get(PrefKey.FUTURE_FOREGROUND)));
		pckFutureBackground.setValue(Color.web(Prefs.get(PrefKey.FUTURE_BACKGROUND)));

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
        if (isInputValid()) {

        	Prefs.put(PrefKey.INTERVAL, spnInterval.getValue().toString());

        	Prefs.put(PrefKey.PAST_FONTSIZE, spnPastFontsize.getValue().toString());
        	Prefs.put(PrefKey.PAST_FOREGROUND, ColorUtils.formatWebHex(pckPastForeground.getValue()));
        	Prefs.put(PrefKey.PAST_BACKGROUND, ColorUtils.formatWebHex(pckPastBackground.getValue()));

        	Prefs.put(PrefKey.PRESENT_FONTSIZE, spnPresentFontsize.getValue().toString());
        	Prefs.put(PrefKey.PRESENT_FOREGROUND, ColorUtils.formatWebHex(pckPresentForeground.getValue()));
        	Prefs.put(PrefKey.PRESENT_BACKGROUND, ColorUtils.formatWebHex(pckPresentBackground.getValue()));

        	Prefs.put(PrefKey.FUTURE_FONTSIZE, spnFutureFontsize.getValue().toString());
        	Prefs.put(PrefKey.FUTURE_FOREGROUND, ColorUtils.formatWebHex(pckFutureForeground.getValue()));
        	Prefs.put(PrefKey.FUTURE_BACKGROUND, ColorUtils.formatWebHex(pckFutureBackground.getValue()));

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

        if (spnInterval.getValue() == null) {
            sbErrorMessage.append("Kein gültiges Intervall.\n");
        }

        if (spnPastFontsize.getValue() == null) {
            sbErrorMessage.append("Keine gültige Schriftgröße für vergangene Ereignisse.\n");
        }

        if (spnPresentFontsize.getValue() == null) {
            sbErrorMessage.append("Keine gültige Schriftgröße für heutige Ereignisse.\n");
        }

        if (spnFutureFontsize.getValue() == null) {
            sbErrorMessage.append("Keine gültige Schriftgröße für zukünftige Ereignisse.\n");
        }

        if (sbErrorMessage.length() == 0) {
            return true;
        }

        // Show the error message.
        Alert alert = AlertUtils.createAlert(AlertType.ERROR);
        alert.initOwner(dialogStage);

        alert.setTitle("Ungültige Eingaben");
        alert.setHeaderText("Bitte korrigieren Sie die fehlerhaften Eingaben.");
        alert.setContentText(sbErrorMessage.toString());

        alert.showAndWait();

        return false;

    }

}

/* EOF */
