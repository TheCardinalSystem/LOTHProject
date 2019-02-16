package com.Cardinal.LOTH.Gui.Components.Menu.Items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;
import com.Cardinal.LOTH.Task.TaskManager;
import com.Cardinal.LOTH.Task.Tasks.TaskShutdown;

public class ExitItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 1L;

	public ExitItem() {
		super("Exit", new ImageIcon(ImageLibrary.EXIT.getImage()));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TaskManager.queue(new TaskShutdown());
	}
}
