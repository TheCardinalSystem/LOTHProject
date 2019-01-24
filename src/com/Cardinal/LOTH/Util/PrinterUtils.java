package com.Cardinal.LOTH.Util;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.PrintQuality;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

public class PrinterUtils {

	public static void promptToPrint(File pdf) throws PrintException, IOException, PrinterException {

		PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
		patts.add(new JobName("Litrugy of the Hours", Locale.ENGLISH));
		patts.add(PrintQuality.DRAFT);
		patts.add(DialogTypeSelection.NATIVE);

		PrinterJob job = PrinterJob.getPrinterJob();

		if (!job.printDialog(patts)) {
			throw new PrinterException("User cancelled printing");
		}
		;

		PrintService myService = job.getPrintService();

		PDDocument doc = PDDocument.load(pdf);
		job.setPrintService(myService);
		job.setPrintable(new PDFPrintable(doc, Scaling.SCALE_TO_FIT));

		job.print(patts);
		doc.close();
	}

}
