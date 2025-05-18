package com.danrus.durability_visibility_options.client.config.demo;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DemoItems {
    private static DemoItems instance;
    private final ItemStack[] items = {new ItemStack(Items.ELYTRA), new ItemStack(Items.NETHERITE_PICKAXE)};
    private ItemStack activeItem = items[0];
    private float tickCounter = 0;

    private DemoItems() {}

    public static DemoItems getInstance() {
        if (instance == null) {
            instance = new DemoItems();
        }
        return instance;
    }

    public ItemStack getActiveItem() {
        return activeItem;
    }

    public void tick() {
        tickCounter += 1;

        if (tickCounter >= 1) {
            tickCounter = 0;

            int damageAmount = Math.round(activeItem.getMaxDamage() * 0.01f);
            int newDamage = activeItem.getDamage() + damageAmount;

            if (newDamage >= activeItem.getMaxDamage()) {
                if (activeItem == items[0]) {
                    activeItem = items[1];
                } else {
                    activeItem = items[0];
                }

                for (ItemStack item : items) {
                    if (item != activeItem) {
                        item.setDamage(0);
                    }
                }
            } else {
                activeItem.setDamage(newDamage);
            }
        }
    }
}