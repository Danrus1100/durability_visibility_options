package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class DurabilityRender {

    public static void renderBar(DrawContext drawContext, ItemStack stack, int x, int y) {
        DurabilityData data = new DurabilityData(stack);
        renderBar(drawContext, data, x, y);
    }

    public static void renderBar(DrawContext drawContext, DurabilityData data, int x, int y) {
        renderBar(drawContext, data, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderBar(DrawContext drawContext, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (config.showDurabilityBarFromPercent <= data.getPercentsInt()) {
            return;
        }
        int targetX = x + 2;
        int targetY = y + 13;

        if (config.isVertical) {
            targetX += config.durabilityBarOffsetX - 2;
            targetY -= config.durabilityBarOffsetY + 11;
        } else {
            targetX += config.durabilityBarOffsetX;
            targetY -= config.durabilityBarOffsetY;
        }
        DrawUtils.pushMatrix(drawContext);

        DrawUtils.translateMatrix(drawContext, targetX, targetY);
        DrawUtils.scaleMatrix(drawContext, config.durabilityBarScale, config.durabilityBarScale);

        DrawUtils.drawBar(drawContext, config, data, 0, 0);

        DrawUtils.popMatrix(drawContext);
    }

    public static void renderPercents(DrawContext drawContext, ItemStack stack, int x, int y) {
        DurabilityData data = new DurabilityData(stack);
        renderPercents(drawContext, data, x, y);
    }

    public static void renderPercents(DrawContext drawContext, DurabilityData data, int x, int y) {
        renderPercents(drawContext, data, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderPercents(DrawContext drawContext, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (config.showDurabilityPercentsFromPercent >= data.getPercentsInt()) {
            int color = DrawUtils.interpolateColorHSV(config.durabilityPercentColorMin, config.durabilityPercentColor, data.getPercentsInt());
            DrawUtils.drawTextInfo(drawContext, config, color, data.getPercentString(config), x + config.durabilityPercentOffsetX, y - config.durabilityPercentOffsetY, config.durabilityPercentScale);
        }
    }

    public static void renderAmount(DrawContext drawContext, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (config.showDurabilityAmountsFromPercent >= data.getPercentsInt()) {
            int color = DrawUtils.interpolateColorHSV(config.durabilityAmountColorMin, config.durabilityAmountColor, data.getPercentsInt());
            DrawUtils.drawTextInfo(drawContext, config, color, String.valueOf(data.durability), x + config.durabilityAmountOffsetX, y - config.durabilityAmountOffsetY, config.durabilityAmountScale);
        }
    }
}
