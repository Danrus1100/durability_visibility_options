package com.danrus.durability_visibility_options.client.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.Color;

public class ModConfig {

    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
//            .id(new Identifier("durability_visibility_options", "config"))
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

    @SerialEntry(comment = "Durability bar color at minimum durability (RGB)")
    public int durabilityBarColor2 = 0xFF0000;

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

    public static Screen getConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("Durability Visibility Options"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("General"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Durability Bar"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.of("Show Durability"))
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
                                        .name(Text.of("Is Vertical"))
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
                                        .name(Text.of("Show From Percent"))
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
                                        .name(Text.of("Horizontal Offset"))
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
                                        .name(Text.of("Vertical Offset"))
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
                                        .name(Text.of("Bar Color (Max)"))
                                        .binding(
                                                new Color(HANDLER.instance().durabilityBarColor),
                                                () -> new Color(HANDLER.instance().durabilityBarColor),
                                                value -> {
                                                    HANDLER.instance().durabilityBarColor = value.getRGB();
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(Color.class)
                                        .name(Text.of("Bar Color (Min)"))
                                        .binding(
                                                new Color(HANDLER.instance().durabilityBarColor2),
                                                () -> new Color(HANDLER.instance().durabilityBarColor2),
                                                value -> {
                                                    HANDLER.instance().durabilityBarColor2 = value.getRGB();
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Durability Percent"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.of("Show Durability Percent"))
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
                                        .name(Text.of("Show Percent Symbol"))
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
                                        .name(Text.of("Horizontal Offset"))
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
                                        .name(Text.of("Vertical Offset"))
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
                                        .name(Text.of("Show From Percent"))
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
                                        .name(Text.of("Text Color"))
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
                        .name(Text.of("Armor HUD"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("General"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.of("Show Armor Durability HUD"))
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
                                        .name(Text.of("Show Percent Symbol"))
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
                                        .name(Text.of("Show in Creative"))
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
                                        .name(Text.of("Show Vanilla Armor HUD"))
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
                                        .name(Text.of("Text Color"))
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
                                        .name(Text.of("Show From Percent"))
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
                                .name(Text.of("Positioning"))
                                .option(Option.createBuilder(int.class)
                                        .name(Text.of("Horizontal Offset"))
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
                                        .name(Text.of("Vertical Offset"))
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
                                        .name(Text.of("Margin"))
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
                                        .name(Text.of("Scale"))
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
                                .name(Text.of("Layout"))
                                .option(Option.createBuilder(ArmorPositionHorizontal.class)
                                        .name(Text.of("Horizontal Position"))
                                        .binding(
                                                ArmorPositionHorizontal.LEFT,
                                                () -> HANDLER.instance().armorHudPositionHorizontal,
                                                value -> {
                                                    HANDLER.instance().armorHudPositionHorizontal = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(ArmorPositionHorizontal.class))
                                        .build())
                                .option(Option.createBuilder(ArmorPositionVertical.class)
                                        .name(Text.of("Vertical Position"))
                                        .binding(
                                                ArmorPositionVertical.TOP,
                                                () -> HANDLER.instance().armorHudPositionVertical,
                                                value -> {
                                                    HANDLER.instance().armorHudPositionVertical = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(ArmorPositionVertical.class))
                                        .build())
                                .option(Option.createBuilder(ArmorAlignment.class)
                                        .name(Text.of("Alignment"))
                                        .binding(
                                                ArmorAlignment.VERTICAL,
                                                () -> HANDLER.instance().armorHudAlignment,
                                                value -> {
                                                    HANDLER.instance().armorHudAlignment = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(ArmorAlignment.class))
                                        .build())
                                .option(Option.createBuilder(ArmorDisplayStyle.class)
                                        .name(Text.of("Display Style"))
                                        .binding(
                                                ArmorDisplayStyle.ICON_PERCENT,
                                                () -> HANDLER.instance().armorHudDisplayStyle,
                                                value -> {
                                                    HANDLER.instance().armorHudDisplayStyle = value;
                                                    HANDLER.save();
                                                }
                                        )
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(ArmorDisplayStyle.class))
                                        .build())
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }

    /**
     * Инициализирует конфигурацию.
     * Вызывайте этот метод при запуске мода.
     */
    public static void initialize() {
        HANDLER.load();
    }

    /**
     * Возвращает экземпляр конфигурации.
     * Используйте этот метод для доступа к настройкам.
     *
     * @return Текущая конфигурация
     */
    public static ModConfig get() {
        return HANDLER.instance();
    }

    /**
     * Сохраняет конфигурацию.
     * Можно вызвать в любое время для принудительного сохранения настроек.
     */
    public static void save() {
        HANDLER.save();
    }
}