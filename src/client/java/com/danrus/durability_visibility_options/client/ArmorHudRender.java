package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

public class ArmorHudRender implements HudRenderCallback {

    public MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (!ModConfig.showArmorDurabilityHud) {
            return;
        }

        if (!ModConfig.showArmorDurabilityHudInCreative) {
            assert client.player != null;
            if (client.player.isCreative()) {
                return;
            }
        }

        MatrixStack matrices = drawContext.getMatrices();
        TextRenderer textRenderer = client.textRenderer;
        matrices.push();
        matrices.translate(calculateRootMatrixPosition().get(0), calculateRootMatrixPosition().get(1), 500);
        float scale = ModConfig.armorDurabilityHudScale;
        matrices.scale(scale, scale, scale);

        getArmorItems().forEach(itemStack -> {
            if (itemStack.getItem() == Items.AIR) {
                return;
            }
            drawArmorPieceAndDurability(drawContext, textRenderer, itemStack, 0, 0);
            if (ModConfig.armorHudAlignment == ModConfig.armorDurabilityHudAlignment.VERTICAL) {
                matrices.translate(0, ModConfig.armorDurabilityHudMirgin, 0);
            } else {
                String durability = getDurabilityText(itemStack);
                if (ModConfig.showArmorDurabilityHudPercentSymbol) {
                    durability += "%";
                }
                matrices.translate(ModConfig.armorDurabilityHudMirgin+textRenderer.getWidth(durability), 0, 0);
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

    private String getDurabilityText(ItemStack stack) {
        String durability = String.valueOf((int) ((float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage() * 100));
        if (ModConfig.showArmorDurabilityHudPercentSymbol) {
            durability += "%";
        }
        return durability;
    }

    private void drawArmorPieceAndDurability(DrawContext drawContext, TextRenderer textRenderer,  ItemStack stack, int x, int y) {
        if (stack.getItem() == Items.AIR) {
            return;
        }
        String durability = getDurabilityText(stack);
        int percentWidth = textRenderer.getWidth(durability);
        int itemWidth = 16;

        if (ModConfig.armorHudDisplayStyle == ModConfig.armorDurabilityHudDisplayStyle.ICON_PERCENT) {
            drawContext.drawItem(stack, x - itemWidth, y);
            drawContext.drawTextWithShadow(textRenderer, durability, x , y + 4, ModConfig.armorDurabilityHudTextColor);
        } else {
            drawContext.drawTextWithShadow(textRenderer, durability, x - itemWidth, y+4, ModConfig.armorDurabilityHudTextColor);
            drawContext.drawItem(stack, x+percentWidth-itemWidth, y);
        }


    }

    private List<Integer> calculateRootMatrixPosition() {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String durability = "100";
        if (ModConfig.showArmorDurabilityHudPercentSymbol) {
            durability += "%";
        }
        int textMaxWidth = textRenderer.getWidth(durability);
        int iconWidth = 16;

        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        int x = switch (ModConfig.armorHudPositionHorizontal) {
            case LEFT -> calculateHorizontalOffset() + iconWidth;
//            case CENTER -> (width / 2) + calculateHorizontalOffset();
            case CENTER -> switch (ModConfig.armorHudAlignment) {
                case HORIZONTAL -> ((width / 2) - (getArmorItems().size() * (iconWidth + textMaxWidth + ModConfig.armorDurabilityHudMirgin - 24)) / 2) + calculateHorizontalOffset();
                case VERTICAL -> (width / 2) + calculateHorizontalOffset();
            };
            case RIGHT -> width - calculateHorizontalOffset() - textMaxWidth;
        };

        int y = switch (ModConfig.armorHudPositionVertical) {
            case TOP -> calculateVerticalOffset();
            case CENTER -> (height / 2) + calculateVerticalOffset();
            case BOTTOM -> switch (ModConfig.armorHudAlignment) {
                case HORIZONTAL -> height + calculateVerticalOffset() - 17 - 10;
                case VERTICAL -> height + calculateVerticalOffset() - (17 * getArmorItems().size()) - 10;
            };
        };


        return List.of(x, y);
    }

    private int calculateHorizontalOffset() {
//        return ModConfig.armorDurabilityHudOffsetX;
        return switch (ModConfig.armorHudPositionHorizontal){
            case LEFT -> ModConfig.armorDurabilityHudOffsetX;
            case CENTER -> ModConfig.armorDurabilityHudOffsetX;
            case RIGHT -> -ModConfig.armorDurabilityHudOffsetX;
        };
    }

    private int calculateVerticalOffset() {
//        return ModConfig.armorDurabilityHudOffsetY;
        return switch (ModConfig.armorHudPositionVertical){
            case TOP -> ModConfig.armorDurabilityHudOffsetY;
            case CENTER -> ModConfig.armorDurabilityHudOffsetX;
            case BOTTOM -> -ModConfig.armorDurabilityHudOffsetY;
        };
    }
}
