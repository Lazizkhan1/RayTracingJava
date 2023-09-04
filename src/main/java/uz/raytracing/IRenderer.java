package uz.raytracing;

import uz.raytracing.util.Vec2;

public interface IRenderer {

    void onResize(int width, int height);

    void render();

    int perPixel(Vec2<Float> coord);

}
