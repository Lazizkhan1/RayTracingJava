package uz.raytracing.util;

public class Vec4 {
    public float x;
    public float y;
    public float z;
    public float w;

    public float r = x;
    public float g = y;
    public float b = z;
    public float a = w;

    public Vec4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4(Vec3 vec3, float t) {
        this.x = vec3.x;
        this.y = vec3.y;
        this.z = vec3.z;
        this.w = t;
    }

    public Vec4(int x, int y, int z, int w) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        this.w = (float) w;
    }

    public Vec4(Vec3 vec3) {
        this(vec3, 0.0f);
    }

    public Vec4 mul(float t) {
        this.x *= t;
        this.y *= t;
        this.z *= t;
        this.w *= t;
        return this;
    }
    public Vec3 xyz() {
        return new Vec3(this.x, this.y, this.z);
    }


}
