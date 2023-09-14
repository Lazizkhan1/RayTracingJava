package uz.raytracing.util.glm;

public class Vec4 {
    public float x;
    public float y;
    public float z;
    public float w;
    public Vec3 xyz;
    public final static Vec4 zero;

    static {
        zero = new Vec4(0, 0, 0, 1);
    }


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

    public Vec4(Vec4 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }


    public Vec4 add(Vec4 v) {
        return new Vec4(
                this.x + v.x,
                this.y + v.y,
                this.z + v.z,
                this.w + v.w);
    }

    public Vec4 mul(float t) {
        return new Vec4(
                this.x * t,
                this.y * t,
                this.z * t,
                this.w * t);
    }

    public Vec4 mul(Vec4 v) {
        return new Vec4(
                this.x * v.x,
                this.y * v.y,
                this.z * v.z,
                this.w * v.w);
    }

    public Vec4 sub(Vec4 v) {
        return new Vec4(
                this.x - v.x,
                this.y - v.y,
                this.z - v.z,
                this.w - v.w);
    }

    public Vec3 xyz() {
        return new Vec3(this.x, this.y, this.z);
    }

    public static Vec4 clamp(Vec4 vec4, float min, float max) {
        return new Vec4(
                clamp_(vec4.x, min, max),
                clamp_(vec4.y, min, max),
                clamp_(vec4.z, min, max),
                clamp_(vec4.w, min, max));
    }

    public static float clamp_(float t, float min, float max) {
        return Math.max(Math.min(t, max), min);
    }
}
