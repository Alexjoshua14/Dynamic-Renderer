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

    private Optional<Float> getT(Ray ray, SceneObject obj) {
        
        if (obj instanceof Sphere) {
            Sphere sphere = (Sphere) obj;

            Vector3 center = ray.getOrigin().minus(sphere.getCenter());

            double a = Math.pow(ray.getDirection().magnitude(), 2);
            double b = 2 * center.dot(ray.getDirection());
            double c = Math.pow(center.magnitude(), 2) - Math.pow(sphere.getRadius(), 2);

            double disc = Math.pow(b, 2) - ( 4 * a * c);

            if (disc < 0) { 
                return Optional.empty();
            }

            double sqrt = Math.sqrt(disc);

            float t1 = (float) ((- b + sqrt) / (2 * a));
            float t2 = (float) ((-b - sqrt) / (2 * a));
            
            //QUESTION: What happens if we're in the object, i.e., t1/t2 < 0 and t2/t1 > 0
            float minT = Math.min(t1, t2);

            return minT > 0 ?
                Optional.of(minT) :
                Optional.empty();
            
        }

        return Optional.empty();
    }

    private Optional<RayCastHit> getClosestObjectInPath(Ray ray) {

        Optional<RayCastHit> closestObj = scene.getObjects()
            .stream()
            .map(
                obj ->
                    getT(ray, obj)
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

        // for (SceneObject obj : scene.getObjects()) {
        //     float t = getT(ray, obj);
        //     if (t >= 0) {
        //         if (closestObj == null) {
        //             closestObj = new RayCastHit(t, obj);
        //         } else if (t < closestObj.getT()) {
        //             closestObj.setT(t);
        //             closestObj.setObj(obj);
        //         }
        //     }
        // }
        
        //Set normal component of closest object
        // if (closestObj != null) {
        //     closestObj.setNormal(obj.normalAt(ray.at(obj.getT())));
        // }


        return closestObj;
    }

    private Color getColorAtPoint(Ray ray, RayCastHit hit) {

        if (hit.getObject() instanceof Sphere) {
            Sphere sphere = (Sphere) hit.getObject();
            float t = hit.getT();

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
                
            Color ambient = m.getKAmbient().times(scene.getAmbientLight());

            return ambient.plus(lightContributions);
        }
        

        return null;
    }
}
