package uz.raytracing;



import uz.raytracing.util.Vec3;
import uz.raytracing.util.Vec2;
import uz.raytracing.util.Image;
import uz.raytracing.util.Vec4;

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
    public void render() {
        for (int y = 0; y < mFinalImage.getHeight(); y++) {
            for (int x = 0; x < mFinalImage.getWidth(); x++) {
                Vec2 coord = new Vec2(x / (float) mFinalImage.getWidth(), y / (float) mFinalImage.getHeight());
                coord = coord.mul(2.0f).sub(1.0f);

                Vec4 color = perPixel(coord);
                color = Vec4.clamp(color, 0.0f, 1.0f);
                mImageData[x + y * mFinalImage.getWidth()] = convertARGB(color);

            }
        }

        mFinalImage.setData(mImageData);
    }

    @Override
    public Vec4 perPixel(Vec2 coord) {
        short r = (short) (coord.x * 255.0f);
        short g = (short) (coord.y * 255.0f);

        Vec3 rayDirection = new Vec3(coord.x, coord.y, -1.0f);
        Vec3 rayOrigin = new Vec3(0.0f, 0.0f, 2.0f);
        Vec3 sphereOrigin = new Vec3();
        Vec3 lightDirection = new Vec3(-1, -1, 1);
        lightDirection.normalize();
        float radius = 0.5f;

        float a = Vec3.dot(rayDirection, rayDirection);
        float b = 2.0f * Vec3.dot(rayOrigin, rayDirection);
        float c = Vec3.dot(rayOrigin, rayOrigin) - radius * radius;

        float discriminant = b * b - 4.0f * a * c;

        if (discriminant >= 0.0f) {
            float t0 = (float) (-b - Math.sqrt(discriminant)) / (2 * a);
            float t1 = (float) (-b + Math.sqrt(discriminant)) / (2 * a);
            Vec4 sphereColor = new Vec4(0.0f, 1.0f, 0.0f, 1.0f);
            Vec3 hitPosition = rayOrigin.add(rayDirection.mul(t1));
            Vec3 normal = hitPosition.sub(sphereOrigin);
            normal.normalize();

            float light = Math.max(Vec3.dot(normal, lightDirection.neg()), 0.0f);

            return new Vec4(sphereColor.xyz().mul(light), 1.0f);
        }

        return new Vec4(0.0f, 0.0f, 0.0f, 1.0f);
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
