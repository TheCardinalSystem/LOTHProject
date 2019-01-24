package com.Cardinal.LOTH.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.itextpdf.html2pdf.HtmlConverter;

public class PDFUtils {

	public static File saveHtmlToPdf(String html) throws IOException {
		String date = LocalDateTime.now().toString().replaceAll(":", ".");
		File output = new File(WorkspaceConstants.WORKINGDIRECTORY + "\\temp\\" + date + ".pdf");
		output.getParentFile().mkdirs();
		for (File f : output.getParentFile().listFiles())
			f.delete();
		HtmlConverter.convertToPdf(html, new FileOutputStream(output));
		return output;
	}

	public static File saveHtmlToPdf(File output, String html) throws FileNotFoundException, IOException {
		if (!output.getParentFile().exists())
			output.getParentFile().mkdirs();

		HtmlConverter.convertToPdf(html, new FileOutputStream(output));
		return output;
	}

}
