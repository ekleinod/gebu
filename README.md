# Das Gebu-Programm

Das Gebu-Programm zeigt Geburtstage in einem Rahmen von zwei Wochen an, d.h. die Geburtstage der vergangenen Woche, die heutigen Geburtstage sowie die zukünftigen Geburtstage der nächsten Woche.
Die Geburtstage können mit einem internen Editor erweitert oder geändert werden.
Das Anzeigeintervall ist einstellbar.

"Das Gebu-Programm" shows birthdays in a two week interval, i.e. birthdays of the last week, the actual birthdays, and the birthdays of the future week.
The birthdays can be edited with an internal editor.
The interval is changeable.


## Bedienung/usage

Das Programm wird mit einem Installer ausgeliefert und benötigt Java zum Laufen.
Ohne Installer ist die Datei `gebu.jar` mit Java auszuführen:

	java -jar gebu.jar

The program comes with an installer and needs Java in order to function.
Without installer just call `gebu.jar`with Java:

	java -jar gebu.jar

## Libraries

Ich verwende folgende Libraries via *maven*:

I am using other libraries in this project (via *maven*):

- edgeutils
	- Hilfsklassen
	- utility classes
	- license: GNU Lesser General Public License 3
- log4j
	- logging framework
	- license: Apache License, Version 2.0, see http://www.apache.org/licenses/LICENSE-2.0
- JUnit
	- unit testing framework
	- license: Eclipse Public License, Version 1.0, see https://www.eclipse.org/legal/epl-v10.html

## Git-Repository

Ich strukturiere das Git-Repository wie im Branching-Modell von http://nvie.com/posts/a-successful-git-branching-model/ vorgeschlagen.
Das bedeutet, es gibt immer die folgenden drei Branches:

The branches are constructed regarding the git branching model of http://nvie.com/posts/a-successful-git-branching-model/
This means, there are always at least three branches:

1. `master` - fertige Versionen (contains released versions)
2. `develop` - Synchronisierungbranch (main synchronisation branch for feature, release, and hotfix branches)
3. `feature/work` - Hauptarbeitsbranch (main working branch for development)

Zusätzlich können folgende Branches auftauchen:

Additionally, the following branches my occur:

- `feature/*` - Features (writing a special feature)
- `release/*` - Release-Synchronisierung (synchronizing release versions between `develop` and `master`)
- `hotfix/*` - Fehlerbehebung (fast bugfixes)

## Neue Versionen

Für eine neue Version:

- Versionsnummer in `Gebu.java` anpassen
- Versionsnummer in `pom.xml` anpassen
- prüfen, ob log-File erzeugt oder abgestellt werden soll (`log4j.xml`)
- *maven test* laufen lassen
- *maven target* "gebu jar with dependencies" ausführen
- erzeugte jar-Datei in jars-Ordner verschieben, dabei korrekt benennen
- `gebu.jar` im Hauptverzeichnis neu verlinken
- Versionsinformation in `README.MD` ergänzen
- jar testen
- *target*-Ordner leeren, Projekt in *eclipse* neu erzeugen
- Installer anpassen, erzeugen (und testen)

## Copyright

Copyright 2016-2017 Ekkart Kleinod <ekleinod@edgesoft.de>

The program is distributed under the terms of the GNU Lesser General Public License.

See COPYING and COPYING.LESSER for details.

This file is part of "Das Gebu-Programm".

"Das Gebu-Programm" is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

"Das Gebu-Programm" is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with "Das Gebu-Programm".  If not, see <http://www.gnu.org/licenses/>.
