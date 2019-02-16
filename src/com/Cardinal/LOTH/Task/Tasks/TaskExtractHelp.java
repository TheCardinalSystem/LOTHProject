package com.Cardinal.LOTH.Task.Tasks;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;

import javax.swing.JFrame;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Gui.Frames.ErrorFrame;
import com.Cardinal.LOTH.Task.ITask;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class TaskExtractHelp implements ITask {

	private JFrame frame;

	public TaskExtractHelp(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public ITask[] runTask() {
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			try {
				File f = new File(WorkspaceConstants.HELPDIRECTORY, "LOTHProject.chm");

				if (!f.exists() || f.length() <= 0) {

					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();

					Files.copy(ClassLoader.getSystemResourceAsStream("assets/Help/LOTHHelp.chm"), f.toPath());

				}
				Desktop.getDesktop().open(f);
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String error = sw.toString();
				return ITask.merge(new ErrorFrame("An unexpected error occurred.", error, frame));
			}
		} else {
			try {
				File f = new File(WorkspaceConstants.HELPDIRECTORY, "Help.zip");
				Files.copy(ClassLoader.getSystemResourceAsStream("assets/Help/Help.zip"), f.toPath());

				ZipFile zip = new ZipFile(f);

				zip.extractAll(f.getParent());

				f.delete();
				Desktop.getDesktop().browse(new File(WorkspaceConstants.HELPDIRECTORY, "Welcome.html").toURI());
			} catch (IOException | ZipException e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String error = sw.toString();
				return ITask.merge(new ErrorFrame("An unexpected error occurred.", error, frame));
			}
		}
		return null;
	}

}
