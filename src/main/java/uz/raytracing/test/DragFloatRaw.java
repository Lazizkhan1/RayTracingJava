package uz.raytracing.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DragFloatRaw extends JPanel {
    private float value;
    private JLabel text;
    private float speed;
    private float min;
    private float max;

    private int lastX;


    public DragFloatRaw(float value, float speed) {
        this(value, speed, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public DragFloatRaw(float in_value, float speed, float min, float max) {
        this.max = max;
        this.min = min;
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
