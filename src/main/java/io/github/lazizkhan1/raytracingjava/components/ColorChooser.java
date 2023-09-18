package io.github.lazizkhan1.raytracingjava.components;

import io.github.lazizkhan1.raytracingjava.Impl.Renderer;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.Color;
import java.awt.MouseInfo;



public class ColorChooser extends JButton {

    private final int width = 650;
    private final int height = 250;
    private final JColorChooser colorChooser;

    //TODO: Add 3 drag int from 0 -> 255 and last color button.

    public ColorChooser(Vec3 value) {
        super();
        colorChooser = new JColorChooser(new Color(Renderer.convertRGB(value)));
        colorChooser.setPreviewPanel(new JPanel());
        colorChooser.setChooserPanels(new AbstractColorChooserPanel[]{colorChooser.getChooserPanels()[1]});

        colorChooser.getSelectionModel().addChangeListener(e -> {
            Color color = colorChooser.getColor();
            value.x = color.getRed() / 255.0f;
            value.y = color.getGreen() / 255.0f;
            value.z = color.getBlue() / 255.0f;
            setBackground(color);
        });
        addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setSize(width, height);
            dialog.add(colorChooser);
            dialog.setLocation(
                    MouseInfo.getPointerInfo().getLocation().x - width / 2,
                    MouseInfo.getPointerInfo().getLocation().y - height / 2
            );
            dialog.setVisible(true);
        });
        setBackground(colorChooser.getColor());
        setSize(100, 100);
        setFocusable(false);
    }

}
