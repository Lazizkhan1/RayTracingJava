package uz.raytracing;

import uz.raytracing.util.Image;

import javax.sound.midi.Soundbank;
import java.awt.image.BufferedImage;

public class Renderer implements IRenderer{

    private Image mFinalImage;
    private int[] mImageData;

    @Override
    public void onResize(int width, int height) {
        if(mFinalImage == null || width != mFinalImage.getWidth() || height != mFinalImage.getHeight()) {
            mFinalImage = new Image(width, height, BufferedImage.TYPE_INT_ARGB);
            mImageData = new int[width * height];
        }
    }

    @Override
    public void render() {
        for(int i = 0; i < mFinalImage.getWidth() * mFinalImage.getHeight(); i++) {
            mImageData[i] = 0xff000000 | (int) (Math.random() * 0xffffff);
        }

        mFinalImage.setData(mImageData);
    }
    public Image getmFinalImage() {
        return mFinalImage;
    }
}
