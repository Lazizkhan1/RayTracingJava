package uz.raytracing.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

public class Image extends BufferedImage {
    public Image(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    public Image(int width, int height, int imageType, IndexColorModel cm) {
        super(width, height, imageType, cm);
    }

    public Image(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
        super(cm, raster, isRasterPremultiplied, properties);
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

        for (int y = h - 1; y > 0; y--, yoff += w) {
            off = yoff;
            for (int x = 0; x < w; x++) {
                pixel = getColorModel().getDataElements(rgbArray[off++], pixel);
                getRaster().setDataElements(x, y, pixel);
            }
        }
    }
}
