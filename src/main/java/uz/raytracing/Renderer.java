package uz.raytracing;


import uz.raytracing.util.glm.Glm;
import uz.raytracing.util.glm.Vec3;
import uz.raytracing.util.glm.Vec2;
import uz.raytracing.util.Image;
import uz.raytracing.util.glm.Vec4;

import java.awt.image.BufferedImage;

public class Renderer implements IRenderer {

    private Image mFinalImage;
    private int[] mImageData;

    @Override
    public void onResize(int width, int height) {
        if (mFinalImage == null || width != mFinalImage.getWidth() || height != mFinalImage.getHeight()) {
            mFinalImage = new Image(width, height, BufferedImage.TYPE_INT_ARGB);
            mImageData = new int[width * height];
        }
    }

    @Override
    public void render(Camera camera) {
        Ray ray = new Ray();
        ray.origin = camera.getPosition();
        for (int y = 0; y < mFinalImage.getHeight(); y++) {
            for (int x = 0; x < mFinalImage.getWidth(); x++) {
                Vec2 coord = new Vec2(x / (float) mFinalImage.getWidth(), y / (float) mFinalImage.getHeight());
                coord = coord.mul(2.0f).sub(1.0f);
                ray.direction = new Vec3(coord.x, coord.y, -1.0f);
                Vec4 color = traceRay(ray);
                color = Vec4.clamp(color, 0.0f, 1.0f);
                mImageData[x + y * mFinalImage.getWidth()] = convertARGB(color);

            }
        }

        mFinalImage.setData(mImageData);
    }

    @Override
    public Vec4 traceRay(Ray ray) {

        float radius = 0.5f;

        float a = Glm.dot(ray.direction, ray.direction);
        float b = 2.0f * Glm.dot(ray.origin, ray.direction);
        float c = Glm.dot(ray.origin, ray.origin) - radius * radius;

        float discriminant = b * b - 4.0f * a * c;

        if (discriminant < 0.0f) return new Vec4(0.0f, 0.0f, 0.0f, 1.0f);

        float closestT = (float) (-b - Math.sqrt(discriminant)) / (2.0f * a);
        float t1 = (float) (-b + Math.sqrt(discriminant)) / (2 * a);


        Vec3 hitPoint = ray.origin.add(ray.direction.mul(closestT));
        Vec3 normal = hitPoint.normalize();

        Vec3 lightDirection = new Vec3(-1, -1, -1).normalize();

        float lightIntensity = Math.max(Glm.dot(normal, lightDirection.neg()), 0.0f);

        Vec3 sphereColor = new Vec3(0.0f, 1.0f, 0.0f);
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

    public Image getmFinalImage() {
        return mFinalImage;
    }

}
