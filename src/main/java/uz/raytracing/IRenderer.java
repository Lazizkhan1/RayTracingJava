package uz.raytracing;

import uz.raytracing.util.Image;

public interface IRenderer {

    void onResize(int width, int height);

    void render();


}
