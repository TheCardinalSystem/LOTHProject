package com.Cardinal.LOTH.Gui.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import com.Cardinal.LOTH.Gui.Libraries.ImageLibrary;

public class ViewFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JEditorPane pane;
	private HTMLEditorKit kit;
	private JScrollPane scrollPane;

	public ViewFrame(String html, String title, MainFrame frame) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(title);

		this.setIconImage(ImageLibrary.FAVICON.getImage());

		pane = new JEditorPane();
		pane.setEditable(false);

		kit = new HTMLEditorKit();
		pane.setEditorKit(kit);

		pane.setText(html);

		scrollPane = new JScrollPane(pane);

		this.setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setSize((int) (width / 2), (int) (height / 2));

		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent e) {
				frame.setVisible(true);
				
			};
		});

		pane.setCaretPosition(0);
	}

}
