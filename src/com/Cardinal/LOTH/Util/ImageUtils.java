package com.Cardinal.LOTH.Util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * A utility class used to perform advanced operations on {@linkplain Image}s,
 * and {@linkplain ImageIcon}s.
 * 
 * @author Cardinal System
 *
 */
public class ImageUtils {

	/**
	 * Resizes the given {@link Image} to the given proportions.
	 * 
	 * @param image
	 *            the image to resize.
	 * @param w
	 *            the width to resize to.
	 * @param h
	 *            the height to resize to.
	 * @return the resized image.
	 * @see ImageUtils#resizeImage(ImageIcon, int, int)
	 * @see ImageUtils#resizeImageCanvas(ImageIcon, Rectangle)
	 */
	public static Image resizeImage(Image image, int w, int h) {
		Image srcImg = image;
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	/**
	 * Resizes the given {@link ImageIcon} to the given proportions.
	 * 
	 * @param image
	 *            the image to resize.
	 * @param w
	 *            the width to resize to.
	 * @param h
	 *            the height to resize to.
	 * @return the resized image icon.
	 * @see ImageUtils#resizeImage(Image, int, int)
	 * @see ImageUtils#resizeImageCanvas(Image, Rectangle)
	 */
	public static ImageIcon resizeImage(ImageIcon image, int w, int h) {
		return new ImageIcon(resizeImage(image.getImage(), w, h));
	}

	/**
	 * Resizes the canvas of the given {@link Image}. Image proportions do not
	 * change.
	 * 
	 * @param image
	 *            the {@link Image} whose canvas to resize.
	 * @param newCanvas
	 *            the new {@link Rectangle canvas} to draw.
	 * @return the image with the new canvas.
	 * @see ImageUtils#resizeImageCanvas(ImageIcon, Rectangle)
	 * @see ImageUtils#resizeImage(ImageIcon, int, int)
	 */
	public static Image resizeImageCanvas(Image image, Rectangle newCanvas) {

		BufferedImage src = (BufferedImage) image;

		if (src.getHeight() == newCanvas.getHeight() && src.getWidth() == newCanvas.getWidth()) {
			return image;
		}

		System.err.println("Details:\n\tOriginal Hieght: " + src.getHeight() + "\n\tOriginal Width: " + src.getWidth()
				+ "\n\tNew Hieght: " + newCanvas.getHeight() + "\n\tNew Width: " + newCanvas.getWidth()
				+ "\n\tNew Location: " + newCanvas.getX() + ", " + newCanvas.getY());

		Image cropped = src.getSubimage(Math.toIntExact(Math.round(src.getWidth() / 2)),
				Math.toIntExact(Math.round(src.getHeight() / 2)), Math.toIntExact(Math.round(newCanvas.getWidth())),
				Math.toIntExact(Math.round(newCanvas.getHeight())));

		return cropped;
	}

	/**
	 * Resizes the canvas of the given {@link ImageIcon}. Image proportions do not
	 * change.
	 * 
	 * @param image
	 *            the {@link ImageIcon} whose canvas to resize.
	 * @param newCanvas
	 *            the new {@link Rectangle canvas} to draw.
	 * @return the image with the new canvas.
	 * @see ImageUtils#resizeImageCanvas(Image, Rectangle)
	 * @see ImageUtils#resizeImage(Image, int, int)
	 */
	public static ImageIcon resizeImageCanvas(ImageIcon image, Rectangle newCanvas) {
		return new ImageIcon(resizeImageCanvas(image.getImage(), newCanvas));
	}

	/**
	 * Resizes an image to fill the entire screen.
	 * 
	 * @param image
	 *            the image to resize.
	 * @return the resized image.
	 * @see ImageUtils#resizeImage(Image, int, int)
	 * @see ImageUtils#resizeImageCanvas(Image, Rectangle)
	 */
	public static Image getFullScreen(Image image) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		return resizeAspectRatio(image, screenSize);

	}

	/**
	 * Resizes the given image to the given size while keeping all proportions the
	 * same. The resulting image may be smaller than the passed dimensions.
	 * 
	 * @param image
	 *            the image to resize.
	 * @param width
	 *            the width of the new image.
	 * @param height
	 *            the height of the new image.
	 * @return the resized image.
	 * @see ImageUtils#resizeAspectRatio(Image, Dimension)
	 * @see ImageUtils#resizeImageCanvas(Image, Rectangle)
	 */
	public static Image resizeAspectRatio(Image image, int width, int height) {
		return resizeAspectRatio(image, new Dimension(width, height));
	}

	public static Dimension getSizeForAspectRatio(int oldWidth, int oldHieght, int newWidth, int newHeight) {
		return getSizeForAspectRatio(new Dimension(oldWidth, oldHieght), new Dimension(newWidth, newHeight));
	}

	/**
	 * Resizes the given image to the given size while keeping all proportions the
	 * same. The resulting image may be smaller than the passed dimensions.
	 * 
	 * @param image
	 *            the image to resize.
	 * @param desiredResize
	 *            the dimensions to resize to.
	 * @return the resized image.
	 * @see ImageUtils#resizeAspectRatio(Image, int, int)
	 * @see ImageUtils#resizeImageCanvas(ImageIcon, Rectangle)
	 */
	public static Image resizeAspectRatio(Image image, Dimension desiredResize) {
		/*
		 * double h = (double) new ImageIcon(image).getIconHeight(); double w = (double)
		 * new ImageIcon(image).getIconWidth(); double nh = (double)
		 * desiredResize.getHeight(); double nw = (double) desiredResize.getWidth(); if
		 * (h > nh || w > nw) { if (h > w) { double percentH = (nh / h) * (double) 100;
		 * System.out.println(percentH); double tallCake = (double) h * (double)
		 * percentH; double fatCake = (double) w * (double) percentH;
		 * System.out.println(tallCake + ":" + fatCake);
		 * 
		 * String newTallCake = String.valueOf(tallCake / 100).substring(0,
		 * String.valueOf(tallCake / 100).indexOf(".")); String newFatCake =
		 * String.valueOf(fatCake / 100).substring(0, String.valueOf(fatCake /
		 * 100).indexOf("."));
		 * 
		 * System.err.println(newTallCake + ":" + newFatCake);
		 * 
		 * return resizeImage(image, Integer.parseInt(newFatCake),
		 * Integer.parseInt(newTallCake)); } else if (w > h) { double percentW = (nw /
		 * w) * (double) 100; System.out.println(percentW); double tallCake = (double) h
		 * * (double) percentW; double fatCake = (double) w * (double) percentW;
		 * System.out.println(tallCake + ":" + fatCake);
		 * 
		 * String newTallCake = String.valueOf(tallCake / 100).substring(0,
		 * String.valueOf(tallCake / 100).indexOf(".")); String newFatCake =
		 * String.valueOf(fatCake / 100).substring(0, String.valueOf(fatCake /
		 * 100).indexOf("."));
		 * 
		 * System.err.println(newTallCake + ":" + newFatCake);
		 * 
		 * return resizeImage(image, Integer.parseInt(newFatCake),
		 * Integer.parseInt(newTallCake)); } }
		 */
		Dimension d = getSizeForAspectRatio(
				new Dimension(new ImageIcon(image).getIconWidth(), new ImageIcon(image).getIconHeight()),
				desiredResize);
		return resizeImage(image, (int) d.getWidth(), (int) d.getHeight());
	}

	public static Dimension getSizeForAspectRatio(Dimension imgSize, Dimension boundary) {

		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}

	/**
	 * Flips the given image horizontally or vertically depending on the boolean
	 * passed.
	 * 
	 * @param image
	 *            the image to flip
	 * @param vertically
	 *            specifies whether the image is to be flipped vertically or
	 *            horizontally.
	 * @return the flipped image.
	 */
	public static Image flip(Image image, boolean vertically) {
		if (vertically) {
			AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			tx.translate(0, -image.getHeight(null));
			image = op.filter((BufferedImage) image, null);
		} else {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			tx.translate(-image.getWidth(null), 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter((BufferedImage) image, null);
		}

		return image;
	}
}