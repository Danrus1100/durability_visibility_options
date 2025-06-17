package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.DurabilityData;
import com.danrus.durability_visibility_options.client.DurabilityRender;
import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Shadow public abstract void drawText(TextRenderer par1, Text par2, int par3, int par4, int par5, boolean par6);

    //? if >1.21.1 {
    /*@Redirect(

            method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItemBar(Lnet/minecraft/item/ItemStack;II)V"
            )

    )
    private void drawStackOverlayMixin(DrawContext instance, ItemStack stack, int x, int y) {
        String countOverride = stack.getCount() == 1 ? null : String.valueOf(stack.getCount());
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        drawItemInSlotMixin(textRenderer, stack, x, y, countOverride, null);
    }
    *///?}

    //? if <=1.21.1 {
    @Inject(
            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemBarVisible()Z"),
            cancellable = true)

    //?}
    private void drawItemInSlotMixin(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        DrawContext thisObject = (DrawContext) (Object) this;
        if (stack.isDamaged() && ModConfig.get().showDurability) {
            DurabilityRender.renderBar(thisObject, stack, x, y);
        }

        if (stack.isDamaged() &&  ModConfig.get().showDurabilityPercent ) {
            DurabilityRender.renderPercents(thisObject, stack, x, y);
        }

        if (stack.isDamaged() && ModConfig.get().showDurabilityAmount) {
            DurabilityRender.renderAmount(thisObject, new DurabilityData(stack), x, y, DurabilityConfig.fromModConfig());
        }
        //? if <=1.21.1 {
        ClientPlayerEntity clientPlayerEntity = MinecraftClient.getInstance().player;
        //? if >1.20.6 {
        float tickDelta = MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true);
        //?} else
        /*float tickDelta = MinecraftClient.getInstance().getTickDelta();*/
        float f = clientPlayerEntity == null ? 0.0F : clientPlayerEntity.getItemCooldownManager().getCooldownProgress(stack.getItem(), tickDelta);
        if (f > 0.0F) {
            int k = y + MathHelper.floor(16.0F * (1.0F - f));
            int l = k + MathHelper.ceil(16.0F * f);
            thisObject.fill(RenderLayer.getGuiOverlay(), x, k, x + 16, l, Integer.MAX_VALUE);
        }

        thisObject.getMatrices().pop();
        ci.cancel();

        //?}
    }
}
