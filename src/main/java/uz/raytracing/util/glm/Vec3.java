package uz.raytracing.util.glm;

import static java.lang.Math.sqrt;

public class Vec3 {
    public float x;
    public float y;
    public float z;

    public Vec3() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }
    public Vec3(float t) {
        this.x = t;
        this.y = t;
        this.z = t;
    }

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 neg() {
        return new Vec3(-this.x, -this.y, -this.z);
    }

    public Vec3 add(Vec3 a) {
        return new Vec3(this.x + a.x, this.y + a.y, this.z + a.z);
    }
    public Vec3 add(float t) {
        return new Vec3(this.x + t, this.y + t, this.z + t);
    }
    public Vec3 sub(Vec3 a) {
        return new Vec3(this.x - a.x, this.y - a.y, this.z - a.z);
    }

    public Vec3 sub(float t) {
        return new Vec3(this.x - t, this.y - t, this.z - t);
    }

    public Vec3 mul(Vec3 a) {
        return new Vec3(this.x * a.x, this.y * a.y, this.z * a.z);
    }

    public Vec3 mul(float t) {
        return new Vec3(this.x * t, this.y * t, this.z * t);
    }

    public float length_squared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float) sqrt(length_squared());
    }

    public Vec3 normalize() {
        float length = this.length();
        this.x = this.x / length;
        this.y = this.y / length;
        this.z = this.z / length;
        return this;
    }

    public void equal(Vec3 a) {
        this.x = a.x;
        this.y = a.y;
        this.z = a.z;
    }

    @Override
    public String toString() {
        return "{" +
                x + ", " +
                y + ", " +
                z + "}";
    }
}
