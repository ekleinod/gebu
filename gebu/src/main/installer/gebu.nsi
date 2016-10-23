# gebu install script.
#
## Legal stuff
#
# Copyright 2016-2016 Ekkart Kleinod <ekleinod@edgesoft.de>
#
# This file is part of "Das Gebu-Programm".
#
# "Das Gebu-Programm" is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# "Das Gebu-Programm" is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with "Das Gebu-Programm".  If not, see <http://www.gnu.org/licenses/>.
#
# @author Ekkart Kleinod
# @version 6.0.0
# @since 6.0.0
#
# use encoding: UTF-8

Name Gebu

RequestExecutionLevel user

# General Symbol Definitions
!define REGKEY "Software\Gebu"
!define VERSION 6.0.0
!define LONG_VERSION ${VERSION} beta 2
!define COMPANY "Ekkart Kleinod (edge-soft)"
!define URL http://www.edgesoft.de/
!define LONGNAME "Das Gebu-Programm"
!define DIRNAME "gebu"
!define INSTALLNAME "../../../../gebu_install.exe"

# MUI Symbol Definitions
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_STARTMENUPAGE_REGISTRY_ROOT "HKCU"
!define MUI_STARTMENUPAGE_REGISTRY_KEY ${REGKEY}
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "Start Menu Folder"
!define MUI_STARTMENUPAGE_DEFAULTFOLDER ${DIRNAME}

# Included files
!include Sections.nsh
!include MUI2.nsh

# Reserved Files
ReserveFile "${NSISDIR}\Plugins\AdvSplash.dll"

# Variables

# show warning when abort
!define MUI_ABORTWARNING
!define MUI_ABORTWARNING_TEXT "${LONGNAME} wurde nicht installiert.$\r$\n$\r$\n\
Sind Sie sicher, dass Sie die Installation abbrechen wollen?"

# Images
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "../resources/images/installer_header.bmp"
!define MUI_WELCOMEFINISHPAGE_BITMAP "../resources/images/installer_welcomefinish.bmp"

# Welcome
!define MUI_WELCOMEPAGE_TITLE "Installation $\"${LONGNAME}$\" ${LONG_VERSION}"
!define MUI_WELCOMEPAGE_TEXT "Willkommen bei der Installation von $\"${LONGNAME}$\".$\r$\n$\r$\n\
Dieser Installer führt Sie durch die Installation. \
Sie können die Installation jederzeit abbrechen, indem Sie unten rechts auf $\"Abbrechen$\" klicken."

!define MUI_FINISHPAGE_TITLE "Installation $\"${LONGNAME}$\" ${LONG_VERSION}"
!define MUI_FINISHPAGE_TEXT "Die Installation wurde erfolgreich abgeschlossen.$\r$\n$\r$\n\
Sie können das Installationsprogramm beenden und danach $\"${LONGNAME}$\" starten."

# Installer pages
!insertmacro MUI_PAGE_WELCOME
!define MUI_PAGE_CUSTOMFUNCTION_LEAVE CopyExisting
!insertmacro MUI_PAGE_DIRECTORY
#!insertmacro MUI_PAGE_STARTMENU Application $StartMenuFolder
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH

# Installer languages
!insertmacro MUI_LANGUAGE German

# Installer attributes
OutFile ${INSTALLNAME}
InstallDir $EXEDIR\${DIRNAME}
CRCCheck on
XPStyle on
ShowInstDetails show
VIProductVersion ${VERSION}.0
VIAddVersionKey /LANG=${LANG_GERMAN} ProductName "${LONGNAME}"
VIAddVersionKey /LANG=${LANG_GERMAN} ProductVersion "${VERSION}"
VIAddVersionKey /LANG=${LANG_GERMAN} CompanyName "${COMPANY}"
VIAddVersionKey /LANG=${LANG_GERMAN} CompanyWebsite "${URL}"
VIAddVersionKey /LANG=${LANG_GERMAN} FileVersion "${VERSION}"
VIAddVersionKey /LANG=${LANG_GERMAN} FileDescription ""
VIAddVersionKey /LANG=${LANG_GERMAN} LegalCopyright ""
InstallDirRegKey HKLM "${REGKEY}" Path
ShowUninstDetails show

# Installer sections
Section -jar SEC0000
		SetOverwrite on
		SetOutPath $INSTDIR
		File ../../../../gebu.jar
		WriteRegStr HKLM "${REGKEY}\Components" jar 1

#		!insertmacro MUI_STARTMENU_WRITE_BEGIN Application
#				CreateDirectory "$SMPROGRAMS\$StartMenuFolder"
#				CreateShortCut "$SMPROGRAMS\$StartMenuFolder\${LONGNAME}.lnk" "$INSTDIR\gebu.jar"
				CreateShortCut "$SMSTARTUP\${LONGNAME}.lnk" "$INSTDIR\gebu.jar"
#		!insertmacro MUI_STARTMENU_WRITE_END

SectionEnd

# Installer functions
Function .onInit
		InitPluginsDir
		Push $R1
		File /oname=$PLUGINSDIR\spltmp.bmp "../resources/images/installer_splash.bmp"
		advsplash::show 1500 600 400 -1 $PLUGINSDIR\spltmp
		Pop $R1
		Pop $R1
FunctionEnd

Function CopyExisting
		IfFileExists "$INSTDIR\gebu.jar" 0 +2
			CopyFiles "gebu.jar" "gebu.orig.jar"
FunctionEnd

# EOF
