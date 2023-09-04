package uz.raytracing;

import uz.raytracing.components.Frame;
import uz.raytracing.components.Panel;
import uz.raytracing.components.SplitPane;
import uz.raytracing.components.Viewport;
import uz.raytracing.util.Image;
import uz.raytracing.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class Application {
    private final Viewport mViewport;
    private final Renderer mRenderer;
    private final JLabel frameTime;

    public Application() {
        mViewport = new Viewport();
        mRenderer = new Renderer();
        JButton renderButton = new JButton("Render");
        frameTime = new JLabel();
        SplitPane splitPane = new SplitPane(mViewport, new Panel(frameTime, renderButton));
        Frame frame = new Frame("Ray Tracing");
        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void start() {
        while (true) {
            Image image = mRenderer.getmFinalImage();
            if(image != null) {
                mViewport.setImage(image);
                mViewport.repaint();
            }
            render();
        }
    }

    public void render() {
        Timer timer = new Timer();
        mRenderer.onResize(mViewport.getWidth(), mViewport.getHeight());
        mRenderer.render();
        frameTime.setText("Frametime: " + timer.calculate());
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }
}