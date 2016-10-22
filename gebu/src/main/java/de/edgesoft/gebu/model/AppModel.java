package de.edgesoft.gebu.model;

import java.nio.file.Paths;

import de.edgesoft.gebu.jaxb.Gebu;
import de.edgesoft.gebu.utils.PrefKey;
import de.edgesoft.gebu.utils.Prefs;

/**
 * Gebu application model.
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
public final class AppModel {

	/**
	 * Gebu event data.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static Gebu dtaGebu = null;

	/**
	 * Flag, if data is modified.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static boolean isModified = false;

	/**
	 * Flag, if data is legacy.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static boolean isLegacy = false;

	/**
     * Returns gebu data.
     *
     * @return gebu data
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public static Gebu getData() {
        return dtaGebu;
    }

	/**
     * Sets gebu data.
     *
     * @return gebu data
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public static void setData(final Gebu theData) {
        dtaGebu = theData;
    }

	/**
     * Is data modified?.
     *
     * @return Is data modified?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public static boolean isModified() {
        return isModified;
    }

	/**
     * Sets modified flag.
     *
     * @param modified data modified?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public static void setModified(final boolean modified) {
        isModified = modified;
    }

	/**
     * Is data legacy?.
     *
     * @return Is data legacy?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public static boolean isLegacy() {
        return isLegacy;
    }

	/**
     * Sets legacy flag.
     *
     * @param legacy data legacy?
	 *
	 * @version 6.0.0
	 * @since 6.0.0
     */
    public static void setLegacy(final boolean legacy) {
    	isLegacy = legacy;
    }

	/**
	 * Returns the file name.
	 *
	 * @return filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static String getFilename() {
		return Prefs.get(PrefKey.FILE);
    }

	/**
	 * Sets the file name.
	 *
	 * @param theFilename filename
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static void setFilename(final String theFilename) {

		if (theFilename == null) {
			Prefs.put(PrefKey.FILE, "");
		} else {
			Prefs.put(PrefKey.FILE, theFilename);
			Prefs.put(PrefKey.PATH, Paths.get(theFilename).getParent().toString());
		}

    }

}

/* EOF */
