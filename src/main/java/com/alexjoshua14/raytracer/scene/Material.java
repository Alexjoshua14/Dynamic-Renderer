package com.alexjoshua14.raytracer.scene;

import lombok.Value;

@Value
public class Material {
    private Color kAmbient;
    private Color kDiffuse; 
    private Color kSpecular;
    private Color kReflection;
    private int alpha;

    public Material(Color kAmbient, Color kDiffuse, Color kSpecular, Color kReflection, int alpha)  {
        this.kAmbient = kAmbient;
        this.kDiffuse = kDiffuse;
        this.kSpecular = kSpecular;
        this.kReflection = kReflection;
        this.alpha = alpha;
    }

    public Color getKAmbient() {
        return this.kAmbient;   
    }

    public Color getKDiffuse() {
        return this.kDiffuse;   
    }

    public Color getKSpecular() {
        return this.kSpecular;   
    }

    public Color getKReflection() {
        return this.kReflection;   
    }

    public int getAlpha() {
        return this.alpha;
    }
}
