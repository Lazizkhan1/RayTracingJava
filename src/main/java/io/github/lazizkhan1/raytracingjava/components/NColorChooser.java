package io.github.lazizkhan1.raytracingjava.components;

import io.github.lazizkhan1.raytracingjava.Impl.Renderer;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

import javax.swing.*;
import java.awt.*;


public class NColorChooser extends JComponent{
    private JColorChooser colorChooser;

    public NColorChooser(String text, Vec3 initialColor) {
        super();
        JLabel label = new JLabel(text);
        colorChooser.setColor(new Color(Renderer.convertRGB(initialColor)));

    }

}
