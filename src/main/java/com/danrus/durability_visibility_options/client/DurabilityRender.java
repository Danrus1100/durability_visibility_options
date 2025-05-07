package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;

public class DurabilityRender {

    public static void renderBar(DrawContext drawContext, int percents, int itemBarStep, int x, int y) {
        if (ModConfig.get().showDurabilityBarFromPercent <= percents || !ModConfig.get().showDurability) {
            return;
        }
        int i = x + 2;
        int j = y + 13;
        if (ModConfig.get().isVertical) {
            i += ModConfig.get().durabilityBarOffsetX - 2;
            j -= ModConfig.get().durabilityBarOffsetY + 11;
            drawContext.fill(RenderLayer.getGui(), i, j, i + 2, j + 13, 200, -16777216);
            drawContext.fill(RenderLayer.getGui(),i+1, j+13, i, j + 13 - itemBarStep, 200, ModConfig.get().durabilityBarColor | 0xFF000000);
        } else {
            i += ModConfig.get().durabilityBarOffsetX;
            j -= ModConfig.get().durabilityBarOffsetY;
            drawContext.fill(RenderLayer.getGui(), i, j, i + 13, j + 2, 200, -16777216);
            drawContext.fill(RenderLayer.getGui(), i, j, i + itemBarStep, j + 1, 200, ModConfig.get().durabilityBarColor | 0xFF000000);
        }
    }

    public static void renderPercents(DrawContext drawContext, int percents, int x, int y) {
        if (ModConfig.get().showDurabilityPercent) {
            if (ModConfig.get().showDurabilityPercentsFromPercent >= percents) {
                String durability = String.valueOf(percents);
                if (ModConfig.get().showPercentSymbol) {
                    durability += "%";
                }
                drawContext.getMatrices().push();
                drawContext.getMatrices().scale(0.5F, 0.5F, 0.5F);
                drawContext.getMatrices().translate(x, y, 500.0F);
                TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                drawContext.drawCenteredTextWithShadow(textRenderer, durability, x + 17 + ModConfig.get().durabilityPercentOffsetX, y + 23 - ModConfig.get().durabilityPercentOffsetY, ModConfig.get().durabilityPercentColor);
                drawContext.getMatrices().pop();
            }
        }
    }
}
