package io.github.lazizkhan1.raytracingjava.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class DragFloat extends JPanel {
    private final DragFloatRaw drag;
    private final float value;

    public DragFloat(String inLabel, float in_value, float speed) {
        this.value = in_value;
        JLabel label = new JLabel(inLabel);
        label.setForeground(Color.LIGHT_GRAY);
        drag = new DragFloatRaw(value, speed);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(2, 2, 2, 2);
        gc.anchor = GridBagConstraints.NORTH;
        gc.weightx = 1;
        gc.weighty = 1;

        add(drag, gc);
        add(label, gc);
    }

    public DragFloat(String label, float in_value, float speed, float min, float max) {
        this.value = in_value;
        JLabel text = new JLabel(label);
        text.setForeground(Color.LIGHT_GRAY);
        drag = new DragFloatRaw(value, speed, min, max);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(2, 2, 2, 2);
        gc.anchor = GridBagConstraints.NORTH;
        gc.weightx = 1;
        gc.weighty = 1;

        add(drag, gc);
        add(text, gc);
    }

    public float update() {
        return drag.update();
    }

}
