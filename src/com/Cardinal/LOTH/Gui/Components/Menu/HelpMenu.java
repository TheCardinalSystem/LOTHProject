package com.Cardinal.LOTH.Gui.Components.Menu;

import javax.swing.JFrame;
import javax.swing.JMenu;

import com.Cardinal.LOTH.Gui.Components.Menu.Items.HelpItem;

public class HelpMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private HelpItem item;

	public HelpMenu(JFrame parent) {
		super("Help");
		this.item = new HelpItem(parent);
		this.add(item);
	}
}
