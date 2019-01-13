package com.Cardinal.LOTH.Gui.Components.Cells;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/**
 * The template for {@link JComboBox} cells that include icons.
 * 
 * Pulled from Bot Creator project.
 * 
 * @author Cardinal System
 *
 */
public interface ComboBoxCell {

	/**
	 * Gets the cell icon.
	 * 
	 * @return the cell icon.
	 */
	public ImageIcon getIcon();

	/**
	 * Gets the cell name.
	 * 
	 * @return the cell name.
	 */
	public String getName();

}