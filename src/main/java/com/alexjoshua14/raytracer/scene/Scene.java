package com.alexjoshua14.raytracer.scene;

import lombok.Value;

import java.util.List;

@Value
public class Scene {
    private Vector3 camera;
    private ImagePlane ImagePlane;
    private Color ambientLight;
    private List<Light> lights;
    private List<SceneObject> objects;

    public Scene(Vector3 camera, ImagePlane ImagePlane, Color ambientLight, List<Light> lights, List<SceneObject> objects) {
        this.camera = camera;
        this.ImagePlane = ImagePlane;
        this.ambientLight = ambientLight;
        this.lights = lights;
        this.objects = objects;
    }     
    
    public Vector3 getCamera() {
        return this.camera;
    }

    public ImagePlane getImagePlane() {
        return this.ImagePlane;
    }

    public Color getAmbientLight() {
        return this.ambientLight;
    }

    public List<Light> getLights() {
        return this.lights;
    }

    public List<SceneObject> getObjects() {
        return this.objects;
    }
}
