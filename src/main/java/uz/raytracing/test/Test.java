package uz.raytracing.test;

import uz.raytracing.components.Frame;
import uz.raytracing.components.Panel;
import uz.raytracing.components.SplitPane;
import uz.raytracing.components.Viewport;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;


public class Test {
    public static void main(String[] args) {
        JButton button = new JButton("Render");
        JLabel label = new JLabel("Framerate");
        Panel controlPanel = new Panel(label, button);
        Viewport viewport = new Viewport();
        SplitPane splitPane = new SplitPane(viewport, controlPanel);
        Frame frame = new Frame("Test");

        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);

    }

}
