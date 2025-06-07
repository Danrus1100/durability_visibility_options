package com.danrus.durability_visibility_options.client.config;

public class DurabilityConfig {

    public String key;

    public boolean showDurability = true;
    public boolean isVertical = false;
    public boolean showDurabilityBarUnderItem = false;
    public boolean showDurabilityBarBackground = true;
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
    public float durabilityPercentScale = 0.5f;
    public int durabilityPercentColor = 0xFFFFFF;
    public int durabilityPercentColorMin = 0xFFFFFF;

    public boolean doRgbBar = false;

    public static final class Builder {
        private final DurabilityConfig config = new DurabilityConfig();

        public Builder fromModConfig() {
            ModConfig modConfig = ModConfig.get();
            return this
                    .setShowDurability(modConfig.showDurability)
                    .setVertical(modConfig.isVertical)
                    .setShowDurabilityBarUnderItem(modConfig.showDurabilityBarUnderItem)
                    .setShowDurabilityBarBackground(modConfig.showDurabilityBarBackground)
                    .setShowDurabilityBarFromPercent(modConfig.showDurabilityBarFromPercent)
                    .setDurabilityBarOffsetX(modConfig.durabilityBarOffsetX)
                    .setDurabilityBarOffsetY(modConfig.durabilityBarOffsetY)
                    .setDurabilityBarColor(modConfig.durabilityBarColor)
                    .setDurabilityBarColorMin(modConfig.durabilityBarColorMin)
                    .setShowDurabilityPercent(modConfig.showDurabilityPercent)
                    .setShowPercentSymbol(modConfig.showPercentSymbol)
                    .setDurabilityPercentOffsetX(modConfig.durabilityPercentOffsetX)
                    .setDurabilityPercentOffsetY(modConfig.durabilityPercentOffsetY)
                    .setShowDurabilityPercentsFromPercent(modConfig.showDurabilityPercentsFromPercent)
                    .setDurabilityPercentScale(modConfig.durabilityPercentScale)
                    .setDurabilityPercentColor(modConfig.durabilityPercentColor)
                    .setDurabilityPercentColorMin(modConfig.durabilityPercentColorMin)
                    .setDoRgbBar(modConfig.doRgbBar);
        }

        public Builder setKey(String key) {
            config.key = key;
            return this;
        }

        public Builder setShowDurability(boolean value) {
            config.showDurability = value;
            return this;
        }

        public Builder setVertical(boolean value) {
            config.isVertical = value;
            return this;
        }

        public Builder setShowDurabilityBarUnderItem(boolean value) {
            config.showDurabilityBarUnderItem = value;
            return this;
        }

        public Builder setShowDurabilityBarBackground(boolean value) {
            config.showDurabilityBarBackground = value;
            return this;
        }

        public Builder setShowDurabilityBarFromPercent(int value) {
            config.showDurabilityBarFromPercent = value;
            return this;
        }

        public Builder setDurabilityBarOffsetX(int value) {
            config.durabilityBarOffsetX = value;
            return this;
        }

        public Builder setDurabilityBarOffsetY(int value) {
            config.durabilityBarOffsetY = value;
            return this;
        }

        public Builder setDurabilityBarColor(int value) {
            config.durabilityBarColor = value;
            return this;
        }

        public Builder setDurabilityBarColorMin(int value) {
            config.durabilityBarColorMin = value;
            return this;
        }

        public Builder setShowDurabilityPercent(boolean value) {
            config.showDurabilityPercent = value;
            return this;
        }

        public Builder setShowPercentSymbol(boolean value) {
            config.showPercentSymbol = value;
            return this;
        }

        public Builder setDurabilityPercentOffsetX(int value) {
            config.durabilityPercentOffsetX = value;
            return this;
        }

        public Builder setDurabilityPercentOffsetY(int value) {
            config.durabilityPercentOffsetY = value;
            return this;
        }

        public Builder setShowDurabilityPercentsFromPercent(int value) {
            config.showDurabilityPercentsFromPercent = value;
            return this;
        }

        public Builder setDurabilityPercentScale(float value) {
            config.durabilityPercentScale = value;
            return this;
        }

        public Builder setDurabilityPercentColor(int value) {
            config.durabilityPercentColor = value;
            return this;
        }

        public Builder setDurabilityPercentColorMin(int value) {
            config.durabilityPercentColorMin = value;
            return this;
        }

        public Builder setDoRgbBar(boolean value) {
            config.doRgbBar = value;
            return this;
        }

        public DurabilityConfig build() {
            return config;
        }
    }

    public DurabilityConfig() {}

    public static Builder builder() {
        return new Builder();
    }

    public static DurabilityConfig fromModConfig() {
        return builder().fromModConfig().build();
    }

}
