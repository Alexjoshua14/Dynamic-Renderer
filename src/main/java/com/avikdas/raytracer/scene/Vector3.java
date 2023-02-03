package com.avikdas.raytracer.scene;

import lombok.Value;
import java.lang.Math;

@Value
public class Vector3 {
    float x;
    float y;
    float z;

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
            x + vector.x,
            y + vector.y,
            z + vector.z
        );
    }

    public Vector3 minus(Vector3 vector) {
        return new Vector3 (
            x - vector.x,
            y - vector.y,
            z - vector.z
        );
    }

    public float dot(Vector3 vector) {
        return (x * vector.x) + (y * vector.y) + (z * vector.z);
    }

    public double magnitude() {
        return Math.sqrt((x * x) + (y * y) + (z * z)); 
    }

    public static Vector3 lerp(Vector3 start, Vector3 end, float t) {
        return start.times(1 - t).plus(end.times(t));
    }
}
