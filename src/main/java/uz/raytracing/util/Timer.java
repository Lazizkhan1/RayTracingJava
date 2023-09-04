package uz.raytracing.util;

public class Timer {
    private long previousTime;

    public Timer() {
        previousTime = System.nanoTime();
    }


    public String calculate() {
        long currentTime = System.nanoTime();
        float frameTime = (currentTime - previousTime) / 1000000.0f;
        previousTime = currentTime;
        return String.format("%.3f", frameTime);
    }
}
