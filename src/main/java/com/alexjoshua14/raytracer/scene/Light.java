package com.alexjoshua14.raytracer.scene;

import lombok.Value;

@Value
public class Light {
    private Vector3 position;
    private ScenePixelColor intensitySpecular;
    private ScenePixelColor intensityDiffuse;

    public Light(Vector3 position, ScenePixelColor intensitySpecular, ScenePixelColor intensityDiffuse) {
        this.position = position;
        this.intensitySpecular = intensitySpecular;
        this.intensityDiffuse = intensityDiffuse;
    }

    public Vector3 getPosition() {
        return this.position;
    }

    public ScenePixelColor getIntensitySpecular() {
        return this.intensitySpecular;   
    }

    public ScenePixelColor getIntensityDiffuse() {
        return this.intensityDiffuse;   
    }

    public Vector3 lightVector(Vector3 intersectionPoint) {
        return position.minus(intersectionPoint).normalized();
    }
}
