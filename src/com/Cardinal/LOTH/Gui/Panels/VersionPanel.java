package com.Cardinal.LOTH.Gui.Panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VersionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel prompt;
	private JComboBox<String> list;

	public VersionPanel() {
		prompt = new JLabel("Select Version");
		
		list = new JComboBox<String>(new String[] { "pre Trident Monastic", "Trident 1570",
			"Trident 1910", "Divino Afflatu", "Reduced 1955", "Rubrics 1960", "1960 Newcalendar" });
		
		list.setSelectedIndex(5);

		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		c.gridx = 0;
		c.gridy = 0;
		add(prompt, c);

		c.gridx = 0;
		c.gridy = 1;
		add(list, c);

	}

	public String getChoice() {
		return (String) list.getSelectedItem();
	}

}
