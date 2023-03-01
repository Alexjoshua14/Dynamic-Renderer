package com.alexjoshua14.raytracer.scene;

import lombok.Value;

@Value
public class ScenePixelColor {
    public static final float MAX = 1;
    public static final ScenePixelColor BLACK = new ScenePixelColor(0, 0, 0);

    /* r, g, and b are percentages that should stay between 0 and 1 inclusive */
    private float r;
    private float g;
    private float b;

    public ScenePixelColor(float r, float g, float b) {
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

    public ScenePixelColor times(ScenePixelColor otherColor) {
        float newR = r * otherColor.getR();
        float newG = g * otherColor.getG();
        float newB = b * otherColor.getB();
        return new ScenePixelColor(newR, newG, newB);
    }

    public ScenePixelColor divide(int num) {
        float newR = r / num;
        float newG = g / num;
        float newB = b / num;
        return new ScenePixelColor(newR, newG, newB);
    }

    public ScenePixelColor times(float num) {
        return new ScenePixelColor(r * num, g * num, b * num);
    }

    public ScenePixelColor plus(ScenePixelColor otherColor) {
        float newR = r + otherColor.getR();
        float newG = g + otherColor.getG();
        float newB = b + otherColor.getB();
        return new ScenePixelColor(newR, newG, newB);
    }

    public ScenePixelColor minus(ScenePixelColor otherColor) {
        float newR = r - otherColor.getR();
        float newG = g - otherColor.getG();
        float newB = b - otherColor.getB();
        return new ScenePixelColor(newR, newG, newB);
    }

    public ScenePixelColor clamped() {
        float newR = r > 0 ? Math.min(MAX, r) : 0;
        float newG = g > 0 ? Math.min(MAX, g) : 0;
        float newB = b > 0 ? Math.min(MAX, b) : 0;
        return new ScenePixelColor(newR, newG, newB);
    }
}
