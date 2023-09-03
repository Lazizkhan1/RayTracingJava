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

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vec3 neg() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    Vec3 addEq(Vec3 a) {
        this.x += a.x;
        this.y += a.y;
        this.z += a.z;
        return this;
    }
    Vec3 subEq(Vec3 a) {
        this.x -= a.x;
        this.y -= a.y;
        this.z -= a.z;
        return this;
    }

    Vec3 mulEq(Vec3 a) {
        this.x *= a.x;
        this.y *= a.y;
        this.z *= a.z;
        return this;
    }

    float length_squared() {
        return x * x + y * y + z * z;
    }

    float length() {
        return (float) sqrt(length_squared());
    }

    // Utility methods



    static Vec3 mul(float t, Vec3 a) {
        return new Vec3(a.x * t, a.y * t, a.z * t);
    }

    static Vec3 mul(Vec3 a, float t) {
        return mul(t, a);
    }

    static Vec3 div(float t, Vec3 a) {
        return mul(1 / t, a);
    }

    static Vec3 div(Vec3 a, float t) {
        return div(t, a);
    }

    static float dot(Vec3 a, Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    static Vec3 cross(Vec3 a, Vec3 b) {
        return new Vec3(a.y * b.z - a.z - b.y,
                        a.z * b.x - a.x * b.z,
                        a.x * b.y - a.y * b.x);
    }

    static Vec3 unitVector(Vec3 a) {
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

