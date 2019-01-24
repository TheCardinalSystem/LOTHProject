package com.Cardinal.LOTH.Task.Tasks;

import java.awt.Container;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFrame;

import com.Cardinal.LOTH.Gui.Frames.ErrorFrame;
import com.Cardinal.LOTH.Gui.Panels.UpdatePanel;
import com.Cardinal.LOTH.Task.ITask;
import com.Cardinal.LOTH.Update.UpdateController;

public class TaskUpdate implements ITask {

	private UpdatePanel pane;

	public TaskUpdate(UpdatePanel panel) {
		pane = panel;
	}

	@Override
	public ITask[] runTask() {
		try {
			UpdateController.update(false);
			pane.updatesComplete();
		} catch (Exception e) {
			pane.updatesComplete();
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
