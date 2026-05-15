package com.danrus.dvo.mixin;

import com.danrus.dvo.DurabilityData;
import com.danrus.dvo.DurabilityRender;
import com.danrus.dvo.config.DurabilityConfig;
import com.danrus.dvo.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiGraphicsExtractor.class)
public abstract class InjectBarMixin {

    @WrapMethod(
            method = "itemBar"
    )
    private void dvo$renderBar(ItemStack stack, int x, int y, Operation<Void> original) {
        GuiGraphicsExtractor self = (GuiGraphicsExtractor) (Object) this;

        if (stack.isDamaged() && ModConfig.get().showDurability) {
            DurabilityRender.renderBar(self, stack, x, y);
        }

        if (stack.isDamaged() &&  ModConfig.get().showDurabilityPercent ) {
            DurabilityRender.renderPercents(self, stack, x, y);
        }

        if (stack.isDamaged() && ModConfig.get().showDurabilityAmount) {
            DurabilityRender.renderAmount(self, new DurabilityData(stack), x, y, DurabilityConfig.fromModConfig());
        }
    }
}
