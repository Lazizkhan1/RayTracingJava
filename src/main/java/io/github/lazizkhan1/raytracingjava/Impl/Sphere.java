package io.github.lazizkhan1.raytracingjava.Impl;


import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

public class Sphere {
    public Vec3 position;
    public float radius;
    public Vec3 albedo;
    public Vec3 origin;

    public Sphere(Vec3 position, float radius, Vec3 albedo) {
        this.position = position;
        this.radius = radius;
        this.albedo = albedo;
    }
}