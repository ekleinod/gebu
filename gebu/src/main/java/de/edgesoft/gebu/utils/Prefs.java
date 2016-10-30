package de.edgesoft.gebu.utils;

import java.util.prefs.Preferences;

import de.edgesoft.gebu.Gebu;
import javafx.stage.Screen;

/**
 * Central preferences class.
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
public class Prefs {

	/**
	 * Preferences object.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static Preferences preferences = null;

	/**
	 * Returns preferences.
	 *
	 * @return preferences
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static Preferences getPreferences() {
		if (preferences == null) {
			preferences = Preferences.userNodeForPackage(Gebu.class);
		}
		return preferences;
	}

	/**
	 * Get preference for key.
	 *
	 * @param theKey preference key
	 * @return preference value
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static String get(final PrefKey theKey) {

		switch (theKey) {

			case DISPLAY_CATEGORIES:
				return getPreferences().get(theKey.value(), "false");

			case FUTURE_BACKGROUND:
				return getPreferences().get(theKey.value(), "#F0F8FF");
			case FUTURE_FONTSIZE:
				return getPreferences().get(theKey.value(), "10");
			case FUTURE_FOREGROUND:
				return getPreferences().get(theKey.value(), "#6495ED");

			case INTERVAL:
				return getPreferences().get(theKey.value(), "7");

			case PAST_BACKGROUND:
				return getPreferences().get(theKey.value(), "#F5F5F5");
			case PAST_FONTSIZE:
				return getPreferences().get(theKey.value(), "10");
			case PAST_FOREGROUND:
				return getPreferences().get(theKey.value(), "#008080");

			case PRESENT_BACKGROUND:
				return getPreferences().get(theKey.value(), "#FFFFFF");
			case PRESENT_FONTSIZE:
				return getPreferences().get(theKey.value(), "12");
			case PRESENT_FOREGROUND:
				return getPreferences().get(theKey.value(), "#D2691E");

			case STAGE_WIDTH:
				return getPreferences().get(theKey.value(), Double.toString(800));
			case STAGE_X:
				return getPreferences().get(theKey.value(), Double.toString((Screen.getPrimary().getBounds().getWidth() - 800) / 2));

			case STAGE_HEIGHT:
				return getPreferences().get(theKey.value(), Double.toString(600));
			case STAGE_Y:
				return getPreferences().get(theKey.value(), Double.toString((Screen.getPrimary().getBounds().getHeight() - 600) / 2));

			case STAGE_SPLIT:
				return getPreferences().get(theKey.value(), Double.toString(0.6));

			case TITLE_FULLPATH:
				return getPreferences().get(theKey.value(), Boolean.FALSE.toString());

			default:
				return getPreferences().get(theKey.value(), "");
		}

	}

	/**
	 * Get preference for key.
	 *
	 * @param theKey preference key
	 * @return preference value
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static String get(final String theKey) {
		return getPreferences().get(theKey, "");
	}

	/**
	 * Set preference value for key.
	 *
	 * @param theKey preference key
	 * @param theValue value
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static void put(final PrefKey theKey, final String theValue) {
		put(theKey.value(), theValue);
	}

	/**
	 * Set preference value for text key.
	 *
	 * @param theKey text key
	 * @param theValue value
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static void put(final String theKey, final String theValue) {
		getPreferences().put(theKey, theValue);
	}

	/**
	 * Removes key.
	 *
	 * @param theKey text key
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static void remove(final String theKey) {
		getPreferences().remove(theKey);
	}

}

/* EOF */
