package com.Cardinal.LOTH.Gui.Components.Cells;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

/**
 * The cell renderer for {@linkplain ComboBoxCell}s.
 * 
 * Pulled from Bot Creator project.
 * 
 * @author Cardinal System
 *
 */
public class ComboBoxCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1234203162579159463L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel jlab = (JLabel) super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
		jlab.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			jlab.setText(((ComboBoxCell) value).getName());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		try {
			jlab.setIcon(((ComboBoxCell) value).getIcon());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return jlab;
	}
}