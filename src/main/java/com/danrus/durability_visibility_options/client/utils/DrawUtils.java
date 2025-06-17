package com.danrus.durability_visibility_options.client.utils;

import com.danrus.durability_visibility_options.client.DurabilityData;
import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.RotationAxis;

import java.awt.*;
public class DrawUtils {

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

    public static void drawGradientHorizontalBar(DrawContext context, int itemBarStep) {
        int past_color = Color.HSBtoRGB(1, 1f, 1f) | 0xFF000000;
        pushMatrix(context);
        rotateMatrix(context, 90);
        for (int k = 0; k < 13; k++) {
            float hue = (float) k / 13f;
            int rgb = Color.HSBtoRGB(hue, 1f, 1f) | 0xFF000000;
            if (k < itemBarStep) {

                context.fillGradient(

                        0,
                        -(k + 1),

                        1,
                        -k,
                        //? if <1.21.6
                        0,
                        rgb,
                        past_color
                );

            }
            past_color = rgb;
        }
        popMatrix(context);
    }

    public static void drawGradientVerticalBar(DrawContext context, int itemBarStep) {
        int past_color = Color.HSBtoRGB(1, 1f, 1f) | 0xFF000000;
        for (int k = 0; k < 13; k++) {
            float hue = (float) k / 13f;
            int rgb = Color.HSBtoRGB(hue, 1f, 1f) | 0xFF000000;
            if (k < itemBarStep) {
                context.fillGradient(

                        0,
                        13 - (k + 1),

                        1,
                        13 - k,
                        //? if <1.21.6
                        0,
                        rgb,
                        past_color
                );
            }
            past_color = rgb;
        }
    }

    public static void drawGradientBar(DrawContext context, int startX, int startY, int itemBarStep){
        drawGradientBar(context, DurabilityConfig.builder().fromModConfig().build(), startX, startY, itemBarStep) ;
    }

    public static void drawGradientBar(DrawContext context, DurabilityConfig config, int startX, int startY, int itemBarStep) {
        if (config.isVertical) {
            if (config.showDurabilityBarBackground) {fill(context, 0, 0, 2, 13, -16777216);}
            drawGradientVerticalBar(context, itemBarStep);
        } else {
            if (config.showDurabilityBarBackground) {fill(context, 0, 0, 13, 2, -16777216);}
            drawGradientHorizontalBar(context, itemBarStep);
        }
    }

    public static void drawBar(DrawContext context, DurabilityConfig config, DurabilityData data, int startX, int startY){
        if (config.doRgbBar){
            drawGradientBar(context, config, startX, startY, data.barStep);
            return;
        }

        if (config.isVertical){
            if (config.showDurabilityBarBackground) {fill(context, 0, 0, 2, 13, -16777216);}
            fill(context, 0, 13, 1, -data.barStep, DrawUtils.interpolateColorHSV(config.durabilityBarColorMin, config.durabilityBarColor, data.getPercentsInt()));
        } else {
            if (config.showDurabilityBarBackground) {fill(context, 0, 0, 13, 2, -16777216);}
            fill(context, 0 , 0, data.barStep, 1, DrawUtils.interpolateColorHSV(config.durabilityBarColorMin, config.durabilityBarColor, data.getPercentsInt()));
        }
    }

    public static void drawTextInfo(DrawContext drawContext, DurabilityConfig config, int color, String text, int x, int y, float scale) {
        DrawUtils.pushMatrix(drawContext);

        float targetX = x + 9;
        float targetY = y + 11;

        DrawUtils.translateMatrix(drawContext, targetX, targetY);
        DrawUtils.scaleMatrix(drawContext, scale, scale);

        drawContext.drawCenteredTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                text,
                0,
                0,
                color
        );

        DrawUtils.popMatrix(drawContext);
    }

    public static void pushMatrix(DrawContext context) {
        //? if >1.21.5 {
        /*context.getMatrices().pushMatrix();
         *///?} else {
        context.getMatrices().push();
        //?}
    }

    public static void popMatrix(DrawContext context) {
        //? if >1.21.5 {
        /*context.getMatrices().popMatrix();
         *///?} else {
        context.getMatrices().pop();
         //?}
    }

    public static void rotateMatrix(DrawContext context, float deg) {
        //? if >1.21.5 {
        /*context.getMatrices().rotate((float) Math.toRadians(deg));
         *///?} else {
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(deg));
        //?}
    }

    public static void translateMatrix(DrawContext context, int x, int y) {
        translateMatrix(context, (float) x, (float) y);
    }

    public static void translateMatrix(DrawContext context, float x, float y) {
        //? if >1.21.5 {
        /*context.getMatrices().translate(x, y);
         *///?} else {
        context.getMatrices().translate(x, y, 200.0F);
        //?}
    }

    public static void scaleMatrix(DrawContext context, int x, int y) {
        scaleMatrix(context, (float) x, (float) y);
    }

    public static void scaleMatrix(DrawContext context, float x, float y) {
        //? if >1.21.5 {
        /*context.getMatrices().scale(x, y);
         *///?} else {
        context.getMatrices().scale(x, y, 1.0F);
        //?}
    }

    public static void fill(DrawContext context, int x, int y, int width, int height, int color) {
        //? if >1.21.5 {
        /*context.fill(x, y, x+width, y+height, color);
         *///?} else {
        context.fill(RenderLayer.getGui(), x, y, x + width, y + height, 200, color);
        //?}

    }
}
