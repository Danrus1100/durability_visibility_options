package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.DurabilityRender;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DrawContext.class)
public class NewDrawContextMixin {

    @Final
    @Shadow
    private MatrixStack matrices;

    @Redirect(
            method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItemBar(Lnet/minecraft/item/ItemStack;II)V"
            )
    )
    private void drawStackOverlayMixin(DrawContext instance, ItemStack stack, int x, int y) {
        int percents = (int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100);
        if (stack.isDamaged() && ModConfig.showDurability) {
            DurabilityRender.renderBar(instance, percents, stack.getItemBarStep(), x, y);
        }

        if (ModConfig.showDurabilityPercent && percents <= ModConfig.showDurabilityPercentsFromPercent && stack.isDamaged()) {
            this.matrices.push();
            this.matrices.scale(0.5F, 0.5F, 0.5F);
            this.matrices.translate(x, y, 500.0F);
            DurabilityRender.renderPercents(instance, percents, x, y);
            this.matrices.pop();
        }
    }
}
