package com.danrus.dvo.config.demo;

import com.danrus.dvo.DurabilityData;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DemoItems {
    private static DemoItems instance;
    private final Item[] items = {
            Items.ELYTRA,
            Items.NETHERITE_PICKAXE,
            Items.DIAMOND_HOE,
            Items.BOW,
            Items.TRIDENT,
            Items.BRUSH,
            Items.WOODEN_SWORD,
            Items.FLINT_AND_STEEL
    };
    @Nullable
    private ItemStack activeItem;
    private float tickCounter = 0;

    private DemoItems() {}

    public static DemoItems getInstance() {
        if (instance == null) {
            instance = new DemoItems();
        }
        return instance;
    }

    public ItemStack getActiveItem() {
        if (activeItem == null) {
            activeItem = createStack(items[0]);
        }
        return activeItem;
    }

    public DurabilityData getData() {
        return new DurabilityData(getActiveItem());
    }

    public void tick() {
        tickCounter += 1;

        if (tickCounter >= 1) {
            tickCounter = 0;

            int damageAmount = Math.round(activeItem.getMaxDamage() * 0.01f);
            int newDamage = activeItem.getDamageValue() + damageAmount;

            if (newDamage >= activeItem.getMaxDamage()) {
                int currentIndex = -1;
                for (int i = 0; i < items.length; i++) {
                    if (items[i] == activeItem.getItem()) {
                        currentIndex = i;
                        break;
                    }
                }

                int nextIndex = (currentIndex + 1) % items.length;
                activeItem = createStack(items[nextIndex]);

                
            } else {
                activeItem.setDamageValue(newDamage);
            }
        }
    }

    private ItemStack createStack(Item item) {
        return DemoStackFactory.create(item);
    }
}