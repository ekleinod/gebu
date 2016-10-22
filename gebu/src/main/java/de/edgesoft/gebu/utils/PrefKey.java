
package de.edgesoft.gebu.utils;

/**
 * Preference keys.
 *
 * For enums I use the coding style of jaxb, so there will be no inconsistencies.
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
public enum PrefKey {
	
	DISPLAY_CATEGORIES,

	FILE,

	FUTURE_BACKGROUND,
	FUTURE_FONTSIZE,
	FUTURE_FOREGROUND,

	INTERVAL,

	PAST_BACKGROUND,
	PAST_FONTSIZE,
	PAST_FOREGROUND,

	PATH,

	PRESENT_BACKGROUND,
	PRESENT_FONTSIZE,
	PRESENT_FOREGROUND,

	STAGE_HEIGHT,
	STAGE_SPLIT,
	STAGE_WIDTH,
	STAGE_X,
	STAGE_Y,
	
	TITLE_FULLPATH,
	;

    private final String value;

    PrefKey() {
        value = name().toLowerCase();
    }

    public String value() {
        return value;
    }

    public static PrefKey fromValue(String v) {
        for (PrefKey c: PrefKey.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

/* EOF */
