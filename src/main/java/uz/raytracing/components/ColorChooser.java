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

    private JColorChooser colorChooser;
    private AbstractColorChooserPanel abs;

    private Vec3 t_value;

    public ColorChooser(String mode, Vec3 value) {
        super();
        t_value = value;
        colorChooser = new JColorChooser(new Color(Renderer.convertRGB(value)));
        colorChooser.setChooserPanels(new AbstractColorChooserPanel[]{colorChooser.getChooserPanels()[1]});
//        for(AbstractColorChooserPanel a: colorChooser.getChooserPanels()) {
//            if (a.getDisplayName().equals(mode)) {
//                abs = a;
//                break;
//            }
//        }
        colorChooser.getChooserPanels()[0].getColorSelectionModel().addChangeListener(e -> {
            System.out.println("dasd");
//            Color color = colorChooser.getColor();
//            t_value.x = color.getRed()   / 255.0f;
//            t_value.y = color.getGreen() / 255.0f;
//            t_value.z = color.getBlue()  / 255.0f;
//            setForeground(color);
        });

    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JColorChooser.showDialog(colorChooser, "Color chooser", colorChooser.getColor());

            }
        });
    }
}
