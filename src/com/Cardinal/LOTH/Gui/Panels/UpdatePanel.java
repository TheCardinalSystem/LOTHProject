package com.Cardinal.LOTH.Gui.Panels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.Cardinal.LOTH.Gui.Frames.ErrorFrame;
import com.Cardinal.LOTH.Gui.Frames.MainFrame;
import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;
import com.Cardinal.LOTH.Task.TaskManager;
import com.Cardinal.LOTH.Task.Tasks.TaskCheckUpdates;
import com.Cardinal.LOTH.Task.Tasks.TaskUpdate;
import com.Cardinal.LOTH.Update.UpdateManager;
import com.Cardinal.LOTH.Util.ImageUtils;

import sun.awt.image.ToolkitImage;

public class UpdatePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel label, image;
	private JButton yes, later, cont;
	private ToolkitImage loader;
	private JPanel buttonPane;
	private GridBagConstraints c;
	private Font font, buttonFont;

	public UpdatePanel(Dimension windowSize) {
		ToolkitImage img = loader = (ToolkitImage) ImageLibrary.LOADING2.getImage();

		label = new JLabel("Checking for Updates...", SwingConstants.CENTER);
		yes = new JButton("Yes");
		later = new JButton("Later");
		cont = new JButton("Continue");

		font = new Font(label.getFont().getName(), Font.BOLD,
				(int) ((windowSize.getHeight() + windowSize.getWidth()) / 61.22222222222222));

		buttonFont = new Font("ButtFont", Font.PLAIN, font.getSize());

		label.setFont(font);
		yes.setFont(buttonFont);
		later.setFont(buttonFont);
		cont.setFont(buttonFont);

		label.setBorder(BorderFactory.createEmptyBorder(0, 0, (int) (windowSize.getHeight() * 0.1), 0));

		Dimension scale = ImageUtils.getSizeForAspectRatio(img.getWidth(), img.getHeight(),
				(int) windowSize.getWidth() / 3, (int) windowSize.getHeight() / 3);

		image = new JLabel(new ImageIcon(
				loader.getScaledInstance((int) scale.getWidth(), (int) scale.getHeight(), Image.SCALE_DEFAULT)));

		c = new GridBagConstraints();
		buttonPane = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, (int) (windowSize.getHeight() * 0.05));
		buttonPane.add(yes, c);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 2;
		buttonPane.add(later, c);

		setLayout(new GridBagLayout());

		c.gridx = 0;
		c.gridy = 0;
		add(label, c);

		c.gridy = 1;
		add(image, c);

		yes.addActionListener(this);
		later.addActionListener(this);
		cont.addActionListener(this);

		TaskManager.queue(new TaskCheckUpdates(this));
	}

	public void updatesFound() {

		label.setText("An update is available. Would you like to install updates now?");
		remove(image);
		add(buttonPane, c);
		revalidate();
		repaint();
	}

	public void noUpdates() {
		label.setText("Everything is up to date!");
		remove(image);
		add(cont, c);
		revalidate();
		repaint();
	}

	private boolean restart = false;

	public void updatesComplete() {
		remove(image);
		label.setText("Update Complete!");
		add(cont, c);
		revalidate();
		repaint();
		restart = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(yes)) {
			label.setText("Updating...");
			remove(buttonPane);
			add(image, c);
			revalidate();
			repaint();
			TaskManager.queue(new TaskUpdate(this));
		} else if (e.getSource().equals(later) || e.getSource().equals(cont)) {
			if (restart) {
				try {
					UpdateManager.restart(UpdateManager.applied);
				} catch (IOException e1) {
					Container parent = getParent();
					while (!(parent instanceof JFrame)) {
						parent = parent.getParent();
					}
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e1.printStackTrace(pw);
					String error = sw.toString();
					TaskManager.queue(
							new ErrorFrame("An unexpected error occurred while updating.", error, (JFrame) parent));
				}
			}
			Container parent = getParent();
			while (!(parent instanceof JFrame)) {
				parent = parent.getParent();
			}
			((MainFrame) parent).proceed();
		}
	}
}
