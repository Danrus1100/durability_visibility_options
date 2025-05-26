package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;

import java.awt.*;

public class DurabilityRender {

    public static int interpolateColorHSV(int colorStart, int colorEnd, int t) {
        float ratio = Math.max(0f, Math.min(1f, t / 100f));

        Color start = new Color(colorStart);
        Color end = new Color(colorEnd);

        float[] hsvStart = Color.RGBtoHSB(start.getRed(), start.getGreen(), start.getBlue(), null);
        float[] hsvEnd = Color.RGBtoHSB(end.getRed(), end.getGreen(), end.getBlue(), null);

        float h1 = hsvStart[0];
        float h2 = hsvEnd[0];

        float deltaH = h2 - h1;
        if (Math.abs(deltaH) > 0.5f) {
            if (deltaH > 0) {
                h1 += 1.0f;
            } else {
                h2 += 1.0f;
            }
        }

        float h = (h1 + (h2 - h1) * ratio) % 1.0f;
        float s = hsvStart[1] + (hsvEnd[1] - hsvStart[1]) * ratio;
        float v = hsvStart[2] + (hsvEnd[2] - hsvStart[2]) * ratio;

        return Color.HSBtoRGB(h, s, v);
    }

    public static void renderBar(DrawContext drawContext, int percents, int itemBarStep, int x, int y) {
        renderBar(drawContext, percents, itemBarStep, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderBar(DrawContext drawContext, int percents, int itemBarStep, int x, int y, DurabilityConfig config) {
        if (config.showDurabilityBarFromPercent <= percents || !config.showDurability) {
            return;
        }
        int i = x + 2;
        int j = y + 13;

        if (config.isVertical) {
            i += config.durabilityBarOffsetX - 2;
            j -= config.durabilityBarOffsetY + 11;
            if (config.showDurabilityBarBackground) {DrawUtils.fill(drawContext, config, i, j, 2, 13, -16777216);}
            if (config.doRgbBar){
                DrawUtils.drawGradientVerticalBar(drawContext, i+3, j, 1, 13, itemBarStep);
            } else {
                DrawUtils.fill(drawContext, config, i, j + 13, 1, -itemBarStep, interpolateColorHSV(config.durabilityBarColorMin, config.durabilityBarColor, percents));
            }

        } else {
            i += config.durabilityBarOffsetX;
            j -= config.durabilityBarOffsetY;
            if (config.showDurabilityBarBackground) {DrawUtils.fill(drawContext, config, i, j, 13, 2, -16777216);}
            if (config.doRgbBar){
                DrawUtils.drawGradientHorizontalBar(drawContext, i, j, 13, 1, itemBarStep);
            } else {
                DrawUtils.fill(drawContext, config, i+13 , j, -itemBarStep, 1, interpolateColorHSV(config.durabilityBarColorMin, config.durabilityBarColor, percents));
            }
        }
    }

    public static void renderPercents(DrawContext drawContext, int percents, int x, int y) {
        renderPercents(drawContext, percents, x, y, DurabilityConfig.fromModConfig());
    }

    public static void renderPercents(DrawContext drawContext, int percents, int x, int y, DurabilityConfig config) {
        if (config.showDurabilityPercent) {
            if (config.showDurabilityPercentsFromPercent >= percents) {
                String durability = String.valueOf(percents);
                if (config.showPercentSymbol) {
                    durability += "%";

                }
                drawContext.getMatrices().push();

                float targetX = x + 9 + config.durabilityPercentOffsetX;
                float targetY = y + 11 - config.durabilityPercentOffsetY;
                drawContext.getMatrices().translate(targetX, targetY, 200.0F);

                float scale = config.durabilityPercentScale;
                drawContext.getMatrices().scale(scale, scale, 1);

                drawContext.drawCenteredTextWithShadow(
                        MinecraftClient.getInstance().textRenderer,
                        durability,
                        0,
                        0,
                        interpolateColorHSV(
                                config.durabilityPercentColorMin,
                                config.durabilityPercentColor,
                                percents
                        )
                );

                drawContext.getMatrices().pop();
            }
        }
    }
}
