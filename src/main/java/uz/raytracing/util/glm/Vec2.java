package uz.raytracing.util.glm;

public class Vec2 {
    public float x;
    public float y;

    public Vec2() {
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 mul(float t) {
        this.x = this.x * t;
        this.y = this.y * t;
        return this;
    }

    public Vec2 sub(float t) {
        this.x = this.x - t;
        this.y = this.y - t;
        return this;
    }
}