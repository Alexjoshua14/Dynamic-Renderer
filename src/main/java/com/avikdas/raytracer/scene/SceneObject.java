package com.avikdas.raytracer.scene;

public interface SceneObject {
    public Material getMaterial();
    public Color getColor();
    public Vector3 getCenter();
}
