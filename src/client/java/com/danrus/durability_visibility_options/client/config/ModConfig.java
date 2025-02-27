package com.danrus.durability_visibility_options.client.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModConfig {
    public enum armorDurabilityHudPositionHorizontal {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum armorDurabilityHudPositionVertical {
        TOP,
        CENTER,
        BOTTOM
    }

    public enum armorDurabilityHudAlignment {
        HORIZONTAL,
        VERTICAL
    }
    public enum armorDurabilityHudDisplayStyle {
        ICON_PERCENT,
        PERCENT_ICON
    }

    public static int durabilityBarColor = 0x00FF00;
    public static int durabilityBarColor2 = 0xFF0000;
    public static boolean showDurability = true;
    public static boolean isVertical = false;
    public static int showDurabilityBarFromPercent = 100;
    public static int durabilityBarOffsetX = 0;
    public static int durabilityBarOffsetY = 0;

    public static boolean showDurabilityPercent = false;
    public static boolean showPercentSymbol = true;
    public static int durabilityPercentOffsetX = 0;
    public static int durabilityPercentOffsetY = 0;
    public static int showDurabilityPercentsFromPercent = 99;
    public static int durabilityPercentColor = 0xFFFFFF;

    public static boolean showArmorDurabilityHud = false;
    public static boolean showArmorDurabilityHudPercentSymbol = true;
    public static boolean showArmorDurabilityHudInCreative = true;
    public static int armorDurabilityHudTextColor = 0xFFFFFF;
    public static int showArmorDurabilityHudFromPercent = 100;
    public static int armorDurabilityHudOffsetX = 0;
    public static int armorDurabilityHudOffsetY = 0;
    public static int armorDurabilityHudMirgin = 20;
    public static float armorDurabilityHudScale = 1.0f;
    public static armorDurabilityHudPositionHorizontal armorHudPositionHorizontal = armorDurabilityHudPositionHorizontal.LEFT;
    public static armorDurabilityHudPositionVertical armorHudPositionVertical = armorDurabilityHudPositionVertical.TOP;
    public static armorDurabilityHudAlignment armorHudAlignment = armorDurabilityHudAlignment.VERTICAL;
    public static armorDurabilityHudDisplayStyle armorHudDisplayStyle = armorDurabilityHudDisplayStyle.ICON_PERCENT;

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
                entryBuilder.startBooleanToggle(Text.translatable("durability_visibility_options.config.is_vertical"), isVertical)
                        .setDefaultValue(false)
                        .setSaveConsumer(value -> isVertical = value)
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

        ConfigCategory armorHud = builder.getOrCreateCategory(Text.translatable("durability_visibility_options.config.category.armor_hud"));

        armorHud.addEntry(
                entryBuilder.startBooleanToggle(Text.translatable("durability_visibility_options.config.armor_hud.show"), showArmorDurabilityHud)
                        .setDefaultValue(false)
                        .setSaveConsumer(value -> showArmorDurabilityHud = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startBooleanToggle(Text.translatable("durability_visibility_options.config.show_percent_symbol"), showArmorDurabilityHudPercentSymbol)
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> showArmorDurabilityHudPercentSymbol = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startBooleanToggle(Text.translatable("durability_visibility_options.config.show_in_creative"), showArmorDurabilityHudInCreative)
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> showArmorDurabilityHudInCreative = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startIntSlider(Text.translatable("durability_visibility_options.config.show_from_percent"), showArmorDurabilityHudFromPercent, 0, 100)
                        .setDefaultValue(100)
                        .setSaveConsumer(value -> showArmorDurabilityHudFromPercent = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startIntField(Text.translatable("durability_visibility_options.config.horizontal_offset"), armorDurabilityHudOffsetX)
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> armorDurabilityHudOffsetX = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startIntField(Text.translatable("durability_visibility_options.config.vertical_offset"), armorDurabilityHudOffsetY)
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> armorDurabilityHudOffsetY = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startIntField(Text.translatable("durability_visibility_options.config.margin"), armorDurabilityHudMirgin)
                        .setDefaultValue(20)
                        .setSaveConsumer(value -> armorDurabilityHudMirgin = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startFloatField(Text.translatable("durability_visibility_options.config.scale"), armorDurabilityHudScale)
                        .setDefaultValue(1.0f)
                        .setSaveConsumer(value -> armorDurabilityHudScale = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startEnumSelector(Text.translatable("durability_visibility_options.config.armor_hud.position_horizontal"), armorDurabilityHudPositionHorizontal.class, armorHudPositionHorizontal)
                        .setDefaultValue(armorDurabilityHudPositionHorizontal.LEFT)
                        .setEnumNameProvider(value -> Text.translatable("durability_visibility_options.config.armor_hud.position_horizontal." + value.name().toLowerCase()))
                        .setSaveConsumer(value -> armorHudPositionHorizontal = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startEnumSelector(Text.translatable("durability_visibility_options.config.armor_hud.position_vertical"), armorDurabilityHudPositionVertical.class, armorHudPositionVertical)
                        .setDefaultValue(armorDurabilityHudPositionVertical.TOP)
                        .setEnumNameProvider(value -> Text.translatable("durability_visibility_options.config.armor_hud.position_vertical." + value.name().toLowerCase()))
                        .setSaveConsumer(value -> armorHudPositionVertical = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startEnumSelector(Text.translatable("durability_visibility_options.config.armor_hud.alignment"), armorDurabilityHudAlignment.class, armorHudAlignment)
                        .setDefaultValue(armorDurabilityHudAlignment.VERTICAL)
                        .setEnumNameProvider(value -> Text.translatable("durability_visibility_options.config.armor_hud.alignment." + value.name().toLowerCase()))
                        .setSaveConsumer(value -> armorHudAlignment = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startEnumSelector(Text.translatable("durability_visibility_options.config.armor_hud.display_style"), armorDurabilityHudDisplayStyle.class, armorHudDisplayStyle)
                        .setDefaultValue(armorDurabilityHudDisplayStyle.ICON_PERCENT)
                        .setEnumNameProvider(value -> Text.translatable("durability_visibility_options.config.armor_hud.display_style." + value.name().toLowerCase()))
                        .setSaveConsumer(value -> armorHudDisplayStyle = value)
                        .build()
        );

        armorHud.addEntry(
                entryBuilder.startColorField(Text.translatable("durability_visibility_options.config.color"), armorDurabilityHudTextColor)
                        .setDefaultValue(0xFFFFFF)
                        .setSaveConsumer(value -> armorDurabilityHudTextColor = value)
                        .build()
        );

        builder.setSavingRunnable(ModConfig::saveConfig);
        return builder.build();
    }
}
