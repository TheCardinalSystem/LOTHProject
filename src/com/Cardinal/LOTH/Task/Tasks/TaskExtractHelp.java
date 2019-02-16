package com.Cardinal.LOTH.Task.Tasks;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFrame;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Gui.Frames.ErrorFrame;
import com.Cardinal.LOTH.Task.ITask;

public class TaskExtractHelp implements ITask {

	private JFrame frame;

	public TaskExtractHelp(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public ITask[] runTask() {
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			try {
				File f = new File(WorkspaceConstants.HELPDIRECTORY, "LOTHHelp.chm");

				if (!f.exists() || f.length() <= 0) {

					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();

					InputStream stream = ClassLoader.getSystemResourceAsStream("assets/Help/LOTHProject.chm");
					FileOutputStream out = new FileOutputStream(f);

					byte[] buffer = new byte[2048];
					@SuppressWarnings("unused")
					int i;
					while ((i = stream.read(buffer)) > 0) {
						out.write(buffer);
					}
					out.flush();
					out.close();
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
				ZipFile zip = new ZipFile(new File(ClassLoader.getSystemResource("assets/Help/Help.zip").toURI()));

				ZipEntry entry;

				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					entry = entries.nextElement();
					File file = new File(WorkspaceConstants.HELPDIRECTORY, entry.getName());

					if (!file.exists() || file.length() < 0) {
						if (!file.getParentFile().exists())
							file.getParentFile().mkdirs();

						InputStream entIn = zip.getInputStream(entry);
						FileOutputStream out = new FileOutputStream(file);
						byte[] buffer = new byte[2048];
						@SuppressWarnings("unused")
						int i;
						while ((i = entIn.read(buffer)) > 0) {
							out.write(buffer);
						}
						out.flush();
						out.close();
					}
				}
				zip.close();
				Desktop.getDesktop().browse(new File(WorkspaceConstants.HELPDIRECTORY, "Welcome.html").toURI());
			} catch (IOException | URISyntaxException e) {
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
