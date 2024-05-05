package io.github.lazizkhan1.raytracingjava.Impl;

import io.github.lazizkhan1.raytracingjava.components.Image;
import io.github.lazizkhan1.raytracingjava.util.glm.Glm;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Skybox {
    private BufferedImage mSphereImage;
    private boolean mIsLoaded;

    public  Skybox(String resourceName) {
        mSphereImage = new Image(2,2, BufferedImage.TYPE_INT_RGB);
        mIsLoaded = false;

        if (resourceName == null) {
            resourceName = "Sky.jpg";
        }

        String finalResourceName = resourceName;
        try {
            System.out.println("Loading skybox "+ finalResourceName +"...");
            File file = new File("src/main/resources/skybox/" + resourceName);
            mSphereImage = ImageIO.read(file);
            System.out.println("Skybox ready.");
            mIsLoaded = true;
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("Error loading skybox " + finalResourceName);
        }
    }

    public Vec3 getSkyColor(Vec3 position) {
        if (!mIsLoaded)
            return new Vec3(0.6f, 0.7f, 0.9f);

        position = Glm.normalize(position);
        float u = (float) (0.5+Math.atan2(position.z, position.x)/(2*Math.PI));
        float v = (float) (0.5 - Math.asin(position.y/Math.PI));

        int skyColor;
        try {
            skyColor = mSphereImage.getRGB(
                    (int)(u*(mSphereImage.getWidth()-1)),
                    (int)(v*(mSphereImage.getHeight()-1))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new Vec3(0.6f, 0.7f, 0.9f);
        }
        return Renderer.convertARGB(skyColor);
    }
}
