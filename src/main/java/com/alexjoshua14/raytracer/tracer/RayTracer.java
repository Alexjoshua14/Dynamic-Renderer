package com.alexjoshua14.raytracer.tracer;

import com.alexjoshua14.raytracer.image.*;
import com.alexjoshua14.raytracer.scene.*;
import java.lang.Math;
import java.util.Optional;

public class RayTracer {
    private static final int RECURSION_DEPTH = 5;
    private static final int X_SAMPLE_COUNT = 4;
    private static final int Y_SAMPLE_COUNT = 4;
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
     * 
     * Steps 2 & 3 are completed in colorFromAnyObjectHit
     */
    public Color tracedValueAtPixel(float x, float y) {
        // float xt = ((float) x) / w;
        // float yt = ((float) y) / h;
        // Ray ray = getIntersectionPoint(xt, yt);
       
        return supersamplingAntialiasing(x, y, X_SAMPLE_COUNT, Y_SAMPLE_COUNT);
        //return colorFromAnyObjectHit(ray, RECURSION_DEPTH);
        // return recursiveTracedValueAtPixel(ray, RECURSION_DEPTH);
    }

    /* Get the ray from the camera to the Image Plane */
    private Ray getIntersectionPoint(float xt, float yt) {
         ImagePlane ip = scene.getImagePlane();
 
         Vector3 top = Vector3.lerp(ip.getTopLeft(), ip.getTopRight(), xt);
         Vector3 bottom = Vector3.lerp(ip.getBottomLeft(), ip.getBottomRight(), xt);

         Vector3 p = Vector3.lerp(top, bottom, yt);

        return new Ray(p, p.minus(scene.getCamera()));
    }

    /* Get the color for a specific pixel based on ambient lighting, direct lighting, shadows, and reflections
     * 
     * Recursion is used to obtain the color contribution from reflections off of other scene objects
     * in the path of reflectance
     * 
     */
    private Color colorFromAnyObjectHit(Ray ray, int numBouncesLeft) {
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
             .min((h0, h1) -> (int) Float.compare(h0.getT(), h1.getT()))
             .map( hit -> {
                Vector3 point = ray.at(hit.getT());
                Vector3 view = ray.getDirection().inverted().normalized();

                Color finalPixelColor = phongLightingAtPoint(ray, hit).clamped();
                
                //Calculate color contributions from reflection if we're still recursing
                if (numBouncesLeft > 0) {
                    //Get reflectance vector
                    Vector3 normal = hit.getNormal();
                    Vector3 reflectance = normal
                                            .times(normal.dot(view))
                                            .times(2)
                                            .minus(view);

                    //Recurse to get reflection
                    Ray reflection = new Ray(point, reflectance);    

                    Color reflectedColor = colorFromAnyObjectHit(reflection, numBouncesLeft - 1);

                    //Add reflection color to color for the pixel based on direct & ambient lighting
                    finalPixelColor = finalPixelColor.plus(
                                        reflectedColor.times(
                                        hit
                                            .getObject()
                                            .getMaterial()
                                            .getKReflection()))
                                        .clamped();
                }

                return finalPixelColor;
             })
             .orElse(Color.BLACK);
     }

    /* Get the color of an individual pixel based on what object
     * is closest to the camera.
     */
    private Color phongLightingAtPoint(Ray ray, RayCastHit hit) {
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
            .filter(light ->
                !isShadowed(obj, ray.at(hit.getT()), light))
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

    /* Determine if any other sceneObject is in the path between the current object and 
     * the specified light source.
     */
    private boolean isShadowed(SceneObject obj, Vector3 point, Light light) {
        //If another object is in the path between the rayToLight and the object
        //return true
        
        Vector3 direction = light.getPosition().minus(point);
        Ray shadowRay = new Ray(point, direction);

        return scene.getObjects()
            .stream()
            .filter(otherObjects -> otherObjects != obj)
            .map(otherObjects -> otherObjects.getT(shadowRay))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .anyMatch(t -> t <= 1);
    }

    private Color supersamplingAntialiasing(float x, float y, int xSampleCount, int ySampleCount) {

        Color averagedColor = Color.BLACK;
        float xt = x / w;
        float yt = y / h;
        float dx = (1f / (w * xSampleCount));
        float dy = ( 1f / (h * ySampleCount));

        for ( float i = 0; i < xSampleCount; i++) {
            for ( float j = 0; j < ySampleCount; j++) {
                
                Ray ray = getIntersectionPoint(
                                            xt + (dx * i), 
                                            yt + (dy * j));
            
                averagedColor = averagedColor.plus(colorFromAnyObjectHit(ray, RECURSION_DEPTH));
            }
        }

        return averagedColor.divide((xSampleCount * ySampleCount));
        
    }
}
