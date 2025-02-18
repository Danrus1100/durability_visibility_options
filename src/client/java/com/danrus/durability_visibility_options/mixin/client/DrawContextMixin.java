package com.danrus.durability_visibility_options.mixin.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Final
    @Shadow
    private MatrixStack matrices;

    @Final
    @Shadow
    private void drawItemBar(ItemStack stack, int x, int y) {

    }

    @Inject(
            method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawStackCount(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void drawStackOverlayMixin(TextRenderer textRenderer, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci){
        DrawContext drawContext = (DrawContext)(Object)this;
        int percents = (int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100);
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
    }

    @Inject(
            method = "drawItemBar",
            at = @At(
                    value = "HEAD"

            ),
            cancellable = true
    )
    private void drawItemBarMixin(ItemStack stack, int x, int y, CallbackInfo ci){
        if (ModConfig.showDurability) {
            int percents = (int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100);
            if (ModConfig.showDurabilityBarFromPercent < percents) {
                ci.cancel();
            }
        } else {
            ci.cancel();
        }
    }

    @ModifyArgs(
            method = "drawItemBar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIIII)V"
    ))
    private void modifyBarColor(Args args) {
        int x1 = args.get(1);
        int y1 = args.get(2);
        int x2 = args.get(3);
        int y2 = args.get(4);

        if (!ModConfig.isVertical) {
            args.set(1, x1);
            args.set(2, y1);
            args.set(3, x2);
            args.set(4, y2);
        } else {
            int barStep = x2 - x1;
            int barHeight = y2 - y1;
            args.set(1, x1);
            args.set(2, y1);
            args.set(3, x1 + barHeight);
            args.set(4, y1 + barStep);
        }

    }

    @ModifyVariable(
            method = "drawItemBar",
            at = @At(
                    value = "HEAD"
            ),
            ordinal = 1,
            argsOnly = true)
    private int modifyBarY(int y){
        int verticalOffset = 0;
        if (ModConfig.isVertical) {
            verticalOffset = 11;
        }
        return y-ModConfig.durabilityBarOffsetY-verticalOffset;
    }

    @ModifyVariable(
            method = "drawItemBar",
            at = @At(
                    value = "HEAD"
            ),
            ordinal = 0,
            argsOnly = true)
    private int modifyBarX(int x){

        return x+ModConfig.durabilityBarOffsetX-1;
    }


}
