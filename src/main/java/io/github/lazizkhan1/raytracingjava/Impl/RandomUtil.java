package io.github.lazizkhan1.raytracingjava.Impl;

import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static Vec3 vec3(float min, float max) {
        return new Vec3(
                ThreadLocalRandom.current().nextFloat(min, max),
                ThreadLocalRandom.current().nextFloat(min, max),
                ThreadLocalRandom.current().nextFloat(min, max)
        );
    }
}
