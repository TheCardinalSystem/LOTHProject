package com.Cardinal.LOTH.Update;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.Cardinal.LOTH.WorkspaceConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class GsonHub {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static Map<String, String> props = new HashMap<String, String>();

	static {
		try {
			props.putAll(
					gson.<Map<String, String>>fromJson(new FileReader(WorkspaceConstants.PROPERTIESFILE), Map.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setProperty(String name, String value) throws JsonIOException, IOException {
		props.put(name, value);
		String json = gson.toJson(props);
		FileWriter writer = new FileWriter(WorkspaceConstants.PROPERTIESFILE, false);
		writer.write(json);
		writer.flush();
		writer.close();
	}

	public static String getProperty(String name) {
		return props.get(name);
	}

	public static void removeProperty(String name) throws JsonIOException, IOException {
		props.remove(name);
		String json = gson.toJson(props);
		FileWriter writer = new FileWriter(WorkspaceConstants.PROPERTIESFILE, false);
		writer.write(json);
		writer.flush();
		writer.close();
	}

	public static Map<String, String> parseUpdateLog(String log) {
		Map<String, String> map = gson.<Map<String, String>>fromJson(log, Map.class);
		return map;
	}
}
