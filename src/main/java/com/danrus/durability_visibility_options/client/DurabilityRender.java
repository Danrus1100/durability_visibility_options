package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;

public class DurabilityRender {
    public static void renderBar(DrawContext drawContext, int percents, int itemBarStep, int x, int y) {
        if (ModConfig.showDurabilityBarFromPercent <= percents) {
            return;
        }
        int i = x + 2;
        int j = y + 13;
        if (ModConfig.isVertical) {
            i += ModConfig.durabilityBarOffsetX - 2;
            j -= ModConfig.durabilityBarOffsetY + 11;
            drawContext.fill(RenderLayer.getGui(), i, j, i + 2, j + 13, 200, -16777216);
            drawContext.fill(RenderLayer.getGui(),i+1, j+13, i, j + 13 - itemBarStep, 200, ModConfig.durabilityBarColor | 0xFF000000);
        } else {
            i += ModConfig.durabilityBarOffsetX;
            j -= ModConfig.durabilityBarOffsetY;
            drawContext.fill(RenderLayer.getGui(), i, j, i + 13, j + 2, 200, -16777216);
            drawContext.fill(RenderLayer.getGui(), i, j, i + itemBarStep, j + 1, 200, ModConfig.durabilityBarColor | 0xFF000000);
        }
    }

    public static void renderPercents(DrawContext drawContext, int percents, int x, int y) {
        if (ModConfig.showDurabilityPercent) {
            if (ModConfig.showDurabilityPercentsFromPercent >= percents) {
                String durability = String.valueOf(percents);
                if (ModConfig.showPercentSymbol) {
                    durability += "%";
                }
                TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                drawContext.drawCenteredTextWithShadow(textRenderer, durability, x + 17 + ModConfig.durabilityPercentOffsetX, y + 23 - ModConfig.durabilityPercentOffsetY, ModConfig.durabilityPercentColor);
            }
        }
    }
}
