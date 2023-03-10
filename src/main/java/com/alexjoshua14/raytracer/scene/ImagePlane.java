package com.alexjoshua14.raytracer.scene;

import lombok.Value;

@Value
public class ImagePlane {
    private Vector3 topLeft;
    private Vector3 topRight;
    private Vector3 bottomLeft;
    private Vector3 bottomRight;

    public ImagePlane(Vector3 topLeft, Vector3 topRight, Vector3 bottomLeft, Vector3 bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public Vector3 getTopLeft() {
        return this.topLeft;
    }

    public Vector3 getTopRight() {
        return this.topRight;
    }

    public Vector3 getBottomLeft() {
        return this.bottomLeft;
    }

    public Vector3 getBottomRight() {
        return this.bottomRight;
    }
}
