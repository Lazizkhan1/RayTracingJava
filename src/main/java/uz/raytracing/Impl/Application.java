package uz.raytracing.Impl;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import uz.raytracing.test.DragFloat;
import uz.raytracing.test.DragFloat3;
import uz.raytracing.components.Frame;
import uz.raytracing.components.Image;
import uz.raytracing.components.Panel;
import uz.raytracing.components.SplitPane;
import uz.raytracing.components.Viewport;
import uz.raytracing.util.Timer;
import uz.raytracing.util.glm.Vec3;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Toolkit;

public class Application {
    private final Viewport mViewport;
    private final Renderer mRenderer;
    private final Frame mMainFrame;
    private final SplitPane mSplitPane;
    private final JLabel mFrameRate;
    private final Camera mCamera;
    private final Scene mScene;
    private final DragFloat3[] dragFloat3s;
    private final DragFloat[] dragFloats;

    public Application() {
        mViewport = new Viewport();
        mRenderer = new Renderer();
        mScene = new Scene();
        mScene.spheres.add(new Sphere(new Vec3(-1, 0, 0), 0.5f, new Vec3(0, 1, 1)));

        dragFloat3s = new DragFloat3[mScene.spheres.size()];
        dragFloats = new DragFloat[mScene.spheres.size()];

        mCamera = new Camera(45.0f, 0.1f, 100.0f);
        mFrameRate = new JLabel("FrameRate: ");
        dragFloat3s[0] = new DragFloat3("Position", mScene.spheres.get(0).position, 0.1f);
        dragFloats[0] = new DragFloat("Radius", mScene.spheres.get(0).radius, 0.05f);

        Panel controlPanel = new Panel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(dragFloat3s[0]);
        controlPanel.add(dragFloats[0]);
        Panel configPanel = new Panel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.add(mFrameRate);
        JScrollPane control = new JScrollPane(controlPanel);
        JScrollPane config = new JScrollPane(configPanel);

        SplitPane controlPane = new SplitPane(JSplitPane.VERTICAL_SPLIT, control, config);
        Panel panel = new Panel(controlPane);
        mSplitPane = new SplitPane(mViewport, panel);
        mMainFrame = new Frame("Ray Tracing", mCamera);
        mMainFrame.add(mSplitPane);
        mMainFrame.setFocusable(true);
        mMainFrame.requestFocus();
        mSplitPane.setContinuousLayout(false);
        Toolkit.getDefaultToolkit().setDynamicLayout(false); // resize bug solution
        mMainFrame.setVisible(true);
    }

    public void start() {
            Timer timer = new Timer();
            while (true) {
                Image image = mRenderer.getmFinalImage();
                if (image != null) {
                    mViewport.setImage(image);
                }
                for(DragFloat3 drag: dragFloat3s) {
                    drag.update();
                }
                for (int i = 0; i < dragFloats.length; i++) {
                    mScene.spheres.get(i).radius = dragFloats[i].update();
                }
                render(timer);
            }
    }

    public void render(Timer timer) {
        float ts = timer.getDeltaTime();
        mRenderer.onResize(mViewport.getWidth(), mViewport.getHeight());
        mCamera.onResize(mViewport.getWidth(), mViewport.getHeight());
        mCamera.onUpdate(Math.min(ts, 0.033f));
        mRenderer.render(mScene, mCamera);
        mFrameRate.setText(String.format("FrameTime: %.3fms", ts * 1000));
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacDarkLaf());
        Application application = new Application();
        application.start();
    }
}