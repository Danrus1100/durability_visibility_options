package com.danrus.dvo.config.demo;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class DemoStackFactory {
    private DemoStackFactory() {}

    public static ItemStack create(Item item) {
        DataComponentMap.Builder components = DataComponentMap.builder()
                .addAll(DataComponents.COMMON_ITEM_COMPONENTS)
                .set(DataComponents.ITEM_NAME, Component.translatable(getTranslationKey(item)))
                .set(DataComponents.ITEM_MODEL, getModel(item));

        int maxDamage = getMaxDamage(item);

        if (maxDamage > 0) {
            components
                    .set(DataComponents.MAX_STACK_SIZE, 1)
                    .set(DataComponents.MAX_DAMAGE, maxDamage)
                    .set(DataComponents.DAMAGE, 0);
        }

        return new ItemStack(Holder.direct(item, components.build()), 1);
    }

    private static Identifier getModel(Item item) {
        if (item == Items.ELYTRA) return Identifier.withDefaultNamespace("elytra");
        if (item == Items.NETHERITE_PICKAXE) return Identifier.withDefaultNamespace("netherite_pickaxe");
        if (item == Items.DIAMOND_HOE) return Identifier.withDefaultNamespace("diamond_hoe");
        if (item == Items.BOW) return Identifier.withDefaultNamespace("bow");
        if (item == Items.TRIDENT) return Identifier.withDefaultNamespace("trident");
        if (item == Items.BRUSH) return Identifier.withDefaultNamespace("brush");
        if (item == Items.WOODEN_SWORD) return Identifier.withDefaultNamespace("wooden_sword");
        if (item == Items.FLINT_AND_STEEL) return Identifier.withDefaultNamespace("flint_and_steel");

        return Identifier.withDefaultNamespace("air");
    }

    private static String getTranslationKey(Item item) {
        if (item == Items.ELYTRA) return "item.minecraft.elytra";
        if (item == Items.NETHERITE_PICKAXE) return "item.minecraft.netherite_pickaxe";
        if (item == Items.DIAMOND_HOE) return "item.minecraft.diamond_hoe";
        if (item == Items.BOW) return "item.minecraft.bow";
        if (item == Items.TRIDENT) return "item.minecraft.trident";
        if (item == Items.BRUSH) return "item.minecraft.brush";
        if (item == Items.WOODEN_SWORD) return "item.minecraft.wooden_sword";
        if (item == Items.FLINT_AND_STEEL) return "item.minecraft.flint_and_steel";

        return "item.minecraft.air";
    }

    private static int getMaxDamage(Item item) {
        if (item == Items.ELYTRA) return 432;
        if (item == Items.NETHERITE_PICKAXE) return 2031;
        if (item == Items.DIAMOND_HOE) return 1561;
        if (item == Items.BOW) return 384;
        if (item == Items.TRIDENT) return 250;
        if (item == Items.BRUSH) return 64;
        if (item == Items.WOODEN_SWORD) return 59;
        if (item == Items.FLINT_AND_STEEL) return 64;

        return 0;
    }
}