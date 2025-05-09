package com.danrus.durability_visibility_options.client.config;

public class DurabilityConfig {
    public boolean showDurability = true;
    public boolean isVertical = false;
    public int showDurabilityBarFromPercent = 99;
    public int durabilityBarOffsetX = 0;
    public int durabilityBarOffsetY = 0;
    public int durabilityBarColor = 0x00FF00;
    public int durabilityBarColorMin = 0xFF0000;

    public boolean showDurabilityPercent = false;
    public boolean showPercentSymbol = true;
    public int durabilityPercentOffsetX = 0;
    public int durabilityPercentOffsetY = 0;
    public int showDurabilityPercentsFromPercent = 99;
    public int durabilityPercentColor = 0xFFFFFF;

    public DurabilityConfig() {}

    public DurabilityConfig(DurabilityConfig other) {
        copyFrom(other);
    }

    public static DurabilityConfig fromModConfig() {
        DurabilityConfig config = new DurabilityConfig();
        ModConfig modConfig = ModConfig.get();

        config.showDurability = modConfig.showDurability;
        config.isVertical = modConfig.isVertical;
        config.showDurabilityBarFromPercent = modConfig.showDurabilityBarFromPercent;
        config.durabilityBarOffsetX = modConfig.durabilityBarOffsetX;
        config.durabilityBarOffsetY = modConfig.durabilityBarOffsetY;
        config.durabilityBarColor = modConfig.durabilityBarColor;
        config.durabilityBarColorMin = modConfig.durabilityBarColorMin;

        config.showDurabilityPercent = modConfig.showDurabilityPercent;
        config.showPercentSymbol = modConfig.showPercentSymbol;
        config.durabilityPercentOffsetX = modConfig.durabilityPercentOffsetX;
        config.durabilityPercentOffsetY = modConfig.durabilityPercentOffsetY;
        config.showDurabilityPercentsFromPercent = modConfig.showDurabilityPercentsFromPercent;
        config.durabilityPercentColor = modConfig.durabilityPercentColor;

        return config;
    }

    // Метод для копирования значений из другого конфига
    private void copyFrom(DurabilityConfig other) {
        this.showDurability = other.showDurability;
        this.isVertical = other.isVertical;
        this.showDurabilityBarFromPercent = other.showDurabilityBarFromPercent;
        this.durabilityBarOffsetX = other.durabilityBarOffsetX;
        this.durabilityBarOffsetY = other.durabilityBarOffsetY;
        this.durabilityBarColor = other.durabilityBarColor;
        this.durabilityBarColorMin = other.durabilityBarColorMin;

        this.showDurabilityPercent = other.showDurabilityPercent;
        this.showPercentSymbol = other.showPercentSymbol;
        this.durabilityPercentOffsetX = other.durabilityPercentOffsetX;
        this.durabilityPercentOffsetY = other.durabilityPercentOffsetY;
        this.showDurabilityPercentsFromPercent = other.showDurabilityPercentsFromPercent;
        this.durabilityPercentColor = other.durabilityPercentColor;
    }

    public DurabilityConfig clone() {
        return new DurabilityConfig(this);
    }

    public DurabilityConfig getWithSpecificParams(
            boolean showDurability,
            boolean isVertical,
            int showDurabilityBarFromPercent,
            int durabilityBarOffsetX,
            int durabilityBarOffsetY,
            int durabilityBarColor,
            int durabilityBarColorMin,

            boolean showDurabilityPercent,
            boolean showPercentSymbol,
            int durabilityPercentOffsetX,
            int durabilityPercentOffsetY,
            int showDurabilityPercentsFromPercent,
            int durabilityPercentColor
    ) {
        this.showDurability = showDurability;
        this.isVertical = isVertical;
        this.showDurabilityBarFromPercent = showDurabilityBarFromPercent;
        this.durabilityBarOffsetX = durabilityBarOffsetX;
        this.durabilityBarOffsetY = durabilityBarOffsetY;
        this.durabilityBarColor = durabilityBarColor;
        this.durabilityBarColorMin = durabilityBarColorMin;

        this.showDurabilityPercent = showDurabilityPercent;
        this.showPercentSymbol = showPercentSymbol;
        this.durabilityPercentOffsetX = durabilityPercentOffsetX;
        this.durabilityPercentOffsetY = durabilityPercentOffsetY;
        this.showDurabilityPercentsFromPercent = showDurabilityPercentsFromPercent;
        this.durabilityPercentColor = durabilityPercentColor;

        return this;
    }

}
