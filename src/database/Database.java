package database;

import java.awt.*;
import java.sql.*;

/**
 * This class is responsible for establishing a connection to
 * the database and retrieving data.  Within this class there are
 * four methods including insertData, checkDup (duplicates), displayData,
 * and deleteData.
 */
public class Database {
    Connection connection = null;
    Statement statement = null;
    ResultSet myRs = null;

    String dbUrl = "jdbc:mysql://localhost:3306/breakout";
    String user = "student";
    String pass = "student";

    String userName;
    int highScore;

    public void insertData(String userName, int highScore){
        this.userName = userName;
        this.highScore = highScore;


        try {
            // connect to database
            connection = DriverManager.getConnection(dbUrl, user, pass);

            // preparing a statement to insert player name and high score
            // to the database.  These values are passed in from the main
            // menu screen and also from player gameplay
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `players`(user_name,high_score) VALUES (?, ?)");
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, highScore);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method checks for duplicate names within the database.  If
     * a duplicate is found, random numeric values will be added to the
     * end of the username.
     * @param userName player entered username
     * @return boolean value if duplicate is found
     * @throws SQLException
     */
    public boolean checkDup(String userName) throws SQLException{
        boolean duplicate = false;

        try {
            // make connection
            connection = DriverManager.getConnection(dbUrl, user, pass);

            // create statement
            statement = connection.createStatement();

            // execute statement
            myRs = statement.executeQuery("select * from players");

            // search for duplicate return true if found
            while (myRs.next()){
                if (myRs.getString("user_name").equals(userName)){
                    duplicate = true;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }finally {
            if (connection != null){
                connection.close();
            }
        }
        return duplicate;
    }

    /**
     * Displays user data from the database
     * @param g graphics object
     * @throws SQLException
     */
    public void displayData(Graphics g) throws SQLException {

        String playerName = null;
        String highScore;
        int x = 300;
        int y = 100;
        int counter = y;

        try {
            // make connection
            connection = DriverManager.getConnection(dbUrl, user, pass);

            // create statement
            statement = connection.createStatement();

            // execute statement to retrieve the top 5 high scores
            // from the table
            myRs = statement.executeQuery("select * FROM players ORDER BY high_score DESC limit 5");

            // while there is still data, then display it
            while (myRs.next()){
                playerName = myRs.getString("user_name");
                highScore = myRs.getString("high_score");
                g.drawString(playerName, x, y + counter);
                g.drawString(highScore, x + 200, y + counter);
                counter += 50;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }finally {
            if (connection != null){
                connection.close();
            }
        }

    }

    /**
     * Deletes data from the database
     * @throws SQLException
     */
    public void deleteData() throws SQLException {
        try {
            // make connection
            connection = DriverManager.getConnection(dbUrl, user, pass);

            // create a statement
            statement = connection.createStatement();

            // execute statement
            myRs = statement.executeQuery("delete * FROM players ORDER BY high_score DESC limit 1");
            // 5 Process result
            while (myRs.next()){

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }finally {
            if (connection != null){
                connection.close();
            }
        }
    }
}
