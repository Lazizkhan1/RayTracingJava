package io.github.lazizkhan1.raytracingjava;

import io.github.lazizkhan1.raytracingjava.Impl.Camera;
import io.github.lazizkhan1.raytracingjava.Impl.HitPayload;
import io.github.lazizkhan1.raytracingjava.Impl.Ray;
import io.github.lazizkhan1.raytracingjava.Impl.Scene;
import io.github.lazizkhan1.raytracingjava.Impl.Settings;
import io.github.lazizkhan1.raytracingjava.components.Image;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec4;

public abstract class AbstractRenderer {
    protected Image mFinalImage;

    protected int[] mImageData;

    protected Vec4[] mAccumulationData;

    protected Scene mActiveScene;

    protected Camera mActiveCamera;

    protected int mFrameIndex = 1;

    protected Settings mSettings = new Settings();

    public abstract void onResize(int width, int height);

    public abstract void render(Scene scene, Camera camera);

    protected abstract HitPayload traceRay(Ray ray);

    protected abstract HitPayload closestHit(Ray ray, float hitDistance, int objectIndex);

    protected abstract HitPayload miss(Ray ray);

    protected abstract Vec4 perPixel(int x, int y);

    public Settings getSettings() {return mSettings;}

    public abstract void resetFrameIndex();

    public Image getFinalImage() {return mFinalImage;}

}