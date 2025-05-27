package com.danrus.durability_visibility_options.client.config;

import com.danrus.durability_visibility_options.client.DurabilityVisibilityOptions;
import com.danrus.durability_visibility_options.client.config.demo.DemoRenderer;
import com.danrus.durability_visibility_options.client.config.demo.DurabilityDemoRenderer;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

import java.awt.Color;

public class ModConfig {

//    public static boolean isOpen = false;

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

    @SerialEntry
    public boolean showDurabilityBarUnderItem = false;

    @SerialEntry
    public boolean showDurabilityBarBackground = true;

    @SerialEntry(comment = "Show durability bar when durability is below this percentage")
    public int showDurabilityBarFromPercent = 99;

    @SerialEntry
    public int durabilityBarOffsetX = 0;

    @SerialEntry
    public int durabilityBarOffsetY = 0;

    @SerialEntry(comment = "Durability bar color at maximum durability (RGB)")
    public int durabilityBarColor = 0x00FF00;

    @SerialEntry(comment = "Durability bar color at minimum durability (RGB)")
    public int durabilityBarColorMin = 0xFF0000;


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
    public float durabilityPercentScale = 0.5f;

    @SerialEntry
    public int durabilityPercentColor = 0xFFFFFF;

    @SerialEntry
    public int durabilityPercentColorMin = 0xFFFFFF;

    @SerialEntry
    public boolean doRgbBar = false;


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

    private static DurabilityDemoRenderer mainRenderer = new DurabilityDemoRenderer();

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
                                                value -> HANDLER.instance().showDurability = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().showDurability = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.is_vertical"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().isVertical,
                                                value -> HANDLER.instance().isVertical = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().isVertical = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_under_item"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().showDurabilityBarUnderItem,
                                                value -> HANDLER.instance().showDurabilityBarUnderItem = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().showDurabilityBarUnderItem = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_bar_background"))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().showDurabilityBarBackground,
                                                value -> HANDLER.instance().showDurabilityBarBackground = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().showDurabilityBarBackground = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_from_percent"))
                                        .binding(
                                                99,
                                                () -> HANDLER.instance().showDurabilityBarFromPercent,
                                                value -> HANDLER.instance().showDurabilityBarFromPercent = value
                                        )
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 99)
                                                .step(1))
                                        .addListener(((option, event) -> {
                                            ModConfig.get().showDurabilityBarFromPercent = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityBarOffsetX,
                                                value -> HANDLER.instance().durabilityBarOffsetX = value
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityBarOffsetX = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityBarOffsetY,
                                                value -> HANDLER.instance().durabilityBarOffsetY = value
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityBarOffsetY = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color_max"))
                                        .binding(
                                                new Color(0x00FF00),
                                                () -> new Color(HANDLER.instance().durabilityBarColor),
                                                value -> HANDLER.instance().durabilityBarColor = value.getRGB()
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityBarColor = option.pendingValue().getRGB();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color_min"))
                                        .binding(
                                                new Color(0xFF0000),
                                                () -> new Color(HANDLER.instance().durabilityBarColorMin),
                                                value -> HANDLER.instance().durabilityBarColorMin = value.getRGB()
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityBarColorMin = option.pendingValue().getRGB();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.percents"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().showDurabilityPercent,
                                                value -> HANDLER.instance().showDurabilityPercent = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().showDurabilityPercent = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_percent_symbol"))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().showPercentSymbol,
                                                value -> HANDLER.instance().showPercentSymbol = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().showPercentSymbol = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityPercentOffsetX,
                                                value -> HANDLER.instance().durabilityPercentOffsetX = value
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityPercentOffsetX = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().durabilityPercentOffsetY,
                                                value -> HANDLER.instance().durabilityPercentOffsetY = value
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityPercentOffsetY = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_from_percent"))
                                        .binding(
                                                99,
                                                () -> HANDLER.instance().showDurabilityPercentsFromPercent,
                                                value -> HANDLER.instance().showDurabilityPercentsFromPercent = value
                                        )
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 100)
                                                .step(1))
                                        .addListener(((option, event) -> {
                                            ModConfig.get().showDurabilityPercentsFromPercent = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.scale"))
                                        .binding(
                                                0.5f,
                                                () -> HANDLER.instance().durabilityPercentScale,
                                                value -> HANDLER.instance().durabilityPercentScale = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityPercentScale = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color_max"))
                                        .binding(
                                                new Color(0xFFFFFF),
                                                () -> new Color(HANDLER.instance().durabilityPercentColor),
                                                value -> HANDLER.instance().durabilityPercentColor = value.getRGB()
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityPercentColor = option.pendingValue().getRGB();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color_min"))
                                        .binding(
                                                new Color(0xFFFFFF),
                                                () -> new Color(HANDLER.instance().durabilityPercentColorMin),
                                                value -> HANDLER.instance().durabilityPercentColorMin = value.getRGB()
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityPercentColorMin = option.pendingValue().getRGB();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.secret"))
                                .option(Option.createBuilder(Boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.rgb_bar"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().doRgbBar,
                                                value -> HANDLER.instance().doRgbBar = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().doRgbBar = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .build())
                        .build())
//                .category(ConfigCategory.createBuilder()
//                        .name(Text.translatable("durability_visibility_options.config.category.armor_hud"))
//                        .group(OptionGroup.createBuilder()
//                                .name(Text.translatable("durability_visibility_options.tab.general"))
//                                .option(Option.createBuilder(boolean.class)
//                                        .name(Text.translatable("durability_visibility_options.config.show"))
//                                        .binding(
//                                                false,
//                                                () -> HANDLER.instance().showArmorDurabilityHud,
//                                                value -> {
//                                                    HANDLER.instance().showArmorDurabilityHud = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(TickBoxControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().showArmorDurabilityHud = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(boolean.class)
//                                        .name(Text.translatable("durability_visibility_options.config.show_percent_symbol"))
//                                        .binding(
//                                                true,
//                                                () -> HANDLER.instance().showArmorDurabilityHudPercentSymbol,
//                                                value -> {
//                                                    HANDLER.instance().showArmorDurabilityHudPercentSymbol = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(TickBoxControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().showArmorDurabilityHudPercentSymbol = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(boolean.class)
//                                        .name(Text.translatable("durability_visibility_options.config.show_in_creative"))
//                                        .binding(
//                                                true,
//                                                () -> HANDLER.instance().showArmorDurabilityHudInCreative,
//                                                value -> {
//                                                    HANDLER.instance().showArmorDurabilityHudInCreative = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(TickBoxControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().showArmorDurabilityHudInCreative = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(boolean.class)
//                                        .name(Text.translatable("durability_visibility_options.config.show_vanilla_armor_hud"))
//                                        .binding(
//                                                true,
//                                                () -> HANDLER.instance().showVanillaArmorHud,
//                                                value -> {
//                                                    HANDLER.instance().showVanillaArmorHud = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(TickBoxControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().showVanillaArmorHud = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(Color.class)
//                                        .name(Text.translatable("durability_visibility_options.config.color"))
//                                        .binding(
//                                                new Color(HANDLER.instance().armorDurabilityHudTextColor),
//                                                () -> new Color(HANDLER.instance().armorDurabilityHudTextColor),
//                                                value -> {
//                                                    HANDLER.instance().armorDurabilityHudTextColor = value.getRGB();
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(ColorControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().armorDurabilityHudTextColor = option.pendingValue().getRGB()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(int.class)
//                                        .name(Text.translatable("durability_visibility_options.config.show_from_percent"))
//                                        .binding(
//                                                100,
//                                                () -> HANDLER.instance().showArmorDurabilityHudFromPercent,
//                                                value -> {
//                                                    HANDLER.instance().showArmorDurabilityHudFromPercent = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
//                                                .range(0, 100)
//                                                .step(1))
//                                        .addListener(((option, event) -> ModConfig.get().showArmorDurabilityHudFromPercent = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .build())
//                        .group(OptionGroup.createBuilder()
//                                .name(Text.translatable("durability_visibility_options.config.category.positioning"))
//                                .option(Option.createBuilder(int.class)
//                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
//                                        .binding(
//                                                0,
//                                                () -> HANDLER.instance().armorDurabilityHudOffsetX,
//                                                value -> {
//                                                    HANDLER.instance().armorDurabilityHudOffsetX = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(IntegerFieldControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().armorDurabilityHudOffsetX = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(int.class)
//                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
//                                        .binding(
//                                                0,
//                                                () -> HANDLER.instance().armorDurabilityHudOffsetY,
//                                                value -> {
//                                                    HANDLER.instance().armorDurabilityHudOffsetY = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(IntegerFieldControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().armorDurabilityHudOffsetY = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(int.class)
//                                        .name(Text.translatable("durability_visibility_options.config.margin"))
//                                        .binding(
//                                                20,
//                                                () -> HANDLER.instance().armorDurabilityHudMirgin,
//                                                value -> {
//                                                    HANDLER.instance().armorDurabilityHudMirgin = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(IntegerFieldControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().armorDurabilityHudMirgin = option.pendingValue()))
////                                        .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer()).build())
//                                        .build())
//                                .option(Option.createBuilder(float.class)
//                                        .name(Text.translatable("durability_visibility_options.config.scale"))
//                                        .binding(
//                                                1.0f,
//                                                () -> HANDLER.instance().armorDurabilityHudScale,
//                                                value -> {
//                                                    HANDLER.instance().armorDurabilityHudScale = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(FloatFieldControllerBuilder::create)
//                                        .addListener(((option, event) -> ModConfig.get().armorDurabilityHudScale = option.pendingValue()))
//                                        .build())
//                                .build())
//                        .group(OptionGroup.createBuilder()
//                                .name(Text.translatable("durability_visibility_options.config.category.layout"))
//                                .option(Option.createBuilder(ArmorPositionHorizontal.class)
//                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.position_horizontal"))
//                                        .binding(
//                                                ArmorPositionHorizontal.LEFT,
//                                                () -> HANDLER.instance().armorHudPositionHorizontal,
//                                                value -> {
//                                                    HANDLER.instance().armorHudPositionHorizontal = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(opt -> EnumControllerBuilder.create(opt)
//                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.position_horizontal." + value.toString().toLowerCase()))
//                                                .enumClass(ArmorPositionHorizontal.class))
//                                        .addListener(((option, event) -> ModConfig.get().armorHudPositionHorizontal = option.pendingValue()))
//                                        .build())
//                                .option(Option.createBuilder(ArmorPositionVertical.class)
//                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.position_vertical"))
//                                        .binding(
//                                                ArmorPositionVertical.TOP,
//                                                () -> HANDLER.instance().armorHudPositionVertical,
//                                                value -> {
//                                                    HANDLER.instance().armorHudPositionVertical = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(opt -> EnumControllerBuilder.create(opt)
//                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.position_vertical." + value.toString().toLowerCase()))
//                                                .enumClass(ArmorPositionVertical.class))
//                                        .addListener(((option, event) -> ModConfig.get().armorHudPositionVertical = option.pendingValue()))
//                                        .build())
//                                .option(Option.createBuilder(ArmorAlignment.class)
//                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.alignment"))
//                                        .binding(
//                                                ArmorAlignment.VERTICAL,
//                                                () -> HANDLER.instance().armorHudAlignment,
//                                                value -> {
//                                                    HANDLER.instance().armorHudAlignment = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(opt -> EnumControllerBuilder.create(opt)
//                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.alignment." + value.toString().toLowerCase()))
//                                                .enumClass(ArmorAlignment.class))
//                                        .addListener(((option, event) -> ModConfig.get().armorHudAlignment = option.pendingValue()))
//                                        .build())
//                                .option(Option.createBuilder(ArmorDisplayStyle.class)
//                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.display_style"))
//                                        .binding(
//                                                ArmorDisplayStyle.ICON_PERCENT,
//                                                () -> HANDLER.instance().armorHudDisplayStyle,
//                                                value -> {
//                                                    HANDLER.instance().armorHudDisplayStyle = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(opt -> EnumControllerBuilder.create(opt)
//                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.display_style." + value.toString().toLowerCase()))
//                                                .enumClass(ArmorDisplayStyle.class))
//                                        .addListener(((option, event) -> ModConfig.get().armorHudDisplayStyle = option.pendingValue()))
//                                        .build())
//                                .build())
//                        .group(OptionGroup.createBuilder()
//                                .name(Text.translatable("durability_visibility_options.config.category.behavior"))
//                                .option(Option.createBuilder(ArmorVanillaStatsAdapt.class)
//                                        .name(Text.translatable("durability_visibility_options.config.armor_hud.armor_vanilla_stats_adapt"))
//                                        .binding(
//                                                ArmorVanillaStatsAdapt.NONE,
//                                                () -> HANDLER.instance().armorVanillaStatsAdapt,
//                                                value -> {
//                                                    HANDLER.instance().armorVanillaStatsAdapt = value;
//                                                    HANDLER.save();
//                                                }
//                                        )
//                                        .controller(opt -> EnumControllerBuilder.create(opt)
//                                                .formatValue(value -> Text.translatable("durability_visibility_options.config.armor_hud.armor_vanilla_stats_adapt." + value.toString().toLowerCase()))
//                                                .enumClass(ArmorVanillaStatsAdapt.class))
//                                        .addListener(((option, event) -> ModConfig.get().armorVanillaStatsAdapt = option.pendingValue()))
//                                        .build())
//                                .build())
//                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("durability_visibility_options.config.presets"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.presets.durability"))
                                .option(ButtonOption.createBuilder()
                                        .action((button, context) -> ModConfig.applyConfig(ConfigPresets.onlyPercentConfig, parent))
                                        .name(Text.translatable("durability_visibility_options.config.presets.only_percents"))
                                        .description(OptionDescription.createBuilder().customImage(ConfigPresets.onlyPercentRenderer).build())
                                        .build()
                                )
                                .option(ButtonOption.createBuilder()
                                        .action((button, context) -> ModConfig.applyConfig(ConfigPresets.verticalBarPlusPercentsConfig, parent))
                                        .name(Text.translatable("durability_visibility_options.config.presets.vertical_bar_plus_percents"))
                                        .description(OptionDescription.createBuilder().customImage(ConfigPresets.verticalBarPlusPercentsRender).build())
                                        .build()
                                )
                                .option(ButtonOption.createBuilder()
                                        .action((button, context) -> ModConfig.applyConfig(ConfigPresets.lowDurabilityAlertConfig, parent))
                                        .name(Text.translatable("durability_visibility_options.config.presets.low_durability_alert"))
                                        .description(OptionDescription.createBuilder().customImage(ConfigPresets.lowDurabilityAlertRenderer).build())
                                        .build()
                                )
                                .option(ButtonOption.createBuilder()
                                        .action((button, context) -> ModConfig.applyConfig(ConfigPresets.uwuConfig, parent))
                                        .name(Text.translatable("durability_visibility_options.config.presets.uwu"))
                                        .description(OptionDescription.createBuilder().customImage(ConfigPresets.uwuRenderer).build())
                                        .build()
                                )
                                .option(ButtonOption.createBuilder()
                                        .action((button, context) -> ModConfig.applyConfig(ConfigPresets.duraviewConfig, parent))
                                        .name(Text.translatable("durability_visibility_options.config.presets.duraview"))
                                        .description(OptionDescription.createBuilder().customImage(ConfigPresets.duraviewRenderer).build())
                                        .build()
                                )
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
        DurabilityVisibilityOptions.parentScreen = parent;
        return yaclScreen;
    }

    public void applyFrom(DurabilityConfig config, Screen parent) {
        ModConfig.get().showDurability = config.showDurability;
        ModConfig.get().isVertical = config.isVertical;
        ModConfig.get().showDurabilityBarUnderItem = config.showDurabilityBarUnderItem;
        ModConfig.get().showDurabilityBarBackground = config.showDurabilityBarBackground;
        ModConfig.get().showDurabilityBarFromPercent = config.showDurabilityBarFromPercent;
        ModConfig.get().durabilityBarOffsetX = config.durabilityBarOffsetX;
        ModConfig.get().durabilityBarOffsetY = config.durabilityBarOffsetY;
        ModConfig.get().durabilityBarColor = config.durabilityBarColor;
        ModConfig.get().durabilityBarColorMin = config.durabilityBarColorMin;

        ModConfig.get().showDurabilityPercent = config.showDurabilityPercent;
        ModConfig.get().showPercentSymbol = config.showPercentSymbol;
        ModConfig.get().durabilityPercentOffsetX = config.durabilityPercentOffsetX;
        ModConfig.get().durabilityPercentOffsetY = config.durabilityPercentOffsetY;
        ModConfig.get().showDurabilityPercentsFromPercent = config.showDurabilityPercentsFromPercent;
        ModConfig.get().durabilityPercentColor = config.durabilityPercentColor;
        ModConfig.get().durabilityPercentColorMin = config.durabilityPercentColorMin;
        HANDLER.save();
//        ModConfig.isOpen = false;
        MinecraftClient.getInstance().setScreen(parent);
        MinecraftClient.getInstance().getToastManager().add(SystemToast.create(MinecraftClient.getInstance(), SystemToast.Type.PERIODIC_NOTIFICATION, Text.translatable("durability_visibility_options.config.title"), Text.translatable("durability_visibility_options.config.presets.applied")));
    }

    public static void applyConfig(DurabilityConfig config, Screen parent) {
        HANDLER.instance().applyFrom(config, parent);
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