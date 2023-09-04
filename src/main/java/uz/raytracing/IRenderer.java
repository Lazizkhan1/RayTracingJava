package uz.raytracing;

import uz.raytracing.util.Vec2;
import uz.raytracing.util.Vec4;

public interface IRenderer {

    void onResize(int width, int height);

    void render();

    Vec4 perPixel(Vec2 coord);

}
