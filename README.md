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

	`java -jar gebu.jar`

The program comes with an installer and needs Java in order to function.
Without installer just call `gebu.jar`with Java:

	`java -jar gebu.jar`

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

## Fertige Versionen/Released Versions

### Version 5

- die bisher gültige Version, leider derzeit offline
- current version, unfortunately offline for now

## To Do

- ESC function
- textual statistics
- logo and splashscreen
- date in the border
- icons for menu items
- icon bar
- do not show events of categories/eventtypes

## Copyright

Copyright 2016-2016 Ekkart Kleinod <ekleinod@edgesoft.de>

The program is distributed under the terms of the GNU Lesser General Public License.

See COPYING and COPYING.LESSER for details.

This file is part of edgeUtils.

edgeUtils is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

edgeUtils is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with edgeUtils.  If not, see <http://www.gnu.org/licenses/>.

