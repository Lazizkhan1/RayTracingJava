package io.github.lazizkhan1.raytracingjava.components;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Dimension;

public class Panel extends JPanel {
    public Panel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 0, 5));
    }

    public Panel(Component... components) {
        this();
        add(components);
    }

    public void add(Component... components) {
        for (Component comp : components)
            addBordered(comp);
    }

    public void addBordered(Component comp, int width, int height) {
        add(Box.createRigidArea(new Dimension(width, height)));
        super.add(comp);
    }

    public void addBordered(Component comp) {
        addBordered(comp, 5, 5);
    }
}
