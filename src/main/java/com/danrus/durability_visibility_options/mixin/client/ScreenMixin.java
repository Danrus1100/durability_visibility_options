package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    //? if >1.20.4 {
    /*@Redirect(
            method = "renderBackground",
            at = @At(
                    value = "INVOKE",
                    //? if >=1.21.4
                    /^target = "Lnet/minecraft/client/gui/screen/Screen;applyBlur()V")^/
                    //? if <=1.21.1
                    target = "Lnet/minecraft/client/gui/screen/Screen;applyBlur(F)V"
    ))
    private void applyBlurMixin(Screen instance, /^? if <=1.21.1 {^/float v/^?}^/)
    {
        if (!ModConfig.isOpen || MinecraftClient.getInstance().player == null) {
            instance.applyBlur(/^? if <=1.21.1 {^/v/^?}^/);
        }
    }
    *///?}
    //? if <=1.20.4 {
    @Inject(
            method = "renderBackground",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void renderBackgroundMixin(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (ModConfig.isOpen) {
            ci.cancel();
        }
    }
    //?}
}
