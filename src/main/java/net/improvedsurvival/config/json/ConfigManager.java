package net.improvedsurvival.config.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.improvedsurvival.Isur;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigManager {
	private static final int version = 5;
	
	static JsonObject data;
	
	public static boolean getBool(String key) {
		if(data == null)
			loadConfig();
		JsonElement e = data.get(key);
		if(e == null)
			return false;
		return e.getAsBoolean();
	}
	
	public static int getInt(String key) {
		if(data == null)
			loadConfig();
		JsonElement e = data.get(key);
		if(e == null)
			return -1;
		return e.getAsInt();
	}
	
	public static JsonArray getArray(String key) {
		if(data == null)
			loadConfig();
		return ((JsonArray) data.get(key));
	}
	
	public static void loadConfig() {
		JsonParser jsonParser = new JsonParser();
		
		String dir = FabricLoader.getInstance().getConfigDirectory().getPath() + "\\" + Isur.MODID + "json";
		
		if(!Files.exists(Paths.get(dir)))
			copyConfig(dir);
		
		try(FileReader reader = new FileReader(dir)) {
			data = (JsonObject) jsonParser.parse(reader);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		if(getInt("version") != version) {
			copyConfig(dir);
			loadConfig();
		}
	}
	
	private static void copyConfig(String to) {
		try {
			InputStream copyDir = ConfigManager.class.getClassLoader().getResource(Isur.MODID + ".json").openStream();
			OutputStream pasteDir = new FileOutputStream(new File(to));
			byte[] buffer = new byte[1024];
			int lengthRead;
			while((lengthRead = copyDir.read(buffer)) > 0) {
				pasteDir.write(buffer, 0, lengthRead);
				pasteDir.flush();
			}
			copyDir.close();
			pasteDir.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}