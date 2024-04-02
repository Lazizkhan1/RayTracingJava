package io.github.lazizkhan1.raytracingjava.Impl;


import io.github.lazizkhan1.raytracingjava.AbstractCamera;
import io.github.lazizkhan1.raytracingjava.util.Mouse;
import io.github.lazizkhan1.raytracingjava.util.glm.Glm;
import io.github.lazizkhan1.raytracingjava.util.glm.Quat;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec2;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec4;

import java.awt.event.KeyEvent;
import java.util.stream.IntStream;

public class Camera extends AbstractCamera {


    public Camera(float verticalFOV, float nearClip, float farClip) {
        super(verticalFOV, nearClip, farClip);
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
        Vec2 mousePos = Mouse.getPosition();
        Vec2 delta = mousePos.sub(mLastMousePosition).mul(0.0015f);
        mLastMousePosition = mousePos.copy();
        boolean moved = false;

        Vec3 upDirection = new Vec3(0.0f, 1.0f, 0.0f);
        Vec3 rightDirection = Glm.cross(mForwardDirection, upDirection);
        float speed = 3f;

        int pressedKeysCount = pressedKey.values().stream().mapToInt(aBoolean -> aBoolean ? 1 : 0).sum();

        if (!isRightButtonDown)
            return false;
        if (pressedKey.getOrDefault(KeyEvent.VK_W, false)) {
            mPosition.addEqual(mForwardDirection.mul(speed * ts / pressedKeysCount));
            moved = true;
        }

        if (pressedKey.getOrDefault(KeyEvent.VK_S, false)) {
            mPosition.subEqual(mForwardDirection.mul(speed * ts / pressedKeysCount));
            moved = true;
        }

        if (pressedKey.getOrDefault(KeyEvent.VK_A, false)) {
            mPosition.subEqual(rightDirection.mul(speed * ts / pressedKeysCount));
            moved = true;
        }

        if (pressedKey.getOrDefault(KeyEvent.VK_D, false)) {
            mPosition.addEqual(rightDirection.mul(speed * ts / pressedKeysCount));
            moved = true;
        }

        if (pressedKey.getOrDefault(KeyEvent.VK_Q, false)) {
            mPosition.subEqual(upDirection.mul(speed * ts / pressedKeysCount));
            moved = true;
        }

        if (pressedKey.getOrDefault(KeyEvent.VK_E, false)) {
            mPosition.addEqual(upDirection.mul(speed * ts / pressedKeysCount));
            moved = true;
        }

        if (delta.x != 0.0f || delta.y != 0.0f) {
            float pitchDelta = delta.y * getRotationSpeed();
            float yawDelta = delta.x * getRotationSpeed();

            Quat q = Glm.normalize(Glm.cross(Glm.angleAxis(-pitchDelta, rightDirection),
                    Glm.angleAxis(-yawDelta, new Vec3(0.f, 1.0f, 0.0f))));

            mForwardDirection = Glm.rotate(q, mForwardDirection);
            delta.x = delta.y = 0;
            moved = true;
        }


        if (moved) {
            recalculateView();
            recalculateRayDirections();
        }

        return moved;
    }

    protected void recalculateProjection() {
        mProjection = Glm.perspectiveFov(
                (float) Math.toRadians(mVerticalFOV),
                (float) mViewportWidth,
                (float) mViewportHeight,
                mNearClip, mFarClip);
        mInverseProjection = Glm.inverse(mProjection);
    }

    protected void recalculateView() {
        mView = Glm.lookAt(mPosition, mPosition.add(mForwardDirection), new Vec3(0, 1, 0));
        mInverseView = Glm.inverse(mView);
    }

    protected void recalculateRayDirections() {
        mRayDirections = new Vec3[mViewportWidth * mViewportHeight];

        IntStream.range(0, mViewportHeight)
                .parallel()
                .forEach(y -> IntStream.range(0, mViewportWidth)
                        .forEach(x -> {
                            Vec2 coord = new Vec2((float) x / (float) mViewportWidth, (float) y / (float) mViewportHeight);
                            coord = coord.mul(2.0f).sub(1.0f);

                            Vec4 target = mInverseProjection.mul(new Vec4(coord.x, coord.y, 1, 1));
                            mRayDirections[x + y * mViewportWidth] = mInverseView.mul(Glm.normalize(target.xyz().div(target.w)));
                        })
                );
    }

    public Vec3[] getRayDirections() {
        return mRayDirections;
    }

    public Vec3 getPosition() {
        return mPosition;
    }

    public void setRightButtonDown(boolean rightButtonDown) {
        isRightButtonDown = rightButtonDown;
    }

    public void setPressedKey(int pressedKey, boolean state) {
        this.pressedKey.put(pressedKey, state);
    }

    public float getRotationSpeed() {
        return 0.3f;
    }

}
