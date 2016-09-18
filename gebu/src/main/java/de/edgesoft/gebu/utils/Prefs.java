package de.edgesoft.gebu.utils;

import java.util.prefs.Preferences;

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
			preferences = Preferences.userNodeForPackage(Prefs.class);
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
			case COLOR_FUTURE_BACKGROUND:
				return getPreferences().get(theKey.value(), "#F0F8FF");
			case COLOR_FUTURE_FOREGROUND:
				return getPreferences().get(theKey.value(), "#6495ED");

			case COLOR_PAST_BACKGROUND:
				return getPreferences().get(theKey.value(), "#F5F5F5");
			case COLOR_PAST_FOREGROUND:
				return getPreferences().get(theKey.value(), "#008080");

			case COLOR_PRESENT_BACKGROUND:
				return getPreferences().get(theKey.value(), "#FFFFFF");
			case COLOR_PRESENT_FOREGROUND:
				return getPreferences().get(theKey.value(), "#D2691E");

			case INTERVAL:
				return getPreferences().get(theKey.value(), "7");

			default:
				return getPreferences().get(theKey.value(), "");
		}

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
	private static void put(final String theKey, final String theValue) {
		getPreferences().put(theKey, theValue);
	}

}

/* EOF */
