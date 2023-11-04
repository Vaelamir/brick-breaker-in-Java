package game;

import java.awt.*;

/**
 * This class is responsible for paddle logic and
 * rendering the paddle to the game screen.
 */
public class Paddle {
    // location of paddle
    float x = 200;
    float y = 610;
    // paddle dimensions
    float height = 20;
    float width = 100;

    public Paddle(){

    }

    /**
     * Draws the paddle to the screen
     * @param g graphics object
     */
    public void drawPaddle(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Returns the bounds of the paddle
     * @return
     */
    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y, (int) width, (int) height );
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }
}
