package uz.raytracing;

import uz.raytracing.util.glm.Vec2;
import uz.raytracing.util.glm.Vec3;
import uz.raytracing.util.glm.Glm;

import java.awt.event.KeyEvent;

public class Camera {
    private final Vec3 mPosition;
    private final Vec3 mForwardDirection;

    private Vec2 mMousePosition;
    private boolean isRightButtonDown = false;
    private int pressedKey = -1;
    private Vec2 delta;


    public Camera(){
        mForwardDirection = new Vec3(0, 0, -1);
        mPosition = new Vec3(0, 0, 6);
    }

    public boolean onUpdate(float ts) {
        boolean moved = false;

        Vec3 upDirection = new Vec3(0.0f, 1.0f, 0.0f);
        Vec3 rightDirection = Glm.cross(mForwardDirection, upDirection);

        float speed = 3f;
        if(isRightButtonDown) {
            switch (pressedKey) {
                case KeyEvent.VK_W -> mPosition.equal(mPosition.add(mForwardDirection.mul(speed * ts)));
                case KeyEvent.VK_S -> mPosition.equal(mPosition.sub(mForwardDirection.mul(speed * ts)));
                case KeyEvent.VK_A -> mPosition.equal(mPosition.sub(rightDirection.mul(speed * ts)));
                case KeyEvent.VK_D -> mPosition.equal(mPosition.add(rightDirection.mul(speed * ts)));
                case KeyEvent.VK_Q -> mPosition.equal(mPosition.add(upDirection.mul(speed * ts)));
                case KeyEvent.VK_E -> mPosition.equal(mPosition.sub(upDirection.mul(speed * ts)));

            }

        }

//        if(Input.isKeyDown('a')) {
//            mPosition.equal(mPosition.sub(rightDirection.mul(speed * ts)));
//            moved = true;
//        }
//        if(Input.isKeyDown('d')) {
//            mPosition.equal(mPosition.add(rightDirection.mul(speed * ts)));
//            moved = true;
//        }
//        if(Input.isKeyDown('q')) {
//            mPosition.equal(mPosition.sub(upDirection.mul(speed * ts)));
//            moved = true;
//        }
//        if(Input.isKeyDown('e')) {
//            mPosition.equal(mPosition.add(upDirection.mul(speed * ts)));
//            moved = true;
//        }

        return moved;
    }


    public Vec3 getPosition() {
        return mPosition;
    }

    public Vec2 getMousePosition() {
        return mMousePosition;
    }

    public void setMousePosition(int newX, int newY) {
        this.delta = new Vec2(this.mMousePosition.x - newX, this.mMousePosition.y - newY );
        this.mMousePosition = new Vec2(newX, newY);
    }

    public void setRightButtonDown(boolean rightButtonDown) {
        isRightButtonDown = rightButtonDown;
    }


    public void setPressedKey(int pressedKey) {
        this.pressedKey = pressedKey;
    }
}
