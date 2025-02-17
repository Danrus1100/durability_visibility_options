package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.CustomMathHelper;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin{
    @Inject(method = "getItemBarColor", at = @At("HEAD"), cancellable = true)
    private void getItemBarColor(ItemStack stack, CallbackInfoReturnable<Integer> cir){
        int maxDamage = stack.getMaxDamage();
        float f = Math.max(0.0F, ((float)maxDamage - (float)stack.getDamage()) / (float)maxDamage);

        float[] MaxColor = CustomMathHelper.rgbToHsv(ModConfig.durabilityBarColor);
        float[] MinColor = CustomMathHelper.rgbToHsv(ModConfig.durabilityBarColor2);

        float[] color = CustomMathHelper.interpolateHsv(MinColor, MaxColor, f);

        cir.setReturnValue(MathHelper.hsvToRgb(color[0]/255, color[1], color[2]));

    }
}
