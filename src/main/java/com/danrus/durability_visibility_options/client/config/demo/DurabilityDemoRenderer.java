package com.danrus.durability_visibility_options.client.config.demo;

// DISCLAIMER:
// I cant write a good code, so try to make this better if
// u wanna use it in your mod.

import com.danrus.durability_visibility_options.client.DurabilityRender;
import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DurabilityDemoRenderer implements DemoRenderer {
    private final DemoItems demoItems = DemoItems.getInstance();
    private boolean useSpecificConfig = false;
    private DurabilityConfig config = DurabilityConfig.fromModConfig();

    public DurabilityDemoRenderer() {}

    public DurabilityDemoRenderer(DurabilityConfig config) {
        this.config = config;
        this.useSpecificConfig = true;
    }

    @Override
    public int render(DrawContext context, int x, int y, int width, float deltaTime) {
        ItemStack demoStack = demoItems.getActiveItem();

        int itemX = (int) (x + MinecraftClient.getInstance().getWindow().getScaledWidth() / 7.5);
        int itemY = y + 45;
        float scale = 6F;

        context.getMatrices().push();

        context.getMatrices().translate(itemX + 8, itemY + 8, 0.0F);
        context.getMatrices().scale(scale, scale, 1.0F);
        context.drawItem(demoStack, -8, -8);

        context.getMatrices().pop();

        context.getMatrices().push();

        context.getMatrices().translate(itemX, itemY, 0.0F);
        context.getMatrices().scale(scale, scale, 1.0F);

        int percents = (int)((1 - demoStack.getDamage() / (float)demoStack.getMaxDamage()) * 100);
        if (demoStack.getItem() == Items.NETHERITE_PICKAXE) { percents += 1; }
        int itemBarStep = Math.round(13.0F - demoStack.getDamage() * 13.0F / demoStack.getMaxDamage());

        DurabilityRender.renderBar(context, percents, itemBarStep, -7, -6, config);
        DurabilityRender.renderPercents(context, percents, -7, -6, config);

        context.getMatrices().pop();

        return 56;
    }

    public void tick() {
        if (!this.useSpecificConfig) {
            config = DurabilityConfig.fromModConfig();
        }
        demoItems.tick();
    }
}