package com.Cardinal.LOTH.Gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Gui.Frames.MainFrame;

public class GUIManager {

	private static MainFrame frame;

	public static void initGUI() {
		frame = new MainFrame("LOTH Project v" + WorkspaceConstants.VERSION, false);
		frame.setVisible(true);
		updateUIFonts();
	}

	public static void updateUIFonts() {
		int size = (int) (((frame.getWidth() + frame.getHeight()) / 2) * 0.0199737023593466);
		Font f = new Font("Tahoma", Font.PLAIN, size);

		getAllComponents(frame).stream()
				.filter(c -> c instanceof JLabel || c instanceof JList || c instanceof JButton
						|| c instanceof JTextField || c instanceof JComboBox || c instanceof JCheckBox)
				.forEach(c -> c.setFont(f));
	}

	private static List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

	public static void setSystemLookAndFeel() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	}

}
