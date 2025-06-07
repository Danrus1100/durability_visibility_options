package com.danrus.durability_visibility_options.client.config.presets;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import com.danrus.durability_visibility_options.client.config.demo.DurabilityDemoRenderer;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;
import java.util.stream.Collectors;

public class PresetsOptions {
    public static List<? extends Option<?>> generate(List<DurabilityConfig> presets, Screen parent) {
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
}
