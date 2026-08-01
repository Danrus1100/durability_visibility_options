package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.config.DurabilityConfig.DisplayStyle;
import com.danrus.durability_visibility_options.client.utils.DrawUtils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class DurabilityRender {

    public static void renderBar(GuiGraphics drawContext, ItemStack stack, int x, int y) {
        DurabilityData data = new DurabilityData(stack);
        renderBar(drawContext, data, x, y);
    }

    public static void renderBar(GuiGraphics drawContext, DurabilityData data, int x, int y) {
        renderBar(drawContext, data, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderBar(GuiGraphics drawContext, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (!shouldDraw(config.durabilityBarDisplayStyle, data, config.showDurabilityBarFromPercent)) {
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

    public static void renderPercents(GuiGraphics drawContext, ItemStack stack, int x, int y) {
        DurabilityData data = new DurabilityData(stack);
        renderPercents(drawContext, data, x, y);
    }

    public static void renderPercents(GuiGraphics drawContext, DurabilityData data, int x, int y) {
        renderPercents(drawContext, data, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderPercents(GuiGraphics drawContext, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (!shouldDraw(config.durabilityPercentDisplayStyle, data, config.showDurabilityPercentsFromPercent)) {
            return;
        }

        int color = DrawUtils.interpolateColorHSV(config.durabilityPercentColorMin, config.durabilityPercentColor, data.getPercentsInt());
        DrawUtils.drawTextInfo(drawContext, config, color, data.getPercentString(config), x + config.durabilityPercentOffsetX, y - config.durabilityPercentOffsetY, config.durabilityPercentScale);
    }

    public static void renderAmount(GuiGraphics drawContext, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (!shouldDraw(config.durabilityAmountDisplayStyle, data, config.showDurabilityAmountsFromPercent)) {
            return;
        }

        int color = DrawUtils.interpolateColorHSV(config.durabilityAmountColorMin, config.durabilityAmountColor, data.getPercentsInt());
        DrawUtils.drawTextInfo(drawContext, config, color, String.valueOf(data.durability), x + config.durabilityAmountOffsetX, y - config.durabilityAmountOffsetY, config.durabilityAmountScale);
    }

    private static boolean shouldDraw(
        DisplayStyle style,
        DurabilityData data,
        int percentThreshold
    ) {
        return switch (style) {
            case ALWAYS -> true;
            case IF_DAMAGED -> data.isDamaged;
            case FROM_PERCENT -> data.getPercentsInt() <= percentThreshold;
            default -> throw new IllegalStateException(String.format("Unknown display style '%s'", style.name()));
        };
    }
}
