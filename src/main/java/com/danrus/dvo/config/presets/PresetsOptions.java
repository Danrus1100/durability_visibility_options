package com.danrus.dvo.config.presets;

import com.danrus.dvo.config.DurabilityConfig;
import com.danrus.dvo.config.ModConfig;
import com.danrus.dvo.config.demo.DemoRendererImpl;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.util.Util;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.danrus.dvo.config.ModConfig.HANDLER;

public class PresetsOptions {
    public static List<? extends Option<?>> generateDefaults(List<DurabilityConfig> presets, Screen parent) {
        return presets.stream()
                .map(
                        config -> ButtonOption.createBuilder()
                                .action((button, conComponent) -> ModConfig.applyConfig(config, parent))
                                .name(Component.translatable("durability_visibility_options.config.presets." + config.key))
                                .description(OptionDescription.createBuilder().customImage(new DemoRendererImpl(config)).build())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static List<? extends Option<?>> generateCustom(List<DurabilityConfig> presets, Screen parent) {
        return presets.stream()
                .map(
                        config -> ButtonOption.createBuilder()
                                .action((button, conComponent) -> ModConfig.applyConfig(config, parent))
                                .name(Component.literal(config.name))
                                .description(OptionDescription.createBuilder().customImage(new DemoRendererImpl(config)).build())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static List<OptionGroup> generatePresetsGroups(Screen parent) {
        List<OptionGroup> list = new java.util.ArrayList<>(List.of(
                OptionGroup.createBuilder()
                        .name(Component.translatable("durability_visibility_options.config.presets.create"))
                        .option(Option.createBuilder(String.class)
                                .name(Component.translatable("durability_visibility_options.config.presets.name"))
                                .controller(StringControllerBuilder::create)
                                .binding("", () -> HANDLER.instance().presetsName, value -> HANDLER.instance().presetsName = value)
                                .description(OptionDescription.createBuilder().customImage(ModConfig.mainRenderer).build())
                                .addListener(((option, event) -> ModConfig.get().presetsName = option.pendingValue()))
                                .build())
                        .option(ButtonOption.createBuilder()
                                .action((screen -> {
                                    if (HANDLER.instance().presetsName.isEmpty()) {
                                        ModConfig.sendToast(SystemToast.SystemToastId.WORLD_ACCESS_FAILURE, "durability_visibility_options.config.presets.needs_name");
                                        return;
                                    }
                                    ConfigPresets.saveCustom(DurabilityConfig.builder()
                                            .fromModConfig()
                                            .setName(ModConfig.get().presetsName)
                                            .setKey(ModConfig.get().presetsName.toLowerCase(Locale.ROOT).replace(" ", "_"))
                                            .build());
                                    screen.onClose();
                                    ModConfig.get().presetsName = "";
                                    ModConfig.sendToast(SystemToast.SystemToastId.PERIODIC_NOTIFICATION, "durability_visibility_options.config.presets.custom_added");
                                }))
                                .name(Component.translatable("durability_visibility_options.config.presets.create"))
                                .description(OptionDescription.createBuilder().customImage(ModConfig.mainRenderer).build())
                                .build())
                        .option(ButtonOption.createBuilder()
                                .action((button, conComponent) -> Util.getPlatform().openFile(ConfigPresets.PRESETS_DIR.toFile()))
                                .name(Component.translatable("durability_visibility_options.config.presets.open_folder"))
                                .text(Component.translatable("durability_visibility_options.config.presets.open_folder.open"))
                                .build())
                        .build(),
                OptionGroup.createBuilder()
                        .name(Component.translatable("durability_visibility_options.config.presets.durability"))
                        .options(PresetsOptions.generateDefaults(ConfigPresets.DEFAULT_CONFIGS, parent))
                        .build()
        ));

        if (!ConfigPresets.getAllPresetKeys().isEmpty()) {
            list.add(
                    OptionGroup.createBuilder()
                            .name(Component.translatable("durability_visibility_options.config.presets.custom"))
                            .options(PresetsOptions.generateCustom(ConfigPresets.getAllPresetKeys().stream()
                                            .map(ConfigPresets::loadCustom)
                                            .collect(Collectors.toList()),
                                    parent
                            ))
                            .build()
            );
        }

        return list;
    }

}
