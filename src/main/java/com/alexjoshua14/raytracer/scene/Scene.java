package com.alexjoshua14.raytracer.scene;

import lombok.Value;

import java.util.List;

@Value
public class Scene {
    Vector3 camera;
    ImagePlane ImagePlane;
    Color ambientLight;
    List<Light> lights;
    List<SceneObject> objects;

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
