package com.alexjoshua14.raytracer.image;

import lombok.Value;

@Value
public class ImageColor {
    public static final int MAX = 0xff;

    /* r, g, and b are values between 0 and 255 inclusive */
    int r;
    int g;
    int b;

    public ImageColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getR() {
        return this.r;
    }

    public float getG() {
        return this.g;
    }

    public float getB() {
        return this.b;
    }
}
