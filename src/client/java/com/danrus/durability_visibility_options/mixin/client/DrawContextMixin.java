package com.danrus.durability_visibility_options.mixin.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.danrus.durability_visibility_options.client.config.ModConfig;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Final
    @Shadow
    private MatrixStack matrices;


    @Final
    @Shadow
    private net.minecraft.client.MinecraftClient client;

//    @Inject(
//            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/player/ItemCooldownManager;getCooldownProgress(Lnet/minecraft/item/Item;F)F"
//            )
//    )
//    private void drawStackOverlayMixin(TextRenderer textRenderer, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci){
//        DrawContext drawContext = (DrawContext)(Object)this;
//        int percents = (int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100);
//        if (ModConfig.showDurabilityPercent && stack.getMaxDamage() > 0 && ModConfig.showDurabilityPercentsFromPercent >= percents) {
//            String durability = String.valueOf(percents);
//            if (ModConfig.showPercentSymbol) {
//                durability += "%";
//            }
//
//            this.matrices.push();
//            this.matrices.scale(0.5F, 0.5F, 0.5F);
//            this.matrices.translate(0.0F, 0.0F, 500.0F);
//
//            drawContext.drawCenteredTextWithShadow(textRenderer, durability , x*2+16+ModConfig.durabilityPercentOffsetX, y*2+24-ModConfig.durabilityPercentOffsetY, ModConfig.durabilityPercentColor);
//
//            this.matrices.scale(2.0F, 2.0F, 2.0F);
//            this.matrices.translate(0.0F, 0.0F, 200.0F);
//            this.matrices.pop();
//
//        }
//    }

    @Inject(
            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At("HEAD"),
            cancellable = true
    )

    private void drawBarMixin(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci){
        if (!stack.isEmpty()) {
            DrawContext drawContext = (DrawContext)(Object)this;
            int percents = (int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100);
            this.matrices.push();
            if (stack.getCount() != 1 || countOverride != null) {
                String string = countOverride == null ? String.valueOf(stack.getCount()) : countOverride;
                this.matrices.translate(0.0F, 0.0F, 200.0F);
                drawContext.drawText(textRenderer, string, x + 19 - 2 - textRenderer.getWidth(string), y + 6 + 3, 16777215, true);
            }

            if (stack.isItemBarVisible() && ModConfig.showDurabilityBarFromPercent >= percents) {
                int i = stack.getItemBarStep();
                int k = x + 2;
                int l = y + 13;

                k = k+ModConfig.durabilityBarOffsetX-1;

                int verticalOffset = 0;
                if (ModConfig.isVertical) {
                    verticalOffset = 0; //TODO: change plase of this
                }
                l = l-ModConfig.durabilityBarOffsetY-verticalOffset;


                if (ModConfig.isVertical) { //FIXME: not properly scaled then vertical
                    int barStep = 13;
                    int barHeight = 13;
                    drawContext.fill(RenderLayer.getGuiOverlay(), k, l, k + barHeight, l + barStep, -16777216);
                    drawContext.fill(RenderLayer.getGuiOverlay(), k, l, k + 1, l + i, -stack.getItemBarColor()); //FIXME: color works wrong
                } else {
                    drawContext.fill(RenderLayer.getGuiOverlay(), k, l, k + 13, l + 2, -16777216);
                    drawContext.fill(RenderLayer.getGuiOverlay(), k, l, k + i, l + 1, -stack.getItemBarColor()); //FIXME: color works wrong
            }

            if (ModConfig.showDurabilityPercent && stack.getMaxDamage() > 0 && ModConfig.showDurabilityPercentsFromPercent >= percents) {
                String durability = String.valueOf(percents);
                if (ModConfig.showPercentSymbol) {
                    durability += "%";
                }

                this.matrices.push();
                this.matrices.scale(0.5F, 0.5F, 0.5F);
                this.matrices.translate(0.0F, 0.0F, 500.0F);

                drawContext.drawCenteredTextWithShadow(textRenderer, durability , x*2+16+ModConfig.durabilityPercentOffsetX, y*2+24-ModConfig.durabilityPercentOffsetY, ModConfig.durabilityPercentColor);

                this.matrices.scale(2.0F, 2.0F, 2.0F);
                this.matrices.translate(0.0F, 0.0F, 200.0F);
                this.matrices.pop();

            }

            ClientPlayerEntity clientPlayerEntity = this.client.player;
            float f = clientPlayerEntity == null ? 0.0F : clientPlayerEntity.getItemCooldownManager().getCooldownProgress(stack.getItem(), this.client.getRenderTickCounter().getTickDelta(true));
            if (f > 0.0F) {
                int k2 = y + MathHelper.floor(16.0F * (1.0F - f));
                int l2 = k2 + MathHelper.ceil(16.0F * f);
                drawContext.fill(RenderLayer.getGuiOverlay(), x, k2, x + 16, l2, Integer.MAX_VALUE);
            }

            this.matrices.pop();
        }
        ci.cancel();
    }

//    @ModifyArgs(
//            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V"
//    ))
//    private void modifyBarColor(Args args) {
//        int x1 = args.get(1);
//        int y1 = args.get(2);
//        int x2 = args.get(3);
//        int y2 = args.get(4);
//
//        if (!ModConfig.isVertical) {
//            args.set(1, x1);
//            args.set(2, y1);
//            args.set(3, x2);
//            args.set(4, y2);
//        } else {
//            int barStep = x2 - x1;
//            int barHeight = y2 - y1;
//            args.set(1, x1);
//            args.set(2, y1);
//            args.set(3, x1 + barHeight);
//            args.set(4, y1 + barStep);
//
//        }
//
//    }
//
//
}}
