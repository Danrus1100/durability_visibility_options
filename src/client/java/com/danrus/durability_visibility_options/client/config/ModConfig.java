package com.danrus.durability_visibility_options.client.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModConfig {
    public static int durabilityBarColor = 0x00FF00;
    public static int durabilityBarColor2 = 0xFF0000;
    public static boolean showDurability = true;
    public static int showDurabilityBarFromPercent = 100;
    public static int durabilityBarOffsetX = 0;
    public static int durabilityBarOffsetY = 0;

    public static boolean showDurabilityPercent = false;
    public static boolean showPercentSymbol = true;
    public static int durabilityPercentOffsetX = 0;
    public static int durabilityPercentOffsetY = 0;
    public static int showDurabilityPercentsFromPercent = 99;
    public static int durabilityPercentColor = 0xFFFFFF;

    public static void load() {
        ModConfigManager.load();
    }

    public static void saveConfig() {
        ModConfigManager.save();
    }

    public static Screen build(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("durability_visibility_options.config.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory bar = builder.getOrCreateCategory(Text.translatable("durability_visibility_options.config.category.bar"));

        bar.addEntry(
                entryBuilder.startBooleanToggle(Text.translatable("durability_visibility_options.config.show"), showDurability)
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> showDurability = value)
                        .build()
        );

        bar.addEntry(
                entryBuilder.startIntSlider(Text.translatable("durability_visibility_options.config.show_from_percent"), showDurabilityBarFromPercent, 0, 100)
                        .setDefaultValue(100)
                        .setSaveConsumer(value -> showDurabilityBarFromPercent = value)
                        .build()
        );

        bar.addEntry(
                entryBuilder.startIntField(Text.translatable("durability_visibility_options.config.horizontal_offset"), durabilityBarOffsetX)
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> durabilityBarOffsetX = value)
                        .build()
        );

        bar.addEntry(
                entryBuilder.startIntField(Text.translatable("durability_visibility_options.config.vertical_offset"), durabilityBarOffsetY)
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> durabilityBarOffsetY = value)
                        .build()
        );

        bar.addEntry(
                entryBuilder.startColorField(Text.translatable("durability_visibility_options.config.color_max"), durabilityBarColor)
                        .setDefaultValue(0x00FF00)
                        .setSaveConsumer(value -> durabilityBarColor = value)
                        .build()
        );



        bar.addEntry(
                entryBuilder.startColorField(Text.translatable("durability_visibility_options.config.color_min"), durabilityBarColor2)
                        .setDefaultValue(0xFF0000)
                        .setSaveConsumer(value -> durabilityBarColor2 = value)
                        .build()
        );

        ConfigCategory percent = builder.getOrCreateCategory(Text.translatable("durability_visibility_options.config.category.percents"));

        percent.addEntry(
                entryBuilder.startBooleanToggle(Text.translatable("durability_visibility_options.config.show"), showDurabilityPercent)
                        .setDefaultValue(false)
                        .setSaveConsumer(value -> showDurabilityPercent = value)
                        .build()
        );

        percent.addEntry(
                entryBuilder.startBooleanToggle(Text.translatable("durability_visibility_options.config.show_percent_symbol"), showPercentSymbol)
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> showPercentSymbol = value)
                        .build()
        );

        percent.addEntry(
                entryBuilder.startIntSlider(Text.translatable("durability_visibility_options.config.show_from_percent"), showDurabilityPercentsFromPercent, 0, 100)
                        .setDefaultValue(99)
                        .setSaveConsumer(value -> showDurabilityPercentsFromPercent = value)
                        .build()
        );

        percent.addEntry(
                entryBuilder.startIntField(Text.translatable("durability_visibility_options.config.horizontal_offset"), durabilityPercentOffsetX)
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> durabilityPercentOffsetX = value)
                        .build()
        );

        percent.addEntry(
                entryBuilder.startIntField(Text.translatable("durability_visibility_options.config.vertical_offset"), durabilityPercentOffsetY)
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> durabilityPercentOffsetY = value)
                        .build()
        );

        percent.addEntry(
                entryBuilder.startColorField(Text.translatable("durability_visibility_options.config.color"), durabilityPercentColor)
                        .setDefaultValue(0xFFFFFF)
                        .setSaveConsumer(value -> durabilityPercentColor = value)
                        .build()
        );

        builder.setSavingRunnable(ModConfig::saveConfig);
        return builder.build();
    }
}
