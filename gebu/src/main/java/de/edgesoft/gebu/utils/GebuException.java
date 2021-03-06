package de.edgesoft.gebu.utils;

/**
 * Special exception (good coding style, I presume).
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
public class GebuException extends Exception {
	
	/** Default serial id. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor with message.
	 * 
	 * @param theErrorMessage error message
	 * 
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public GebuException(String theErrorMessage) {
		super(theErrorMessage);
	}

}

/* EOF */
