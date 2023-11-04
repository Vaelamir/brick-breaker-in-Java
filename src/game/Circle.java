package game;

import java.awt.*;

/**
 * This class establishes and draws the circle object
 * that is used for the ball in the game.
 */
public class Circle {

    float x;
    float y;
    float xSpeed ;
    float ySpeed ;

    /**
     * Constructor
     */
    public Circle(){

    }

    /**
     * Draws the circle and sets the color
     * @param g graphics object
     */
    public void drawCircle(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval((int) x, (int) y, 30,30);
    }

    // wraps the circle in a rectangle
    // for collision detection
    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y, 20,20);
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
