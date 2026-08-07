package com.danrus.durability_visibility_options.client.config.demo;

import com.danrus.durability_visibility_options.client.DurabilityData;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

//? if >=26.1 {
import net.minecraft.world.item.Item;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
//?}

public class DemoItems {
    private static DemoItems instance;

    private ItemStack[] items = null;
    private ItemStack activeItem = null;

    public void initializeItemList() {
        if (items != null) return;

        items = new ItemStack[] {
            //? if >=26.1 {
            ItemStacks.ELYTRA,
            ItemStacks.NETHERITE_PICKAXE,
            ItemStacks.DIAMOND_HOE,
            ItemStacks.BOW,
            ItemStacks.TRIDENT,
            ItemStacks.BRUSH,
            ItemStacks.WOODEN_SWORD,
            ItemStacks.FLINT_AND_STEEL
            //?} else {
            /*new ItemStack(Items.ELYTRA),
            new ItemStack(Items.NETHERITE_PICKAXE),
            new ItemStack(Items.DIAMOND_HOE),
            new ItemStack(Items.BOW),
            new ItemStack(Items.TRIDENT),
            new ItemStack(Items.BRUSH),
            new ItemStack(Items.WOODEN_SWORD),
            new ItemStack(Items.FLINT_AND_STEEL)
            *///?}
        };

        activeItem = items[0];
    }

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
            int newDamage = activeItem.getDamageValue() + damageAmount;

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
                        item.setDamageValue(0);
                    }
                }
            } else {
                activeItem.setDamageValue(newDamage);
            }
        }
    }

    //? if >=26.1 {
    // minecraft 26.1+ makes item components be bound later than in earlier versions
    // the effect is that the preview screen won't work if you haven't joined a world yet.
    // so we basically "bootstrap" our own versions of the components for the items we want here
    private static class ItemStacks {
        public static final ItemStack ELYTRA = itemStack(
            Items.ELYTRA,
            432,
            "item.minecraft.elytra",
            "elytra"
        );

        public static final ItemStack NETHERITE_PICKAXE = itemStack(
            Items.NETHERITE_PICKAXE,
            ToolMaterial.NETHERITE.durability(),
            "item.minecraft.netherite_pickaxe",
            "netherite_pickaxe"
        );

        public static final ItemStack DIAMOND_HOE = itemStack(
            Items.DIAMOND_HOE,
            ToolMaterial.DIAMOND.durability(),
            "item.minecraft.diamond_hoe",
            "diamond_hoe"
        );

        public static final ItemStack BOW = itemStack(
            Items.BOW,
            384,
            "item.minecraft.bow",
            "bow"
        );

        public static final ItemStack TRIDENT = itemStack(
            Items.TRIDENT,
            250,
            "item.minecraft.trident",
            "trident"
        );

        public static final ItemStack BRUSH = itemStack(
            Items.BRUSH,
            64,
            "item.minecraft.brush",
            "brush"
        );

        public static final ItemStack WOODEN_SWORD = itemStack(
            Items.WOODEN_SWORD,
            ToolMaterial.WOOD.durability(),
            "item.minecraft.wooden_sword",
            "wooden_sword"
        );

        public static final ItemStack FLINT_AND_STEEL = itemStack(
            Items.FLINT_AND_STEEL,
            64,
            "item.minecraft.flint_and_steel",
            "flint_and_steel"
        );

        private static ItemStack itemStack(
            Item item,
            int maxDamage,
            @NonNull String itemName,
            @NonNull String itemModel
        ) {
            DataComponentMap.Builder properties = DataComponentMap.builder()
                .addAll(DataComponents.COMMON_ITEM_COMPONENTS)
                .set(DataComponents.MAX_DAMAGE, maxDamage)
                .set(DataComponents.MAX_STACK_SIZE, 1)
                .set(DataComponents.ITEM_NAME, Component.translatable(itemName))
                .set(DataComponents.ITEM_MODEL, Identifier.withDefaultNamespace(itemModel));

            return new ItemStack(Holder.direct(item, properties.build()), 1);
        }
    }
    //?}
}