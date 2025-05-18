package com.danrus.durability_visibility_options.client.utils;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;

public class DrawUtils {
    public static void fill(DrawContext context, DurabilityConfig config, int x, int y, int width, int height, int color) {
        int z = 200;
        if (config.showDurabilityBarUnderItem) {
            z = 0;
        }
        context.fill(RenderLayer.getGui(), x, y, x + width, y + height, z, color);
    }
}
