package com.danrus.durability_visibility_options.client.utils;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.RotationAxis;

import java.awt.*;

public class DrawUtils {
    public static void fill(DrawContext context, DurabilityConfig config, int x, int y, int width, int height, int color) {
        int z = 200;
        if (config.showDurabilityBarUnderItem) {
            z = 0;
        }
        context.fill(RenderLayer.getGui(), x, y, x + width, y + height, z, color);
    }

    public static void drawGradientHorizontalBar(DrawContext context, int startX, int startY, int width, int height, int itemBarStep) {
        int past_color = Color.HSBtoRGB(1, 1f, 1f) | 0xFF000000;
        startX -= 3;
        context.getMatrices().push();
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
        for (int k = 0; k < 13; k++) {
            float hue = (float) k / 13f;
            int rgb = Color.HSBtoRGB(hue, 1f, 1f) | 0xFF000000;
            if (k < itemBarStep) {

                context.fillGradient(

                        startY,
                        -startX - 16  + k,

                        startY + height,
                        -startX + width/13 + k - 16,
                        200,
                        past_color,
                        rgb
                );

            }
            past_color = rgb;
        }
        context.getMatrices().pop();
    }

    public static void drawGradientVerticalBar(DrawContext context, int startX, int startY, int width, int height, int itemBarStep) {
        int past_color = Color.HSBtoRGB(1, 1f, 1f) | 0xFF000000;
        startX -= 3;
        for (int k = 0; k < 13; k++) {
            float hue = (float) k / 13f;
            int rgb = Color.HSBtoRGB(hue, 1f, 1f) | 0xFF000000;
            if (k < itemBarStep) {
                context.fillGradient(

                        startX,
                        startY + height - (k + 1) * (height / 13),

                        startX + width,
                        startY + height - k * (height / 13),
                        500,
                        rgb,
                        past_color
                );
            }
            past_color = rgb;
        }
    }
}
