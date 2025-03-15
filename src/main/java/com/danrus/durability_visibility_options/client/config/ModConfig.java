package com.danrus.durability_visibility_options.client.config;

import com.danrus.durability_visibility_options.client.DurabilityVisibilityOptions;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;

public class ModConfig {

    public static boolean isOpen = false;

    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("durability_visibility_options.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    // Durability Bar
    @SerialEntry(comment = "Enable durability bar display")
    public boolean showDurability = true;

    @SerialEntry(comment = "Display durability bar vertically")
    public boolean isVertical = false;

    @SerialEntry(comment = "Show durability bar when durability is below this percentage")
    public int showDurabilityBarFromPercent = 100;

    @SerialEntry
    public int durabilityBarOffsetX = 0;

    @SerialEntry
    public int durabilityBarOffsetY = 0;

    @SerialEntry(comment = "Durability bar color at maximum durability (RGB)")
    public int durabilityBarColor = 0x00FF00;


    // Durability Percent
    @SerialEntry
    public boolean showDurabilityPercent = false;

    @SerialEntry
    public boolean showPercentSymbol = true;

    @SerialEntry
    public int durabilityPercentOffsetX = 0;

    @SerialEntry
    public int durabilityPercentOffsetY = 0;

    @SerialEntry
    public int showDurabilityPercentsFromPercent = 99;

    @SerialEntry
    public int durabilityPercentColor = 0xFFFFFF;

    // Armor HUD
    @SerialEntry
    public boolean showArmorDurabilityHud = false;

    @SerialEntry
    public boolean showArmorDurabilityHudPercentSymbol = true;

    @SerialEntry
    public boolean showArmorDurabilityHudInCreative = true;

    @SerialEntry
    public boolean showVanillaArmorHud = true;

    @SerialEntry
    public int armorDurabilityHudTextColor = 0xFFFFFF;

    @SerialEntry
    public int showArmorDurabilityHudFromPercent = 100;

    @SerialEntry
    public int armorDurabilityHudOffsetX = 0;

    @SerialEntry
    public int armorDurabilityHudOffsetY = 0;

    @SerialEntry
    public int armorDurabilityHudMirgin = 20;

    @SerialEntry
    public float armorDurabilityHudScale = 1.0f;

    @SerialEntry
    public ArmorPositionHorizontal armorHudPositionHorizontal = ArmorPositionHorizontal.LEFT;

    @SerialEntry
    public ArmorPositionVertical armorHudPositionVertical = ArmorPositionVertical.TOP;

    @SerialEntry
    public ArmorAlignment armorHudAlignment = ArmorAlignment.VERTICAL;

    @SerialEntry
    public ArmorDisplayStyle armorHudDisplayStyle = ArmorDisplayStyle.ICON_PERCENT;

    @SerialEntry
    public ArmorVanillaStatsAdapt armorVanillaStatsAdapt = ArmorVanillaStatsAdapt.NONE;

    // Enums для конфигурации
    public enum ArmorPositionHorizontal {
        LEFT, CENTER, RIGHT
    }

    public enum ArmorPositionVertical {
        TOP, CENTER, BOTTOM
    }

    public enum ArmorAlignment {
        HORIZONTAL, VERTICAL
    }

    public enum ArmorDisplayStyle {
        ICON_PERCENT, PERCENT_ICON
    }

    public enum ArmorVanillaStatsAdapt {
        NONE, HEARTS, AIR
    }

    public static Screen getConfigScreen(Screen parent) {
        Screen yaclScreen =  YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("durability_visibility_options.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("durability_visibility_options.tab.general"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.bar"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show"))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().showDurability,
                                                value -> {
                                                    HANDLER.instance().showDurability = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.is_vertical"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().isVertical,
                                                value -> {
                                                    HANDLER.instance().isVertical = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_from_percent"))
                                        .binding(
                                                100,
                                                () -> HANDLER.instance().showDurabilityBarFromPercent,
                                                value -> {
                                                    HANDLER.instance().showDurabilityBarFromPercent = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 100)
                                                .step(1))
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityBarOffsetX,
                                                value -> {
                                                    HANDLER.instance().durabilityBarOffsetX = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityBarOffsetY,
                                                value -> {
                                                    HANDLER.instance().durabilityBarOffsetY = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color"))
                                        .binding(
                                                new Color(0x00FF00),
                                                () -> new Color(HANDLER.instance().durabilityBarColor),
                                                value -> {
                                                    HANDLER.instance().durabilityBarColor = value.getRGB();
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.percents"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().showDurabilityPercent,
                                                value -> {
                                                    HANDLER.instance().showDurabilityPercent = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_percent_symbol"))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().showPercentSymbol,
                                                value -> {
                                                    HANDLER.instance().showPercentSymbol = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityPercentOffsetX,
                                                value -> {
                                                    HANDLER.instance().durabilityPercentOffsetX = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityPercentOffsetY,
                                                value -> {
                                                    HANDLER.instance().durabilityPercentOffsetY = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_from_percent"))
                                        .binding(
                                                99,
                                                () -> HANDLER.instance().showDurabilityPercentsFromPercent,
                                                value -> {
                                                    HANDLER.instance().showDurabilityPercentsFromPercent = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 100)
                                                .step(1))
                                        .build())
                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color"))
                                        .binding(
                                                new Color(HANDLER.instance().durabilityPercentColor),
                                                () -> new Color(HANDLER.instance().durabilityPercentColor),
                                                value -> {
                                                    HANDLER.instance().durabilityPercentColor = value.getRGB();
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("durability_visibility_options.config.category.armor_hud"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.tab.general"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().showArmorDurabilityHud,
                                                value -> {
                                                    HANDLER.instance().showArmorDurabilityHud = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_percent_symbol"))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().showArmorDurabilityHudPercentSymbol,
                                                value -> {
                                                    HANDLER.instance().showArmorDurabilityHudPercentSymbol = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_in_creative"))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().showArmorDurabilityHudInCreative,
                                                value -> {
                                                    HANDLER.instance().showArmorDurabilityHudInCreative = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_vanilla_armor_hud"))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().showVanillaArmorHud,
                                                value -> {
                                                    HANDLER.instance().showVanillaArmorHud = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color"))
                                        .binding(
                                                new Color(HANDLER.instance().armorDurabilityHudTextColor),
                                                () -> new Color(HANDLER.instance().armorDurabilityHudTextColor),
                                                value -> {
                                                    HANDLER.instance().armorDurabilityHudTextColor = value.getRGB();
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_from_percent"))
                                        .binding(
                                                100,
                                                () -> HANDLER.instance().showArmorDurabilityHudFromPercent,
                                                value -> {
                                                    HANDLER.instance().showArmorDurabilityHudFromPercent = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 100)
                                                .step(1))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.positioning"))
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().armorDurabilityHudOffsetX,
                                                value -> {
                                                    HANDLER.instance().armorDurabilityHudOffsetX = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().armorDurabilityHudOffsetY,
                                                value -> {
                                                    HANDLER.instance().armorDurabilityHudOffsetY = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.margin"))
                                        .binding(
                                                20,
                                                () -> HANDLER.instance().armorDurabilityHudMirgin,
                                                value -> {
                                                    HANDLER.instance().armorDurabilityHudMirgin = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.scale"))
                                        .binding(
                                                1.0f,
                                                () -> HANDLER.instance().armorDurabilityHudScale,
                                                value -> {
                                                    HANDLER.instance().armorDurabilityHudScale = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.layout"))
                                .option(Option.createBuilder(ArmorPositionHorizontal.class)
                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.position_horizontal"))
                                        .binding(
                                                ArmorPositionHorizontal.LEFT,
                                                () -> HANDLER.instance().armorHudPositionHorizontal,
                                                value -> {
                                                    HANDLER.instance().armorHudPositionHorizontal = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.position_horizontal." + value.toString().toLowerCase()))
                                                .enumClass(ArmorPositionHorizontal.class))
                                        .build())
                                .option(Option.createBuilder(ArmorPositionVertical.class)
                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.position_vertical"))
                                        .binding(
                                                ArmorPositionVertical.TOP,
                                                () -> HANDLER.instance().armorHudPositionVertical,
                                                value -> {
                                                    HANDLER.instance().armorHudPositionVertical = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.position_vertical." + value.toString().toLowerCase()))
                                                .enumClass(ArmorPositionVertical.class))
                                        .build())
                                .option(Option.createBuilder(ArmorAlignment.class)
                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.alignment"))
                                        .binding(
                                                ArmorAlignment.VERTICAL,
                                                () -> HANDLER.instance().armorHudAlignment,
                                                value -> {
                                                    HANDLER.instance().armorHudAlignment = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.alignment." + value.toString().toLowerCase()))
                                                .enumClass(ArmorAlignment.class))
                                        .build())
                                .option(Option.createBuilder(ArmorDisplayStyle.class)
                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.display_style"))
                                        .binding(
                                                ArmorDisplayStyle.ICON_PERCENT,
                                                () -> HANDLER.instance().armorHudDisplayStyle,
                                                value -> {
                                                    HANDLER.instance().armorHudDisplayStyle = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.display_style." + value.toString().toLowerCase()))
                                                .enumClass(ArmorDisplayStyle.class))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.behavior"))
                                .option(Option.createBuilder(ArmorVanillaStatsAdapt.class)
                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.armor_vanilla_stats_adapt"))
                                        .binding(
                                                ArmorVanillaStatsAdapt.NONE,
                                                () -> HANDLER.instance().armorVanillaStatsAdapt,
                                                value -> {
                                                    HANDLER.instance().armorVanillaStatsAdapt = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.armor_vanilla_stats_adapt." + value.toString().toLowerCase()))
                                                .enumClass(ArmorVanillaStatsAdapt.class))
                                        .build())
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
        DurabilityVisibilityOptions.parentScreen = parent;
        return yaclScreen;
    }
    public static void initialize() {
        HANDLER.load();
    }

    public static ModConfig get() {
        return HANDLER.instance();
    }

    public static void save() {
        HANDLER.save();
    }
}