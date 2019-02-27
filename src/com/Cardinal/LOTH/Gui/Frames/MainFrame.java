package com.Cardinal.LOTH.Gui.Frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import com.Cardinal.LOTH.Gui.Components.MainMenuBar;
import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;
import com.Cardinal.LOTH.Gui.Panels.IntroPane;
import com.Cardinal.LOTH.Gui.Panels.PrimaryPanel;
import com.Cardinal.LOTH.Gui.Panels.UpdatePanel;
import com.Cardinal.LOTH.Update.UpdateManager;
import com.Cardinal.LOTH.Util.ImageUtils;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private IntroPane pane;
	private PrimaryPanel primPane;
	private UpdatePanel upPane;
	private MainMenuBar bar;

	public MainFrame(String title, boolean skipIntro) {
		super(title);

		this.setIconImage(ImageLibrary.FAVICON.getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setSize((int) (width / 1.9), (int) height / 2);
		this.setLocation((int) width / 4, (int) height / 4);

		this.bar = new MainMenuBar(this);

		if (skipIntro || UpdateManager.skipUpdates) { // Used for developing
			this.add(primPane = new PrimaryPanel(this.getSize()));
			addResizeListener();
		} else {
			this.add(pane = new IntroPane(ImageUtils.resizeAspectRatio(ImageLibrary.CARDINAL.getImage(),
					this.getWidth() / 2, this.getHeight() / 2), this, 0.02F, 0.03F, 0));
		}
	}

	private int proceed = 0;

	private void addResizeListener() {
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				primPane.resizeEvent(getSize());
			}
		});
	}

	public void proceed() {
		if (proceed == 2) {
			remove(upPane);
			this.add(primPane = new PrimaryPanel(this.getSize()));
			addResizeListener();
			this.setJMenuBar(bar);
		} else if (proceed == 1) {
			remove(pane);
			upPane = new UpdatePanel(this.getSize());
			this.add(upPane);
			remove(pane);
			proceed++;
		} else {
			remove(pane);
			pane = new IntroPane(ImageUtils.resizeAspectRatio(ImageLibrary.SINGERS.getImage(), this.getWidth(),
					(int) (this.getHeight() * 0.9)), this, 0.03F, 0.03F, 2000);
			this.add(pane);
			proceed++;
		}
		this.revalidate();
		this.repaint();
	}

}
