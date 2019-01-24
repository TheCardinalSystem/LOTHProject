package com.Cardinal.LOTH.Task.Tasks;

import java.awt.Container;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import com.Cardinal.LOTH.Gui.Frames.ErrorFrame;
import com.Cardinal.LOTH.Gui.Panels.UpdatePanel;
import com.Cardinal.LOTH.Task.ITask;
import com.Cardinal.LOTH.Update.UpdateController;

public class TaskCheckUpdates implements ITask {
	private UpdatePanel pane;

	public TaskCheckUpdates(UpdatePanel panel) {
		pane = panel;
	}

	@Override
	public ITask[] runTask() {
		try {
			boolean b = UpdateController.checkForUpdates();
			if (b) {
				pane.updatesFound();
			} else {
				pane.noUpdates();
			}
		} catch (IOException | URISyntaxException e) {
			Container parent = pane.getParent();
			while (!(parent instanceof JFrame)) {
				parent = parent.getParent();
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String error = sw.toString();
			return ITask.merge(new ErrorFrame("An unexpected error occurred while updating.", error, (JFrame) parent));
		}
		return null;
	}

}
