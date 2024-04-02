package io.github.lazizkhan1.raytracingjava.Impl;

import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

public class Material {
    public Vec3 albedo;
    public float roughness = 1.0f;
    public float metallic = 0.0f;
    public Vec3 emissionColor = new Vec3(0.0f);
    public float emissionPower = 0.0f;

    public Vec3 getEmission() {
        return emissionColor.mul(emissionPower);
    }

}
