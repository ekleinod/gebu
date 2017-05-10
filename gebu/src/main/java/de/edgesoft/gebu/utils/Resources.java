package de.edgesoft.gebu.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.edgesoft.gebu.Gebu;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Resources helper.
 *
 * ## Legal stuff
 *
 * Copyright 2016-2017 Ekkart Kleinod <ekleinod@edgesoft.de>
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
	 * Project properties singleton.
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	private static Properties prpProject = null;

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
		return new Image(Gebu.class.getClassLoader().getResourceAsStream(theImagePath));
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
            Gebu.logger.catching(e);
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

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(Gebu.class.getClassLoader().getResourceAsStream(theFileName)))) {

			StringBuilder sbReturn = new StringBuilder();
			String sLine = null;

			while ((sLine = reader.readLine()) != null) {
			    sbReturn.append(sLine);
			    sbReturn.append(System.lineSeparator());
			}

			return sbReturn.toString();

		} catch (Exception e) {
            Gebu.logger.catching(e);
			return "";
		}

    }

	/**
	 * Loads properties from resources.
	 *
	 * @param theFileName pane name
	 * @return loaded file as properties
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Properties loadProperties(final String theFileName) {

		Properties prpReturn = new Properties();

		try {

			prpReturn.load(Gebu.class.getClassLoader().getResourceAsStream(theFileName));

		} catch (Exception e) {
            Gebu.logger.catching(e);
		}

		return prpReturn;

    }

	/**
	 * Loads project properties from resources.
	 *
	 * @return project properties
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Properties getProjectProperties() {
		if (prpProject == null) {
			prpProject = loadProperties("project.properties");
		}
		return prpProject;
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

		String sReturn = null;

		try {

			Map<String, String> mapPrefs = new HashMap<>();

			for (PrefKey key : PrefKey.values()) {
				mapPrefs.put(key.toString().toLowerCase(), Prefs.get(key));
			}

			mapPrefs.put("content", "${content}");

			String sWebView = loadFile("webview.html");
			if (sWebView == null) {
				sWebView = "${content}";
			}

			Template tplWebView = new Template("webview", new StringReader(sWebView), new Configuration(Configuration.VERSION_2_3_26));

			try (StringWriter wrtContent = new StringWriter()) {
				tplWebView.process(mapPrefs, wrtContent);
				sReturn = wrtContent.toString();
			}

		} catch (IOException | TemplateException e) {
            Gebu.logger.catching(e);
			sReturn = e.getMessage();
		}

		return sReturn;

    }

	/**
	 * Loads event view from resources.
	 *
	 * @return event view as template
	 *
	 * @version 6.0.0
	 * @since 6.0.0
	 */
	public static Template loadEventView() {

		try {

			return new Template("eventview", new StringReader(loadFile("eventview.html")), new Configuration(Configuration.VERSION_2_3_26));

		} catch (IOException e) {
            Gebu.logger.catching(e);
			return null;
		}

    }

}

/* EOF */
