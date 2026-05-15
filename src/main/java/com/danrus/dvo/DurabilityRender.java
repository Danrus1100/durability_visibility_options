package com.danrus.dvo;

import com.danrus.dvo.config.DurabilityConfig;
import com.danrus.dvo.utils.DrawUtils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

public class DurabilityRender {

    public static void renderBar(GuiGraphicsExtractor guiGraphics, ItemStack stack, int x, int y) {
        DurabilityData data = new DurabilityData(stack);
        renderBar(guiGraphics, data, x, y);
    }

    public static void renderBar(GuiGraphicsExtractor guiGraphics, DurabilityData data, int x, int y) {
        renderBar(guiGraphics, data, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderBar(GuiGraphicsExtractor guiGraphics, DurabilityData data, int x, int y, DurabilityConfig config) {
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
        guiGraphics.pose().pushMatrix();

        guiGraphics.pose().translate(targetX, targetY);
        guiGraphics.pose().scale(config.durabilityBarScale);

        DrawUtils.drawBar(guiGraphics, config, data, 0, 0);

        guiGraphics.pose().popMatrix();
    }

    public static void renderPercents(GuiGraphicsExtractor GuiGraphicsExtractor, ItemStack stack, int x, int y) {
        DurabilityData data = new DurabilityData(stack);
        renderPercents(GuiGraphicsExtractor, data, x, y);
    }

    public static void renderPercents(GuiGraphicsExtractor GuiGraphicsExtractor, DurabilityData data, int x, int y) {
        renderPercents(GuiGraphicsExtractor, data, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderPercents(GuiGraphicsExtractor GuiGraphicsExtractor, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (config.showDurabilityPercentsFromPercent >= data.getPercentsInt()) {
            int color = DrawUtils.interpolateColorHSV(config.durabilityPercentColorMin, config.durabilityPercentColor, data.getPercentsInt());
            DrawUtils.drawTextInfo(GuiGraphicsExtractor, config, color, data.getPercentString(config), x + config.durabilityPercentOffsetX, y - config.durabilityPercentOffsetY, config.durabilityPercentScale);
        }
    }

    public static void renderAmount(GuiGraphicsExtractor GuiGraphicsExtractor, DurabilityData data, int x, int y, DurabilityConfig config) {
        if (config.showDurabilityAmountsFromPercent >= data.getPercentsInt()) {
            int color = DrawUtils.interpolateColorHSV(config.durabilityAmountColorMin, config.durabilityAmountColor, data.getPercentsInt());
            DrawUtils.drawTextInfo(GuiGraphicsExtractor, config, color, String.valueOf(data.durability), x + config.durabilityAmountOffsetX, y - config.durabilityAmountOffsetY, config.durabilityAmountScale);
        }
    }
}
