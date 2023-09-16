package uz.raytracing.components;

import java.awt.image.BufferedImage;

public class Image extends BufferedImage {
    public Image(int width, int height, int imageType) {
        super(width, height, imageType);
        setAccelerationPriority(1.0f);
    }


    /**
     * Calling setRGB() method with image width, and height
     */
    public void setData(int[] rgbArray) {
        int h = getHeight();
        int w = getWidth();
        int yoff = 0;
        int off;
        Object pixel = null;

        for (int y = h - 1; y >= 0; y--, yoff += w) {
            off = yoff;
            for (int x = 0; x < w; x++) {
                pixel = getColorModel().getDataElements(rgbArray[off++], pixel);
                getRaster().setDataElements(x, y, pixel);
            }
        }
    }
}
