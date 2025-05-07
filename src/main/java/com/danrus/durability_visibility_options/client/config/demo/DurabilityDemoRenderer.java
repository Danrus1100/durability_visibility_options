package com.danrus.durability_visibility_options.client.config.demo;

// DISCLAIMER:
// I cant write a good code, so try to make this better if
// u wanna use it in your mod.

import com.danrus.durability_visibility_options.client.DurabilityRender;
import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DurabilityDemoRenderer implements DemoRenderer {

    private final ItemStack[] demoItems = {new ItemStack(Items.ELYTRA), new ItemStack(Items.NETHERITE_PICKAXE)};
    private ItemStack aciveDemoItem = demoItems[0];
    private float tickCounter = 0;

    @Override
    public int render(DrawContext context, int x, int y, int width, float deltaTime) {
        ItemStack demoStack = aciveDemoItem;

        int itemX = (int) (x + MinecraftClient.getInstance().getWindow().getScaledWidth() / 7.5);
        int itemY = y + 45;
        float scale = 6F;

        context.getMatrices().push();

        context.getMatrices().translate(itemX + 8, itemY + 8, 0.0F); // Move to item center
        context.getMatrices().scale(scale, scale, 1.0F);
        context.drawItem(demoStack, -8, -8); // Center the item

        context.getMatrices().pop();

        context.getMatrices().push();

        context.getMatrices().translate(itemX, itemY, 0.0F);
        context.getMatrices().scale(scale, scale, 1.0F);

        int percents = (int)((1 - demoStack.getDamage() / (float)demoStack.getMaxDamage()) * 100);
        if (aciveDemoItem.getItem() == Items.NETHERITE_PICKAXE) { percents += 1; }
        int itemBarStep = Math.round(13.0F - demoStack.getDamage() * 13.0F / demoStack.getMaxDamage());

        // idk why here -7 and -6, i just picked the values by eye
        DurabilityRender.renderBar(context, percents, itemBarStep, -7, -6);
        DurabilityRender.renderPercents(context, percents, -7, -6);

        context.getMatrices().pop();

        return 56;
    }

    public void tick() {
        tickCounter += 1;

        if (tickCounter >= 2) {
            tickCounter = 0;

            int damageAmount = Math.round(aciveDemoItem.getMaxDamage() * 0.01f);
            int newDamage = aciveDemoItem.getDamage() + damageAmount;

            if (newDamage >= aciveDemoItem.getMaxDamage()) {
                if (aciveDemoItem == demoItems[0]) {
                    aciveDemoItem = demoItems[1];
                } else {
                    aciveDemoItem = demoItems[0];
                }

                for (ItemStack item : demoItems) {
                    if (item != aciveDemoItem) {
                        item.setDamage(0);
                    }
                }
            } else {
                aciveDemoItem.setDamage(newDamage);
            }
        }
    }
}
