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
            method = "renderWidget",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIIII)V"
            )
    )
    private void drawContextMixin(net.minecraft.client.gui.DrawContext instance, java.util.function.Function<net.minecraft.util.Identifier, net.minecraft.client.render.RenderLayer> function, Identifier identifier, int i1, int i2, int i3, int i4, int i5)
    {
        if (!ModConfig.isOpen || MinecraftClient.getInstance().player == null) {
            instance.drawGuiTexture(RenderLayer::getGuiTextured, identifier, i1, i2, i3, i4, i5);
        }
    }
}
