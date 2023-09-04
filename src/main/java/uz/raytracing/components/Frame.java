package uz.raytracing.components;

import uz.raytracing.util.Property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class Frame extends JFrame {
    public Frame(String title) {
        super(title);
        setSize(Property.get("FrameWidth"), Property.get("FrameHeight"));
        setLocation(Property.get("FrameLocationX"), Property.get("FrameLocationY"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HashMap<String, String> propertiesTable = new HashMap<>();
                propertiesTable.put("FrameWidth", getWidth() + "");
                propertiesTable.put("FrameHeight", getHeight() + "");
                propertiesTable.put("FrameLocationX", getLocationOnScreen().x + "");
                propertiesTable.put("FrameLocationY", getLocationOnScreen().y + "");
                propertiesTable.put("DividerLocation", ((SplitPane) (((Frame) e.getSource()).getContentPane().getComponent(0))).getDividerLocation() + "");
                Property.set(propertiesTable);                       //I know this is piece of shit, but I couldn't find other shorter way :/
                super.windowClosing(e);

            }
        });
    }

    public void add(Component... components) {
        for (Component comp : components)
            add(comp);
    }

    @Override
    public Component add(Component comp) {
        return super.add(comp);
    }
}
