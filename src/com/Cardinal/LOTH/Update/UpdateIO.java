package com.Cardinal.LOTH.Update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.Cardinal.LOTH.Web.WebParser;

public class UpdateIO {

	public static Path downloadUpdate(String downloadURL) throws IOException {
		URL url = new URL(downloadURL);
		String path = url.getPath();
		InputStream stream = url.openStream();
		Path update;

		File f = new File(WorkspaceConstants.WORKINGDIRECTORY + "/temp");
		if (!f.exists())
			f.mkdirs();

		Files.copy(stream, update = Paths.get(f.getAbsolutePath() + "/" + path.substring(path.lastIndexOf("/") + 1)),
				StandardCopyOption.REPLACE_EXISTING);

		return update;
	}

	public static Path applyUpdate(Path updatePath) throws IOException, URISyntaxException {
		File f = getJarFile();
		String path = f.getAbsolutePath(), path2 = updatePath.toString();
		path = path.substring(path.indexOf("file") + 6).replaceAll("(%20)", " ");
		GsonHub.setProperty(WorkspaceConstants.UPDATEPROPERTY, path);
		if (path.endsWith(".jar")) {
			return Files.move(updatePath,
					Paths.get(path.substring(0, path.lastIndexOf("\\")) + path2.substring(path2.lastIndexOf("\\"))),
					StandardCopyOption.REPLACE_EXISTING);
		} else {
			Path target = Paths
					.get(path.substring(0, path.lastIndexOf("\\")) + path2.substring(path2.lastIndexOf("\\")));

			return Files.move(updatePath, target, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private static File getJarFile() throws FileNotFoundException {
		String path = WebParser.class.getResource(WebParser.class.getSimpleName() + ".class").getFile();
		if (path.startsWith("/")) {
			throw new FileNotFoundException("This is not a jar file: \n" + path);
		}
		path = ClassLoader.getSystemClassLoader().getResource(path).getFile();

		return new File(path.substring(0, path.lastIndexOf('!')));
	}
}
