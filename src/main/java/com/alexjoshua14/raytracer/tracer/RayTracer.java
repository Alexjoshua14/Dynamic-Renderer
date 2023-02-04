package com.alexjoshua14.raytracer.tracer;

import com.alexjoshua14.raytracer.image.*;
import com.alexjoshua14.raytracer.scene.*;
import java.lang.Math;
import java.util.Optional;

public class RayTracer {
    Scene scene;
    int w;
    int h;

    public RayTracer(Scene scene, int w, int h) {
        this.scene = scene;
        this.w = w;
        this.h = h;
    }

    /* Gets the color of an individual pixel through the following steps:
     * 1. Calculate the ray between the camera and a specific
     *      location on the image plane
     * 2. Determine if there is a closest object to the camera in the path of the ray
     *      between the camera and the image plane otherwise note that we're looking
     *      directly at the image plane
     * 3. Determine what color the spot on the object / image plane is according to
     *      the object's visual characteristics and lights present
     */
    public Color tracedValueAtPixel(int x, int y) {
        float xt = ((float) x) / w;
        float yt = ((float) y) / h;
        Ray ray = getIntersectionPoint(xt, yt);
        
        /* Now see if the ray hits any object */
        Optional<RayCastHit> closestObj = getClosestObjectInPath(ray);

        // Ray intersected a scene object
        if (closestObj.isPresent()) {
            return getColorAtPoint(ray, closestObj.get()).clamped();
        }

        // Ray did not intersect any scene object
        return new Color(
            (ray.getDirection().getX() + 1.92f) / 3.84f,
            (ray.getDirection().getY() + 1.08f) / 2.16f,
            ray.getDirection().getZ() / -4
        );
    }

    /* Get the ray from the camera to the Image Plane */
    private Ray getIntersectionPoint(float xt, float yt) {
         ImagePlane ip = scene.getImagePlane();
 
         Vector3 top = Vector3.lerp(ip.getTopLeft(), ip.getTopRight(), xt);
         Vector3 bottom = Vector3.lerp(ip.getBottomLeft(), ip.getBottomRight(), xt);

         Vector3 p = Vector3.lerp(top, bottom, yt);

        return new Ray(p, p.minus(scene.getCamera()));
    }

    /* Get the closest object in the path of the ray */
    private Optional<RayCastHit> getClosestObjectInPath(Ray ray) {
       return scene.getObjects()
            .stream()
            .map(
                obj -> 
                    obj.getT(ray)
                    .map( t -> new RayCastHit(
                        t,
                        obj,
                        obj.surfaceNormal(ray.at(t)))
                    )
                )
            .filter(Optional::isPresent)
            .map(Optional::get)
            .min((h0, h1) -> (int) Math.signum(h0.getT() - h1.getT()))
            .map(hit -> Optional.of(hit))
            .orElse(Optional.empty());
    }

    /* Get the color of an individual pixel based on what object
     * is closest to the camera.
     */
    private Color getColorAtPoint(Ray ray, RayCastHit hit) {
        SceneObject obj = hit.getObject();
        float t = hit.getT();

        Vector3 sNormal = obj.surfaceNormal(ray.at(t));
        Material m = obj.getMaterial();
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
            
        Color ambient = m.getKAmbient().times(scene.getAmbientLight());

        return ambient.plus(lightContributions);
    }
}
