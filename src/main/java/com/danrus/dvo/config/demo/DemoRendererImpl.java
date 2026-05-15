package com.danrus.dvo.config.demo;

import com.danrus.dvo.DurabilityRender;
import com.danrus.dvo.config.DurabilityConfig;
import com.danrus.dvo.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class DemoRendererImpl implements DemoRenderer {
    private final DemoItems demoItems = DemoItems.getInstance();
    private boolean useSpecificConfig = false;
    private DurabilityConfig config = DurabilityConfig.fromModConfig();

    public DemoRendererImpl() {}

    public DemoRendererImpl(DurabilityConfig config) {
        this.config = config;
        this.useSpecificConfig = true;
    }

    @Override
    public int render(GuiGraphicsExtractor context, int x, int y, int width, float deltaTime) {

        int itemX = (int) (x + Minecraft.getInstance().getWindow().getGuiScaledWidth() / 7.5);
        int itemY = y + 45;
        float scale = 6F;

        var pose = context.pose();
        pose.pushMatrix();


        pose.translate(itemX + 8, itemY + 8);
        pose.scale(scale);

        context.fakeItem(demoItems.getActiveItem(), -8, -8);

        pose.popMatrix();

        pose.pushMatrix();

        pose.translate(itemX, itemY);
        pose.scale(scale);

        if (config.showDurability) {DurabilityRender.renderBar(context, demoItems.getData(), -7, -6, config);}
        if (config.showDurabilityPercent) {DurabilityRender.renderPercents(context, demoItems.getData(),  -7, -6, config);}
        if (config.showDurabilityAmount) {DurabilityRender.renderAmount(context, demoItems.getData(), -7, -6, config);}

        pose.popMatrix();

        return 56;
    }

    public void tick() {
        if (!this.useSpecificConfig) {
            config = DurabilityConfig.fromModConfig();
        }
        demoItems.tick();
    }
}