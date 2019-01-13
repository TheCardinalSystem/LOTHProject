package com.Cardinal.LOTH.Gui.Libraries;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public enum ImageLibrary {

	FAVICON("updatedlogo.png"), 
	LOADING("loader.gif"), 
	CARDINAL("logo.png"), 
	SINGERS("singers.png"), 
	BACKGROUND("combined.png");

	/**
	 * The image instance of 'this'.
	 */
	private Image IMAGE;
	private URL path;

	/**
	 * Sets the image instance of 'this'.
	 * 
	 * @param path
	 *            the location of the image in the 'assets' resource folder.
	 */
	private ImageLibrary(String path) {
		Image img = null;
		try {
			URL url = ClassLoader.getSystemResource("assets/Images/" + path);
			img = path.endsWith(".gif") ? Toolkit.getDefaultToolkit().getImage(this.path = url)
					: ImageIO.read(this.path = url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.IMAGE = img;
	}

	/**
	 * Gets the image instance of 'this'.
	 * 
	 * @return the image instance of 'this'.
	 * @throws ResourceLoadException
	 *             thrown if the image was unable to load.
	 */
	public Image getImage() {
		return this.IMAGE;
	}

	public URL getURL() {
		return path;
	}

}
