package com.Cardinal.LOTH.Task.Tasks;

import java.awt.Container;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.NoRouteToHostException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import com.Cardinal.LOTH.Gui.Frames.ErrorFrame;
import com.Cardinal.LOTH.Gui.Frames.MainFrame;
import com.Cardinal.LOTH.Gui.Panels.UpdatePanel;
import com.Cardinal.LOTH.Task.ITask;
import com.Cardinal.LOTH.Update.UpdateManager;

public class TaskCheckUpdates implements ITask {
	private UpdatePanel pane;

	public TaskCheckUpdates(UpdatePanel panel) {
		pane = panel;
	}

	@Override
	public ITask[] runTask() {
		try {
			boolean b = UpdateManager.checkForUpdates();
			if (b) {
				pane.updatesFound();
			} else {
				pane.noUpdates();
			}
		} catch (URISyntaxException | IOException e) {
			Container parent = pane.getParent();
			while (!(parent instanceof JFrame)) {
				parent = parent.getParent();
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String error = sw.toString();
			if (e instanceof NoRouteToHostException || e instanceof UnknownHostException) {
				((MainFrame) parent).proceed();
				return ITask.merge(new ErrorFrame("Unable to check for updates. Please check your internet connection.",
						error, (JFrame) parent).scaleToParent().boldenFont());
			} else {
				return ITask
						.merge(new ErrorFrame("An unexpected error occurred while updating.", error, (JFrame) parent));
			}

		}
		return null;
	}

}
