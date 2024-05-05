package io.github.lazizkhan1.raytracingjava.Impl;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import io.github.lazizkhan1.raytracingjava.components.*;
import io.github.lazizkhan1.raytracingjava.util.Timer;
import io.github.lazizkhan1.raytracingjava.util.glm.Vec3;

import javax.swing.*;
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

//        Material materialGreen = mScene.materials.set(new Material());
//        materialGreen.albedo = new Vec3(0.0f, 1.0f, 0.0f);
//        materialGreen.roughness = 0.0f;
//        materialGreen.metallic = 0.0f;
//
//        Material materialRed = mScene.materials.set(new Material());
//        materialRed.albedo = new Vec3(1.0f, 0.0f, 0.0f);
//        materialRed.roughness = 0.0f;
//        materialRed.metallic = 0.0f;
//
//        Material materialWhite = mScene.materials.set(new Material());
//        materialWhite.albedo = new Vec3(1.0f, 1.0f, 1.0f);
//        materialWhite.roughness = 0.0f;
//        materialWhite.metallic = 0.0f;
//
//        Material materialWhite2 = mScene.materials.set(new Material());
//        materialWhite2.albedo = new Vec3(1.0f, 1.0f, 1.0f);
//        materialWhite2.roughness = 0.0f;
//        materialWhite2.metallic = 0.0f;
//
//        Material materialLight = mScene.materials.set(new Material());
//        materialLight.albedo = new Vec3(1.0f, 1.0f, 1.0f);
//        materialLight.emissionColor = new Vec3(1.0f, 1.0f, 1.0f);
//        materialLight.emissionPower = 0.0f;
//
        Material mirror = mScene.materials.set(new Material());
        mirror.albedo = new Vec3(1.0f, 1.0f, 1.0f);
        mirror.emissionColor = new Vec3(1.0f, 1.0f, 1.0f);
        mirror.metallic = 1.0f;
        mirror.roughness = 1.0f;
        mirror.emissionPower = 0f;
//
//        Material top = mScene.materials.set(new Material());
//        top.albedo = new Vec3(1.0f, 1.0f, 1.0f);
//        top.roughness = 0.0f;
//        top.metallic = 0.0f;
//
//
//
        mScene.spheres.add(new Sphere(
                new Vec3(0.0f, 0.0f, 0.0f),
                1f, 0)
        );
//        mScene.spheres.add(new Sphere(
//                new Vec3(2.0f, -31.0f, 0.0f),
//                30f, 2)
//        );
//
//        mScene.spheres.add(new Sphere(
//                new Vec3(31.5f, 0.0f, 0.0f),
//                30f, 0)
//        );
//        mScene.spheres.add(new Sphere(
//                new Vec3(0.0f, 0.0f, -34.0f),
//                30f, 3)
//        );
//
//        mScene.spheres.add(new Sphere(
//                new Vec3(0.0f, 5.2f, 1.3f),
//                1.9f, 4)
//        );
//
//        mScene.spheres.add(new Sphere(
//                new Vec3(0.0f, 0.0f, 0.0f),
//                1f, 5)
//        );
//
//        mScene.spheres.add(new Sphere(
//                new Vec3(0.0f, 1.0f, 0.0f),
//                1f, 6)
//        );

        dragFloat3s = new DragFloat3[mScene.spheres.size()];
        dragFloats = new DragFloat[mScene.spheres.size() * 4];

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

        for (int i = 0, j = mScene.materials.size(); i < mScene.materials.size(); j += 2, i++) {
            dragFloats[j] = new DragFloat("Roughness", mScene.materials.get(i).roughness, 0.005f, 0.0f, 1.0f);
            controlPanel.add(dragFloats[j]);

            dragFloats[j + 1] = new DragFloat("Metallic", mScene.materials.get(i).metallic, 0.005f, 0.0f, 1.0f);
            controlPanel.add(dragFloats[j + 1]);

            dragFloats[j + 2] = new DragFloat("Emission Power", mScene.materials.get(i).emissionPower, 0.005f, 0.0f, Float.MAX_VALUE);
            controlPanel.add(dragFloats[j + 2]);

            controlPanel.add(new JLabel("Albedo"));
            controlPanel.add(new ColorChooser(mScene.materials.get(i).albedo));
            controlPanel.add(new JLabel("Emission"));
            controlPanel.add(new ColorChooser(mScene.materials.get(i).emissionColor));
            controlPanel.add(new JSeparator());
        }

        controlPanel.revalidate();
        Panel configPanel = new Panel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> mRenderer.resetFrameIndex());
        resetButton.setFocusable(false);
        JCheckBox accumulate = new JCheckBox("Accumulate", mRenderer.getSettings().accumulate);
        JCheckBox multiThreaded = new JCheckBox("Multi Threading", mRenderer.getSettings().multiThreaded);
        multiThreaded.addActionListener(e -> mRenderer.getSettings().multiThreaded = multiThreaded.isSelected());
        multiThreaded.setFocusable(false);
        accumulate.addActionListener(e -> mRenderer.getSettings().accumulate = accumulate.isSelected());
        accumulate.setFocusable(false);
        JButton exportImage = new JButton("Export Image");
        exportImage.addActionListener(e -> mViewport.exportImage());
        exportImage.setFocusable(false);
        configPanel.add(mFrameRate, resetButton, accumulate, multiThreaded, exportImage);
        JScrollPane control = new JScrollPane();
        control.setViewportView(controlPanel);
        JScrollPane config = new JScrollPane(configPanel);
        control.setWheelScrollingEnabled(true);
        config.setWheelScrollingEnabled(true);
        control.getVerticalScrollBar().setUnitIncrement(16);
        config.getVerticalScrollBar().setUnitIncrement(16);
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

        for (int i = 0, j = mScene.materials.size(); i < mScene.materials.size(); j += 2, i++) {
            mScene.materials.get(i).roughness = dragFloats[j].update();
            mScene.materials.get(i).metallic = dragFloats[j + 1].update();
            mScene.materials.get(i).emissionPower = dragFloats[j + 2].update();
        }

        if (mCamera.onUpdate(ts))
            mRenderer.resetFrameIndex();
    }

    @Override
    public void onUIRender() {
        mFrameRate.setText(String.format("FrameTime: %.3fms", m_LastRenderTime * 1000));

        Image image = mRenderer.getFinalImage();

        if (image != null) mViewport.setImage(image);

        render();
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacDarkLaf());
        Application app = new Application();
        System.gc();
        app.pushLayer(new RayTracing());
        app.run();
    }


}