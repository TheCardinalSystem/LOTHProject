
package com.Cardinal.LOTH.Gui.Panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.Cardinal.LOTH.Gui.Libraries.BorderLibrary;

public class LangPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel prompt;
	private JCheckBox lat;
	private JList<String> list = new JList<String>(
			"Latin Deutsch English Espanol Francais Italiano Magyar Polski Polski-Newer".split(" "));

	public LangPanel() {
		prompt = new JLabel("Select Language");
		lat = new JCheckBox("Include Latin");
		
		list.setSelectedIndex(2);
		
		list.setBorder(BorderLibrary.RAISED.getBorder());

		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		c.gridx = 0;
		c.gridy = 0;
		add(prompt, c);

		c.gridx = 0;
		c.gridy = 1;
		add(list, c);

		c.gridx = 0;
		c.gridy = 2;
		add(lat, c);

	}

	public String getChoice() {
		return lat.isSelected() ? "Lat+" + list.getSelectedValue() : list.getSelectedValue();
	}

}
