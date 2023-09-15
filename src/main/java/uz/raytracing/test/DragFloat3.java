package uz.raytracing.test;

import uz.raytracing.util.glm.Vec3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;

public class DragFloat3 extends JPanel{
    private final JLabel label;
    private final DragFloatRaw[] drag;
    private final GridBagConstraints gc;
    private final Vec3 value;

    public DragFloat3(String inLabel, Vec3 in_value, float speed) {
        this.value = in_value;
        label = new JLabel(inLabel);
        label.setForeground(Color.LIGHT_GRAY);
        drag = new DragFloatRaw[3];
        drag[0] = new DragFloatRaw(value.x, 0.05f);
        drag[1] = new DragFloatRaw(value.y, 0.05f);
        drag[2] = new DragFloatRaw(value.z, 0.05f);
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(2, 2, 2, 2);
        gc.anchor = GridBagConstraints.NORTH;
        gc.weightx = 1;
        gc.weighty = 1;
        for(int i = 0; i < 3; i++) {
            add(drag[i], gc);
        }

        add(label, gc);
    }

    public void update() {
        value.x = drag[0].update();
        value.y = drag[1].update();
        value.z = drag[2].update();
    }

}
