package io.github.lazizkhan1.raytracingjava.Impl;

import io.github.lazizkhan1.raytracingjava.components.Layer;
import io.github.lazizkhan1.raytracingjava.util.Timer;

import java.util.ArrayList;

public class Application {
    private final ArrayList<Layer> m_LayerStack = new ArrayList<>();
    private float m_TimeStep = 0.0f;
    private float m_FrameTime = 0.0f;
    private float m_LastFrameTime = 0.0f;

    public void run() {

        while (true) {
            for(Layer layer: m_LayerStack) {
                layer.onUpdate(m_TimeStep);
                layer.onUIRender();
            }

            float time = new Timer().getDeltaTime();
            m_FrameTime = time - m_LastFrameTime;
            m_TimeStep = Math.max(m_FrameTime, 0.0333f);
            m_LastFrameTime = time;
        }

    }

    public void pushLayer(Layer layer) {
        m_LayerStack.add(layer);
    }

}