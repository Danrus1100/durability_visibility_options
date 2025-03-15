package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    private void customDrawArmor(DrawContext d, Identifier i, int i1, int i2, int i3, int i4, int i5, int i6) {
        //? if <=1.20.4 >1.20.1
        /*d.drawGuiTexture(i, i1, i2, i3, i4);*/
        //? if <=1.20.1
        /*d.drawTexture(i, i1, i2, i3, i4, i5, i6);*/
    }

    //? if >1.20.4 {
    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    private static void renderArmor(DrawContext context, PlayerEntity player, int i, int j, int k, int x, CallbackInfo ci) {
        if (!ModConfig.get().showVanillaArmorHud) {
            ci.cancel();
        }
    }
    //?}

    //? if <=1.20.4 {

    /*@Redirect(
            method = "renderStatusBars",
            slice = @Slice(
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V")
            ),
            at = @At(
                    value = "INVOKE",
                    //? if >1.20.1
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
                    //? if <=1.20.1
                    /^target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"^/
            )

    )//? if >1.20.1 {
    private void renderStatusBarsMixin(DrawContext instance, net.minecraft.util.Identifier texture, int i1, int i2, int i3, int i4)
    //?} else
    /^private void renderStatusBarsMixin(DrawContext instance, Identifier texture, int i1, int i2, int i3, int i4, int i5, int i6)^/
    {
        //? if >1.20.1
        int i5 = 0; int i6 = 0;
        if (ModConfig.get().showVanillaArmorHud) {
            customDrawArmor(instance, texture, i1, i2, i3, i4, i5, i6);
        }
    }


    *///?}
}
