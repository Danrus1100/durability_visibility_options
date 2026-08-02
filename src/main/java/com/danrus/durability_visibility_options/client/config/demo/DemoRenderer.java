package com.danrus.durability_visibility_options.client.config.demo;

import dev.isxander.yacl3.gui.image.ImageRenderer;

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else
import net.minecraft.client.gui.GuiGraphics;

public interface DemoRenderer extends ImageRenderer {
    @Override
    int render(
        //? if >=26.1 {
        /*GuiGraphicsExtractor context,
        *///?} else
        GuiGraphics context,

        int x,
        int y,
        int width,
        float deltaTime
    );
    default void tick() {}
    default void close() {}
}
