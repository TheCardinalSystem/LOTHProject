package com.Cardinal.LOTH.Gui.Panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.Cardinal.LOTH.Gui.Handle.ComboboxToolTipRenderer;

public class ActionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel heading;
	private JCheckBox priest;
	private JComboBox<String> actions = new JComboBox<String>();
	private ComboboxToolTipRenderer renderer;

	public ActionPanel(Dimension windowSize, String... actions) {
		heading = new JLabel("Please select an action:");
		priest = new JCheckBox("I am a priest");
		renderer = new ComboboxToolTipRenderer();
		
		ArrayList<String> tooltips = new ArrayList<String>();
		for (int i = 0; i < actions.length; i++) {
			if (i % 2 == 0) {
				this.actions.addItem(actions[i]);
			} else {
				tooltips.add(actions[i]);
			}
		}

		renderer.setTooltips(tooltips);

		this.actions.setSelectedIndex(0);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		add(heading, c);

		c.gridx = 0;
		c.gridy = 2;
		add(priest, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, (int) (windowSize.height * 0.0130208333333333), 0);
		add(this.actions, c);
	}

	public String getSelection() {
		return (String) actions.getSelectedItem();
	}

	public boolean isPriest() {
		return priest.isSelected();
	}
}
