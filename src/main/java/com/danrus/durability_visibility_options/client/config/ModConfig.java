package com.danrus.durability_visibility_options.client.config;

import com.danrus.durability_visibility_options.client.config.demo.DemoRendererImpl;
import com.danrus.durability_visibility_options.client.config.presets.PresetsOptions;
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
    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("durability_visibility_options.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public float zLayer = 200.0F;

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
    public float durabilityBarScale = 1.0F;

    @SerialEntry
    public float durabilityBarOffsetX = 0;

    @SerialEntry
    public float durabilityBarOffsetY = 0;

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
    public int percentAccuracy = 0;

    @SerialEntry
    public float durabilityPercentOffsetX = 0;

    @SerialEntry
    public float durabilityPercentOffsetY = 0;

    @SerialEntry
    public int showDurabilityPercentsFromPercent = 99;

    @SerialEntry
    public float durabilityPercentScale = 0.5f;

    @SerialEntry
    public int durabilityPercentColor = 0xFFFFFF;

    @SerialEntry
    public int durabilityPercentColorMin = 0xFFFFFF;


    // Durability Counter
    @SerialEntry
    public boolean showDurabilityAmount = false;

    @SerialEntry
    public float durabilityAmountOffsetX = 0;

    @SerialEntry
    public float durabilityAmountOffsetY = 0;

    @SerialEntry
    public int showDurabilityAmountsFromPercent = 99;

    @SerialEntry
    public float durabilityAmountScale = 0.5f;

    @SerialEntry
    public int durabilityAmountColor = 0xFFFFFF;

    @SerialEntry
    public int durabilityAmountColorMin = 0xFFFFFF;


    @SerialEntry
    public boolean doRgbBar = false;

    @SerialEntry
    public String presetsName = "";

    public static DemoRendererImpl mainRenderer = new DemoRendererImpl();

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
                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.scale"))
                                        .binding(
                                                1f,
                                                () -> HANDLER.instance().durabilityBarScale,
                                                value -> HANDLER.instance().durabilityBarScale = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityBarScale = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0F,
                                                () -> HANDLER.instance().durabilityBarOffsetX,
                                                value -> HANDLER.instance().durabilityBarOffsetX = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityBarOffsetX = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0F,
                                                () -> HANDLER.instance().durabilityBarOffsetY,
                                                value -> HANDLER.instance().durabilityBarOffsetY = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
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
                                        .name(Text.translatable("durability_visibility_options.config.percent_accuracy"))
                                        .binding(
                                                0,
                                                () -> HANDLER.instance().percentAccuracy,
                                                value -> HANDLER.instance().percentAccuracy = value
                                        )
                                        .customController(opt -> IntegerFieldControllerBuilder.create(opt)
                                                .max(10)
                                                .min(0)
                                                .build())
                                        .addListener(((option, event) -> {
                                            ModConfig.get().percentAccuracy = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0F,
                                                () -> HANDLER.instance().durabilityPercentOffsetX,
                                                value -> HANDLER.instance().durabilityPercentOffsetX = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .addListener(((option, event) -> {
                                            ModConfig.get().durabilityPercentOffsetX = option.pendingValue();
                                            HANDLER.save();
                                        }))
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0F,
                                                () -> HANDLER.instance().durabilityPercentOffsetY,
                                                value -> HANDLER.instance().durabilityPercentOffsetY = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
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
                                .name(Text.translatable("durability_visibility_options.config.category.amount"))

                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("durability_visibility_options.config.show"))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().showDurabilityAmount,
                                                value -> HANDLER.instance().showDurabilityAmount = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .addListener((option, event) -> {
                                            ModConfig.get().showDurabilityAmount = option.pendingValue();
                                            HANDLER.save();
                                        })
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.horizontal_offset"))
                                        .binding(
                                                0F,
                                                () -> HANDLER.instance().durabilityAmountOffsetX,
                                                value -> HANDLER.instance().durabilityAmountOffsetX = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .addListener((option, event) -> {
                                            ModConfig.get().durabilityAmountOffsetX = option.pendingValue();
                                            HANDLER.save();
                                        })
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.vertical_offset"))
                                        .binding(
                                                0F,
                                                () -> HANDLER.instance().durabilityAmountOffsetY,
                                                value -> HANDLER.instance().durabilityAmountOffsetY = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .addListener((option, event) -> {
                                            ModConfig.get().durabilityAmountOffsetY = option.pendingValue();
                                            HANDLER.save();
                                        })
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(int.class)
                                        .name(Text.translatable("durability_visibility_options.config.show_from_percent"))
                                        .binding(
                                                99,
                                                () -> HANDLER.instance().showDurabilityAmountsFromPercent,
                                                value -> HANDLER.instance().showDurabilityAmountsFromPercent = value
                                        )
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 100).step(1))
                                        .addListener((option, event) -> {
                                            ModConfig.get().showDurabilityAmountsFromPercent = option.pendingValue();
                                            HANDLER.save();
                                        })
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(float.class)
                                        .name(Text.translatable("durability_visibility_options.config.scale"))
                                        .binding(
                                                0.5f,
                                                () -> HANDLER.instance().durabilityAmountScale,
                                                value -> HANDLER.instance().durabilityAmountScale = value
                                        )
                                        .controller(FloatFieldControllerBuilder::create)
                                        .addListener((option, event) -> {
                                            ModConfig.get().durabilityAmountScale = option.pendingValue();
                                            HANDLER.save();
                                        })
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color_max"))
                                        .binding(
                                                new Color(0xFFFFFF),
                                                () -> new Color(HANDLER.instance().durabilityAmountColor),
                                                value -> HANDLER.instance().durabilityAmountColor = value.getRGB()
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .addListener((option, event) -> {
                                            ModConfig.get().durabilityAmountColor = option.pendingValue().getRGB();
                                            HANDLER.save();
                                        })
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .option(Option.createBuilder(Color.class)
                                        .name(Text.translatable("durability_visibility_options.config.color_min"))
                                        .binding(
                                                new Color(0xFFFFFF),
                                                () -> new Color(HANDLER.instance().durabilityAmountColorMin),
                                                value -> HANDLER.instance().durabilityAmountColorMin = value.getRGB()
                                        )
                                        .controller(ColorControllerBuilder::create)
                                        .addListener((option, event) -> {
                                            ModConfig.get().durabilityAmountColorMin = option.pendingValue().getRGB();
                                            HANDLER.save();
                                        })
                                        .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                        .build())

                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("durability_visibility_options.config.category.secret"))
                                .collapsed(true)
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
                                .option(
                                        Option.createBuilder(float.class)
                                                .name(Text.translatable("durability_visibility_options.config.z_layer"))
                                                .binding(
                                                        1f,
                                                        () -> HANDLER.instance().zLayer,
                                                        value -> HANDLER.instance().zLayer = value
                                                )
                                                .controller(FloatFieldControllerBuilder::create)
                                                .addListener(((option, event) -> {
                                                    ModConfig.get().zLayer = option.pendingValue();
                                                    HANDLER.save();
                                                }))
                                                .description(OptionDescription.createBuilder().customImage(mainRenderer).build())
                                                .build()
                                )
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("durability_visibility_options.config.presets"))
                        .groups(PresetsOptions.generatePresetsGroups(parent))
                        .build())
                .build()
                .generateScreen(parent);
        return yaclScreen;
    }

    public void applyFrom(DurabilityConfig config, Screen parent) {
        ModConfig.get().zLayer = config.zLayer;

        ModConfig.get().showDurability = config.showDurability;
        ModConfig.get().isVertical = config.isVertical;
        ModConfig.get().showDurabilityBarUnderItem = config.showDurabilityBarUnderItem;
        ModConfig.get().showDurabilityBarBackground = config.showDurabilityBarBackground;
        ModConfig.get().showDurabilityBarFromPercent = config.showDurabilityBarFromPercent;
        ModConfig.get().durabilityBarScale = config.durabilityBarScale;
        ModConfig.get().durabilityBarOffsetX = config.durabilityBarOffsetX;
        ModConfig.get().durabilityBarOffsetY = config.durabilityBarOffsetY;
        ModConfig.get().durabilityBarColor = config.durabilityBarColor;
        ModConfig.get().durabilityBarColorMin = config.durabilityBarColorMin;

        ModConfig.get().showDurabilityPercent = config.showDurabilityPercent;
        ModConfig.get().showPercentSymbol = config.showPercentSymbol;
        ModConfig.get().durabilityPercentScale = config.durabilityPercentScale;
        ModConfig.get().durabilityPercentOffsetX = config.durabilityPercentOffsetX;
        ModConfig.get().durabilityPercentOffsetY = config.durabilityPercentOffsetY;
        ModConfig.get().showDurabilityPercentsFromPercent = config.showDurabilityPercentsFromPercent;
        ModConfig.get().durabilityPercentColor = config.durabilityPercentColor;
        ModConfig.get().durabilityPercentColorMin = config.durabilityPercentColorMin;

        ModConfig.get().showDurabilityAmount = config.showDurabilityAmount;
        ModConfig.get().durabilityAmountOffsetX = config.durabilityAmountOffsetX;
        ModConfig.get().durabilityAmountOffsetY = config.durabilityAmountOffsetY;
        ModConfig.get().showDurabilityAmountsFromPercent = config.showDurabilityAmountsFromPercent;
        ModConfig.get().durabilityAmountScale = config.durabilityAmountScale;
        ModConfig.get().durabilityAmountColor = config.durabilityAmountColor;
        ModConfig.get().durabilityAmountColorMin = config.durabilityAmountColorMin;

        ModConfig.get().doRgbBar = config.doRgbBar;

        HANDLER.save();
        MinecraftClient.getInstance().setScreen(parent);
        sendToast(SystemToast.Type.PERIODIC_NOTIFICATION, "durability_visibility_options.config.presets.applied");
    }

    public static void sendToast(SystemToast.Type type, String description) {
        MinecraftClient.getInstance().getToastManager().add(SystemToast.create(MinecraftClient.getInstance(), type, Text.translatable("durability_visibility_options.config.title"), Text.translatable(description)));
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