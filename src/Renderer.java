import database.Database;
import game.*;
import sound.Sound;
import ui.HighScore;
import ui.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;


/**
 * This class builds the foundation needed for the game to function.
 * This class is responsible for setting up the canvas that contains
 * the game world and calling the run method that launches the game loop.
 * Along with the game loop the render method is an important method that
 * is responsible for rendering all onscreen objects.
 */
public class Renderer extends JFrame implements Runnable {

    private BufferStrategy bufferStrategy;
    private volatile boolean running;
    private Thread gameThread;
    private FrameRate frameRate;
    private KeyboardInput input;

    float timer ;
    float gameHeight = 640;
    float gameWidth = 960;
    float ballSpeedX = .1f;
    float ballSpeedY = .1f;
    float paddleSpeedPosX = .15f;
    float paddleSpeedNegX = .15f;
    int lives = 3;
    int score;
    boolean startGame = false;
    boolean ballLaunched = false;

    CreateLoginForm loginForm ;
    MainMenu menu;
    Font font;
    Circle circle;
    Paddle paddle;
    String playerInitials;
    HighScore highScore;
    Rect extra = null;
    Sound sound;
    LinkList list = new LinkList();
    ListIterator iterator = list.getIterator();
    GameState gameState = GameState.MENU;

    /**
     * Constructor for the render class. This
     * method instantiates all objects that will
     * be called in the class and calls for the
     * bricks to be drawn.
     */
    public Renderer() {
        frameRate = new FrameRate();
        input = new KeyboardInput();
        font = new Font("Courier", Font.BOLD, 20);
        circle = new Circle();
        paddle = new Paddle();
        menu = new MainMenu();
        sound = new Sound();
        loginForm = new CreateLoginForm();
        highScore = new HighScore();

        drawBricks();
    }

    /**
     * This method plays the sound when collision
     * happens within the game.
     * @param i variable chooses the chosen sound value
     *          stored in an array
     */
    public void playPaddleHit(int i){
        sound.setFile(i);
        sound.play();
    }

    /**
     * This method creates the user interface and establishes
     * the associated key and mouse listeners.  Additionally,
     * the render thread is created and then the start method
     * call is invoked.  The method start() calls the run method
     * that begins the game loop.
     */
    public void createGUI() {
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        canvas.setSize((int) gameWidth, (int) gameHeight);
        canvas.addKeyListener(input);
        canvas.addMouseListener(menu);
        canvas.addMouseMotionListener(menu);
        canvas.addMouseListener(highScore);
        canvas.addMouseMotionListener(highScore);
        canvas.setFocusable(true);
        getContentPane().add(canvas);
        pack();

        setIgnoreRepaint(true);
        setLocationRelativeTo(null);
        setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Exits the system when the window is closed
     */
    public void closeWindow() {
        try {
            running = false;
            gameThread.join();
        } catch (InterruptedException e) {

        }
        System.exit(0);
    }

    /**
     * Sleep is called to give the system a chance
     * the catchup and or allows for garbage collection
     * to run.
     * @param sleep Takes the amount of time to sleep
     */
    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Draws the onscreen bricks for gameplay. Double
     * for loops for rows and columns, then adds to the
     * iterator for the link list.
     */
    private void drawBricks(){
        // reset iterator, so it
        // starts at the beginning
        // of the linked list
        iterator.reset();

        // loops for rows
        for (int i = 0; i < 4; i++) {
            // loops for columns
            for (int j = 0; j < 17; j++) {
                Rect rect = new Rect();
                rect.setY((i * 35) + 50);
                rect.setX((j * 55) + 15);

                // insert to the iterator then
                // adds to the linked list
                iterator.insertBefore(rect);
            }
        }
    }

    /**
     * This class is responsible for rendering the offscreen
     * items for page flipping. Additionally, getting a
     * graphics object in each loop validates the bufferstrategy.
     */
    private void renderFrame() {
        // renders a single frame
        do {
            // the second loop ensures that the data in the
            // drawing buffer are consistent
            do {
                // sets a new graphics object to null each loop
                Graphics g = null;
                try {
                    // gets a new graphic context each loop to
                    // ensure the strategy is valid
                    g = bufferStrategy.getDrawGraphics();
                    // sets a rectangle for the background and clears
                    // the color
                    g.clearRect(0, 0, getWidth(), getHeight());
                    // calls the render method and pass in the graphics object
                    render(g);
                } finally {
                    // if the graphics object is null
                    // just dispose it
                    if (g != null) {
                        g.dispose();
                    }
                }
                // if the screen is restored continue
                // rendering
            } while (bufferStrategy.contentsRestored());
            // displays the buffer
            bufferStrategy.show();
            // if the screen is minimized
            // continue to render
        } while (bufferStrategy.contentsLost());
    }

    /**
     * This method polls for user key presses and
     * sets the game state if the space bar is pressed.
     * @param delta takes delta time in the form of a float
     */
    private void input(float delta) {
        // listens for user key presses
        input.polling();

        // starts the game and changes the game state if the user presses
        // the space bar
        if (input.keyDownOnce(KeyEvent.VK_SPACE) && gameState == GameState.GAME) {
            startGame = true;
        }
    }

    /**
     * Checks for collision detection of the ball, paddle, and bricks.
     */
    private void collisionDetection() {
        // if the ball intersects the paddle then the
        // paddle sound is played
        if (circle.getBounds().intersects(paddle.getBounds())) {
            playPaddleHit(0);
            // if the ball hits the paddle then change direction
            if (circle.getBounds().getY() < paddle.getY()) {
                circle.setySpeed(-ballSpeedY);
            }
        }
    }

    /**
     * This method is responsible for updating the game objects such as,
     * the bricks, paddle, ball, username, and score.  The iterator will
     * iterate over the linked list and remove bricks as the ball intersects
     * with them.  Additionally, ball boundaries and paddle movement are
     * controlled and updated here.
     * @param delta
     */
    private void updateObjects(float delta) {

        // calls collision method
        collisionDetection();

        // if the game is launched set the ball position
        if (startGame && !ballLaunched) {
            circle.setySpeed(-ballSpeedY);
            circle.setxSpeed(ballSpeedX);
            ballLaunched = true;
        } else if (!ballLaunched) {
            circle.setX(paddle.getX() + 30);
            circle.setY(paddle.getY() - 30);
        }

        if (gameState == GameState.GAME){
            // loop through the iterator and check for collision
            // update ball direction as collision occurs
            iterator.reset();
            if (!list.isEmpty()){
                while (!iterator.atEnd()) {
                    iterator.nextLink();
                    if (circle.getBounds().intersects(iterator.getCurrent().rect.getBounds())){
                        //System.out.println("intersect");
                        if (circle.getBounds().x > iterator.getCurrent().rect.x) {
                            circle.setxSpeed(ballSpeedX);
                        }
                        if (circle.getBounds().x < iterator.getCurrent().rect.x) {
                            circle.setxSpeed(-ballSpeedX);
                        }

                        if (circle.getBounds().y > iterator.getCurrent().rect.y) {
                            circle.setySpeed(ballSpeedY);
                        }

                        if (circle.getBounds().y < iterator.getCurrent().rect.y) {
                            circle.setySpeed(-ballSpeedY);
                        }
                        playPaddleHit(1);
                        iterator.deleteCurrent();
                        addPoints();
                    }
                }
            }

            // ball boundaries within the game world
            if (circle.getX() > getWidth() - 30){
                circle.setxSpeed(-ballSpeedX);
            }

            if (circle.getX() < 0){
                circle.setxSpeed(ballSpeedX);
            }

            if (circle.getY() < 0){

                circle.setySpeed(ballSpeedY);
            }

            // bottom floor
//        if (circle.getY() > 640) {
//            circle.setySpeed(-ballSpeedY);
//        }

            // paddle movement positive x
            if (input.keyDown(KeyEvent.VK_D)){
                paddle.setX(paddle.getX() + paddleSpeedPosX);
                if (paddle.getX() > gameWidth - 100){
                    paddleSpeedPosX = 0;
                }else {
                    paddleSpeedPosX = .15f;
                }
            }

            // paddle movement for negative x
            if (input.keyDown(KeyEvent.VK_A)){
                paddle.setX(paddle.getX() - paddleSpeedNegX);
                if (paddle.getX() <= 0){
                    paddleSpeedNegX = 0;
                }else {
                    paddleSpeedNegX = .15f;
                }
            }
            // ball movement
            circle.setX(circle.getX() + circle.getxSpeed());
            circle.setY(circle.getY() + circle.getySpeed());
        }
    }

    /**
     * Creates a font object and sets the color and font. Draws
     * a string for onscreen lives.
     * @param g graphics object
     */
    private void UIfont(Graphics g){
        Font font1 = new Font("Calibri", Font.BOLD, 30);
        g.setColor(Color.WHITE);
        g.setFont(font1);
        g.drawString("Lives " + lives, 50,30);
    }

    /**
     * Creates a font object and sets the color and font. Draws
     * a string for onscreen score.
     * @param g graphics object
     */
    private void playerPoints(Graphics g){
        Font font1 = new Font("Calibri", Font.BOLD, 30);
        g.setColor(Color.WHITE);
        g.setFont(font1);
        g.drawString("Score " + score, (int) (gameWidth - 150), 30);
    }

    /**
     * Creates a font object and sets the color and font. Draws
     * a string for onscreen player name.
     * @param g graphics object
     */
    private void playerName(Graphics g){

        Font font1 = new Font("Calibri", Font.BOLD, 30);
        g.setColor(Color.WHITE);
        g.setFont(font1);
        g.drawString("Player: " + getPlayerInitials(), (int) (gameWidth / 2 - 100 ), 30);
    }

    /**
     * Responsible for points earned by breaking
     * bricks.  Uses a random object for adding
     * points as player breaks bricks.
     * @return returns score
     */
    private int addPoints(){
        Random random = new Random();
        int randomInt = random.nextInt(12);
        score += randomInt;
        return score;
    }

    /**
     * get player name
     * @return playerInitials
     */
    public String getPlayerInitials() {
        return playerInitials;
    }

    /**
     * sets the player name
     * @param playerInitials takes a string
     */
    public void setPlayerInitials(String playerInitials) {
        this.playerInitials = playerInitials;
    }

    /**
     * Checks if the game has been launched and if not
     * then prompts the user to enter their username
     * through a popup window.  After user enters name
     * the game screen is loaded.
     */
    private void enterPlayerName(){

        if (loginForm.launchGame){

        }else {
            while (!loginForm.launchGame){
                if (!loginForm.closeWindow){
                    loginForm.setSize(500,300);
                    loginForm.setVisible(true);
                    loginForm.setLocationRelativeTo(null);

                }else {
                    gameState = GameState.MENU;
                    ballLaunched = false;
                    startGame = false;
                    loginForm.setLaunchGame(false);
                    menu.setPushPlay(false);
                    menu.setPlayerName(false);
                    loginForm.setCloseWindow(false);
                    gameState = GameState.MENU;
                    break;
                }
            }
            setPlayerInitials(loginForm.userName);
            loginForm.setVisible(false);
            menu.setPlayerName(false);
        }
    }

    /**
     * Updates the game state as the player
     * navigates through the main menu and or
     * gameplay.
     */
    private void updateGameState(){
        if (menu.isPlayerName() ){
            enterPlayerName();
        }

        if (gameState == GameState.MENU){
            menu.setKeysActive(true);
            highScore.setKeysActive(false);
        }

        if (menu.isPushPlay()){
            gameState = GameState.GAME;
            highScore.setPushBack(false);
            menu.setKeysActive(false);
        }

        if (menu.isPushHighScore()){
            gameState = GameState.HIGH_SCORE;
            menu.setKeysActive(false);
            highScore.setKeysActive(true);
        }

        if (highScore.isPushBack()){
            gameState = GameState.MENU;
            highScore.setKeysActive(false);
            menu.setKeysActive(true);
            menu.setPushHighScore(false);
            highScore.setPushBack(false);
        }
    }

    /**
     * This method is only called from the renderFrame method and gives
     * access to the graphics object created with the bufferstrategy. This
     * method is responsible for rendering all onscreen items and updating
     * the game state through key presses.
     * @param g graphics object
     */
    private void render(Graphics g){


        updateGameState();

        // checks for the game state and calls the framerate class and sets the
        // uiFont, playerPoints, and playerName.
        if (gameState == GameState.GAME || gameState == GameState.GAME_OVER){
            frameRate.calculate();
            g.setColor(Color.RED);
            //g.drawString(frameRate.getFrameRate(), 30,30);
            UIfont(g);
            playerPoints(g);
            playerName(g);

            // if the ball has not been launched
            // sets a new font object and onscreen
            // message
            if (!ballLaunched && lives != 0){
                Font font1 = new Font("Calibri", Font.BOLD, 50);
                g.setFont(font1);
                g.setColor(Color.WHITE);
                g.drawString("Press Space to Start", (int) gameWidth / 2 - 200, (int) gameHeight / 2);
            }

            // if player lives are zero
            // then the game is over and
            // displays an onscreen message
            if (lives == 0){
                Font font1 = new Font("Calibri", Font.BOLD, 50);
                g.setFont(font1);
                g.drawString("Game Over", (int) gameWidth / 2 - 110, (int) gameHeight / 2);
                timer++;

                // pauses the game once its over
                // before returning to the main menu
                if (timer == 10000){
                    timer = 0;
                    lives = 1;
                    ballLaunched = false;
                    startGame = false;
                    loginForm.setLaunchGame(false);
                    menu.setPushPlay(false);
                    Database database = new Database();
                    database.insertData(playerInitials, score);
                    score = 0;
                    gameState = GameState.MENU;
                    drawBricks();
                }
            }
            iterator.reset();

            // grabs extra brick within the list
            if (!list.isEmpty()){
                extra = iterator.getCurrent().rect;
            }

            // if the list is not empty then draw the extra brick
            // and listen for collision detection
            if (!list.isEmpty()) {
                extra.draw(g);
                if (circle.getBounds().intersects(extra.getBounds())) {
                    if (!list.isEmpty()) {
                        iterator.deleteCurrent();
                    }
                }
            }

            // renders bricks
            iterator.reset();
            if (!list.isEmpty()){
                while (!iterator.atEnd()) {
                    iterator.nextLink();
                    iterator.getCurrent().rect.draw(g);
                }
            }

            // draws paddle and ball
            paddle.drawPaddle(g);
            circle.drawCircle(g);

            // checks if the ball has gone
            // below the game world and decrements
            // player lives
            if (circle.getY() > getHeight()){
                lives = lives - 1;
                ballLaunched = false;
                startGame = false;

                // if lives equal 0 game state
                // is changed to game over
                if (lives == 0){
                    gameState = GameState.GAME_OVER;
                    menu.setKeysActive(true);
                    playPaddleHit(1);
                }
            }

            // if the linked list is empty
            // then the player has won the game
            if (list.isEmpty()){
                gameState = GameState.GAME_OVER;
            }

            // checks for different game states
            // and renders the appropriate screen
        }else if (gameState == GameState.MENU){
                menu.render(g);
        }else if (gameState == GameState.User_NAME){

        }else if (gameState == GameState.HIGH_SCORE){
            highScore.render(g);
        }
    }

    /**
     * Game loop is responsible for calling
     * the core gameplay methods.
     * @param delta
     */
    private void gameLoop(float delta){
        input(delta);
        renderFrame();
        updateObjects(delta);
        //sleep(10L);
    }

    private void initialize(){

    }


    /**
     * Overridden run method is a part of the Runnable interface.
     * When start() is called from the buffer then the run method
     * is invoked and the program begins.
     */
    @Override
    public void run() {

        // boolean that indicates the game is
        // running
        running = true;
//        frameRate.init();

        // variables for delta time
        double currentTime = System.nanoTime();
        double lastTime = currentTime;
        double nsPerFrame;

        // while loop that contains the game loop
        // this while loop is the foundational
        // loop for the game
        while (running){
            currentTime = System.nanoTime();
            nsPerFrame = currentTime - lastTime;
            gameLoop((float) (nsPerFrame / 1.0E9));
            lastTime = currentTime;
        }
    }
}


