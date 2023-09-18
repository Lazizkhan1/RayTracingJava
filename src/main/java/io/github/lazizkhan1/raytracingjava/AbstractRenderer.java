package io.github.lazizkhan1.raytracingjava;

import io.github.lazizkhan1.raytracingjava.Impl.Camera;
import io.github.lazizkhan1.raytracingjava.Impl.Ray;
import io.github.lazizkhan1.raytracingjava.Impl.Scene;
import io.github.lazizkhan1.raytracingjava.components.Image;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec4;

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
