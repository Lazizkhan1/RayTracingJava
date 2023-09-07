package uz.raytracing;

import uz.raytracing.components.Frame;
import uz.raytracing.components.Panel;
import uz.raytracing.components.SplitPane;
import uz.raytracing.components.Viewport;
import uz.raytracing.components.Image;
import uz.raytracing.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;

public class Application {
    private final Viewport mViewport;
    private final Renderer mRenderer;
    private final JLabel frameTime;
    private final Camera mCamera;

    public Application() {
        mViewport = new Viewport();
        mRenderer = new Renderer();
        mCamera = new Camera(45.0f, 0.1f, 100.0f);
        JButton renderButton = new JButton("Render");
        frameTime = new JLabel();
        SplitPane splitPane = new SplitPane(mViewport, new Panel(frameTime, renderButton));
        Frame frame = new Frame("Ray Tracing", mCamera);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.setVisible(true);
    }

    public void start() {
        Timer timer = new Timer();
        while (true) {
            Image image = mRenderer.getmFinalImage();
            if (image != null) {
                mViewport.setImage(image);
                mViewport.repaint();
            }
            render(timer);

        }
    }

    public void render(Timer timer) {
        float ts = timer.getDeltaTime();
        mRenderer.onResize(mViewport.getWidth(), mViewport.getHeight());
        mCamera.onResize(mViewport.getWidth(), mViewport.getHeight());
        mCamera.onUpdate(Math.min(ts, 0.033f));
        mRenderer.render(mCamera);
        frameTime.setText(String.format("FrameTime: %.3fms", ts * 1000));
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }
}