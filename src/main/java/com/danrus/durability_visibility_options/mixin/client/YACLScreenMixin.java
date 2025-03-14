package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.tab.TabExt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(YACLScreen.class)
public class YACLScreenMixin {
    @Inject(method = "close", at = @At("HEAD"))
    private void close(CallbackInfo ci) {
        ModConfig.isOpen = false;
        System.out.println("YACL closed");
    }

    @Redirect(method = "renderBackground", at = @At(value = "INVOKE", target = "Ldev/isxander/yacl3/gui/tab/TabExt;renderBackground(Lnet/minecraft/client/gui/DrawContext;)V"))
    private void renderBackgroundMixin(TabExt instance, DrawContext graphics) {
        if (!ModConfig.isOpen) {
            instance.renderBackground(graphics);
        }
    }
}
