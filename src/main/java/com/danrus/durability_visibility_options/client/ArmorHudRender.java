package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class ArmorHudRender implements HudRenderCallback {


    public MinecraftClient client = MinecraftClient.getInstance();

    //? if <=1.20.6 {
    /*@Override
    public void onHudRender(DrawContext drawContext, float v) {
        RenderTickCounter renderTickCounter = new RenderTickCounter(0.0F, 0L /^? if >1.20.2 {^/, f -> 0.0F/^?}^/);
        onHudRender(drawContext, renderTickCounter);
    }
    *///?}

    //? if >1.20.6
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (!ModConfig.get().showArmorDurabilityHud) {
            return;
        }

        if (!ModConfig.get().showArmorDurabilityHudInCreative) {
            assert client.player != null;
            if (client.player.isCreative()) {
                return;
            }
        }

        MatrixStack matrices = drawContext.getMatrices();
        TextRenderer textRenderer = client.textRenderer;
        matrices.push();
        matrices.translate(calculateRootMatrixPosition().get(0), calculateRootMatrixPosition().get(1), 500);
        float scale = ModConfig.get().armorDurabilityHudScale;
        matrices.scale(scale, scale, scale);

        getArmorItems().forEach(itemStack -> {
            if (itemStack.getItem() == Items.AIR || getDurabilityPercent(itemStack) > ModConfig.get().showArmorDurabilityHudFromPercent) {
                return;
            }
            drawArmorPieceAndDurability(drawContext, textRenderer, itemStack);
            if (ModConfig.get().armorHudAlignment == ModConfig.ArmorAlignment.VERTICAL) {
                matrices.translate(0, ModConfig.get().armorDurabilityHudMirgin, 0);
            } else {
                String durability = getDurabilityText(itemStack);
                if (ModConfig.get().showArmorDurabilityHudPercentSymbol) {
                    durability += "%";
                }
                matrices.translate(ModConfig.get().armorDurabilityHudMirgin+textRenderer.getWidth(durability), 0, 0);
            }
        });


        matrices.pop();
    }

    // ---------------------------------------------------------------

    public static List<ItemStack> getArmorItems() {
        assert MinecraftClient.getInstance().player != null;
        List<ItemStack> itemsOriginal = MinecraftClient.getInstance().player.getInventory().armor.reversed();
       List<ItemStack> items = new java.util.ArrayList<>(List.of());
       for (int i = 0; i < 4; i++) {
           if (itemsOriginal.get(i).getItem() != Items.AIR) {
               items.add(itemsOriginal.get(i));
           }
       }
       return items;
    }

    private int getDurabilityPercent(ItemStack stack) {
        return (int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100);
    }

    private String getDurabilityText(ItemStack stack) {
        String durability = String.valueOf(getDurabilityPercent(stack));
        if (ModConfig.get().showArmorDurabilityHudPercentSymbol) {
            durability += "%";
        }
        return durability;
    }

    private void drawArmorPieceAndDurability(DrawContext drawContext, TextRenderer textRenderer,  ItemStack stack) {
        if (stack.getItem() == Items.AIR) {
            return;
        }
        String durability = getDurabilityText(stack);
        int percentWidth = textRenderer.getWidth(durability);
        int itemWidth = 24;

        switch (ModConfig.get().armorHudDisplayStyle) {
            case PERCENT_ICON -> {
                drawContext.drawItem(stack, itemWidth, 0);
                drawContext.drawTextWithShadow(textRenderer, durability, 0 , 4, ModConfig.get().armorDurabilityHudTextColor);
            }
            case ICON_PERCENT -> {
                drawContext.drawTextWithShadow(textRenderer, durability, itemWidth, 4, ModConfig.get().armorDurabilityHudTextColor);
                drawContext.drawItem(stack, percentWidth-itemWidth+8, 0);
            }
        }

    }

    private List<Integer> calculateRootMatrixPosition() {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String durability = "100";
        if (ModConfig.get().showArmorDurabilityHudPercentSymbol) {
            durability += "%";
        }
        int textMaxWidth = textRenderer.getWidth(durability);
        int iconWidth = 16;

        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        int x = switch (ModConfig.get().armorHudPositionHorizontal) {
            case LEFT -> calculateHorizontalOffset() + iconWidth;
            case CENTER -> switch (ModConfig.get().armorHudAlignment) {
                case HORIZONTAL -> ((width / 2) - (getArmorItems().size() * (iconWidth + textMaxWidth + ModConfig.get().armorDurabilityHudMirgin - 24)) / 2) + calculateHorizontalOffset();
                case VERTICAL -> (width / 2) + calculateHorizontalOffset();
            };
            case RIGHT -> switch (ModConfig.get().armorHudAlignment) {
                case HORIZONTAL -> width - calculateHorizontalOffset() - textMaxWidth * getArmorItems().size() - iconWidth * getArmorItems().size();
                case VERTICAL -> width - calculateHorizontalOffset() - textMaxWidth;
            };
        };

        x += switch (ModConfig.get().armorHudDisplayStyle) {
            case ICON_PERCENT -> -22;
            case PERCENT_ICON -> -15;
        };

        int y = switch (ModConfig.get().armorHudPositionVertical) {
            case TOP -> calculateVerticalOffset();
            case CENTER -> (height / 2) + calculateVerticalOffset();
            case BOTTOM -> switch (ModConfig.get().armorHudAlignment) {
                case HORIZONTAL -> height + calculateVerticalOffset() - 17 - 10;
                case VERTICAL -> height + calculateVerticalOffset() - (17 * getArmorItems().size()) - 10;
            };
        };


        return List.of(x, y);
    }

    private int calculateHorizontalOffset() {
        return switch (ModConfig.get().armorHudPositionHorizontal){
            case LEFT, CENTER -> ModConfig.get().armorDurabilityHudOffsetX;
            case RIGHT -> -ModConfig.get().armorDurabilityHudOffsetX;
        };
    }

    public static int getHealthRows(PlayerEntity player) {
        float maxHealth = player.getMaxHealth();
        int absorption = (int) player.getAbsorptionAmount();
        return MathHelper.ceil((maxHealth + absorption) / 20.0F);
    }

    public static boolean isAirBarVisible(PlayerEntity player) {
        return player.getAir() < player.getMaxAir();
    }

    private int calculateVerticalOffset() {
        return switch (ModConfig.get().armorHudPositionVertical){
            case TOP, CENTER -> ModConfig.get().armorDurabilityHudOffsetY;
            case BOTTOM -> {
                assert client.player != null;
                int addY = switch (ModConfig.get().armorVanillaStatsAdapt) {
                    case HEARTS -> -(getHealthRows(client.player) * 10);
                    case AIR -> -(isAirBarVisible(client.player) ? 10 : 0);
                    case NONE -> 0;
                };
                yield -ModConfig.get().armorDurabilityHudOffsetY + addY;
            }
        };
    }

}
