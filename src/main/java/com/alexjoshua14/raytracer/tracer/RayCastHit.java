package com.alexjoshua14.raytracer.tracer;

import com.alexjoshua14.raytracer.scene.*;

public class RayCastHit {
    private float t;
    private SceneObject obj;
    private Vector3 normal;

    public RayCastHit(float t, SceneObject obj, Vector3 normal) {
        this.t = t;
        this.obj = obj;
        this.normal = normal;
    }

    public float getT() {
        return t;
    }

    public SceneObject getObject() {
        return this.obj;
    }

    public Vector3 getNormal() {
        return this.normal;
    }

    public void setT(float t) {
        this.t = t;
    }

    public void setObject(SceneObject obj) {
        this.obj = obj;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }
}
