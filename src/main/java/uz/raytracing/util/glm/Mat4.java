package uz.raytracing.util.glm;


public class Mat4 {
    public float m00, m10, m20, m30;
    public float m01, m11, m21, m31;
    public float m02, m12, m22, m32;
    public float m03, m13, m23, m33;


    public Mat4(float f) {
        this(
                f, 0, 0, 0,
                0, f, 0, 0,
                0, 0, f, 0,
                0, 0, 0, f);
    }

    public Mat4(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13,
                float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    public Vec4 mul(Vec4 v) {
        return new Vec4(
                m00 * v.x + m01 * v.y + m02 * v.z + m03 * v.w,
                m10 * v.x + m11 * v.y + m12 * v.z + m13 * v.w,
                m20 * v.x + m21 * v.y + m22 * v.z + m23 * v.w,
                m30 * v.x + m31 * v.y + m32 * v.z + m33 * v.w);
    }
}
