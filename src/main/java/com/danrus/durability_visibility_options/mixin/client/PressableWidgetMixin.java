package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PressableWidget.class)
public class PressableWidgetMixin {
    @Redirect(
            //? if >1.20.2
            /*method = "renderWidget",*/
            //? if <=1.20.2
            method = "renderButton",
            at = @At(
                    value = "INVOKE",
                    //? if >=1.21.4
                    /*target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIIII)V"*/
                    //? if <=1.21.1
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
            )
    )
    //? if >=1.21.4
    /*private void drawContextMixin(net.minecraft.client.gui.DrawContext instance, java.util.function.Function<net.minecraft.util.Identifier, net.minecraft.client.render.RenderLayer> function, Identifier identifier, int i1, int i2, int i3, int i4, int i5)*/
    //? if <=1.21.1
    private void drawContextMixin(DrawContext instance, Identifier identifier, int i1, int i2, int i3, int i4)
    {
        if (!ModConfig.isOpen || MinecraftClient.getInstance().player == null) {
            instance.drawGuiTexture(/*? if <1.21.4 >1.20.6 {*//*RenderLayer::getGuiTextured, *//*?}*/ identifier, i1, i2, i3, i4/*? if =1.21.4 {*//*, i5*//*?}*/);
        }
    }
}
