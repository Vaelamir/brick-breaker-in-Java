/**
 * The linked list holds the links from the
 * link class
 */
public class LinkList {

    // refers to first item
    private Link first;

    /**
     * Constructor sets the first
     * link to null (list is empty)
     */
    public LinkList(){
        first = null;
    }

    /**
     * Gets the value of first
     * @return
     */
    public Link getFirst(){
        return first;
    }

    /**
     * Sets the value of first
     * @param f
     */
    public void setFirst(Link f){
        first = f;
    }

    /**
     * Returns true if the list is empty
     * @return
     */
    public boolean isEmpty(){
        return first == null;
    }

    /**
     * Gets the iterator for the list
     * @return
     */
    public ListIterator getIterator(){
        return new ListIterator(this);
    }

    /**
     * Display the list starting
     * from the beginning
     */
    public void displayList(){
        Link current = first;
        while (current != null){
            current.displayLink();
            current = current.next;
        }
    }
}
