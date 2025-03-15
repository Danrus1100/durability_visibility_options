package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class DurabilityVisibilityOptions implements ClientModInitializer {

    public static Screen parentScreen;

    @Override
    public void onInitializeClient() {
        ModConfig.initialize();
        HudRenderCallback.EVENT.register(new ArmorHudRender());

    }
}
