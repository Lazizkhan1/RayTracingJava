package uz.raytracing.components;

import uz.raytracing.Impl.Renderer;
import uz.raytracing.util.glm.Vec3;
import uz.raytracing.util.glm.Vec4;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorChooser extends JButton {

    private final int width = 650;
    private final int height = 250;
    private final JColorChooser colorChooser;

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
