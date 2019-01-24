package com.Cardinal.LOTH.Gui.Frames;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Gui.Libraries.BorderLibrary;
import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;
import com.Cardinal.LOTH.Task.ITask;
import com.Cardinal.LOTH.Util.ClipboardUtils;
import com.Cardinal.LOTH.io.ConsoleHandler;

public class ErrorFrame extends JFrame implements ITask, ActionListener {

	private static final long serialVersionUID = 1L;

	private JFrame parent;
	private JLabel mes;
	private JTextArea area;
	private JButton copy, export, close, details;
	private JPanel buttonPane, anteDetailPane;
	private JScrollPane scrollPane;
	private Dimension detailSize;
	private GridBagConstraints c = new GridBagConstraints();

	public ErrorFrame(String error, String stacktrace, JFrame frame) {

		super("Error");

		parent = frame;
		frame.setVisible(false);

		mes = new JLabel(error, SwingConstants.CENTER);
		area = new JTextArea(stacktrace);

		area.setEditable(false);

		copy = new JButton("Copy Error");
		export = new JButton("Save Error Log");
		close = new JButton("Close");
		details = new JButton("Details...");

		copy.addActionListener(this);
		export.addActionListener(this);
		close.addActionListener(this);
		details.addActionListener(this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setSize((int) (width / 2), (int) (height / 6));

		detailSize = new Dimension((int) (width / 2), (int) (height / 2));

		this.setLocation((int) width / 4, (int) height / 3);

		int margin = (int) (this.getHeight() * 0.0520833333333333);

		buttonPane = new JPanel(new GridBagLayout());

		c.gridx = 2;
		buttonPane.add(close, c);
		c.gridx = 0;
		c.insets = new Insets(0, 0, 0, margin);
		buttonPane.add(copy, c);
		c.gridx = 1;
		buttonPane.add(export, c);

		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, margin, 0));

		int otherMargin = (int) (this.getHeight() * 0.078125),
				lastMargin = (int) (this.getWidth() * 0.0732064421669107);

		scrollPane = new JScrollPane(area);
		scrollPane.setBorder(
				new CompoundBorder(BorderFactory.createEmptyBorder(otherMargin, lastMargin, otherMargin, lastMargin),
						BorderLibrary.NORMAL.getBorder()));

		mes.setBorder(BorderFactory.createEmptyBorder(margin, 0, 0, 0));

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		anteDetailPane = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, otherMargin, 0);
		anteDetailPane.add(mes, c);

		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		anteDetailPane.add(details, c);

		this.setLayout(new BorderLayout());
		add(anteDetailPane, BorderLayout.CENTER);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent e) {
				frame.setVisible(true);
			};
		});

		this.setIconImage(ImageLibrary.FAVICON.getImage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(details)) {
			this.setSize(detailSize);
			anteDetailPane.removeAll();
			remove(anteDetailPane);
			add(mes, BorderLayout.NORTH);
			add(scrollPane, BorderLayout.CENTER);
			add(buttonPane, BorderLayout.SOUTH);
			close.requestFocus();
		} else if (e.getSource().equals(close)) {
			this.dispose();
		} else if (e.getSource().equals(copy)) {
			ClipboardUtils.setClipboardText(area.getText());
			copy.setText("Copied");
			copy.setEnabled(false);
		} else if (e.getSource().equals(export)) {
			export.setEnabled(false);
			File output = new File(WorkspaceConstants.WORKINGDIRECTORY + "\\logs\\"
					+ LocalDateTime.now().toString().replaceAll(":", ".") + ".err");

			if (!output.getParentFile().exists())
				output.getParentFile().mkdirs();

			try {
				PrintWriter writer = new PrintWriter(output);
				writer.print(area.getText());
				writer.println();
				writer.println("Console Output ---------------");
				writer.println(ConsoleHandler.getStringOutputStream().getString());
				writer.flush();
				writer.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			try {
				Desktop.getDesktop().open(output.getParentFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public ErrorFrame scaleToParent() {
		this.setSize(parent.getSize());
		this.setLocation(parent.getLocation());
		return this;
	}

	public ErrorFrame boldenFont() {
		Font font = new Font(mes.getFont().getName(), Font.BOLD, (int) (mes.getFont().getSize() * 1.5));
		mes.setFont(font);
		//Font but = new Font("ButFont", font.getStyle(), font.getSize());
		copy.setFont(font);
		export.setFont(font);
		close.setFont(font);
		details.setFont(font);
		
		return this;
	}

	@Override
	public ITask[] runTask() {
		this.setVisible(true);
		return null;
	}

}
