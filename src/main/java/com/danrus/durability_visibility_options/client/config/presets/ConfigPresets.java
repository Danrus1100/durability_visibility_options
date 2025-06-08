package com.danrus.durability_visibility_options.client.config.presets;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigPresets {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PRESETS_DIR_NAME = "durability_visibility_options_presets";
    private static Path CONFIG_DIR;
    public static Path PRESETS_DIR;

    public static final List<DurabilityConfig> DEFAULT_CONFIGS = List.of(

            DurabilityConfig.builder()
                    .setKey("only_percents")
                    .setShowDurability(false)
                    .setShowDurabilityPercent(true)
                    .build(),

            DurabilityConfig.builder()
                    .setKey("vertical_bar_plus_percents")
                    .setVertical(true)
                    .setShowDurabilityPercent(true)
                    .setDurabilityPercentOffsetX(4)
                    .build(),

            DurabilityConfig.builder()
                    .setKey("low_durability_alert")
                    .setShowDurability(false)
                    .setShowDurabilityPercent(true)
                    .setShowDurabilityPercentsFromPercent(25)
                    .setDurabilityPercentOffsetY(4)
                    .setDurabilityPercentColor(0xFF0000)
                    .setDurabilityPercentColorMin(0xFF0000)
                    .build(),

            DurabilityConfig.builder()
                    .setKey("uwu")
                    .setDurabilityBarColor(0xE268FF)
                    .setDurabilityBarColorMin(0xFF9D9D)
                    .build(),

            DurabilityConfig.builder()
                    .setKey("duraview")
                    .setShowDurability(false)
                    .setShowDurabilityPercent(true)
                    .setDurabilityPercentOffsetX(3)
                    .setDurabilityPercentColor(0x00FF00)
                    .setDurabilityPercentColorMin(0xFF0000)
                    .build()
    );

    public static void initialize() {
        CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
        PRESETS_DIR = CONFIG_DIR.resolve(PRESETS_DIR_NAME);
        try {
            Files.createDirectories(PRESETS_DIR);
        } catch (IOException e) {
            System.err.println("Failed to create config presets directory: " + PRESETS_DIR + " - " + e.getMessage());
        }
    }

    private static Path getPresetsDirectory() {
        if (PRESETS_DIR == null) {
            initialize();
        }
        return PRESETS_DIR;
    }

    public static boolean saveCustom(DurabilityConfig config) {
        if (config.name == null || config.name.trim().isEmpty()) {
            System.err.println("DurabilityConfig name cannot be null or empty for saving.");
            return false;
        }

        String fileName = config.name.trim().replaceAll("[^\\p{L}\\p{N}-_\\.]", "_") + ".json";
        Path filePath = getPresetsDirectory().resolve(fileName);

        config.key = fileName.replace(".json", "");

        try (Writer writer = new FileWriter(filePath.toFile())) {
            GSON.toJson(config, writer);
            System.out.println("DurabilityConfig saved to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save DurabilityConfig to " + filePath + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static DurabilityConfig loadCustom(String key) {
        if (key == null || key.trim().isEmpty()) {
            System.err.println("Config key cannot be null or empty for loading.");
            return null;
        }

        String fileName = key.trim() + ".json";
        Path filePath = getPresetsDirectory().resolve(fileName);

        if (!Files.exists(filePath)) {
            System.out.println("DurabilityConfig file not found: " + filePath);
            return null;
        }

        try (Reader reader = new FileReader(filePath.toFile())) {
            DurabilityConfig config = GSON.fromJson(reader, DurabilityConfig.class);
            if (config != null) {
                config.key = key;
            }
            System.out.println("DurabilityConfig loaded from: " + filePath);
            return config;
        } catch (IOException e) {
            System.err.println("Failed to load DurabilityConfig from " + filePath + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            System.err.println("Failed to parse DurabilityConfig JSON from " + filePath + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getAllPresetKeys() {
        Path presetsDir = getPresetsDirectory();
        if (!Files.exists(presetsDir) || !Files.isDirectory(presetsDir)) {
            System.out.println("Presets directory does not exist or is not a directory: " + presetsDir);
            return List.of(); // Return an empty immutable list
        }

        try (Stream<Path> paths = Files.list(presetsDir)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .map(path -> {
                        String fileName = path.getFileName().toString();
                        return fileName.substring(0, fileName.length() - ".json".length());
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Failed to list preset files in " + presetsDir + ": " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public static void openPresetsFolderInExplorer() {
        Path presetsDir = getPresetsDirectory();

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            try {
                Files.createDirectories(presetsDir);
                Desktop.getDesktop().open(presetsDir.toFile());
                System.out.println("Opened presets folder in explorer: " + presetsDir);
            } catch (IOException e) {
                System.err.println("Failed to open presets folder in explorer: " + presetsDir + " - " + e.getMessage());
                e.printStackTrace();
            } catch (SecurityException e) {
                System.err.println("Security exception: Not allowed to open presets folder. " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Desktop API not supported or OPEN action not supported. Cannot open presets folder directly.");
            System.out.println("Presets folder path: " + presetsDir.toAbsolutePath());
        }
    }
}
