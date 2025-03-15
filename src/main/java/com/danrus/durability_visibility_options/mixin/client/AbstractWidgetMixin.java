package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.utils.YACLRenderHelper;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {
    @Redirect(
            method = "drawButtonRect",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/isxander/yacl3/gui/utils/YACLRenderHelper;renderButtonTexture(Lnet/minecraft/client/gui/DrawContext;IIIIZZ)V")
    )
    private void renderButtonTextureMixin(
            net.minecraft.client.gui.DrawContext instance,
            int x, int y, int width, int height, boolean enabled, boolean hovered
    ) {
        if (!ModConfig.isOpen || MinecraftClient.getInstance().player == null) {
            YACLRenderHelper.renderButtonTexture(instance, x, y, width, height, enabled, hovered);
        }
    }
}
