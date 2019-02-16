package com.Cardinal.LOTH.Gui.Components;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.Cardinal.LOTH.Gui.Components.Menu.FileMenu;
import com.Cardinal.LOTH.Gui.Components.Menu.HelpMenu;

public class MainMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private HelpMenu help;
	private FileMenu file;

	public MainMenuBar(JFrame parent) {
		this.file = new FileMenu();
		this.help = new HelpMenu(parent);
		this.add(file);
		this.add(help);
	}
}
