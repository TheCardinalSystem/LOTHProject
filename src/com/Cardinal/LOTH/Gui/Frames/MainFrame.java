package com.Cardinal.LOTH.Gui.Frames;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;
import com.Cardinal.LOTH.Gui.Panels.IntroPane;
import com.Cardinal.LOTH.Gui.Panels.PrimaryPanel;
import com.Cardinal.LOTH.Util.ImageUtils;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private IntroPane pane;

	public MainFrame(String title) {
		super(title);

		this.setIconImage(ImageLibrary.FAVICON.getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setSize((int) width / 2, (int) height / 2);
		this.setLocation((int) width / 4, (int) height / 4);

		pane = new IntroPane(ImageUtils.resizeAspectRatio(ImageLibrary.CARDINAL.getImage(), this.getWidth() / 2,
				this.getHeight() / 2), this, 0.02F, 0.03F, 0);
		this.add(pane);
	}

	private boolean flag = false;

	public void proceed() {
		this.remove(pane);
		if (flag == true) {
			this.add(new PrimaryPanel(this.getSize()));
		} else {
			pane = new IntroPane(ImageUtils.resizeAspectRatio(ImageLibrary.SINGERS.getImage(), this.getWidth(),
					(int) (this.getHeight() * 0.9)), this, 0.03F, 0.03F, 2000);
			this.add(pane);
			flag = true;
		}
		this.revalidate();
		this.repaint();
	}

}
