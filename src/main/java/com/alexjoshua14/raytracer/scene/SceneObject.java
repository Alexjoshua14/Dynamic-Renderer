package com.alexjoshua14.raytracer.scene;

public interface SceneObject {
    public Material getMaterial();
    public Color getColor();
    public Vector3 getCenter();
    public Vector3 surfaceNormal(Vector3 point);
}
