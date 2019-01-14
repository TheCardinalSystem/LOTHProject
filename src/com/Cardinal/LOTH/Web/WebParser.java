package com.Cardinal.LOTH.Web;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.print.PrintException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.Cardinal.LOTH.Gui.Frames.MainFrame;
import com.Cardinal.LOTH.Util.HourUtils.Hour;
import com.Cardinal.LOTH.Util.WebUtils.WebLang;

public class WebParser {
	public static void main(String[] args) throws IOException, PrintException, PrinterException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		MainFrame frame = new MainFrame("LOTH Prototype");
		frame.setVisible(true);
		/*
		 * int response = JOptionPane.showOptionDialog(null,
		 * "Please select a language format.", "LOTH Prototype",
		 * JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new
		 * Object[] { "English", "Latin", "English-Latin" }, "English");
		 * 
		 * try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		 * catch (ClassNotFoundException | InstantiationException |
		 * IllegalAccessException | UnsupportedLookAndFeelException e) {
		 * e.printStackTrace(); } int custTime = JOptionPane.showConfirmDialog(null,
		 * "Do you want the current canonical hour?", "LOTH Prototype",
		 * JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		 * 
		 * Hour hour = null; LocalDate date = null; if (custTime ==
		 * JOptionPane.YES_OPTION) { hour = HourUtils.getCurrentHour(); date =
		 * LocalDate.now(); } else { TimePromptFrame frame = new
		 * TimePromptFrame("LOTH Prototype"); try { LocalDateTime dateTime =
		 * frame.getResult(); date = dateTime.toLocalDate(); hour =
		 * HourUtils.getHour(dateTime.toLocalTime()); } catch (InterruptedException e) {
		 * e.printStackTrace(); } }
		 * 
		 * System.out.println("Fetching data..."); String html = getHtmlForHour(hour,
		 * (response == 0 ? WebLang.ENG : response == 1 ? WebLang.LAT : WebLang.BOTH),
		 * date); System.out.println("Processing data..."); File f =
		 * PDFUtils.saveHtmlToPdf(html);
		 * System.out.println("Prompting user for action...");
		 * PrinterUtils.promptToPrint(f);
		 */
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

	/*
	 * public static String getHourPage(Hour hour, String date, String lang,
	 * UpdatableProgressPane pane) throws MalformedURLException, IOException {
	 * HttpURLConnection connect = (HttpURLConnection) new
	 * URL("https://divinumofficium.com/cgi-bin/horas/officium.pl")
	 * .openConnection(); connect.setRequestMethod("POST");
	 * connect.setRequestProperty("Accept",
	 * "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,
	 *//*
		 * ;q=0.8"); connect.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		 * connect.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
		 * connect.setRequestProperty("Cache-Control", "max-age=0");
		 * connect.setRequestProperty("Connection", "keep-alive"); //
		 * connect.setRequestProperty("Content-Length", "1127");
		 * connect.setRequestProperty("Content-Type",
		 * "application/x-www-form-urlencoded"); connect.setRequestProperty("Origin",
		 * "https://divinumofficium.com"); connect.setRequestProperty("Host",
		 * "divinumofficium.com");
		 * connect.setRequestProperty("Upgrade-Insecure-Requests", "1");
		 * connect.setRequestProperty("User-Agent",
		 * "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36"
		 * ); connect.setRequestProperty("Referer",
		 * "https://divinumofficium.com/cgi-bin/horas/officium.pl");
		 * 
		 * connect.setDoOutput(true); connect.setInstanceFollowRedirects(false);
		 * 
		 * lang = (lang.indexOf("+") > 1 ? lang.substring(lang.indexOf("+")) : lang);
		 * 
		 * String data = "date=" + date + "&expand=all&version=Rubrics+1960&lang2=" +
		 * lang +
		 * "&setup=general%3B%3B%3B%24expand%3D%27all%27%3B%3B%24version%3D%27Rubrics+1960%27%3B%3B%24lang2%3D%27"
		 * + lang +
		 * "%27%3B%3B%24accented%3D%27plain%27%3B%3B%3Bgeneralc%3B%3B%3B%24expand%3D%27all%27%3B%3B%24version1%3D%27Divino+Afflatu%27%3B%3B%24version2%3D%27Rubrics+1960%27%3B%3B%24lang2%3D%27English%27%3B%3B%24accented%3D%27plain%27%3B%3B%3Bgeneralccheck%3B%3B%3Booooo%3B%3B%3Bgeneralcheck%3B%3B%3Boooo%3B%3B%3Bparameters%3B%3B%3B%24priest%3D%270%27%3B%3B%24building%3D%270%27%3B%3B%24lang1%3D%27Latin%27%3B%3B%24psalmvar%3D%270%27%3B%3B%24whitebground%3D%271%27%3B%3B%24blackfont%3D%27black%27%3B%3B%24smallblack%3D%27-1+black%27%3B%3B%24redfont%3D%27+italic+red%27%3B%3B%24initiale%3D%27%2B2+bold+italic+red%27%3B%3B%24largefont%3D%27%2B1+bold+italic+red%27%3B%3B%24smallfont%3D%271+red%27%3B%3B%24titlefont%3D%27%2B1+red%27%3B%3B%24screenheight%3D%271024%27%3B%3B%24textwidth%3D%27100%27%3B%3B%3Bparameterscheck%3B%3B%3Bbbtbbtcccccccnn%3B%3B%3B&command=pray"
		 * + hour.toString(WebLang.LAT) +
		 * "&searchvalue=0&officium=officium.pl&browsertime=" + date +
		 * "&accented=plain&caller=0&notes=";
		 * 
		 * connect.setRequestProperty("charset", "utf-8");
		 * connect.setRequestProperty("Content-Length",
		 * Integer.toString(data.length())); connect.setUseCaches(false);
		 * 
		 * byte[] postData = data.getBytes(StandardCharsets.UTF_8); try
		 * (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
		 * wr.write(postData); }
		 * 
		 * pane.setCapacity(connect.getContentLength());
		 * 
		 * int avail, bytesRead = 0; StringBuilder builder = new StringBuilder();
		 * InputStream stream = connect.getInputStream(); while ((avail =
		 * stream.available()) > 1) { byte[] buffer = new byte[avail]; int amount =
		 * stream.read(buffer, 0, buffer.length); builder.append(new String(buffer, 0,
		 * amount)); bytesRead += amount; pane.update(bytesRead); } return
		 * builder.toString(); }
		 */
}
