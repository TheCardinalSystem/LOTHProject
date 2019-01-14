package com.Cardinal.LOTH.Gui.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.text.html.HTMLEditorKit;

import com.Cardinal.LOTH.Gui.Frames.MainFrame;
import com.Cardinal.LOTH.Gui.Frames.ViewFrame;
import com.Cardinal.LOTH.Gui.Libraries.BorderLibrary;
import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;
import com.Cardinal.LOTH.Task.TaskManager;
import com.Cardinal.LOTH.Task.Tasks.ActionHandler;
import com.Cardinal.LOTH.Util.ImageUtils;

import sun.awt.image.ToolkitImage;

public class PrimaryPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String[] actions = { "View", "Loads the hour into this window", "Print",
			"Sends the hour to your printer", "Export PDF", "Saves the hour to a chosen directory" };

	private TimePrompt timePane;
	private ActionPanel actionPane;
	private LangPanel langPane;
	private VersionPanel verPane;
	private JButton execute, closeHour, popOut;
	private JPanel thatAndExecute, hourConts, timeAndVer;
	private JLabel image;
	private ImageIcon load, sing;

	public PrimaryPanel(Dimension windowSize) {

		timePane = new TimePrompt(windowSize);
		langPane = new LangPanel();
		actionPane = new ActionPanel(windowSize, actions);
		verPane = new VersionPanel();

		langPane.setBorder(BorderLibrary.NORMAL.getBorder());

		popOut = new JButton("Pop-Out Hour");
		closeHour = new JButton("Close Hour");
		execute = new JButton("Execute Command");
		execute.addActionListener(this);
		closeHour.addActionListener(this);
		popOut.addActionListener(this);

		closeHour.setEnabled(false);
		popOut.setEnabled(false);

		hourConts = new JPanel(new BorderLayout());
		hourConts.add(closeHour, BorderLayout.SOUTH);
		hourConts.add(popOut, BorderLayout.NORTH);

		thatAndExecute = new JPanel(new BorderLayout());
		thatAndExecute.add(hourConts, BorderLayout.NORTH);
		thatAndExecute.add(actionPane, BorderLayout.CENTER);
		thatAndExecute.add(execute, BorderLayout.SOUTH);

		thatAndExecute.setBorder(BorderLibrary.NORMAL.getBorder());

		int hgap = (int) (windowSize.getWidth() * 0.0732064421669107);

		timeAndVer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		timeAndVer.add(verPane);
		timeAndVer.add(timePane);

		int margin = (int) (windowSize.getHeight() * 0.0220833333333333);
		timeAndVer.setBorder(new CompoundBorder(BorderLibrary.NORMAL.getBorder(),
				BorderFactory.createEmptyBorder(margin, 0, margin, 0)));
		verPane.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK),
				BorderFactory.createEmptyBorder(0, 0, 0, hgap / 2)));
		timePane.setBorder(BorderFactory.createEmptyBorder(0, hgap / 2, 0, 0));

		this.setLayout(new BorderLayout());
		add(timeAndVer, BorderLayout.SOUTH);
		add(langPane, BorderLayout.WEST);
		add(thatAndExecute, BorderLayout.EAST);

		int width = (int) (windowSize.getWidth() * 0.6661786237188873),
				hieght = (int) (windowSize.getHeight() * 0.6875);

		int width2 = (int) (windowSize.getWidth() * 0.7320644216691069),
				hieght2 = (int) (windowSize.getHeight() * 0.9765625);

		ToolkitImage img = (ToolkitImage) ImageLibrary.LOADING.getImage();

		Dimension scale = ImageUtils.getSizeForAspectRatio(img.getWidth(), img.getHeight(), width2, hieght2);

		load = new ImageIcon(img.getScaledInstance(scale.width, scale.height, Image.SCALE_DEFAULT));

		image = new JLabel(
				sing = new ImageIcon(ImageUtils.resizeAspectRatio(ImageLibrary.BACKGROUND.getImage(), width, hieght)));

		add(image, BorderLayout.CENTER);

		initHTMLViewer();
		/*
		 * this.setLayout(new GridBagLayout());
		 * 
		 * GridBagConstraints c = new GridBagConstraints(); c.gridx = 0; c.gridy = 0;
		 * add(langPane, c);
		 * 
		 * c.gridx = 4; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1; add(actionPane,
		 * c);
		 * 
		 * c.gridx = 1; c.gridy = 1; c.gridwidth = 4; c.gridheight = 2; add(timePane,
		 * c);
		 * 
		 * c.gridx = 1; c.gridy = 4; c.gridheight = 1; c.gridwidth = 2; add(execute, c);
		 */
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(execute)) {
			execute.setEnabled(false);
			popOut.setEnabled(false);
			closeHour.setEnabled(false);
			remove(scrollPane);
			currentHTML = null;
			add(image, BorderLayout.CENTER);
			image.setIcon(load);
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			LocalDateTime time = timePane.getTime();
			String lang = langPane.getChoice();
			String action = actionPane.getSelection();
			String version = verPane.getChoice();
			boolean isPriest = actionPane.isPriest();

			TaskManager.queue(new ActionHandler(action, lang, version, isPriest, time, this));
		} else if (e.getSource().equals(closeHour)) {
			closeHour.setEnabled(false);
			remove(scrollPane);
			currentHTML = null;
			add(image, BorderLayout.CENTER);
			if (!image.getIcon().equals(sing))
				image.setIcon(sing);
			repaint();
		} else if (e.getSource().equals(popOut)) {
			popOut.setEnabled(false);
			JFrame parent = getParentFrame();
			ViewFrame frame = new ViewFrame(currentHTML, "Canonical Hour", (MainFrame) parent);
			frame.setLocationRelativeTo(this);
			frame.setVisible(true);
			parent.setVisible(false);
		}
	}

	public JFrame getParentFrame() {
		Container parent = this.getParent();
		while (!(parent instanceof JFrame)) {
			parent = parent.getParent();
		}
		return (JFrame) parent;
	}

	public void hideLoadPane() {
		image.setIcon(sing);
		execute.setEnabled(true);
	}

	private JEditorPane pane;
	private HTMLEditorKit kit;
	private JScrollPane scrollPane;
	private String currentHTML;

	private void initHTMLViewer() {
		pane = new JEditorPane();
		pane.setEditable(false);

		kit = new HTMLEditorKit();
		pane.setEditorKit(kit);

		scrollPane = new JScrollPane(pane);
	}

	public void displayHTML(String html) {
		closeHour.setEnabled(true);
		popOut.setEnabled(true);
		remove(image);
		pane.setText(currentHTML = html);
		this.add(scrollPane, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
