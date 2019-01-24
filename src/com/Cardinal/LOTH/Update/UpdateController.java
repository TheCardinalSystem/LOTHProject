package com.Cardinal.LOTH.Update;

import java.awt.Desktop;
import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Util.WebUtils;
import com.Cardinal.LOTH.Web.WebParser;

public class UpdateController {

	public static Path applied;

	public static boolean checkForUpdates() throws IOException, URISyntaxException {
		if (!WebUtils.checkNetworkConnection("www.github.com", 5000)) {
			throw new NoRouteToHostException("Unable to reach remote host. Please check your internet connection.");
		}

		String json = WebParser.getVersionLog();
		Map<String, String> versions = GsonHub.parseUpdateLog(json);
		String version = getLatestVersion(versions);
		return Integer.parseInt(version.replaceAll("\\.", "")) > Integer
				.parseInt(WorkspaceConstants.VERSION.replaceAll("\\.", ""));
	}

	public static void update(boolean restart) throws IOException, URISyntaxException {
		String json = WebParser.getVersionLog();
		Map<String, String> versions = GsonHub.parseUpdateLog(json);
		String url = getLatestURL(versions);

		System.out.println(System.getProperty("os.name"));
		String ex = System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : ".jar";

		Path update = UpdateIO.downloadUpdate(url + ex);
		applied = UpdateIO.applyUpdate(update);
		if (restart)
			restart(applied);
	}

	public static String getLatestVersion(Map<String, String> versions) {
		return versions.get("Latest");
	}

	public static String getLatestURL(Map<String, String> versions) {
		return versions.get(versions.get("Latest"));
	}

	public static void restart(Path target) throws IOException {
		Desktop.getDesktop().open(target.toFile());
		System.exit(0);
	}
}
