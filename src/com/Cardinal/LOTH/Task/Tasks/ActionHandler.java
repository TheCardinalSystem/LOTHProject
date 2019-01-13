package com.Cardinal.LOTH.Task.Tasks;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import javax.print.PrintException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.Cardinal.LOTH.Gui.Frames.ErrorFrame;
import com.Cardinal.LOTH.Gui.Panels.PrimaryPanel;
import com.Cardinal.LOTH.Task.ITask;
import com.Cardinal.LOTH.Util.HourUtils;
import com.Cardinal.LOTH.Util.PDFUtils;
import com.Cardinal.LOTH.Util.PrinterUtils;
import com.Cardinal.LOTH.Web.WebParser;

public class ActionHandler implements ITask {

	private String com, la, ver;
	private LocalDateTime ti;
	private PrimaryPanel in;
	private boolean pr;

	public ActionHandler(String command, String lang, String version, boolean isPriest, LocalDateTime time,
			PrimaryPanel invoker) {
		com = command;
		ti = time;
		la = lang;
		in = invoker;
		ver = version;
		pr = isPriest;
	}

	@Override
	public ITask[] runTask() {
		if (com.equals("Export PDF")) {
			JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
			chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			chooser.setFileFilter(new FileFilter() {

				@Override
				public String getDescription() {
					return "(PDF) Portable Document Format";
				}

				@Override
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".pdf");
				}
			});

			chooser.showOpenDialog(in);

			File output = chooser.getSelectedFile();

			if (output == null) {
				in.hideLoadPane();
				return null;
			}

			String html;
			try {
				html = WebParser.getHtmlForHour(HourUtils.getHour(ti.toLocalTime()), la, ver, pr, ti.toLocalDate());
				PDFUtils.saveHtmlToPdf(output, html);
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String error = sw.toString();
				return ITask
						.merge(new ErrorFrame(e instanceof UnknownHostException || e instanceof NoRouteToHostException
								? "Unable to resolve the remote host. Please check your internet connection."
								: e instanceof SocketTimeoutException
										? "The connection timed out. Please check your internet connection."
										: "An unknown error occurred:",
								error, in.getParentFrame()));
			}
			in.hideLoadPane();
			return null;
		} else if (com.equals("Print")) {
			JOptionPane.showMessageDialog(in, "Printing is slow. This will take a few minutes.", "Print",
					JOptionPane.INFORMATION_MESSAGE);
			String html;
			try {
				html = WebParser.getHtmlForHour(HourUtils.getHour(ti.toLocalTime()), la, ver, pr, ti.toLocalDate());
				File f = PDFUtils.saveHtmlToPdf(html);
				PrinterUtils.promptToPrint(f);
			} catch (IOException | PrintException | PrinterException e) {
				in.hideLoadPane();
				if (e.getMessage().equals("User cancelled printing")) {
					return null;
				}
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String error = sw.toString();
				return ITask
						.merge(new ErrorFrame(e instanceof UnknownHostException || e instanceof NoRouteToHostException
								? "Unable to resolve the remote host. Please check your internet connection."
								: e instanceof SocketTimeoutException
										? "The connection timed out. Please check your internet connection."
										: "An unknown error occurred:",
								error, in.getParentFrame()));
			}
		} else if (com.equals("View")) {

			String html;
			try {
				html = WebParser.getHtmlForHour(HourUtils.getHour(ti.toLocalTime()), la, ver, pr, ti.toLocalDate());
			} catch (IOException e) {
				in.hideLoadPane();
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String error = sw.toString();
				return ITask
						.merge(new ErrorFrame(e instanceof UnknownHostException || e instanceof NoRouteToHostException
								? "Unable to resolve the remote host. Please check your internet connection."
								: e instanceof SocketTimeoutException
										? "The connection timed out. Please check your internet connection."
										: "An unknown error occurred:",
								error, in.getParentFrame()));
			}

			/*
			 * ViewFrame frame = new ViewFrame(html, "LOTH Prototype");
			 * frame.setVisible(true); frame.setLocationRelativeTo(in);
			 */
			in.hideLoadPane();
			in.displayHTML(html);
			return null;
		}
		in.hideLoadPane();
		return null;
	}

}
