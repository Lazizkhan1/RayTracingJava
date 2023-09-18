package io.github.lazizkhan1.raytracingjava.components;

public interface Layer {
    void onUpdate(float ts);
    void onUIRender();
}
