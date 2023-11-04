package ui;

import database.Database;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * The highScore class extends the mainMenu class and
 * renders the player names and associated high scores
 * from the database to the game screen.
 *
 */
public class HighScore extends MainMenu {

    public Rectangle backButton = new Rectangle(420, 440, 130, 80);
    private boolean backHighlight = false;
    public boolean keysActive = false;
    public boolean pushBack = false;

    Database database;

    /**
     * Constructor
     */
    public HighScore(){
        database = new Database();
    }


    /**
     * This method sets the fonts and renders the
     * back button from the high score page.  Additionally,
     * it calls the display method from the database to
     * display player data.
     * @param g graphic object
     */
    public void render(Graphics g){

        // sets graphic object
        Graphics2D g2d = (Graphics2D) g;

        // sets fonts
        Font font = new Font("Calibri", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Brick Breaker Clone", 290, 100);

        Font font1 = new Font("Calibri", Font.BOLD, 30);
        g.setFont(font1);

        g2d.draw(backButton);

        try {
            // calls display from the database
            database.displayData(g);
        }catch (SQLException e){

        }

        // high lights buttons
        if (backHighlight){
            g.setColor(Color.GRAY);
            g2d.fill(backButton);
        }

        String back = "Back";

        int strWidth;
        int strHeight;

        // gets the dimensions of the string
        strWidth = g.getFontMetrics().stringWidth(back);
        strHeight = g.getFontMetrics().getHeight();

        // sets color and draws the string to the page
        g.setColor(Color.WHITE);
        g.drawString(back, (int) (backButton.getX() + backButton.getWidth() / 2 - strWidth / 2),
                (int) (backButton.getY() + backButton.getHeight() / 2 + strHeight / 4));

    }

    public boolean isPushBack() {
        return pushBack;
    }

    public void setPushBack(boolean pushBack) {
        this.pushBack = pushBack;
    }

    public void setKeysActive(boolean keysActive) {

        this.keysActive = keysActive;
    }

    /**
     * Listens for mouse clicks for
     * the back button
     * @param e mouse event
     */
    public void mouseClicked(MouseEvent e){
        Point p = e.getPoint();

        if (keysActive){
            if (backButton.contains(p)) {
                setPushBack(true);
            }
        }
    }

    /**
     * If the users mouse moves into the
     * button area, the button is highlighted.
     * @param e mouse event
     */
    public void mouseMoved(MouseEvent e){
        Point p = e.getPoint();
        backHighlight = backButton.contains(p);
    }
}
