package com.alexjoshua14.raytracer.scene;

import com.alexjoshua14.raytracer.tracer.*;
import java.util.Optional;

public interface SceneObject {
    public Material getMaterial();
    public ScenePixelColor getColor();
    public Vector3 getCenter();
    public Vector3 getVelocity(); //Units per millisecond
    public Vector3 updateCenter(Vector3 newCenter);
    public Vector3 surfaceNormal(Vector3 point);

    /* Returns the closest point of intersection based on the path
     * of the specified ray
     */
    public Optional<Float> getT(Ray ray);
}
