package xipe.utils.render;

import java.awt.Color;

import xipe.module.Mod;

public class ColorUtils {

    public static int defaultClientColor = new Color(255, 20, 100).getRGB();

    public static Color defaultClientColor() {
        return new Color(255, 20, 100);
    }

    public static int rainbow(float seconds, float saturation, float brightness) {
        float hue = (float)(System.currentTimeMillis() % (long)((int)(seconds * 1000.0f))) / (seconds * 1000.0f);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int rainbow(float seconds, float saturation, float brigtness, long index) {
        float hue = (float)((System.currentTimeMillis() + index) % (long)((int)(seconds * 1000.0f))) / (seconds * 1000.0f);
        int color = Color.HSBtoRGB(hue, saturation, 1.0f);
        return color;
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0f + (float)index / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color fade(Color color, Color color2, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() + (long)index % 2000L) / 1000.0f + (float)index / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color getColor(int color) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        return new Color(red, green, blue, alpha);
    }

    public static int transparent(int rgb, int opacity) {
        Color color = new Color(rgb);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity).getRGB();
    }

    public static int transparent(int opacity) {
        Color color = Color.BLACK;
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity).getRGB();
    }

    public static int blendColours(int[] colours, double progress) {
        int size = colours.length;
        if (progress == 1.0) {
            return colours[0];
        }
        if (progress == 0.0) {
            return colours[size - 1];
        }
        double mulProgress = Math.max(0.0, (1.0 - progress) * (double)(size - 1));
        int index = (int)mulProgress;
        return ColorUtils.fadeBetween(colours[index], colours[index + 1], mulProgress - (double)index);
    }

    public static int fadeBetween(int startColour, int endColour, double progress) {
        if (progress > 1.0) {
            progress = 1.0 - progress % 1.0;
        }
        return ColorUtils.fadeTo(startColour, endColour, progress);
    }

    public static int fadeBetween(int startColour, int endColour, long offset) {
        return ColorUtils.fadeBetween(startColour, endColour, (double)((System.currentTimeMillis() + offset) % 2000L) / 1000.0);
    }

    public static int fadeBetween(int startColour, int endColour) {
        return ColorUtils.fadeBetween(startColour, endColour, 0L);
    }

    public static int fadeTo(int startColour, int endColour, double progress) {
        double invert = 1.0 - progress;
        int r = (int)((double)(startColour >> 16 & 0xFF) * invert + (double)(endColour >> 16 & 0xFF) * progress);
        int g = (int)((double)(startColour >> 8 & 0xFF) * invert + (double)(endColour >> 8 & 0xFF) * progress);
        int b = (int)((double)(startColour & 0xFF) * invert + (double)(endColour & 0xFF) * progress);
        int a = (int)((double)(startColour >> 24 & 0xFF) * invert + (double)(endColour >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public Color hex(String hex) {
        return Color.decode(hex);
    }

    public Color rgba(int red, int green, int blue, int alpha) {
        return new Color(red, green, blue, alpha);
    }

    public Color rgb(int red, int green, int blue) {
        return this.rgba(red, green, blue, 255);
    }

    public static Color hexToRgb(String hex) {
        try {
            return Color.decode("#" + hex.replace("#", ""));
        }
        catch (NumberFormatException e) {
            System.err.println("Invalid hex string!");
            return Color.WHITE;
        }
    }
}