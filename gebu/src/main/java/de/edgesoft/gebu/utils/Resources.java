package de.edgesoft.gebu.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;

import de.edgesoft.edgeutils.files.FileAccess;
import de.edgesoft.gebu.Gebu;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Resources helper.
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
public class Resources {

	/**
	 * Loads image from resources.
	 *
	 * @param theImagePath image path
	 * @return loaded image
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Image loadImage(final String theImagePath) {
		return new Image(String.format("file:src/main/resources/%s", theImagePath));
	}

	/**
	 * Loads fxml pane from resources.
	 *
	 * @param thePaneName pane name
	 * @return loaded pane
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Map.Entry<Pane, FXMLLoader> loadPane(final String thePaneName) {

        try {
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Gebu.class.getResource(String.format("view/%s.fxml", thePaneName)));
            return new AbstractMap.SimpleImmutableEntry<>((Pane) loader.load(), loader);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * Loads file from resources.
	 *
	 * @param theFileName pane name
	 * @return loaded file as string
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static String loadFile(final String theFileName) {

		try {
			return FileAccess.readFile(Paths.get(String.format("src/main/resources/%s", theFileName))).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
    }

	/**
	 * Loads web view from resources.
	 *
	 * @return web view as string
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static String loadWebView() {

		String sReturn = loadFile("webview.html");
		if (sReturn == null) {
			sReturn = "**content**";
		}

		sReturn = sReturn
				.replace("**past foreground**", Prefs.get(PrefKey.PAST_FOREGROUND))
				.replace("**past fontsize**", Prefs.get(PrefKey.PAST_FONTSIZE))
				.replace("**past background**", Prefs.get(PrefKey.PAST_BACKGROUND))

				.replace("**present foreground**", Prefs.get(PrefKey.PRESENT_FOREGROUND))
				.replace("**present fontsize**", Prefs.get(PrefKey.PRESENT_FONTSIZE))
				.replace("**present background**", Prefs.get(PrefKey.PRESENT_BACKGROUND))

				.replace("**future foreground**", Prefs.get(PrefKey.FUTURE_FOREGROUND))
				.replace("**future fontsize**", Prefs.get(PrefKey.FUTURE_FONTSIZE))
				.replace("**future background**", Prefs.get(PrefKey.FUTURE_BACKGROUND))
				;

		return sReturn;
		
    }

}

/* EOF */
