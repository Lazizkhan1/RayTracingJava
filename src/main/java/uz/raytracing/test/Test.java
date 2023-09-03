package uz.raytracing.test;

import uz.raytracing.util.Viewport;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Viewport viewport = new Viewport();
        JPanel controlPanel = new JPanel();
        JButton button = new JButton("Render");
        JLabel label = new JLabel("Framerate");
        JFrame frame = new JFrame("Test");
        File fileProperty = getPropertyFile();



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    File propertyFile = getPropertyFile();
                    FileOutputStream os = new FileOutputStream("Laziz");
                    Properties properties = new Properties();
                    properties.put("FrameWidth", frame.getWidth() + "");
                    properties.put("FrameHeight", frame.getHeight() + "");
                    properties.put("ScreenLocationX", frame.getLocationOnScreen().x + "");
                    properties.put("ScreenLocationY", frame.getLocationOnScreen().y + "");
                    os.close();
                    properties.store(os, "Main screen configuration");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } finally {
                  super.windowClosing(e);
                }
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(label);
        controlPanel.add(button);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewport, controlPanel);

        frame.add(splitPane);

        frame.setVisible(true);

    }

    public static File getPropertyFile() {
        return new File("./src/main/java/uz/raytracing/config/application.properties");
    }
}
