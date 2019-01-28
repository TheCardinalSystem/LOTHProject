package com.Cardinal.LOTH.Gui;

import javax.swing.UnsupportedLookAndFeelException;

import com.Cardinal.LOTH.Gui.Frames.MainFrame;

public class UIManager {

	public static void initGUI() {
		new MainFrame("LOTH Prototype", false).setVisible(true);
	}

	public static void setSystemLookAndFeel() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	}

}
