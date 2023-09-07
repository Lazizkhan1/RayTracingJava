package uz.raytracing.components;

import uz.raytracing.Camera;
import uz.raytracing.util.Mouse;
import uz.raytracing.util.Property;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Frame extends JFrame {
    private final Camera mCamera;

    public Frame(String title, Camera camera) {
        super(title);
        this.mCamera = camera;
        setSize(Property.get("WindowWidth"), Property.get("WindowHeight"));
        setLocation(Property.get("WindowLocationX"), Property.get("WindowLocationY"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(getWindowAdapter());
        addKeyListener(getKeyListener());

    }

    public void add(Component... components) {
        for (Component comp : components)
            add(comp);
    }

    @Override
    public Component add(Component comp) {
        return super.add(comp);
    }

    @Override
    public void setVisible(boolean b) {
        Viewport viewport = (Viewport) ((SplitPane) getContentPane().getComponent(0)).getComponent(0);
        viewport.setFocusable(true);
        viewport.requestFocusInWindow();
        viewport.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    mCamera.setRightButtonDown(true);
                    Mouse.setLockMode();
                    setCursor(getToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "Blank cursor"));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    mCamera.setRightButtonDown(false);
                    setCursor(Cursor.getDefaultCursor());
                    Mouse.setNormalMode();
                }
            }
        });
        super.setVisible(b);
    }

    private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
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
        };
    }

    private KeyListener getKeyListener() {
        return new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                mCamera.setPressedKey(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                mCamera.setPressedKey(e.getKeyCode(), false);
            }
        };
    }
}
