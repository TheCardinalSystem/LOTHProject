package com.Cardinal.LOTH.Web;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.print.PrintException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Gui.Frames.MainFrame;
import com.Cardinal.LOTH.Update.GsonHub;
import com.Cardinal.LOTH.Util.HourUtils.Hour;
import com.Cardinal.LOTH.Util.WebUtils.WebLang;

public class WebParser {
	public static void main(String[] args) throws IOException, PrintException, PrinterException {

		String path = GsonHub.getProperty("updateTrash");

		if (path != null) {
			new File(path).delete();
			GsonHub.removeProperty("updateTrash");
		}

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		MainFrame frame = new MainFrame("LOTH Prototype");
		frame.setVisible(true);
	}

	public static String getVersionLog() throws IOException, URISyntaxException {
		Scanner sc = new Scanner(new URL(WorkspaceConstants.UPDATELOG).openStream(), "UTF-8");
		sc.useDelimiter("\\A");
		String json = sc.next();
		sc.close();
		return json;
	}

	public static String getHtmlForHour(Hour hour, String lang, String version, boolean isPriest, LocalDate date)
			throws IOException {

		String dateString = date.format(DateTimeFormatter.ofPattern("MM-d-uuuu"));
		Document doc = getHourDoc(hour, dateString, lang, version, isPriest);

		Element el = cropHourTable(doc, lang);

		String html = addHeaderToHourTable(el, doc, hour, lang, date);

		return html;
	}

	public static String addHeaderToHourTable(Element hourTable, Document doc, Hour header, String lang,
			LocalDate date) {
		return "<body>"
				+ doc.getElementsByTag("p").first().outerHtml().replaceFirst("<br\\s*\\/?>", "")
						.replaceFirst("<br\\s*\\/?>", "")
				+ "<center><div style='font-size:15;margin-bottom:20'>"
				+ date.format(DateTimeFormatter.ofPattern("MMM dd, uuuu")) + " | "
				+ (lang.indexOf("+") > 1 ? lang.replaceFirst("\\+", " - ") : lang).replaceFirst("Lat", "Latin")
				+ "</div></center>" + hourTable.outerHtml() + "</body>";
	}

	public static Element cropHourTable(Document page, String lang) {
		org.jsoup.nodes.Element element = page.getElementsByTag("table").get(0);
		org.jsoup.nodes.Element body = element.getElementsByTag("tbody").get(0);
		for (org.jsoup.nodes.Element tr : body.getElementsByTag("tr")) {
			org.jsoup.select.Elements tds = tr.getElementsByTag("td");
			if (lang.startsWith("Lat")) {
				org.jsoup.select.Elements elements = tds.get(0).getElementsByTag("div");
				if (!elements.isEmpty())
					elements.get(0).remove();
				if (lang.equals("Lat"))
					tds.get(1).remove();
			} else {
				tds.get(0).remove();
			}
		}
		return element;
	}

	public static Document getHourDoc(Hour hour, String date, String lang, String version, boolean isPriest)
			throws IOException {

		lang = lang.contains("+") ? lang.substring(lang.indexOf("+") + 1) : lang;

		Document doc = Jsoup.connect("https://divinumofficium.com/cgi-bin/horas/officium.pl")
				.url("https://divinumofficium.com/cgi-bin/horas/officium.pl")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*//*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, br").header("Accept-Language", "en-US,en;q=0.9")
				.header("Cache-Control", "max-age=0").header("Connection", "keep-alive")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Origin", "https://divinumofficium.com").header("Host", "divinumofficium.com")
				.header("Upgrade-Insecure-Requests", "1").method(Method.POST).header("DNT", "1")
				.referrer("https://divinumofficium.com/cgi-bin/horas/officium.pl")
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
				.data("date", date, "expand", "all", "popup", "0 ", "version", version, "lang2", lang, "command",
						"pray" + hour.toString(WebLang.LAT), "searchvalue", "0", "officium", "officium.pl",
						"browsertime", date, "accented", "plain", "caller", "0")
				.data("setup",
						"general;;;$expand='all';;$accented='plain';;;generalc;;;$expand='all';;$accented='plain';;;generalccheck;;;ooooo;;;generalcheck;;;oooo;;;parameters;;;$priest='"
								+ String.valueOf(isPriest ? 1 : 0)
								+ "';;$building='0';;$lang1='Latin';;$psalmvar='0';;$whitebground='1';;$blackfont='black';;$smallblack='-1 black';;$redfont=' italic red';;$initiale='+2 bold italic red';;$largefont='+1 bold italic red';;$smallfont='1 red';;$titlefont='+1 red';;$screenheight='1024';;$textwidth='100';;;parameterscheck;;;bbtbbtcccccccnn;;;")
				.post();
		return doc;
	}
}
