/**
 * The Link class is the link within the
 * linked list and holds a rectangle object.
 * The bricks for the game will be held within
 * these links
 */
public class Link{
    public Rect rect;
    public Link next;

    /**
     * Constructor that takes a rectangle
     * object
     * @param rect
     */
    public Link(Rect rect){
        this.rect = rect;
    }

    /**
     * Displays the link
     */
    public void displayLink(){
        System.out.println(rect + " ");
    }
}
