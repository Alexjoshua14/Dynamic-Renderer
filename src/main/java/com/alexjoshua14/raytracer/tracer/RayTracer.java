package com.alexjoshua14.raytracer.tracer;

import com.alexjoshua14.raytracer.image.*;
import com.alexjoshua14.raytracer.scene.*;
import java.lang.Math;

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
        /* Get the ray from the camera to the Image Plane */
        float xt = ((float) x) / w;
        float yt = ((float) y) / h;

        ImagePlane ip = scene.getImagePlane();

        Vector3 top = Vector3.lerp(ip.getTopLeft(), ip.getTopRight(), xt);
        Vector3 bottom = Vector3.lerp(ip.getBottomLeft(), ip.getBottomRight(), xt);

        Vector3 p =  Vector3.lerp(top, bottom, yt);

        Ray ray = new Ray(p, p.minus(scene.getCamera()));
        
        /* Now see if the ray hits any object */
        float minT = -1f;
        SceneObject closestObj = null;
        for (SceneObject obj : scene.getObjects()) {
            float t = getT(ray, obj);
            if (t >= 0) {
                if (minT == -1f || t < minT) {
                    minT = t;
                    closestObj = obj;
                }
            }
        }

        // Ray intersected a scene object
        if (closestObj != null) {
            return getColorAtPoint(ray, minT, closestObj).clamped();
        }

        // Ray did not intersect any scene object
        return new Color(
            (ray.getDirection().getX() + 1.92f) / 3.84f,
            (ray.getDirection().getY() + 1.08f) / 2.16f,
            ray.getDirection().getZ() / -4
        );
    }

    private float getT(Ray ray, SceneObject obj) {
        
        if (obj instanceof Sphere) {
            Sphere sphere = (Sphere) obj;

            Vector3 center = ray.getOrigin().minus(sphere.getCenter());

            double a = Math.pow(ray.getDirection().magnitude(), 2);
            double b = 2 * center.dot(ray.getDirection());
            double c = Math.pow(center.magnitude(), 2) - Math.pow(sphere.getRadius(), 2);

            double disc = Math.pow(b, 2) - ( 4 * a * c);

            if (disc >= 0) {
                float t1 = (float) ((disc - b) / (2 * a));
                float t2 = (float) (((disc * -1) - b) / (2 * a));
                
                if ( t1 > 0 && t2 > 0) {
                    return Math.min(t1, t2);
                } else if (t1 > 0) {
                    return t1;
                } else if (t2 > 0) {
                    return t2;
                }
            }
        }
        return -1f;
    }

    private Color getColorAtPoint(Ray ray, float t, SceneObject obj) {

        if (obj instanceof Sphere) {
            Sphere sphere = (Sphere) obj;
        
            Vector3 sNormal = sphere.surfaceNormal(ray.at(t));

            Material m = sphere.getMaterial();

            Color phongIllumination = m.getKAmbient().times(scene.getAmbientLight());
            
            Color lightContributions = scene
                .getLights()
                .stream()
                .filter(light -> 
                    sNormal.dot(light.lightVector(ray.at(t))) > 0)
                .map(light -> {
                    Vector3 l = light.lightVector(ray.at(t));
                    Vector3 r = sNormal.times(sNormal.dot(l) * 2).minus(l);
                    Vector3 view = ray.getDirection().inverted().normalized();

                    Color diffuse = light.getIntensityDiffuse()
                                    .times(m.getKDiffuse())
                                    .times(l.dot(sNormal));

                    Color specular = m.getKSpecular()
                                    .times(light.getIntensitySpecular())
                                    .times((float) Math.pow(
                                        view.dot(r), m.getAlpha()));
                    
                    return diffuse.plus(specular);
                })
                .reduce(
                    Color.BLACK, (prev, additionalLight) -> prev.plus(additionalLight)
                );
                
            return lightContributions;
        }
        

        return null;
    }
}
