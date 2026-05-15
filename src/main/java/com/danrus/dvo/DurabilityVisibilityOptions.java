package com.danrus.dvo;

import com.danrus.dvo.config.ModConfig;
import com.danrus.dvo.config.presets.ConfigPresets;
import net.fabricmc.api.ClientModInitializer;

public class DurabilityVisibilityOptions implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigPresets.initialize();
        ModConfig.initialize();

    }
}
