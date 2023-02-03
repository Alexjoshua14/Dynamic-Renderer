package com.alexjoshua14.raytracer.scene;

import lombok.Value;

@Value
public class Color {
    float r;
    float g;
    float b;

    public Color(float r, float g, float b) {
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
