import java.awt.*;

/**
 * The rect class is responsible for drawing and
 * returning the boundaries of a rectangle object
 * in the game world
 */
public class Rect {
    float x;
    float y;
    float height;
    float width;

    /**
     * Constructor that sets initial rectangle
     * values
     */
    public Rect(){
        x = 100;
        y = 100;
        height = 30;
        width = 50;
    }

    /**
     * Sets the color and rectangle position and size
     * @param g graphics object
     */
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Retuns the rectangle boundaries
     * @return Rectangle along with position and size
     */
    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y, (int) width, (int) height );
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
