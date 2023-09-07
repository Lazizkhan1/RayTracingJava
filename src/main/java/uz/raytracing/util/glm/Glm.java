package uz.raytracing.util.glm;

public class Glm {
    public static float dot(Vec3 a, Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static float dot(Quat q1, Quat q2) {
        return q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;
    }

    public static float length(Quat q) {
        return (float) Math.sqrt(dot(q, q));
    }

    public static Vec3 cross(Vec3 a, Vec3 b) {
        return new Vec3(
                a.y * b.z - a.z * b.y,
                a.z * b.x - a.x * b.z,
                a.x * b.y - a.y * b.x);
    }

    public static Vec3 rotate(Quat q, Vec3 v) {
        return q.mul(v);
    }

    public static Quat angleAxis(float degAngle, Vec3 v) {
        float s = (float) Math.sin(Math.toRadians(degAngle) * 0.5f);
        return new Quat((float) Math.cos(Math.toRadians(degAngle) * 0.5f), v.x * s, v.y * s, v.z * s);
    }

    public static Quat cross(Quat q1, Quat q2) {
        return new Quat(
                q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z,
                q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y,
                q1.w * q2.y + q1.y * q2.w + q1.z * q2.x - q1.x * q2.z,
                q1.w * q2.z + q1.z * q2.w + q1.x * q2.y - q1.y * q2.x);
    }

    public static Vec3 normalize(Vec3 v) {
        float length = v.length();
        return new Vec3(v.x / length, v.y / length, v.z / length);
    }

    public static Mat4 perspectiveFov(float fov, float width, float height, float zNear, float zFar) {
        float h = (float) Math.cos(0.5 * fov) / (float) Math.sin(0.5 * fov);
        float w = h * height / width;

        Mat4 result = new Mat4(0);
        result.m00 = w;
        result.m11 = h;
        result.m22 = -(zFar + zNear) / (zFar - zNear);
        result.m23 = -1.0f;
        result.m32 = -(2.0f * zFar * zNear) / (zFar - zNear);
        return result;
    }

    public static Mat4 inverse(Mat4 m) {
        float a = m.m00 * m.m11 - m.m01 * m.m10;
        float b = m.m00 * m.m12 - m.m02 * m.m10;
        float c = m.m00 * m.m13 - m.m03 * m.m10;
        float d = m.m01 * m.m12 - m.m02 * m.m11;
        float e = m.m01 * m.m13 - m.m03 * m.m11;
        float f = m.m02 * m.m13 - m.m03 * m.m12;
        float g = m.m20 * m.m31 - m.m21 * m.m30;
        float h = m.m20 * m.m32 - m.m22 * m.m30;
        float i = m.m20 * m.m33 - m.m23 * m.m30;
        float j = m.m21 * m.m32 - m.m22 * m.m31;
        float k = m.m21 * m.m33 - m.m23 * m.m31;
        float l = m.m22 * m.m33 - m.m23 * m.m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        return new Mat4(
                (+m.m11 * l - m.m12 * k + m.m13 * j) * det,
                (-m.m01 * l + m.m02 * k - m.m03 * j) * det,
                (+m.m31 * f - m.m32 * e + m.m33 * d) * det,
                (-m.m21 * f + m.m22 * e - m.m23 * d) * det,
                (-m.m10 * l + m.m12 * i - m.m13 * h) * det,
                (+m.m00 * l - m.m02 * i + m.m03 * h) * det,
                (-m.m30 * f + m.m32 * c - m.m33 * b) * det,
                (+m.m20 * f - m.m22 * c + m.m23 * b) * det,
                (+m.m10 * k - m.m11 * i + m.m13 * g) * det,
                (-m.m00 * k + m.m01 * i - m.m03 * g) * det,
                (+m.m30 * e - m.m31 * c + m.m33 * a) * det,
                (-m.m20 * e + m.m21 * c - m.m23 * a) * det,
                (-m.m10 * j + m.m11 * h - m.m12 * g) * det,
                (+m.m00 * j - m.m01 * h + m.m02 * g) * det,
                (-m.m30 * d + m.m31 * b - m.m32 * a) * det,
                (+m.m20 * d - m.m21 * b + m.m22 * a) * det);
    }

    public static Mat4 lookAt(Vec3 eye, Vec3 center, Vec3 up) {
        // f(normalize(center - eye))
        float fX = center.x - eye.x;
        float fY = center.y - eye.y;
        float fZ = center.z - eye.z;
        float inverseSqrt = 1f / (float) Math.sqrt(fX * fX + fY * fY + fZ * fZ);
        fX *= inverseSqrt;
        fY *= inverseSqrt;
        fZ *= inverseSqrt;
        // s(normalize(cross(f, up)))
        float sX = fY * up.z - fZ * up.y;
        float sY = fZ * up.x - fX * up.z;
        float sZ = fX * up.y - fY * up.x;
        inverseSqrt = 1.0f / (float) Math.sqrt(sX * sX + sY * sY + sZ * sZ);
        sX *= inverseSqrt;
        sY *= inverseSqrt;
        sZ *= inverseSqrt;
        // u(cross(s, f))
        float uX = sY * fZ - sZ * fY;
        float uY = sZ * fX - sX * fZ;
        float uZ = sX * fY - sY * fX;
        Mat4 res = new Mat4(0.0f);
        res.m00 = sX;
        res.m01 = uX;
        res.m02 = -fX;
        res.m03 = 0f;
        res.m10 = sY;
        res.m11 = uY;
        res.m12 = -fY;
        res.m13 = 0f;
        res.m20 = sZ;
        res.m21 = uZ;
        res.m22 = -fZ;
        res.m23 = 0f;
        res.m30 = -sX * eye.x - sY * eye.y - sZ * eye.z;
        res.m31 = -uX * eye.x - uY * eye.y - uZ * eye.z;
        res.m32 = +fX * eye.x + fY * eye.y + fZ * eye.z;
        res.m33 = 1f;
        return res;
    }

    public static Quat normalize(Quat q) {
        float len = length(q);
        if (len <= 0) { // Problem
            return new Quat(1, 0, 0, 0);
        }
        float oneOverLen = 1 / len;
        return new Quat(q.w * oneOverLen, q.x * oneOverLen, q.y * oneOverLen, q.z * oneOverLen);
    }

}
