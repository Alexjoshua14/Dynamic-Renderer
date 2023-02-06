package com.alexjoshua14.raytracer.tracer;

import com.alexjoshua14.raytracer.scene.Vector3;
import lombok.Value;

@Value
public class Ray {
    private Vector3 origin;
    private Vector3 direction;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 getOrigin() {
        return this.origin;
    }

    public Vector3 getDirection() {
        return this.direction;
    }

    public Vector3 at(float t) {
        return origin.plus(direction.times(t));
    }
}
