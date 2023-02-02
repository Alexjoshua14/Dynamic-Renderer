package com.avikdas.raytracer.tracer;

import com.avikdas.raytracer.image.*;
import com.avikdas.raytracer.scene.*;

public class RayTracer {
    Scene scene;
    int w;
    int h;

    public RayTracer(Scene scene, int w, int h) {
        this.scene = scene;
        this.w = w;
        this.h = h;
    }



    public Color tracedValueAtPixel(int x, int y) {
        //x coordinate converted to [0, 255] range
        float r = (float) x / W;
        //y coordinate converted to [0, 255] range
        float g = (float) y / H;

        //Splash of blue
        float b = .01f;
        return new Color(r, g, b);
    }
}
