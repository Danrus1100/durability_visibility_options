package com.danrus.durability_visibility_options.client.config.demo;

import dev.isxander.yacl3.gui.image.ImageRenderer;
import net.minecraft.client.gui.DrawContext;

public interface DemoRenderer extends ImageRenderer {
    int render(DrawContext context, int x, int y, int width, float deltaTime);
    default void tick() {}
    default void close() {}
}
