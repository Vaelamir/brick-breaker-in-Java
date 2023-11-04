import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * This program is a clone of a classic 2D game called brick breaker.
 * It was made from scratch using only built in Java libraries for
 * shapes, collision detection, and Swing components for UI.  This
 * is the main method that instantiates a render object when called.
 *
 */


public class Main {

    public static void main(String[] args) throws SQLException {

            Renderer renderer = new Renderer();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                // when run is invoked the game is launched
                public void run() {
                    renderer.createGUI();
                }
            });

            renderer.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    renderer.closeWindow();
                }
            });
        }
    }
