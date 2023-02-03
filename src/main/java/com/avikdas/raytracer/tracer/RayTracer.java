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
        float xt = ((float) x) / w;
        //y coordinate converted to [0, 255] range
        float yt = ((float) y) / h;

        ImagePlane ip = scene.getImagePlane();

        Vector3 t = Vector3.lerp(ip.getTopLeft(), ip.getTopRight(), xt);
        Vector3 b = Vector3.lerp(ip.getBottomLeft(), ip.getBottomRight(), xt);

        Vector3 p =  Vector3.lerp(t, b, yt);

        Ray ray = new Ray(p, p.minus(scene.getCamera()));
        
        return new Color(
            (ray.getDirection().getX() + 1.92f) / 3.84f,
            (ray.getDirection().getY() + 1.08f) / 2.16f,
            ray.getDirection().getZ() / -4
        );
    }
}
