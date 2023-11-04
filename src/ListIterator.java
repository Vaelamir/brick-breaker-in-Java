/**
 * This class contains the linked list iterator
 * this iterator traverses through the links on the
 * list allowing for access to that data
 */
public class ListIterator {
    // the current link on the list
    private Link current;
    // previous link
    private Link previous;
    // the list itself
    private LinkList ourList;

    /**
     * Constructor sets ourList and resets()
     * the iterator to the start
     * @param list
     */
    public ListIterator(LinkList list){
        ourList = list;
        reset();
    }

    /**
     * Starts at the beginning
     */
    public void reset(){
        current = ourList.getFirst();
        previous = null;
    }

    /**
     * Returns true if it is the last
     * link in the list
     * @return
     */
    public boolean atEnd(){
        return current.next == null;
    }

    /**
     * Move to the next link
     */
    public void nextLink(){
        previous = current;
        current = current.next;
    }

    /**
     * Returns the current link
     * @return
     */
    public Link getCurrent(){
        return current;
    }

    /**
     * Inserts after the current link - not
     * currently used for this program but
     * included for functionality
     * @param rect
     */
    public void insertAfter(Rect rect){
        Link newLink = new Link(rect);

        if (ourList.isEmpty()){
            ourList.setFirst(newLink);
            current = newLink;
        }else {
            newLink.next = current.next;
            current.next = newLink;
            nextLink();
        }
    }

    /**
     * Insert before the current link
     * @param rect
     */
    public void insertBefore(Rect rect){
        Link newLink = new Link(rect);

        // refers to the beginning of the list
        if (previous == null){
            newLink.next = ourList.getFirst();
            ourList.setFirst(newLink);
            reset();
        // else not the beginning
        }else {
            newLink.next = previous.next;
            previous.next = newLink;
            current = newLink;
        }
    }

    /**
     * Deletes the current link
     * @return
     */
    public Rect deleteCurrent(){
        Rect value = current.rect;
        // starts at the beginning of
        // the list
        if (previous == null){
            ourList.setFirst(current.next);
            reset();
        // if not at the beginning
        }else {
            previous.next = current.next;
            if (atEnd()){
                reset();
            }else {
                current = current.next;
            }
        }
        return value;
    }
}
