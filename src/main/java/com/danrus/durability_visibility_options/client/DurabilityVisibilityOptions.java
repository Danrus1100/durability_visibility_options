package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import com.danrus.durability_visibility_options.client.config.presets.ConfigPresets;
import net.fabricmc.api.ClientModInitializer;

public class DurabilityVisibilityOptions implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigPresets.initialize();
        ModConfig.initialize();

    }
}
