package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class DurabilityVisibilityOptions implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModConfig.initialize();
        HudRenderCallback.EVENT.register(new ArmorHudRender());
    }
}
