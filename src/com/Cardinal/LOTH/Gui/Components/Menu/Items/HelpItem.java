package com.Cardinal.LOTH.Gui.Components.Menu.Items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;
import com.Cardinal.LOTH.Task.TaskManager;
import com.Cardinal.LOTH.Task.Tasks.TaskExtractHelp;

public class HelpItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JFrame parent;

	public HelpItem(JFrame parent) {
		super("Help", new ImageIcon(ImageLibrary.HELP.getImage()));
		this.addActionListener(this);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TaskManager.queue(new TaskExtractHelp(parent));
	}
}
