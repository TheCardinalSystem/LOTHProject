package com.Cardinal.LOTH.Gui.Panels;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.Cardinal.LOTH.Gui.Components.FadeLabel;
import com.Cardinal.LOTH.Gui.Frames.MainFrame;

public class IntroPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private float direction;
	private FadeLabel label;

	public IntroPane(Image image, MainFrame frameIn, float fadeIn, float fadeOut, long delay) {
		if (!(fadeIn > 0)) {
			if (fadeIn < 0)
				fadeIn *= -1;
			else
				fadeIn = 0.01F;
		}
		if (!(fadeOut < 0)) {
			if (fadeOut > 0)
				fadeOut *= -1;
			else
				fadeOut = -0.03F;
		}
		direction = fadeIn;
		final float bsFinalShit = fadeOut;

		setLayout(new BorderLayout());

		label = new FadeLabel((BufferedImage) image);
		label.setAlpha(0f);
		add(label);

		Timer timer = new Timer(50, new ActionListener() {
			boolean flag = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (flag == true) {
					((Timer) e.getSource()).stop();
					frameIn.proceed();
				}
				float alpha = label.getAlpha();
				alpha += direction;
				if (alpha < 0) {
					flag = true;
					alpha = 0f;
				} else if (alpha > 1) {
					alpha = 1;
					direction = bsFinalShit;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				label.setAlpha(alpha);
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
	}
}