package com.butukay.tridentfly.config;

import net.fabricmc.loader.api.FabricLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class TridentFlyConfigUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "tridentFly.json");

    public static TridentFlyConfig config;

    public static void loadConfig() {
        try {
            if (configFile.exists()) {
                Reader reader = new FileReader(configFile);
                config = GSON.fromJson(reader, TridentFlyConfig.class);
            } else {
                config = new TridentFlyConfig();
                saveConfig();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            configFile.createNewFile();

            Writer writer = new FileWriter(configFile, false);

            GSON.toJson(config, writer);

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TridentFlyConfig getConfig() {
        return config;
    }
}
