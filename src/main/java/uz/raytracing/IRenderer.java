package uz.raytracing;

import uz.raytracing.util.glm.Vec4;

public interface IRenderer {

    void onResize(int width, int height);

    void render(Camera camera);

    Vec4 traceRay(Ray ray);

}
