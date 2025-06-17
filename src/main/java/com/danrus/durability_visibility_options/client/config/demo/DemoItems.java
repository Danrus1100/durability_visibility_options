package com.danrus.durability_visibility_options.client.config.demo;

import com.danrus.durability_visibility_options.client.DurabilityData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Random;

public class DemoItems {
    private static DemoItems instance;
    private final ItemStack[] items = {
            new ItemStack(Items.ELYTRA),
            new ItemStack(Items.NETHERITE_PICKAXE),
            new ItemStack(Items.DIAMOND_HOE),
            new ItemStack(Items.BOW),
            new ItemStack(Items.TRIDENT),
            new ItemStack(Items.BRUSH),
            new ItemStack(Items.WOODEN_SWORD),
            new ItemStack(Items.FLINT_AND_STEEL)
    };
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

    public DurabilityData getData() {
        return new DurabilityData(activeItem);
    }

    public void tick() {
        tickCounter += 1;

        if (tickCounter >= 1) {
            tickCounter = 0;

            int damageAmount = Math.round(activeItem.getMaxDamage() * 0.01f);
            int newDamage = activeItem.getDamage() + damageAmount;

            if (newDamage >= activeItem.getMaxDamage()) {
                int currentIndex = -1;
                for (int i = 0; i < items.length; i++) {
                    if (items[i] == activeItem) {
                        currentIndex = i;
                        break;
                    }
                }

                int nextIndex = (currentIndex + 1) % items.length;
                activeItem = items[nextIndex];

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