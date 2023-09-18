package io.github.lazizkhan1.raytracingjava.util;

import io.github.lazizkhan1.raytracingjava.util.glm.Vec2;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;

public class Mouse {
    private static Vec2 mCurrentPosition = new Vec2();

    private static Vec2 mLastPosition = new Vec2();

    private static boolean isLocked = false;

    private static Robot mRobot;

    static {
        try {
            mRobot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vec2 getPosition() {
        if(!isLocked) {
            mLastPosition.equal(mCurrentPosition);
            mCurrentPosition.equal(MouseInfo.getPointerInfo().getLocation());
            return mCurrentPosition;
        }
        mCurrentPosition.equal(mCurrentPosition.add(new Vec2(MouseInfo.getPointerInfo().getLocation()).sub(mLastPosition)));
        mRobot.mouseMove((int)mLastPosition.x, (int)mLastPosition.y);
        return mCurrentPosition;
    }

    public static void setLockMode() {
        isLocked = true;
        mLastPosition.equal(MouseInfo.getPointerInfo().getLocation());
    }

    public static void setNormalMode() {
        isLocked = false;
        mRobot.mouseMove((int)mLastPosition.x, (int)mLastPosition.y);
    }
}
