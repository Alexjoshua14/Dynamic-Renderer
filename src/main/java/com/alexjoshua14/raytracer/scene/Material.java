package com.alexjoshua14.raytracer.scene;

import lombok.Value;

@Value
public class Material {
    private ScenePixelColor kAmbient;
    private ScenePixelColor kDiffuse; 
    private ScenePixelColor kSpecular;
    private ScenePixelColor kReflection;
    private int alpha;

    public Material(ScenePixelColor kAmbient, ScenePixelColor kDiffuse, ScenePixelColor kSpecular, ScenePixelColor kReflection, int alpha)  {
        this.kAmbient = kAmbient;
        this.kDiffuse = kDiffuse;
        this.kSpecular = kSpecular;
        this.kReflection = kReflection;
        this.alpha = alpha;
    }

    public ScenePixelColor getKAmbient() {
        return this.kAmbient;   
    }

    public ScenePixelColor getKDiffuse() {
        return this.kDiffuse;   
    }

    public ScenePixelColor getKSpecular() {
        return this.kSpecular;   
    }

    public ScenePixelColor getKReflection() {
        return this.kReflection;   
    }

    public int getAlpha() {
        return this.alpha;
    }
}
