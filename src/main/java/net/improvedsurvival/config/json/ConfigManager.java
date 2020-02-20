package net.improvedsurvival.config.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.fabricmc.loader.api.FabricLoader;
import net.improvedsurvival.Isur;

public class ConfigManager {
    static JsonObject data;

    public static boolean getBool(String key) {
        if (data == null)
            loadConfig();
        JsonElement e = (JsonElement) data.get(key);
        if (e == null)
            return false;
        return e.getAsBoolean();
    }

    public static int getInt(String key) {
        if (data == null)
            loadConfig();
        JsonElement e = (JsonElement) data.get(key);
        if (e == null)
            return -1;
        return e.getAsInt();
    }

    public static JsonArray getArray(String key) {
        if (data == null)
            loadConfig();
        return ((JsonArray) data.get(key));
    }

    public static void loadConfig() {

        JsonParser jsonParser = new JsonParser();

        String dir = FabricLoader.getInstance().getConfigDirectory().getPath() + "\\" + Isur.MODID + ".json";

        if (!Files.exists(Paths.get(dir)))
            copyConfig(dir);

        try (FileReader reader = new FileReader(dir)) {
            data = (JsonObject) jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int latestVersion = getLatestVersionNumber();

        if (getInt("version") != latestVersion && latestVersion != -1) {
            copyConfig(dir);
            loadConfig();
        }
    }

    private static int getLatestVersionNumber() {
        JsonParser jsonParser = new JsonParser();
        String jsonResult = "";
        try {
            InputStream jsonDir = ConfigManager.class.getClassLoader().getResource(Isur.MODID + ".json").openStream();
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = jsonDir.read(buffer)) > 0) {
                for(int i = 0; i < lengthRead; i++)
                    jsonResult += (char)buffer[i];
            }
            jsonDir.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        JsonObject rootConfig = (JsonObject)jsonParser.parse(jsonResult);

        if(rootConfig == null)
            return -1;

        return rootConfig.get("version").getAsInt();
    }

    private static void copyConfig(String to){
        try {
            InputStream copyDir = ConfigManager.class.getClassLoader().getResource(Isur.MODID + ".json").openStream();
            OutputStream pasteDir = new FileOutputStream(new File(to));
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = copyDir.read(buffer)) > 0) {
                pasteDir.write(buffer, 0, lengthRead);
                pasteDir.flush();
            }
            copyDir.close();
            pasteDir.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}