package uz.raytracing.components;

import uz.raytracing.Camera;
import uz.raytracing.util.Property;
import uz.raytracing.util.glm.Vec2;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.*;
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
        Vec2 lastCursorPosition = new Vec2();
        viewport.setFocusable(true);
        viewport.requestFocusInWindow();
        viewport.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    mCamera.setRightButtonDown(true);
                    setCursor(getToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "Blank cursor"));
                    lastCursorPosition.x = e.getXOnScreen();
                    lastCursorPosition.y = e.getYOnScreen();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mCamera.setRightButtonDown(false);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    try {
                        Robot robot = new Robot();
                        robot.mouseMove((int) lastCursorPosition.x, (int) lastCursorPosition.y);
                    } catch (AWTException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        viewport.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getModifiersEx() == 4096) {
                    mCamera.setMousePosition(e.getXOnScreen(), e.getYOnScreen());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {


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
                mCamera.setPressedKey(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                mCamera.setPressedKey(-1);
            }
        };
    }
}
