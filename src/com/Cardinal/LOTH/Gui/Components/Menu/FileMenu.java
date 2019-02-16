package com.Cardinal.LOTH.Gui.Components.Menu;

import javax.swing.JMenu;

import com.Cardinal.LOTH.Gui.Components.Menu.Items.ExitItem;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private ExitItem item;

	public FileMenu() {
		super("File");
		item = new ExitItem();
		this.add(item);
	}
}
