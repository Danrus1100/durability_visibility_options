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
        
        public boolean showArmorDurabilityHud = ModConfig.showArmorDurabilityHud;
        public boolean showArmorDurabilityHudPercentSymbol = ModConfig.showArmorDurabilityHudPercentSymbol;
        public boolean showArmorDurabilityHudInCreative = ModConfig.showArmorDurabilityHudInCreative;
        public int armorDurabilityHudTextColor = ModConfig.armorDurabilityHudTextColor;
        public int showArmorDurabilityHudFromPercent = ModConfig.showArmorDurabilityHudFromPercent;
        public int armorDurabilityHudOffsetX = ModConfig.armorDurabilityHudOffsetX;
        public int armorDurabilityHudOffsetY = ModConfig.armorDurabilityHudOffsetY;
        public int armorDurabilityHudMirgin = ModConfig.armorDurabilityHudMirgin;
        public String armorHudPositionHorizontal = ModConfig.armorHudPositionHorizontal.name();
        public String armorHudPositionVertical = ModConfig.armorHudPositionVertical.name();
        public String armorHudAlignment = ModConfig.armorHudAlignment.name();
        public String armorHudDisplayStyle = ModConfig.armorHudDisplayStyle.name();
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

                ModConfig.showArmorDurabilityHud = data.showArmorDurabilityHud;
                ModConfig.showArmorDurabilityHudInCreative = data.showArmorDurabilityHudInCreative;
                ModConfig.showArmorDurabilityHudPercentSymbol = data.showArmorDurabilityHudPercentSymbol;
                ModConfig.armorDurabilityHudTextColor = data.armorDurabilityHudTextColor;
                ModConfig.armorDurabilityHudMirgin = data.armorDurabilityHudMirgin;
                ModConfig.showArmorDurabilityHudFromPercent = data.showArmorDurabilityHudFromPercent;
                ModConfig.armorDurabilityHudOffsetX = data.armorDurabilityHudOffsetX;
                ModConfig.armorDurabilityHudOffsetY = data.armorDurabilityHudOffsetY;

                if (data.armorHudPositionHorizontal != null) {
                    try {
                        ModConfig.armorHudPositionHorizontal = ModConfig.armorDurabilityHudPositionHorizontal.valueOf(data.armorHudPositionHorizontal);
                    } catch (IllegalArgumentException ignored) {}
                }

                if (data.armorHudPositionVertical != null) {
                    try {
                        ModConfig.armorHudPositionVertical = ModConfig.armorDurabilityHudPositionVertical.valueOf(data.armorHudPositionVertical);
                    } catch (IllegalArgumentException ignored) {}
                }
                
                if (data.armorHudAlignment != null) {
                    try {
                        ModConfig.armorHudAlignment = ModConfig.armorDurabilityHudAlignment.valueOf(data.armorHudAlignment);
                    } catch (IllegalArgumentException ignored) {}
                }
                
                if (data.armorHudDisplayStyle != null) {
                    try {
                        ModConfig.armorHudDisplayStyle = ModConfig.armorDurabilityHudDisplayStyle.valueOf(data.armorHudDisplayStyle);
                    } catch (IllegalArgumentException ignored) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}