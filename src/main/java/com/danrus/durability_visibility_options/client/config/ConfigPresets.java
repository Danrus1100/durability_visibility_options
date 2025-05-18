package com.danrus.durability_visibility_options.client.config;

import com.danrus.durability_visibility_options.client.config.demo.DurabilityDemoRenderer;

public class ConfigPresets {
    public static final DurabilityConfig onlyPercentConfig = DurabilityConfig.builder()
            .setShowDurability(false)
            .setShowDurabilityPercent(true)
            .build();

    public static final DurabilityDemoRenderer onlyPercentRenderer = new DurabilityDemoRenderer(onlyPercentConfig);


    public static final DurabilityConfig verticalBarPlusPercentsConfig = DurabilityConfig.builder()
            .setVertical(true)
            .setShowDurabilityPercent(true)
            .setDurabilityPercentOffsetX(9)
            .build();

    public static final DurabilityDemoRenderer verticalBarPlusPercentsRender = new DurabilityDemoRenderer(verticalBarPlusPercentsConfig);


    public static final DurabilityConfig lowDurabilityAlertConfig = DurabilityConfig.builder()
            .setShowDurability(false)
            .setShowDurabilityPercent(true)
            .setShowDurabilityPercentsFromPercent(25)
            .setDurabilityPercentOffsetY(6)
            .setDurabilityPercentColor(0xFF0000)
            .setDurabilityPercentColorMin(0xFF0000)
            .build();

    public static final DurabilityDemoRenderer lowDurabilityAlertRenderer = new DurabilityDemoRenderer(lowDurabilityAlertConfig);

    public static final DurabilityConfig uwuConfig = DurabilityConfig.builder()
            .setDurabilityBarColor(0xE268FF)
            .setDurabilityBarColorMin(0xFF9D9D)
            .build();

    public static final DurabilityDemoRenderer uwuRenderer = new DurabilityDemoRenderer(uwuConfig);

    public static final DurabilityConfig duraviewConfig = DurabilityConfig.builder()
            .setShowDurability(false)
            .setShowDurabilityPercent(true)
            .setDurabilityPercentOffsetX(5)
            .setDurabilityPercentColor(0x00FF00)
            .setDurabilityPercentColorMin(0xFF0000)
            .build();

    public static final DurabilityDemoRenderer duraviewRenderer = new DurabilityDemoRenderer(duraviewConfig);
}
