package com.danrus.durability_visibility_options.client.config.presets;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import com.danrus.durability_visibility_options.client.config.demo.DurabilityDemoRenderer;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.danrus.durability_visibility_options.client.config.ModConfig.HANDLER;

public class PresetsOptions {
    public static List<? extends Option<?>> generateDefaults(List<DurabilityConfig> presets, Screen parent) {
        return presets.stream()
                .map(
                        config -> ButtonOption.createBuilder()
                                .action((button, context) -> ModConfig.applyConfig(config, parent))
                                .name(Text.translatable("durability_visibility_options.config.presets." + config.key))
                                .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer(config)).build())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static List<? extends Option<?>> generateCustom(List<DurabilityConfig> presets, Screen parent) {
        return presets.stream()
                .map(
                        config -> ButtonOption.createBuilder()
                                .action((button, context) -> ModConfig.applyConfig(config, parent))
                                .name(Text.literal(config.name))
                                .description(OptionDescription.createBuilder().customImage(new DurabilityDemoRenderer(config)).build())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static List<OptionGroup> generatePresetsGroups(Screen parent) {
        List<OptionGroup> list = new java.util.ArrayList<>(List.of(
                OptionGroup.createBuilder()
                        .name(Text.translatable("durability_visibility_options.config.presets.create"))
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("durability_visibility_options.config.presets.name"))
                                .controller(StringControllerBuilder::create)
                                .binding("", () -> HANDLER.instance().presetsName, value -> HANDLER.instance().presetsName = value)
                                .description(OptionDescription.createBuilder().customImage(ModConfig.mainRenderer).build())
                                .addListener(((option, event) -> ModConfig.get().presetsName = option.pendingValue()))
                                .build())
                        .option(ButtonOption.createBuilder()
                                .action((screen -> {
                                    if (HANDLER.instance().presetsName.isEmpty()) {
                                        ModConfig.sendToast(SystemToast.Type.WORLD_ACCESS_FAILURE, "durability_visibility_options.config.presets.needs_name");
                                        return;
                                    }
                                    ConfigPresets.saveCustom(DurabilityConfig.builder()
                                            .fromModConfig()
                                            .setName(ModConfig.get().presetsName)
                                            .setKey(ModConfig.get().presetsName.toLowerCase(Locale.ROOT).replace(" ", "_"))
                                            .build());
                                    screen.close();
                                    ModConfig.get().presetsName = "";
                                    ModConfig.sendToast(SystemToast.Type.PERIODIC_NOTIFICATION, "durability_visibility_options.config.presets.custom_added");
                                }))
                                .name(Text.translatable("durability_visibility_options.config.presets.create"))
                                .description(OptionDescription.createBuilder().customImage(ModConfig.mainRenderer).build())
                                .build())
                        .option(ButtonOption.createBuilder()
                                .action((button, context) -> Util.getOperatingSystem().open(ConfigPresets.PRESETS_DIR.toFile()))
                                .name(Text.translatable("durability_visibility_options.config.presets.open_folder"))
                                .text(Text.translatable("durability_visibility_options.config.presets.open_folder.open"))
                                .build())
                        .build(),
                OptionGroup.createBuilder()
                        .name(Text.translatable("durability_visibility_options.config.presets.durability"))
                        .options(PresetsOptions.generateDefaults(ConfigPresets.DEFAULT_CONFIGS, parent))
                        .build()
        ));

        if (!ConfigPresets.getAllPresetKeys().isEmpty()) {
            list.add(
                    OptionGroup.createBuilder()
                            .name(Text.translatable("durability_visibility_options.config.presets.custom"))
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
