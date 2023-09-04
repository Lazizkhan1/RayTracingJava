package uz.raytracing;

import uz.raytracing.util.Image;
import uz.raytracing.util.Vec2;

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
                Vec2<Float> coord = new Vec2<>(x / (float) mFinalImage.getWidth(), y / (float) mFinalImage.getHeight());
                mImageData[x + y * mFinalImage.getWidth()] = perPixel(coord);
            }
        }

        mFinalImage.setData(mImageData);
    }

    @Override
    public int perPixel(Vec2<Float> coord) {
        int r = (int) (coord.x * 255.0f);
        int g = (int) (coord.y * 255.0f);
        return 0xFF000000 | (g << 16) | (r << 8);
    }

    public Image getmFinalImage() {
        return mFinalImage;
    }
}
