package com.alexjoshua14.raytracer.scene;

import lombok.Value;

@Value
public class Color {
    public static final float MAX = 1;
    public static final Color BLACK = new Color(0, 0, 0);

    /* r, g, and b are percentages that should stay between 0 and 1 inclusive */
    private float r;
    private float g;
    private float b;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public Color times(Color otherColor) {
        float newR = r * otherColor.getR();
        float newG = g * otherColor.getG();
        float newB = b * otherColor.getB();
        return new Color(newR, newG, newB);
    }

    public Color divide(int num) {
        float newR = r / num;
        float newG = g / num;
        float newB = b / num;
        return new Color(newR, newG, newB);
    }

    public Color times(float num) {
        return new Color(r * num, g * num, b * num);
    }

    public Color plus(Color otherColor) {
        float newR = r + otherColor.getR();
        float newG = g + otherColor.getG();
        float newB = b + otherColor.getB();
        return new Color(newR, newG, newB);
    }

    public Color minus(Color otherColor) {
        float newR = r - otherColor.getR();
        float newG = g - otherColor.getG();
        float newB = b - otherColor.getB();
        return new Color(newR, newG, newB);
    }

    public Color clamped() {
        float newR = r > 0 ? Math.min(MAX, r) : 0;
        float newG = g > 0 ? Math.min(MAX, g) : 0;
        float newB = b > 0 ? Math.min(MAX, b) : 0;
        return new Color(newR, newG, newB);
    }
}
