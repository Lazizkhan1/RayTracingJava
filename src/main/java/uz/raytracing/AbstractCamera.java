package uz.raytracing;

import uz.raytracing.util.glm.Mat4;
import uz.raytracing.util.glm.Vec2;
import uz.raytracing.util.glm.Vec3;

import java.util.concurrent.ConcurrentHashMap;


public abstract class AbstractCamera {

    protected Mat4 mProjection = new Mat4(1.0f);
    protected Mat4 mInverseProjection = new Mat4(1.0f);
    protected Mat4 mView = new Mat4(1.0f);
    protected Mat4 mInverseView = new Mat4(1.0f);

    protected final Vec3 mPosition;
    protected Vec3 mForwardDirection;

    protected Vec3[] mRayDirections;
    protected Vec2 mLastMousePosition = new Vec2();

    protected int mViewportWidth;
    protected int mViewportHeight;

    protected final float mVerticalFOV;
    protected final float mNearClip;
    protected final float mFarClip;
    protected boolean isRightButtonDown = false;
    protected final ConcurrentHashMap<Integer, Boolean> pressedKey = new ConcurrentHashMap<>();

    public AbstractCamera(float verticalFOV, float nearClip, float farClip) {
        this.mVerticalFOV = verticalFOV;
        this.mNearClip = nearClip;
        this.mFarClip = farClip;

        mForwardDirection = new Vec3(0, 0, -1);
        mPosition = new Vec3(0, 0, 1.5f);
    }

    abstract public void onResize(int width, int height);
    abstract public boolean onUpdate(float ts);

    abstract protected void recalculateProjection();
    abstract protected void recalculateView();
    abstract protected void recalculateRayDirections();

    public abstract Vec3[] getRayDirections();

    public abstract Vec3 getPosition();
    protected abstract void setRightButtonDown(boolean rightButtonDown);
    protected abstract void setPressedKey(int pressedKey, boolean state);
    public abstract float getRotationSpeed();



}
