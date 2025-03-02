package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.DurabilityRender;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
//? if >1.19.4 {
import net.minecraft.client.gui.DrawContext;
//?} else
/*import net.minecraft.client.gui.DrawableHelper;*/
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Final
    @Shadow
    private MatrixStack matrices;

    @Final
    @Shadow
    private MinecraftClient client;

    //? if >1.21.1 {
    @Redirect(

            method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItemBar(Lnet/minecraft/item/ItemStack;II)V"
            )

    )
    private void drawStackOverlayMixin(DrawContext instance, ItemStack stack, int x, int y) {
        String countOverride = stack.getCount() == 1 ? null : String.valueOf(stack.getCount());
        TextRenderer textRenderer = this.client.textRenderer;
        drawItemInSlotMixin(textRenderer, stack, x, y, countOverride, null);
    }
    //?}

    //? if <=1.21.1 {
    /*@Inject(
            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemBarVisible()Z"),
            cancellable = true)
     
    *///?}
    @Unique
    private void drawItemInSlotMixin(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        DrawContext instance = (DrawContext) (Object) this;
        int percents = (int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100);
        if (stack.isDamaged() && ModConfig.get().showDurability) {
            DurabilityRender.renderBar(instance, percents, stack.getItemBarStep(), x, y);
        }

        if (ModConfig.get().showDurabilityPercent && percents <= ModConfig.get().showDurabilityPercentsFromPercent && stack.isDamaged()) {
            this.matrices.push();
            this.matrices.scale(0.5F, 0.5F, 0.5F);
            this.matrices.translate(x, y, 500.0F);
            DurabilityRender.renderPercents(instance, percents, x, y);
            this.matrices.pop();
        }
        //? if <=1.21.1 {
        /*ClientPlayerEntity clientPlayerEntity = this.client.player;
        //? if >1.20.6 {
        float tickDelta = this.client.getRenderTickCounter().getTickDelta(true);
        //?} else
        /^float tickDelta = this.client.getTickDelta();^/
        float f = clientPlayerEntity == null ? 0.0F : clientPlayerEntity.getItemCooldownManager().getCooldownProgress(stack.getItem(), tickDelta);
        if (f > 0.0F) {
            int k = y + MathHelper.floor(16.0F * (1.0F - f));
            int l = k + MathHelper.ceil(16.0F * f);
            instance.fill(RenderLayer.getGuiOverlay(), x, k, x + 16, l, Integer.MAX_VALUE);
        }

        this.matrices.pop();
        ci.cancel();
        
        *///?}
    }
}
