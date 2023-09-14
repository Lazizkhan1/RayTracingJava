package uz.raytracing.Impl;

import uz.raytracing.util.glm.Vec3;

public class Sphere {
    public Vec3 position;
    public float radius;
    public Vec3 albedo;

    public Sphere(Vec3 position, float radius, Vec3 albedo) {
        this.position = position;
        this.radius = radius;
        this.albedo = albedo;
    }
}