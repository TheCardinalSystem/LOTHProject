package com.Cardinal.LOTH.Gui.Libraries;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public enum BorderLibrary {

	/**
	 * Single pixel lined border. <center>
	 * <table border="0" cellspacing="1" cellpadding="4" style="border: 1px solid
	 * #000;">
	 * <tr>
	 * <td></td>
	 * <td>Normal Border&nbsp;&nbsp;</td>
	 * </tr>
	 * </table>
	 * </center>
	 */
	NORMAL,
	/**
	 * Triple pixel lined border. <center>
	 * <table border="0" cellspacing="1" cellpadding="4" style="border: 3px solid
	 * #000;">
	 * <tr>
	 * <td></td>
	 * <td>Thick Border&nbsp;&nbsp;</td>
	 * </tr>
	 * </table>
	 * </center>
	 */
	THICK,
	/**
	 * Raised bevel border.
	 * 
	 * <style> .mainpressed{ background: #f7f7f7; width: 550px; height: 50px;
	 * border-radius: 5px; -o-border-radius: 5px; -moz-border-radius: 5px;
	 * -webkit-border-radius: 5px; margin: 2px auto; border-left: 1px solid #e0e0e0;
	 * border-top: 1px solid #e0e0e0; border-right: 1px solid #c1c1c1;
	 * border-bottom: 1px solid #c1c1c1; } #raises { background: #f7f7f7; width:
	 * 250px; border-radius: 5px; -o-border-radius: 5px; -moz-border-radius: 5px;
	 * -webkit-border-radius: 5px; border-left: 1px solid #e0e0e0; border-top: 1px
	 * solid #e0e0e0; border-right: 1px solid #c1c1c1; border-bottom: 1px solid
	 * #c1c1c1; position: relative; margin: 9px -7px; display: inline-block;
	 * text-shadow: 0 1px #38678B; text-align: center; padding: 5px; color: black; }
	 * 
	 * 
	 * </style> <div class="mainpressed"><center><div id="raises">Raised
	 * Border</div></center></div>
	 */
	RAISED;

	/**
	 * Gets the border represented by this enum.
	 * 
	 * @return the border representation of this enum.
	 */
	public Border getBorder() {
		switch (this) {
		case NORMAL:
			return BorderFactory.createLineBorder(Color.BLACK, 1);
		case THICK:
			return BorderFactory.createLineBorder(Color.BLACK, 3);
		case RAISED:
			return BorderFactory.createRaisedBevelBorder();
		default:
			return BorderFactory.createLineBorder(Color.BLACK, 1);
		}
	}

}