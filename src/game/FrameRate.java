package game;

/**
 * The frameRate class detects the frame
 * rate per second in the game loop
 */
public class FrameRate {
    private String frameRate;
    private long lastTime;
    private long delta;
    private int frameCount;

    /**
     * Initializes the frameRate and captures
     * the current time in milliseconds
     */
    public void init(){
        frameRate = "FPS 0";
        lastTime = System.currentTimeMillis();
    }

    /**
     * This method calculates the elapsed time
     * between the frames and then updates the
     * display string
     */
    public void calculate(){
        long current = System.currentTimeMillis();
        delta += current - lastTime;
        lastTime = current;
        frameCount++;

        if (delta > 1000){
            delta -= 1000;
            frameRate = String.format("FPS %s", frameCount);
            frameCount = 0;
        }
    }

    public String getFrameRate() {
        return frameRate;
    }
}
