package com.danrus.dvo.config.demo;

import dev.isxander.yacl3.gui.image.ImageRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface DemoRenderer extends ImageRenderer {
    int render(GuiGraphicsExtractor context, int x, int y, int width, float deltaTime);
    default void tick() {}
    default void close() {}
}
