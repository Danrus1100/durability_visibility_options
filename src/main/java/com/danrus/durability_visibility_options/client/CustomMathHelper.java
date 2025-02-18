package com.danrus.durability_visibility_options.client;

public class CustomMathHelper {
    public static float[] rgbToHsv(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return rgbToHsv(r, g, b);
    }

    public static float[] rgbToHsv(int r, int g, int b) {
        float[] hsv = new float[3];

        float red = r / 255.0f;
        float green = g / 255.0f;
        float blue = b / 255.0f;

        float max = Math.max(red, Math.max(green, blue));
        float min = Math.min(red, Math.min(green, blue));
        float delta = max - min;

        // Hue calculation
        if (delta == 0) {
            hsv[0] = 0;
        } else if (max == red) {
            hsv[0] = 60 * (((green - blue) / delta) % 6);
        } else if (max == green) {
            hsv[0] = 60 * (((blue - red) / delta) + 2);
        } else {
            hsv[0] = 60 * (((red - green) / delta) + 4);
        }

        if (hsv[0] < 0) {
            hsv[0] += 360;
        }

        // Saturation calculation
        if (max == 0) {
            hsv[1] = 0;
        } else {
            hsv[1] = delta / max;
        }

        // Value calculation
        hsv[2] = max;

        return hsv;
    }

    public static float[] interpolateHsv (float[] start, float[] end, float t) {
        float[] result = new float[3];

        result[0] = start[0] + (end[0] - start[0]) * t;
        result[1] = start[1] + (end[1] - start[1]) * t;
        result[2] = start[2] + (end[2] - start[2]) * t;

        return result;
    }
}
