package io.github.lazizkhan1.raytracingjava.Impl;


import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

public class Sphere {
    public Vec3 position;
    public float radius;
    public int materialIndex;

    public Sphere(Vec3 position, float radius, int index) {
        this.position = position;
        this.radius = radius;
        this.materialIndex = index;
    }
}