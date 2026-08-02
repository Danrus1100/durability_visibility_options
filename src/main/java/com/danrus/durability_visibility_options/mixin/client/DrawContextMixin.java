package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.DurabilityData;
import com.danrus.durability_visibility_options.client.DurabilityRender;
import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else
import net.minecraft.client.gui.GuiGraphics;


//? if >=1.21.11 {
/*import net.minecraft.client.renderer.rendertype.RenderType;
*///?} else
import net.minecraft.client.renderer.RenderType;

//? if >=26.1 {
/*@Mixin(GuiGraphicsExtractor.class)
*///?} else
@Mixin(GuiGraphics.class)
public abstract class DrawContextMixin {

    //? if >=26.1 {
    /*@Shadow public abstract void text(Font par1, Component par2, int par3, int par4, int par5, boolean par6);
    *///?} else if >=1.21.6 {
    /*@Shadow public abstract void drawString(Font par1, Component par2, int par3, int par4, int par5, boolean par6);
    *///?} else {
    @Shadow public abstract int drawString(Font par1, Component par2, int par3, int par4, int par5, boolean par6);
    //?}

    //? if >1.21.1 {
    /*@Redirect(

        //? if >=26.1 {
        /^method = "itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
        ^///?} else
        method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",

        at = @At(
            value = "INVOKE",
            //? if >=26.1 {
            /^target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;itemBar(Lnet/minecraft/world/item/ItemStack;II)V"
            ^///?} else
            target = "Lnet/minecraft/client/gui/GuiGraphics;renderItemBar(Lnet/minecraft/world/item/ItemStack;II)V"
        )
    )
    private void drawStackOverlayMixin(
        //? if >=26.1 {
        /^GuiGraphicsExtractor instance,
        ^///?} else
        GuiGraphics instance,

        ItemStack stack,
        int x,
        int y
    ) {
        String countOverride = stack.getCount() == 1 ? null : String.valueOf(stack.getCount());
        Font textRenderer = Minecraft.getInstance().font;
        drawItemInSlotMixin(textRenderer, stack, x, y, countOverride, null);
    }
    *///?}

    //? if <=1.21.1 {
    @Inject(
            method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isBarVisible()Z"),
            cancellable = true)

    //?}
    private void drawItemInSlotMixin(Font textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        //? if >=26.1 {
        /*GuiGraphicsExtractor thisObject = (GuiGraphicsExtractor) (Object) this;
        *///?} else
        GuiGraphics thisObject = (GuiGraphics) (Object) this;

        if (stack.isDamageableItem()) {
            if (ModConfig.get().showDurability) {
                DurabilityRender.renderBar(thisObject, stack, x, y);
            }

            if (ModConfig.get().showDurabilityPercent) {
                DurabilityRender.renderPercents(thisObject, stack, x, y);
            }

            if (ModConfig.get().showDurabilityAmount) {
                DurabilityRender.renderAmount(thisObject, new DurabilityData(stack), x, y, DurabilityConfig.fromModConfig());
            }
        }

        //? if <=1.21.1 {
        LocalPlayer clientPlayerEntity = Minecraft.getInstance().player;
        //? if >1.20.6 {
        float tickDelta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        //?} else
        /*float tickDelta = Minecraft.getInstance().getFrameTime();*/
        float f = clientPlayerEntity == null ? 0.0F : clientPlayerEntity.getCooldowns().getCooldownPercent(stack.getItem(), tickDelta);
        if (f > 0.0F) {
            int k = y + Mth.floor(16.0F * (1.0F - f));
            int l = k + Mth.ceil(16.0F * f);
            thisObject.fill(RenderType.guiOverlay(), x, k, x + 16, l, Integer.MAX_VALUE);
        }

        thisObject.pose().popPose();
        ci.cancel();

        //?}
    }
}
