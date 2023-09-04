package uz.raytracing.util;

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
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public Vec3 add(Vec3 a) {
        this.x += a.x;
        this.y += a.y;
        this.z += a.z;
        return this;
    }
    public Vec3 add(float t) {
        this.x += t;
        this.y += t;
        this.z += t;
        return this;
    }
    public Vec3 sub(Vec3 a) {
        this.x -= a.x;
        this.y -= a.y;
        this.z -= a.z;
        return this;
    }

    public Vec3 sub(float t) {
        this.x -= t;
        this.y -= t;
        this.z -= t;
        return this;
    }

    public Vec3 mul(Vec3 a) {
        this.x *= a.x;
        this.y *= a.y;
        this.z *= a.z;
        return this;
    }

    public Vec3 mul(float t) {
        this.x *= t;
        this.y *= t;
        this.z *= t;
        return this;
    }

    public float length_squared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float) sqrt(length_squared());
    }

    public void normalize() {
        float length = this.length();
        this.x = this.x / length;
        this.y = this.y / length;
        this.z = this.z / length;
    }

    // Utility methods





    public static Vec3 mul(float t, Vec3 a) {
        return new Vec3(a.x * t, a.y * t, a.z * t);
    }

    public static Vec3 mul(Vec3 a, float t) {
        return mul(t, a);
    }

    public static Vec3 div(float t, Vec3 a) {
        return mul(1 / t, a);
    }

    public static Vec3 div(Vec3 a, float t) {
        return div(t, a);
    }

    public static float dot(Vec3 a, Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Vec3 cross(Vec3 a, Vec3 b) {
        return new Vec3(a.y * b.z - a.z - b.y,
                        a.z * b.x - a.x * b.z,
                        a.x * b.y - a.y * b.x);
    }

    public static Vec3 unitVector(Vec3 a) {
        return  div(a, a.length());
    }

    @Override
    public String toString() {
        return "{" +
                x + ", " +
                y + ", " +
                z + "}";
    }
}
