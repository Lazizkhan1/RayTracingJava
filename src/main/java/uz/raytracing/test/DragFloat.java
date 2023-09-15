package uz.raytracing.test;

import uz.raytracing.util.glm.Vec3;

import javax.swing.*;
import java.awt.*;

public class DragFloat extends JPanel {
    private final JLabel label;
    private final DragFloatRaw drag;
    private final GridBagConstraints gc;
    private float value;

    public DragFloat(String inLabel, float in_value, float speed) {
        this.value = in_value;
        label = new JLabel(inLabel);
        label.setForeground(Color.LIGHT_GRAY);
        drag = new DragFloatRaw(value, 0.05f);
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(2, 2, 2, 2);
        gc.anchor = GridBagConstraints.NORTH;
        gc.weightx = 1;
        gc.weighty = 1;

        add(drag, gc);
        add(label, gc);
    }

    public float update() {
        return drag.update();
    }

}
