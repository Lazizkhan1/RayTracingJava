package uz.raytracing.util.glm;

import java.awt.*;

public class Vec2 {
    public float x;
    public float y;

    public Vec2() {
        x = 0.0f;
        y = 0.0f;
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Vec2 mul(float t) {
        return new Vec2(this.x * t, this.y * t);
    }

    public Vec2 sub(float t) {
        return new Vec2(this.x - t, this.y - t);
    }

    public Vec2 sub(Vec2 v) {
        return new Vec2(this.x - v.x, this.y - v.y);
    }

    public Vec2 copy() {
        return new Vec2(x, y);
    }


    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}