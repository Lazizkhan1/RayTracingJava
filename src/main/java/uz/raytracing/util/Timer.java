package uz.raytracing.util;

public class Timer {
    private long previousTime;

    public Timer() {
        previousTime = System.nanoTime();
    }


    public float getDeltaTime() {
        long currentTime = System.nanoTime();
        float frameTime = (currentTime - previousTime) / 1000000000.0f;
        previousTime = currentTime;
        return frameTime;
    }
}
