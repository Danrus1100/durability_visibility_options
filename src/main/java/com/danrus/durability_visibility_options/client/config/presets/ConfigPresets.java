package com.danrus.durability_visibility_options.client.config.presets;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;

import java.util.List;

public class ConfigPresets {
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

    public DurabilityConfig loadCustom(String filename) {
        return new DurabilityConfig(); //TODO: Implement
    }

    public void saveCustom(DurabilityConfig config){
        return; //TODO: Implement
    }
}
