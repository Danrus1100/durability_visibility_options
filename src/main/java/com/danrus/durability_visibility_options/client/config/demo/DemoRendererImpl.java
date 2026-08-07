package com.danrus.durability_visibility_options.client.config.demo;

// DISCLAIMER:
// I cant write a good code, so try to make this better if
// u wanna use it in your mod.

import com.danrus.durability_visibility_options.client.DurabilityRender;
import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import com.danrus.durability_visibility_options.client.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

//? if >=26.1
import java.util.Objects;

public class DemoRendererImpl implements DemoRenderer {
    private final DemoItems demoItems = DemoItems.getInstance();
    private boolean useSpecificConfig = false;
    private DurabilityConfig config = DurabilityConfig.fromModConfig();

    public DemoRendererImpl() {}

    public DemoRendererImpl(DurabilityConfig config) {
        this.config = config;
        this.useSpecificConfig = true;
        demoItems.initializeItemList();
    }

    @Override
    public int render(
        GuiGraphicsExtractor context,
        int x,
        int y,
        int width,
        float deltaTime
    ) {

        int itemX = (int) (x + Minecraft.getInstance().getWindow().getGuiScaledWidth() / 7.5);
        int itemY = y + 45;
        float scale = 6F;

        DrawUtils.pushMatrix(context);

        DrawUtils.translateMatrix(context, itemX + 8, itemY + 8);
        DrawUtils.scaleMatrix(context, scale, scale);

        //? if >=26.1 {
        context.item(
        //?} else
        //context.renderItem(

            //? if >=26.1 {
            Objects.requireNonNull(demoItems.getActiveItem()),
            //?} else
            //demoItems.getActiveItem(),
            -8,
            -8
        );

        DrawUtils.popMatrix(context);

        DrawUtils.pushMatrix(context);

        DrawUtils.translateMatrix(context, itemX, itemY);
        DrawUtils.scaleMatrix(context, scale, scale);

        if (config.showDurability) {DurabilityRender.renderBar(context, demoItems.getData(), -7, -6, config);}
        if (config.showDurabilityPercent) {DurabilityRender.renderPercents(context, demoItems.getData(),  -7, -6, config);}
        if (config.showDurabilityAmount) {DurabilityRender.renderAmount(context, demoItems.getData(), -7, -6, config);}

        DrawUtils.popMatrix(context);

        return 56;
    }

    public void tick() {
        if (!this.useSpecificConfig) {
            config = DurabilityConfig.fromModConfig();
        }
        demoItems.tick();
    }
}