package io.github.lazizkhan1.raytracingjava.Impl;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import io.github.lazizkhan1.raytracingjava.components.*;
import io.github.lazizkhan1.raytracingjava.util.Timer;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Toolkit;

public class RayTracing implements Layer {
    private final Viewport mViewport;
    private final Renderer mRenderer;
    private final JLabel mFrameRate;
    private final Camera mCamera;
    private final Scene mScene;
    private final DragFloat3[] dragFloat3s;
    private final DragFloat[] dragFloats;

    private float m_LastRenderTime;

    public RayTracing() {
        mViewport = new Viewport();
        mRenderer = new Renderer();
        mScene = new Scene();

        Material materialPink = mScene.materials.set(new Material());
        materialPink.albedo = new Vec3(1.0f, 0.0f, 1.0f);
        materialPink.roughness = 0.0f;
        materialPink.metallic = 0.0f;

        Material materialBlue = mScene.materials.set(new Material());
        materialBlue.albedo = new Vec3(0.2f, 0.3f, 1.0f);
        materialBlue.roughness = 0.1f;
        materialBlue.metallic = 0.0f;

        mScene.spheres.add(new Sphere(
                new Vec3(0.0f, 0.0f, 0.0f),
                1f,0)
        );
        mScene.spheres.add(new Sphere(
                new Vec3(0.0f, -101.0f, 0.0f),
                100f,1)
        );

        dragFloat3s = new DragFloat3[mScene.spheres.size()];
        dragFloats = new DragFloat[mScene.spheres.size() * 3];

        mCamera = new Camera(45.0f, 0.1f, 100.0f);
        mFrameRate = new JLabel("FrameRate: ");
        Panel controlPanel = new Panel();

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < mScene.spheres.size(); i++) {
            dragFloat3s[i] = new DragFloat3("Position", mScene.spheres.get(i).position, 0.1f);
            controlPanel.add(dragFloat3s[i]);

            dragFloats[i] = new DragFloat("Radius", mScene.spheres.get(i).radius, 0.05f);
            controlPanel.add(dragFloats[i]);

            controlPanel.add(new JSeparator());
        }

        for (int i = 0, j = mScene.spheres.size(); i < mScene.materials.size(); j += 2, i++) {
            dragFloats[j] = new DragFloat("Roughness", mScene.materials.get(i).roughness, 0.005f, 0.0f, 1.0f);
            controlPanel.add(dragFloats[j]);

            dragFloats[j + 1] = new DragFloat("Metallic", mScene.materials.get(i).metallic, 0.005f, 0.0f, 1.0f);
            controlPanel.add(dragFloats[j + 1]);

            controlPanel.add(new ColorChooser(mScene.materials.get(i).albedo));
            controlPanel.add(new JSeparator());
        }

        controlPanel.revalidate();
        Panel configPanel = new Panel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.add(mFrameRate);
        JScrollPane control = new JScrollPane();
        control.setViewportView(controlPanel);
        JScrollPane config = new JScrollPane(configPanel);

        SplitPane controlPane = new SplitPane("SecondarySplitPane", JSplitPane.VERTICAL_SPLIT, control, config);
        SplitPane mSplitPane = new SplitPane("PrimarySplitPane", JSplitPane.HORIZONTAL_SPLIT, mViewport, controlPane);
        Frame mMainFrame = new Frame("Ray Tracing", mCamera);
        mMainFrame.add(mSplitPane);
        mMainFrame.setFocusable(true);
        mMainFrame.requestFocus();
        mSplitPane.setContinuousLayout(false);
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        mMainFrame.setVisible(true);
    }


    public void render() {
        Timer timer = new Timer();
        mRenderer.onResize(mViewport.getWidth(), mViewport.getHeight());
        mCamera.onResize(mViewport.getWidth(), mViewport.getHeight());
        mRenderer.render(mScene, mCamera);
        m_LastRenderTime = timer.getDeltaTime();
    }

    @Override
    public void onUpdate(float ts) {
        for (int i = 0; i < mScene.spheres.size(); i++) {
            mScene.spheres.get(i).radius = dragFloats[i].update();
            dragFloat3s[i].update();
        }

        for (int i = 0, j = mScene.spheres.size(); i < mScene.spheres.size(); j += 2, i++) {
            mScene.materials.get(i).roughness = dragFloats[j].update();
            mScene.materials.get(i).metallic = dragFloats[j + 1].update();
        }

        mCamera.onUpdate(ts);
    }

    @Override
    public void onUIRender() {
        mFrameRate.setText(String.format("FrameTime: %.3fms", m_LastRenderTime * 1000));

        Image image = mRenderer.getmFinalImage();

        if (image != null) mViewport.setImage(image);

        render();
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacDarkLaf());
        Application app = new Application();
        app.pushLayer(new RayTracing());
        app.run();
    }


}