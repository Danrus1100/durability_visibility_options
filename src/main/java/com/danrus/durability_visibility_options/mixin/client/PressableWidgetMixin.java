package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PressableWidget.class)
public class PressableWidgetMixin {
   /*
   renderWidget по моим раследованием делится на 2 основные части:
   1) рендерится основа кнопки
   2) рендерится текст кнопки

   нас интересует только 1 пункт. я заметил что в identifier передается
   некий ButtonTextures, в конструктор которого передается 3 индефикотра
   тектур кнопки: обычная, выделеная и выключенная
   нужно подменить текстуры на свои чтобы добиться нужного мне результата
    */

    private static final ButtonTextures CUSTOM_TEXTURES = new ButtonTextures(
            Identifier.of("durability_visibility_options", "button"),
            Identifier.of("durability_visibility_options", "button_disabled"),
            Identifier.of("durability_visibility_options", "button_highlighted")
    );

//    @Redirect(
//            method = "renderWidget",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/gui/screen/ButtonTextures;get(ZZ)Lnet/minecraft/util/Identifier;")
//    )
//    private Identifier getCustomButtonTextures(ButtonTextures buttonTextures, boolean b1, boolean b2) {
//        if (ModConfig.isOpen) {
//            return CUSTOM_TEXTURES.get(b1, b2);
//        }
//        RenderSystem.enableBlend();
//        Identifier result = buttonTextures.get(b1, b2);
//        RenderSystem.disableBlend();
//        return result;
//    }
    @Redirect(
            method = "renderWidget",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIIII)V"
            )
    )
    private void drawContextMixin(
            net.minecraft.client.gui.DrawContext instance,
            java.util.function.Function<net.minecraft.util.Identifier, net.minecraft.client.render.RenderLayer> function,
            Identifier identifier,
            int i1, int i2, int i3, int i4, int i5
    ) {
        if (!ModConfig.isOpen) {
            instance.drawGuiTexture(RenderLayer::getGuiTextured, identifier, i1, i2, i3, i4, i5);
        }
    }


}
