package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class implements a key listener to poll
 * for user keystrokes.
 */
public class KeyboardInput implements KeyListener {
    // arrays for keys and
    // for polling results
    private boolean [] keys;
    private int [] polled;

    /**
     * Constructor that initializes both
     * arrays
     */
    public KeyboardInput (){
        keys = new boolean[256];
        polled = new int[256];
    }

    /**
     * Detects if a user has pressed a key down on the
     * keyboard and limits it to a single press
     * @param keyCode the keycode returned from the key event
     * @return if a key has been pressed and its associated index
     */
    public boolean keyDownOnce(int keyCode){
        return polled[keyCode] == 1;
    }

    /**
     * Detects if a user has pressed down a key and will
     * continue to allow the key press to have impact
     * @param keyCode the keycode returned from the key event
     * @return if a key has been pressed and its associated index
     */
    public boolean keyDown(int keyCode){
        return polled[keyCode] > 0;
    }

    /**
     * Searches for key that is pressed as the game loop is running
     */
    public void polling(){
        // iterates through the keys array
        // and if a key index has been returned
        // continue to increase the index value in
        // the polled array
        for (int i = 0; i < keys.length; i++){
            if (keys[i]){
                polled[i]++;
            }else {
                polled[i] = 0;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Stores the key event by the user in an int
     * variable and if the value is within the array
     * range set the boolean to true
     * @param e key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length){
            keys[keyCode] = true;
        }
    }

    /**
     * Stores the key event by the user in an int
     * variable and if the value is within the array
     * range set the boolean to false
     * @param e key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length){
            keys[keyCode] = false;
        }
    }
}
