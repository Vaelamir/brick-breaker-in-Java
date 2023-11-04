import database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Random;

/**
 * This class adds the popup window to get the
 * player username before the game can begin. After
 * the username is entered the player may begin the
 * game. This same username is later stored to the
 * database along with the associated score
 */
class CreateLoginForm extends JFrame implements ActionListener {
    JButton b1;
    JPanel newPanel;
    JLabel userLabel;
    JTextField  textField1;
    public String userName;
    public boolean launchGame = false;
    boolean closeWindow = false;
    Database database = new Database();

    /**
     * Constructor that calls the init method.
     */
    CreateLoginForm() {
        init();
    }

    /**
     * This method sets the font, jpanel, jframe, layout,
     * and text for the popup text box.
     */
    public void init() {
        // instantiates a font object that is used for
        // the text box
        Font font = new Font("Calibri", Font.BOLD, 20);

        // create label for username
        userLabel = new JLabel();
        userLabel.setFont(font);
        userLabel.setText("Username");
        userLabel.setForeground(Color.WHITE);

        // create text field to get username from the user
        textField1 = new JTextField(15);

        // create submit button
        b1 = new JButton("SUBMIT");

        // lays out the panel with a grid layout
        newPanel = new JPanel(new GridLayout(3, 3));

        // create panel
        JFrame jFrame = new JFrame();
        jFrame.setBackground(Color.BLACK);

        newPanel.setLayout(new FlowLayout());
        newPanel.setBackground(Color.BLACK);

        // adds username, textfield, and button
        // to the panel
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(b1);

        //set border to panel
        add(newPanel, BorderLayout.CENTER);

        // listens for button clicks
        // and sets the text to instruct
        // the user
        b1.addActionListener(this);
        setTitle("Username");
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Window event that grabs the event and stores
     * it within a variable.  If the window is closed
     * the window is set to false and the boolean is
     * set to true.
     */
    @Override
    protected void processWindowEvent(WindowEvent e) {

        int event = e.getID();
        if (event == WindowEvent.WINDOW_CLOSING){
            setVisible(false);
            closeWindow = true;
            setDefaultCloseOperation(javax.swing.
                    WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    public void setCloseWindow(boolean closeWindow) {
        this.closeWindow = closeWindow;
    }

    /**
     * Listens if the user tries to close the windodw by
     * pressing the 'x'
     * @param l the window focus listener
     */
    @Override
    public synchronized void addWindowFocusListener(WindowFocusListener l) {

    }

    /**
     * Action listener that is helpful when the
     * submit button is pressed.  This method also
     * calls the checkDup method in the database class
     * to check if a duplicate username has been entered.
     * @param ae action parameter
     */
    public void actionPerformed(ActionEvent ae) {

        // sets the text field with username
        setUserName(textField1.getText());

        // if the username has entered a value then
        // call the checkDup method to validate the username
        // in the database
        if (!userName.isEmpty() ) {
            try {
                // if a duplicate exists add numeric
                // values at the end of username
                if (database.checkDup(userName)){
                    Random random = new Random();
                    int randomInt = random.nextInt(20);
                    userName = userName + randomInt;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // launch the game
            setLaunchGame(true);
        }
        else{

        }
    }

    public void setLaunchGame(boolean launchGame) {
        this.launchGame = launchGame;
    }
}

