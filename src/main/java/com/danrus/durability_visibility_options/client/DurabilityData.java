package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import net.minecraft.world.item.ItemStack;

public class DurabilityData {
    public int barStep;
    public int maxDurability;
    public int durability;
    public boolean isDamaged;
    private float percents;

    public DurabilityData(ItemStack stack) {
        this.barStep = stack.getBarWidth();
        this.durability = stack.getMaxDamage() - stack.getDamageValue();
        this.maxDurability = stack.getMaxDamage();
        this.isDamaged = stack.isDamaged();
        this.percents = 100F * (float)durability / maxDurability;
    }

    public String getPercentString(DurabilityConfig config) {
        String result = String.format("%." + config.percentAccuracy + "f", this.percents);
        if (config.showPercentSymbol) {
            result += "%";
        }
        return result;
    }

    public int getPercentsInt() {
        return Math.round(percents);
    }

    public float getPercents() {
        return percents;
    }

    public void reducePercents(float amount) {
        setPercents(this.percents - amount);
    }

    public void setPercents(float value) {
        if (value > 100F) {
            this.percents = 100F;
            this.durability = this.maxDurability;
            return;
        } else if (value < 0F) {
            this.durability = 0;
            this.percents = 0F;
            return;
        }
        this.durability = (int) (durability - percents / 100 * maxDurability);
        this.percents = value;
    }
}
