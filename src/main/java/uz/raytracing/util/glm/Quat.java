package uz.raytracing.util.glm;


public class Quat {
    public float w, x, y, z;

    public Quat(glm.quat.Quat q) {
        this(q.w, q.x, q.y, q.z);
    }

    public Quat(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 mul(Vec3 v) {
        Vec3 quatVec = new Vec3(x, y, z);
        Vec3 uv = Glm.cross(quatVec, v);
        Vec3 uuv = Glm.cross(quatVec, uv);
        return v.add((uv.mul(w).add(uuv)).mul(2));
    }
}
