package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class is the main menu class for the
 * user interface.  Additionally, it contains
 * the play, high score, and quit buttons that
 * are highlighted when the players mouse enters
 * the rectangle.
 */
public class MainMenu extends MouseAdapter {

    // rectangle objects for main menu buttons
    public Rectangle playButton = new Rectangle(420, 200, 150, 80);
    public Rectangle highscoreButton = new Rectangle(420, 320, 150, 80);
    public Rectangle quitButton = new Rectangle(420, 440, 150, 80);

    // booleans for highlighting the buttons
    private boolean playHighlight = false;
    private boolean highscoreHighlight = false;
    private boolean quitHighlight = false;

    public boolean keysActive = true;
    public boolean menuState = false;

    public boolean pushHighScore = false;
    public boolean pushPlay = false;
    public boolean playerName = false;


    /**
     *The render method in the mainMenu class renders the ui
     * menu with three buttons play, high score, and quit.
     * @param g graphic object
     */
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font font = new Font("Calibri", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Brick Breaker Clone", 290, 100);

        Font font1 = new Font("Calibri", Font.BOLD, 30);
        g.setFont(font1);

        g2d.draw(playButton);
        g2d.draw(highscoreButton);
        g2d.draw(quitButton);

        if (playHighlight){
            g.setColor(Color.GRAY);
            g2d.fill(playButton);
        }

        if (highscoreHighlight){
            g.setColor(Color.GRAY);
            g2d.fill(highscoreButton);
        }

        if (quitHighlight){
            g.setColor(Color.GRAY);
            g2d.fill(quitButton);
        }

        String play = "Play";
        String highScore = "High Score";
        String quit = "Quit";

        int strWidth;
        int strHeight;

        strWidth = g.getFontMetrics().stringWidth(play);
        strHeight = g.getFontMetrics().getHeight();

        g.setColor(Color.WHITE);
        g.drawString(play, (int) (playButton.getX() + playButton.getWidth() / 2 - strWidth / 2),
                (int) (playButton.getY() + playButton.getHeight() / 2 + strHeight / 4));

        g.setColor(Color.WHITE);
        g.drawString(highScore, (int) (highscoreButton.getX() + highscoreButton.getWidth() / 2 - (strWidth + 12) ),
                (int) (highscoreButton.getY() + highscoreButton.getHeight() / 2 + strHeight / 4));

        g.setColor(Color.WHITE);
        g.drawString(quit, (int) (quitButton.getX() + quitButton.getWidth() / 2 - strWidth / 2),
                (int) (quitButton.getY() + quitButton.getHeight() / 2 + strHeight / 4));

    }

    public boolean isPushHighScore() {
        return pushHighScore;
    }

    public void setPushHighScore(boolean pushHighScore) {
        this.pushHighScore = pushHighScore;
    }

    public void setKeysActive(boolean keysActive) {
        this.keysActive = keysActive;
    }

    public boolean isPushPlay() {
        return pushPlay;
    }

    public void setPushPlay(boolean pushPlay) {
        this.pushPlay = pushPlay;
    }

    public boolean isPlayerName() {
        return playerName;
    }

    public void setPlayerName(boolean playerName) {
        this.playerName = playerName;
    }

    /**
     * Listens for mouse clicks and sets the appropriate
     * boolean.
     * @param e mouse event
     */
    public void mouseClicked(MouseEvent e){
        Point p = e.getPoint();
        if (keysActive){
            if (playButton.contains(p)){
                setPushPlay(true);
                setPushHighScore(false);
                setPlayerName(true);
            }else if (highscoreButton.contains(p)){
                setPushHighScore(true);
                setPushPlay(false);
            }
            else if (quitButton.contains(p)){
                System.exit(0);
            }
        }
    }

    /**
     * If the user moves the mouse over any of
     * the three buttons it highlights the button.
     * @param e mouse event
     */
    public void mouseMoved(MouseEvent e){
        Point p = e.getPoint();
        playHighlight = playButton.contains(p);
        highscoreHighlight = highscoreButton.contains(p);
        quitHighlight = quitButton.contains(p);
    }
}
