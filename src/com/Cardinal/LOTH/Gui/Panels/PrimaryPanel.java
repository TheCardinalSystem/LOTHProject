package com.Cardinal.LOTH.Gui.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
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

	private TimePanel timePane;
	private ActionPanel actionPane;
	private LangPanel langPane;
	private VersionPanel verPane;
	private JButton execute, closeHour, popOut;
	private JPanel thatAndExecute, hourConts, timeAndVer;
	private JLabel image;
	private ImageIcon load, sing;
	private Image loader, combined;
	private Dimension originalLoadSize;

	public PrimaryPanel(Dimension windowSize) {

		timePane = new TimePanel(windowSize);
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

		timeAndVer = new JPanel();
		timeAndVer.setLayout(new BoxLayout(timeAndVer, BoxLayout.X_AXIS));
		timeAndVer.add(verPane);
		timeAndVer.add(timePane);

		int hgap = (int) (windowSize.getWidth() * 0.0732064421669107);
		int margin = (int) (windowSize.getHeight() * 0.0220833333333333);
		timeAndVer.setBorder(new CompoundBorder(BorderLibrary.NORMAL.getBorder(),
				BorderFactory.createEmptyBorder(margin, 0, margin, 0)));
		verPane.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK),
				BorderFactory.createEmptyBorder(0, 0, 0, hgap / 2)));
		timePane.setBorder(BorderFactory.createEmptyBorder(0, hgap / 2, 0, 0));

		JScrollPane scr, scr1, scr2;

		this.setLayout(new BorderLayout());
		add(scr = new JScrollPane(timeAndVer), BorderLayout.SOUTH);
		add(scr1 = new JScrollPane(langPane), BorderLayout.WEST);
		add(scr2 = new JScrollPane(thatAndExecute), BorderLayout.EAST);

		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		scr1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scr1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		scr2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scr2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		int width = (int) (windowSize.getWidth() * 0.6661786237188873),
				hieght = (int) (windowSize.getHeight() * 0.6875);

		int width2 = (int) (windowSize.getWidth() * 0.7320644216691069),
				hieght2 = (int) (windowSize.getHeight() * 0.9765625);

		ToolkitImage img = (ToolkitImage) (loader = ImageLibrary.LOADING.getImage());

		originalLoadSize = new Dimension(img.getWidth(), img.getHeight());

		Dimension scale = ImageUtils.getSizeForAspectRatio(img.getWidth(), img.getHeight(), width2, hieght2);

		load = new ImageIcon(img.getScaledInstance(scale.width, scale.height, Image.SCALE_DEFAULT));

		image = new JLabel(sing = new ImageIcon(
				ImageUtils.resizeAspectRatio(combined = ImageLibrary.BACKGROUND.getImage(), width, hieght)));

		image.setBackground(Color.BLACK);
		add(image, BorderLayout.CENTER);

		initHTMLViewer();
	}

	public void resizeEvent(Dimension windowSize) {
		checkImageScales();

		int hgap = (int) (windowSize.getWidth() * 0.0732064421669107);
		int margin = (int) (windowSize.getHeight() * 0.0220833333333333);
		timeAndVer.setBorder(new CompoundBorder(BorderLibrary.NORMAL.getBorder(),
				BorderFactory.createEmptyBorder(margin, 0, margin, 0)));
		verPane.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK),
				BorderFactory.createEmptyBorder(0, 0, 0, hgap / 2)));
		timePane.setBorder(BorderFactory.createEmptyBorder(0, hgap / 2, 0, 0));
	}

	public void checkImageScales() {
		Dimension scale = ImageUtils.getSizeForAspectRatio(sing.getIconWidth(), sing.getIconHeight(), image.getWidth(),
				image.getHeight());

		Dimension loadScale = ImageUtils.getFillAspectRatio((int) originalLoadSize.getWidth(),
				(int) originalLoadSize.getHeight(), image.getWidth(), image.getHeight());

		if (sing.getIconHeight() != scale.getHeight() || sing.getIconWidth() != scale.getWidth()) {
			sing.setImage(combined.getScaledInstance(scale.width, scale.height, Image.SCALE_DEFAULT));
		}
		if (load.getIconHeight() != loadScale.getHeight() || load.getIconWidth() != loadScale.getWidth()) {
			load.setImage(loader.getScaledInstance(loadScale.width, loadScale.height, Image.SCALE_DEFAULT));
		}
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
			checkImageScales();
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			LocalDateTime time = timePane.getTime();
			String lang = langPane.getChoice();
			String action = actionPane.getSelection();
			String version = verPane.getChoice();
			boolean isPriest = actionPane.isPriest();

			TaskManager.queue(new ActionHandler(action, lang, version, isPriest, time, this));
		} else if (e.getSource().equals(closeHour)) {
			closeHour.setEnabled(false);
			popOut.setEnabled(false);
			remove(scrollPane);
			currentHTML = null;
			add(image, BorderLayout.CENTER);
			if (!image.getIcon().equals(sing))
				image.setIcon(sing);
			checkImageScales();
			repaint();
		} else if (e.getSource().equals(popOut)) {
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
		Dimension scale = ImageUtils.getSizeForAspectRatio(sing.getIconWidth(), sing.getIconHeight(), image.getWidth(),
				image.getHeight());

		if (sing.getIconHeight() != scale.getHeight() || sing.getIconWidth() != scale.getWidth()) {
			sing.setImage(combined.getScaledInstance(scale.width, scale.height, Image.SCALE_DEFAULT));
		}
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
