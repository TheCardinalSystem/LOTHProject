package com.Cardinal.LOTH;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import com.Cardinal.LOTH.Gui.UIManager;
import com.Cardinal.LOTH.Update.UpdateManager;
import com.Cardinal.LOTH.io.ConsoleHandler;
import com.google.gson.JsonIOException;

public class WorkspaceConstants {

	public static final String VERSION = "1.3.0", WORKINGDIRECTORY = System.getProperty("user.home") + "\\.loth",
			HELPDIRECTORY = WORKINGDIRECTORY + "\\Help",
			UPDATELOG = "https://raw.githubusercontent.com/TheCardinalSystem/LOTHProject/master/versionLog.json",
			PROPERTIESFILE = WORKINGDIRECTORY + "\\properties.json", UPDATEPROPERTY = "updateTrash";

	public static void main(String[] args) throws JsonIOException, IOException {
		ConsoleHandler.init();
		UpdateManager.init();

		try {
			UIManager.setSystemLookAndFeel();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		UIManager.initGUI();
	}
}
