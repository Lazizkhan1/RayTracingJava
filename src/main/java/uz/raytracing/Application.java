package uz.raytracing;

import uz.raytracing.util.Viewport;

import javax.swing.*;
import java.awt.*;

public class Application {
    private final JFrame mMainFrame;
    private final Viewport mViewport;
//    private int mViewportWidth;
//    private int mViewportHeight;
    private final Renderer mRenderer;

    public Application() {
        mMainFrame = new JFrame("RayTracing");
        mMainFrame.setSize(800, 600);
        mMainFrame.setLocationRelativeTo(null);
        mMainFrame.setLayout(new GridLayout());
        mMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mViewport = new Viewport();
        mRenderer = new Renderer();
        mMainFrame.add(mViewport);
        mMainFrame.setVisible(true);

    }

    public void start() {
        new Thread(() -> {
            while (true) {
                mRenderer.onResize(mViewport.getWidth(), mViewport.getHeight());
                mRenderer.render();
                mViewport.setImage(mRenderer.getmFinalImage());
                mViewport.repaint();

            }
        }).start();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }
}
