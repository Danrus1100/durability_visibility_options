package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;

public class Durability_visibility_optionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModConfig.load();
    }
}
