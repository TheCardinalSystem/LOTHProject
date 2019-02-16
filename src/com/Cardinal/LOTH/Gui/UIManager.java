package com.Cardinal.LOTH.Gui;

import javax.swing.UnsupportedLookAndFeelException;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Gui.Frames.MainFrame;

public class UIManager {

	public static void initGUI() {
		new MainFrame("LOTH Project v" + WorkspaceConstants.VERSION, false).setVisible(true);
	}

	public static void setSystemLookAndFeel() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	}

}
