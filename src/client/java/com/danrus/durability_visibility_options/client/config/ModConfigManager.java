package com.danrus.durability_visibility_options.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/durability_visibility_options.json");

    public static class ConfigData {
        public int durabilityBarColor = ModConfig.durabilityBarColor;
        public int durabilityBarColor2 = ModConfig.durabilityBarColor2;
        public boolean showDurability = ModConfig.showDurability;
        public boolean isVertical = ModConfig.isVertical;
        public int showDurabilityBarFromPercent = ModConfig.showDurabilityBarFromPercent;
        public int durabilityBarOffsetX = ModConfig.durabilityBarOffsetX;
        public int durabilityBarOffsetY = ModConfig.durabilityBarOffsetY;

        public boolean showDurabilityPercent = ModConfig.showDurabilityPercent;
        public boolean showPercentSymbol = ModConfig.showPercentSymbol;
        public int durabilityPercentOffsetX = ModConfig.durabilityPercentOffsetX;
        public int durabilityPercentOffsetY = ModConfig.durabilityPercentOffsetY;
        public int durabilityPercentColor = ModConfig.durabilityPercentColor;
        public int showDurabilityPercentsFromPercent = ModConfig.showDurabilityPercentsFromPercent;
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(new ConfigData(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                ModConfig.durabilityBarColor = data.durabilityBarColor;
                ModConfig.durabilityBarColor2 = data.durabilityBarColor2;
                ModConfig.showDurability = data.showDurability;
                ModConfig.isVertical = data.isVertical;
                ModConfig.showDurabilityBarFromPercent = data.showDurabilityBarFromPercent;
                ModConfig.durabilityBarOffsetX = data.durabilityBarOffsetX;
                ModConfig.durabilityBarOffsetY = data.durabilityBarOffsetY;

                ModConfig.showDurabilityPercent = data.showDurabilityPercent;
                ModConfig.showPercentSymbol = data.showPercentSymbol;
                ModConfig.durabilityPercentOffsetX = data.durabilityPercentOffsetX;
                ModConfig.durabilityPercentOffsetY = data.durabilityPercentOffsetY;
                ModConfig.durabilityPercentColor = data.durabilityPercentColor;
                ModConfig.showDurabilityPercentsFromPercent = data.showDurabilityPercentsFromPercent;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
