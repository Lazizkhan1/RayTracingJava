package uz.raytracing;

import uz.raytracing.util.glm.Glm;
import uz.raytracing.util.glm.Mat4;
import uz.raytracing.util.glm.Quat;
import uz.raytracing.util.glm.Vec2;
import uz.raytracing.util.glm.Vec3;
import uz.raytracing.util.glm.Vec4;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class Camera {
    private Mat4 mProjection = new Mat4(1.0f);
    private Mat4 mInverseProjection = new Mat4(1.0f);
    private Mat4 mView = new Mat4(1.0f);
    private Mat4 mInverseView = new Mat4(1.0f);


    private final Vec3 mPosition;
    private Vec3 mForwardDirection;

    private ArrayList<Vec3> mRayDirections;

    private Vec2 mMousePosition = new Vec2();

    private int mViewportWidth;
    private int mViewportHeight;
    private float mVerticalFOV = 45.0f;
    private float mNearClip = 0.1f;
    private float mFarClip = 100.0f;
    private boolean isRightButtonDown = false;
    private int pressedKey = -1;
    private Vec2 delta = new Vec2();


    public Camera(float verticalFOV, float nearClip, float farClip){
        this.mVerticalFOV = verticalFOV;
        this.mNearClip = nearClip;
        this.mFarClip = farClip;

        mForwardDirection = new Vec3(0, 0, -1);
        mPosition = new Vec3(0, 0, 10);
    }

    public void onResize(int width, int height) {
        if (width == mViewportWidth && height == mViewportHeight)
            return;

        mViewportWidth = width;
        mViewportHeight = height;

        recalculateProjection();
        recalculateRayDirections();
    }

    public boolean onUpdate(float ts) {
        boolean moved = false;

        Vec3 upDirection = new Vec3(0.0f, 1.0f, 0.0f);
        Vec3 rightDirection = Glm.cross(mForwardDirection, upDirection);

        float speed = 3f;
        if(isRightButtonDown) {
            switch (pressedKey) {
                case KeyEvent.VK_W -> {
                    mPosition.equal(mPosition.add(mForwardDirection.mul(speed * ts)));
                    moved = true;
                }
                case KeyEvent.VK_S -> {
                    mPosition.equal(mPosition.sub(mForwardDirection.mul(speed * ts)));
                    moved = true;
                }
                case KeyEvent.VK_A -> {
                    mPosition.equal(mPosition.sub(rightDirection.mul(speed * ts)));
                    moved = true;
                }
                case KeyEvent.VK_D -> {
                    mPosition.equal(mPosition.add(rightDirection.mul(speed * ts)));
                    moved = true;
                }
                case KeyEvent.VK_Q -> {
                    mPosition.equal(mPosition.add(upDirection.mul(speed * ts)));
                    moved = true;
                }
                case KeyEvent.VK_E -> {
                    mPosition.equal(mPosition.sub(upDirection.mul(speed * ts)));
                    moved = true;
                }
            }
        }

        if (delta.x != 0.0f || delta.y != 0.0f)
        {
            float pitchDelta = delta.y * getRotationSpeed();
            float yawDelta = delta.x * getRotationSpeed();

            Quat q = Glm.normalize(Glm.cross(Glm.angleAxis(-pitchDelta, rightDirection),
                    Glm.angleAxis(-yawDelta, new Vec3(0.f, 1.0f, 0.0f))));

            mForwardDirection = Glm.rotate(q, mForwardDirection);
            delta.x = delta.y = 0;
            moved = true;
        }

        if (moved)
        {
            recalculateView();
            recalculateRayDirections();
        }

        return moved;
    }

    private void recalculateProjection() {
        mProjection = Glm.perspectiveFov(
                (float) Math.toRadians(mVerticalFOV),
                (float) mViewportWidth,
                (float) mViewportHeight,
                mNearClip, mFarClip);
        mInverseProjection = Glm.inverse(mProjection);
    }

    private void recalculateRayDirections() {
        mRayDirections = new ArrayList<>(Collections.nCopies(mViewportWidth * mViewportHeight, null));

        try {
            for (int y = 0; y < mViewportHeight; y++) {
                for (int x = 0; x < mViewportWidth; x++) {
                    Vec2 coord = new Vec2(x / (float) mViewportWidth, y / (float) mViewportHeight);
                    coord = coord.mul(2.0f).sub(1.0f);

                    Vec4 target = mInverseProjection.mul(new Vec4(coord.x, coord.y, 1, 1));

                    Vec3 rayDirection = new Vec3(mInverseView.mul(new Vec4(Glm.normalize(new Vec3(target).div(target.w)),0.0f)));
                    mRayDirections.set(x + y * mViewportWidth, rayDirection);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException();
        }
    }

    private void recalculateView() {
        mView = Glm.lookAt(mPosition, mPosition.add(mForwardDirection) , new Vec3(0, 1, 0));
        mInverseView = Glm.inverse(mView);
    }

    public ArrayList<Vec3> getRayDirections() {
        return mRayDirections;
    }

    public Vec3 getPosition() {
        return mPosition;
    }

    public Vec2 getMousePosition() {
        return mMousePosition;
    }

    public Mat4 getProjection() {
        return mProjection;
    }

    public Mat4 getInverseProjection() {
        return mInverseProjection;
    }

    public void setMousePosition(int newX, int newY) {
        this.delta = new Vec2(this.mMousePosition.x - newX, this.mMousePosition.y - newY ).mul(1f);
        this.mMousePosition = new Vec2(newX, newY);
    }

    public void setRightButtonDown(boolean rightButtonDown) {
        isRightButtonDown = rightButtonDown;
    }

    public void setPressedKey(int pressedKey) {
        this.pressedKey = pressedKey;
    }

    public float getRotationSpeed() {
        return 0.3f;
    }

}
