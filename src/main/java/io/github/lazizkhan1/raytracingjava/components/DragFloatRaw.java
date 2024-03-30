package io.github.lazizkhan1.raytracingjava.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DragFloatRaw extends JPanel {
    private float value;
    private final JLabel text;
    private float speed;
    private final float min;
    private final float max;

    private int lastX;


    public DragFloatRaw(float value, float speed) {
        this(value, speed, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public DragFloatRaw(float in_value, float speed, float inMin, float inMax) {
        this.max = inMax;
        this.min = inMin;
        this.value = in_value;
        this.speed = speed;

        text = new JLabel(String.format("%.3f", value));

        text.setForeground(Color.WHITE);
        text.setHorizontalAlignment(SwingConstants.TRAILING);
        setBackground(Color.DARK_GRAY);
        add(text);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    setBackground(Color.GRAY);
                    lastX = e.getXOnScreen();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(Color.DARK_GRAY);
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
                    int t = e.getXOnScreen();
                    value += (t - lastX) * speed;
                    if (value > max) {
                        value = max;
                    } else if (value < min) {
                        value = min;
                    }
                    text.setText(String.format("%.3f", value));
                    lastX = t;
                }
            }
        });
    }

    public float update() {
        return value;
    }
}
