package com.alexjoshua14.raytracer.scene;

import lombok.Value;
import java.lang.Math;

@Value
public class Vector3 {
    private float x;
    private float y;
    private float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public Vector3 times(float s) {
        return new Vector3 (
            x * s,
            y * s,
            z * s
        );
    }

    public Vector3 plus(Vector3 vector) {
        return new Vector3 (
            x + vector.getX(),
            y + vector.getY(),
            z + vector.getZ()
        );
    }

    public Vector3 minus(Vector3 vector) {
        return new Vector3 (
            x - vector.getX(),
            y - vector.getY(),
            z - vector.getZ()
        );
    }

    public float dot(Vector3 vector) {
        return (x * vector.getX()) + (y * vector.getY()) + (z * vector.getZ());
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this)); 
    }

    public Vector3 normalized() {
        return this.times((float) (1 / magnitude()));
    }

    public Vector3 inverted() {
        return this.times(-1);
    }

    public static Vector3 lerp(Vector3 start, Vector3 end, float t) {
        return start.times(1 - t).plus(end.times(t));
    }
}
