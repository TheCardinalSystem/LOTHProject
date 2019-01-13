package com.Cardinal.LOTH.Util;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtils {

	public static void setClipboardText(String text) {
		StringSelection selection = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
	}
}
