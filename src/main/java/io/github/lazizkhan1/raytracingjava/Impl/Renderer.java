package io.github.lazizkhan1.raytracingjava.Impl;


import io.github.lazizkhan1.raytracingjava.AbstractRenderer;
import io.github.lazizkhan1.raytracingjava.components.Image;
import io.github.lazizkhan1.raytracingjava.util.RandomUtil;
import io.github.lazizkhan1.raytracingjava.util.glm.Glm;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec4;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class Renderer extends AbstractRenderer {

    public static int convertARGB(Vec4 color) {
        short r = (short) (color.x * 255.0f);
        short g = (short) (color.y * 255.0f);
        short b = (short) (color.z * 255.0f);
        short a = (short) (color.w * 255.0f);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int convertRGB(Vec3 color) {
        short r = (short) (color.x * 255.0f);
        short g = (short) (color.y * 255.0f);
        short b = (short) (color.z * 255.0f);
        return (r << 16) | (g << 8) | b;
    }

    public static Vec3 convertARGB(int color) {
        short r = (short) ((color >> 16) & 0xFF);
        short g = (short) ((color >> 8) & 0xFF);
        short b = (short) (color & 0xFF);
        return new Vec3(r / 255.0f, g / 255.0f, b / 255.0f);
    }


    public void onResize(int width, int height) {
        Image image = getFinalImage();
        if (image == null || width != image.getWidth() || height != image.getHeight()) {
            mFinalImage = new Image(width, height, BufferedImage.TYPE_INT_ARGB);
            mImageData = new int[width * height];
            mAccumulationData = new Vec4[width * height];
            for (int i = 0; i < mImageData.length; i++)
                mAccumulationData[i] = new Vec4();
        }

    }

    public void render(Scene scene, Camera camera) {
        mActiveScene = scene;
        mActiveCamera = camera;

        if (mFrameIndex == 1) {
            mAccumulationData = new Vec4[mFinalImage.getWidth() * mFinalImage.getHeight()];
            for (int i = 0; i < mAccumulationData.length; i++)
                mAccumulationData[i] = new Vec4();
        }

        if (mSettings.multiThreaded) {
            IntStream.range(0, mFinalImage.getHeight()) // Outer loop (rows)
                    .parallel() // Stream is now parallel
                    .forEach(y -> IntStream.range(0, mFinalImage.getWidth()) // Inner loop (columns)
                            .forEach(x -> {
                                Vec4 color = perPixel(x, y);
                                mAccumulationData[x + y * mFinalImage.getWidth()].addEqual(color);
                                Vec4 accumulatedColor = mAccumulationData[x + y * mFinalImage.getWidth()];
                                Vec4 divided = accumulatedColor.mul(1.0f / mFrameIndex);
                                accumulatedColor = Vec4.clamp(divided, 0.0f, 1.0f);
                                mImageData[x + y * mFinalImage.getWidth()] = convertARGB(accumulatedColor);
                            }));
        }
        else {
            for (int y = 0; y < mFinalImage.getHeight(); y++) {
                for (int x = 0; x < mFinalImage.getWidth(); x++) {
                    Vec4 color = perPixel(x, y);
                    mAccumulationData[x + y * mFinalImage.getWidth()].addEqual(color);
                    Vec4 accumulatedColor = mAccumulationData[x + y * mFinalImage.getWidth()];

                    Vec4 divided = accumulatedColor.mul(1.0f / mFrameIndex);

                    accumulatedColor = Vec4.clamp(divided, 0.0f, 1.0f);
                    mImageData[x + y * mFinalImage.getWidth()] = convertARGB(accumulatedColor);
                }
            }
        }
        mFinalImage.setData(mImageData);

        if (mSettings.accumulate)
            mFrameIndex++;
        else
            mFrameIndex = 1;
    }

    @Override
    protected Vec4 perPixel(int x, int y) {
        Ray ray = new Ray();
        ray.origin = mActiveCamera.getPosition();
        ray.direction = mActiveCamera.getRayDirections()[x + y * mFinalImage.getWidth()];

        Vec3 light = new Vec3(0.0f);
        Vec3 contribution = new Vec3(1.0f);
        int bounces = 5;

        long[] seed = new long[]{x + (long) y * mFinalImage.getWidth()};
        seed[0] *= mFrameIndex;

        for (int i = 0; i < bounces; i++) {
            seed[0] += i;
            HitPayload payload = traceRay(ray);
            if (payload.hitDistance < 0.0f) {
                Vec3 skyColor = mActiveScene.skybox.getSkyColor(ray.direction);
                light.addEqual(skyColor.mul(contribution));
                break;
            }

            final Sphere sphere = mActiveScene.spheres.get(payload.objectIndex);
            final Material material = mActiveScene.materials.get(sphere.materialIndex);
//            light.addEqual(material.albedo.mul(contribution));

            contribution.mulEqual(material.albedo);
            light.addEqual(material.getEmission());
            ray.origin = payload.worldPosition.add(payload.worldNormal.mul(0.0001f));
            Vec3 specularDir = Glm.reflect(ray.direction, payload.worldNormal.add(
                    RandomUtil.vec3(-0.5f, 0.5f).mul(material.roughness))
            );
            Vec3 diffuseDir = Glm.normalize(payload.worldNormal.add(RandomUtil.inUnitSphere()));
            ray.direction = Glm.mix(specularDir, diffuseDir, material.metallic);
        }
        return new Vec4(light, 1.0f);
    }

    @Override
    public void resetFrameIndex() {
        mFrameIndex = 1;
        for (int i = 0; i < mAccumulationData.length; i++)
            mAccumulationData[i] = new Vec4();

    }

    @Override
    protected HitPayload closestHit(Ray ray, float hitDistance, int objectIndex) {
        final Sphere closestSphere = mActiveScene.spheres.get(objectIndex);

        HitPayload payload = new HitPayload();
        payload.hitDistance = hitDistance;
        payload.objectIndex = objectIndex;


        Vec3 origin = ray.origin.sub(closestSphere.position);
        payload.worldPosition = origin.add(ray.direction.mul(hitDistance));
        payload.worldNormal = Glm.normalize(payload.worldPosition);
        payload.worldPosition.addEqual(closestSphere.position);
        return payload;
    }

    @Override
    protected HitPayload traceRay(Ray ray) {

        int closestSphere = -1;
        float hitDictance = Float.MAX_VALUE;

        for (int i = 0; i < mActiveScene.spheres.size(); i++) {
            Sphere sphere = mActiveScene.spheres.get(i);
            Vec3 origin = ray.origin.sub(sphere.position);

            float a = Glm.dot(ray.direction, ray.direction);
            float b = 2.0f * Glm.dot(origin, ray.direction);
            float c = Glm.dot(origin, origin) - sphere.radius * sphere.radius;

            float discriminant = b * b - 4.0f * a * c;

            if (discriminant < 0.0f)
                continue;

            //        float t1 = (float) (-b + Math.sqrt(discriminant)) / (2 * a); //currently unused
            float closestT = (float) (-b - Math.sqrt(discriminant)) / (2.0f * a);


            if (closestT < hitDictance && closestT > 0.0f) {
                hitDictance = closestT;
                closestSphere = i;
            }
        }

        if (closestSphere < 0) {
            return miss(ray);
        }

        return closestHit(ray, hitDictance, closestSphere);

    }

    @Override
    protected HitPayload miss(Ray ray) {
        HitPayload payload = new HitPayload();
        payload.hitDistance = -1.0f;
        return payload;
    }


}
