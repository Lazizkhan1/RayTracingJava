package uz.raytracing;

import uz.raytracing.Impl.Camera;
import uz.raytracing.Impl.Ray;
import uz.raytracing.Impl.Scene;
import uz.raytracing.components.Image;
import uz.raytracing.util.glm.Vec4;

public abstract class AbstractRenderer {
    protected Image mFinalImage;
    protected int[] mImageData;


    public abstract void onResize(int width, int height);

    public abstract void render(Scene scene, Camera camera);

    public abstract Vec4 traceRay(Scene scene, Ray ray);

    public Image getmFinalImage() {
        return mFinalImage;
    }

}
