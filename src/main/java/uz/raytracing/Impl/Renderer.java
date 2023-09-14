package uz.raytracing.Impl;


import uz.raytracing.AbstractRenderer;
import uz.raytracing.util.glm.Glm;
import uz.raytracing.util.glm.Vec3;
import uz.raytracing.components.Image;
import uz.raytracing.util.glm.Vec4;

import java.awt.image.BufferedImage;

public class Renderer extends AbstractRenderer {


    public void onResize(int width, int height) {
        Image image = getmFinalImage();
        if (image == null || width != image.getWidth() || height != image.getHeight()) {
            mFinalImage = new Image(width, height, BufferedImage.TYPE_INT_ARGB);
            mImageData = new int[width * height];
        }
    }


    public void render(Scene scene, Camera camera) {
        Ray ray = new Ray();
        ray.origin = camera.getPosition();

        for (int y = 0; y < mFinalImage.getHeight(); y++) {
            for (int x = 0; x < mFinalImage.getWidth(); x++) {
                ray.direction = camera.getRayDirections()[x + y * mFinalImage.getWidth()];

                Vec4 color = traceRay(scene, ray);
                color = Vec4.clamp(color, 0.0f, 1.0f);
                mImageData[x + y * mFinalImage.getWidth()] = convertARGB(color);

            }

        }

        mFinalImage.setData(mImageData);
    }

    public Vec4 traceRay(Scene scene, Ray ray) {

        if(scene.spheres.isEmpty())
            return Vec4.zero;

        Sphere sphere = scene.spheres.get(0);
//        sphere.radius = 0.5f;
//        sphere.position = new Vec3(0.0f, 0.0f, 0.0f);
//        sphere.albedo = new Vec3(0.0f, 1.0f, 0.0f);

        Vec3 origin = ray.origin.sub(sphere.position);
        float a = Glm.dot(ray.direction, ray.direction);
        float b = 2.0f * Glm.dot(origin, ray.direction);
        float c = Glm.dot(origin, origin) - sphere.radius * sphere.radius;

        float discriminant = b * b - 4.0f * a * c;

        if (discriminant < 0.0f)
            return Vec4.zero;

        float closestT = (float) (-b - Math.sqrt(discriminant)) / (2.0f * a);
//        float t1 = (float) (-b + Math.sqrt(discriminant)) / (2 * a); currently unused

        if (closestT < 0.0f) return new Vec4(0, 0, 0, 1);
        Vec3 hitPoint = origin.add(ray.direction.mul(closestT));
        Vec3 normal = Glm.normalize(hitPoint);

        Vec3 lightDirection = Glm.normalize(new Vec3(-1, -1, -1));

        float lightIntensity = Math.max(Glm.dot(normal, lightDirection.neg()), 0.0f);

        Vec3 sphereColor = new Vec3(sphere.albedo);
        sphereColor.equal(sphereColor.mul(lightIntensity));
        return new Vec4(sphereColor, 1.0f);

    }

    public int convertARGB(Vec4 color) {
        short r = (short) (color.x * 255.0f);
        short g = (short) (color.y * 255.0f);
        short b = (short) (color.z * 255.0f);
        short a = (short) (color.w * 255.0f);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }


}
