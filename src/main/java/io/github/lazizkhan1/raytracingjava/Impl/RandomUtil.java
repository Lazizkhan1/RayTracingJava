package io.github.lazizkhan1.raytracingjava.Impl;

import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

import java.util.Random;

public class RandomUtil {
    public static Random random = new Random();

    public static Vec3 vec3(float min, float max) {
        return new Vec3(
                random.nextFloat(min, max),
                random.nextFloat(min, max),
                random.nextFloat(min, max)
        );
    }
}
