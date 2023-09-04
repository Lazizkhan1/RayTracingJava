package uz.raytracing.components;

import uz.raytracing.util.Property;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class Frame extends JFrame {
    public Frame(String title) {
        super(title);
        setSize(Property.get("WindowWidth"), Property.get("WindowHeight"));
        setLocation(Property.get("WindowLocationX"), Property.get("WindowLocationY"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HashMap<String, String> propertiesTable = new HashMap<>();
                propertiesTable.put("WindowWidth", getWidth() + "");
                propertiesTable.put("WindowHeight", getHeight() + "");
                propertiesTable.put("WindowLocationX", getLocationOnScreen().x + "");
                propertiesTable.put("WindowLocationY", getLocationOnScreen().y + "");
                propertiesTable.put("DividerLocation", ((SplitPane) (((Frame) e.getSource()).getContentPane().getComponent(0))).getDividerLocation() + "");
                Property.set(propertiesTable);                       //I know this is piece of shit, but I couldn't find other shorter way :/
                super.windowClosing(e);

            }
        });
    }
    public Frame(String title, Component... components) {
        super(title);
        add(components);
    }

    public Frame() {
        super("");
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
