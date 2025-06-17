package com.danrus.durability_visibility_options.client;

import com.danrus.durability_visibility_options.client.config.DurabilityConfig;
import net.minecraft.item.ItemStack;

public class DurabilityData {
    public int barStep;
    public int maxDurability;
    public int durability;
    private float percents;

    public DurabilityData(ItemStack stack) {
        this.barStep = stack.getItemBarStep();
        this.durability = stack.getMaxDamage() - stack.getDamage();
        this.maxDurability = stack.getMaxDamage();
        this.percents = 100F - ((float)(maxDurability - durability) / maxDurability) * 100;
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
