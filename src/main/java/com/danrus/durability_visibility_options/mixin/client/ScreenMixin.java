package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Screen.class)
public class ScreenMixin {

    @Redirect(
            method = "renderBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/Screen;applyBlur()V")
    )
    private void applyBlurMixin(Screen instance) {
        if (!ModConfig.isOpen) {
            instance.applyBlur();
        }
    }
}
