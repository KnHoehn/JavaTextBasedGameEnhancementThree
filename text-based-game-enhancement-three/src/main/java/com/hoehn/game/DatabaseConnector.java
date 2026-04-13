/**Package contains all game files. */
package com.hoehn.game;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public final class DatabaseConnector {

    /**
     * Database url constant.
     */
    private static final String DB_URL;
    /**
     * Database username constant.
     */
    private static final String DB_USERNAME;
    /**
     * Database password constant.
     */
    private static final String DB_PASSWORD;
    /**
     * Random for the salt.
     */
    private static final SecureRandom RANDOM = new SecureRandom();
    /**
     * Variable representing 3600 seconds in an hour.
     */
    private static final int SECONDS_PER_HOUR = 3600;
    /**
     * Variable representing 60 seconds in a minute.
     */
    private static final int SECONDS_PER_MINUTE = 60;

    private DatabaseConnector() {
    }

    static {

        Properties properties = new Properties();

        //Reads the values from the db.properties and saves them into the constant variables.
        try (InputStream input = DatabaseConnector.class.getResourceAsStream("/db.properties")) {

            if (input == null) {
                System.out.println("File not found");
            }

            properties.load(input);

            DB_URL = properties.getProperty("dbUrl");
            DB_USERNAME = properties.getProperty("dbUserName");
            DB_PASSWORD = properties.getProperty("dbPassword");

        } catch (IOException e) {
            throw new RuntimeException("Unable to open file");
        }
    }

    /**
     * This method save's the user score into the database.
     *
     * @param userName   the user's username.
     * @param totalScore the user's end game score.
     * @param moves      the total moves the user had at the end of the game.
     * @param time       the time it took the user to beat the game.
     * @param themeType  the theme the user chose.
     *
     */
    public static void saveScore(final String userName, final Long totalScore, final int moves, final long time, final String themeType) {

        String insertQuery = "INSERT INTO score_board(user_name, score, moves, time, theme)" + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setLong(2, totalScore);
            preparedStatement.setInt(3, moves);
            preparedStatement.setLong(4, time);
            preparedStatement.setString(5, themeType);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }

    /** This method displays the top ten scores for the game of all time.
     * */
    public static void getLeaderboard() {

        int leaderNumber = 0;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet leaderBoard = statement.executeQuery("SELECT user_name, score, moves, time, theme FROM score_board ORDER BY score DESC LIMIT 10");

            while (leaderBoard.next()) {
                String userNames = leaderBoard.getString("user_name");
                long score = leaderBoard.getLong("score");
                int moves = leaderBoard.getInt("moves");
                long time = leaderBoard.getLong("time");
                String theme = leaderBoard.getString("theme");
                long hours = time / SECONDS_PER_HOUR;
                long minutes = (time % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
                long seconds = time % SECONDS_PER_MINUTE;
                String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                leaderNumber++;
                System.out.println(leaderNumber + ". User Name: " + userNames + " | Theme: " + theme + " | Score: " + score + " | Moves: " + moves + " | Time: " + formattedTime);
            }

        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }

    /** This method gets the user's top 10 personal best scores.
     *
     * @param userName the user's username.
     *
     */
    public static void getPersonalBest(final String userName) {

        ResultSet personalBest = null;

        int leaderNumber = 0;

        String retrieveQuery = "SELECT user_name, score, moves, time, theme FROM score_board WHERE user_name = ? ORDER BY score DESC LIMIT 10";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement selectPreparedStatement = connection.prepareStatement(retrieveQuery);) {

            selectPreparedStatement.setString(1, userName);

            personalBest = selectPreparedStatement.executeQuery();

            while (personalBest.next()) {
                String userNames = personalBest.getString("user_name");
                long score = personalBest.getLong("score");
                int moves = personalBest.getInt("moves");
                long time = personalBest.getLong("time");
                String theme = personalBest.getString("theme");

                long hours = time / SECONDS_PER_HOUR;

                long minutes = (time % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;

                long seconds = time % SECONDS_PER_MINUTE;

                String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                leaderNumber++;

                System.out.println(leaderNumber + ". User Name: " + userNames + " | Theme: " + theme + " | Score: " + score + " | Moves: " + moves + " | Time: " + formattedTime);
            }

        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
    }

    /** This method logs in the user by connecting to the database. *
     *
     * @param userName the user's username.
     * @param password the user's password.
     * @return login status.
     */
    public static boolean login(final String userName, final String password) {

        boolean loggedIn = false;

        String retrieveQuery = "SELECT user_name, user_password, salt FROM users WHERE user_name = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement selectPreparedStatement = connection.prepareStatement(retrieveQuery);) {

            selectPreparedStatement.setString(1, userName);

            ResultSet users = selectPreparedStatement.executeQuery();

            if (users.next()) {
                String storedUserName = users.getString("user_name");
                String storedSaltedPassword = users.getString("user_password");
                String storedSalt = users.getString("salt");

                byte[] decodedSalt = Base64.getDecoder().decode(storedSalt);

                MessageDigest md;
                md = MessageDigest.getInstance("SHA-512");
                md.update(decodedSalt);
                byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));

                String saltedPassword = Base64.getEncoder().encodeToString(digest);

                if (saltedPassword.equals(storedSaltedPassword)) {
                    System.out.println("Logged in as " + storedUserName);
                    loggedIn = true;
                } else {
                    System.out.println("Wrong Password");
                }

            } else {

                loggedIn = DatabaseConnector.createAccount(userName, password);
            }
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
        return loggedIn;
    }

    /**
     * This method creates a new account of no account for the username was found. *
     *
     * @param userName the user's username.
     * @param password the user's password.
     * @return login status.
     */
    public static boolean createAccount(final String userName, final String password) {

        boolean loggedIn = false;

        String insertQuery = "INSERT INTO users(user_name, user_password, salt)" + "VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);) {

            // Hashes and salts the password before storing.

            byte[] salt = new byte[16];
            RANDOM.nextBytes(salt);

            MessageDigest md;
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));

            String saltedPassword = Base64.getEncoder().encodeToString(digest);
            String saltString = Base64.getEncoder().encodeToString(salt);

            insertPreparedStatement.setString(1, userName);
            insertPreparedStatement.setString(2, saltedPassword);
            insertPreparedStatement.setString(3, saltString);

            insertPreparedStatement.executeUpdate();

            System.out.println("No account for " + userName + " found, account created.");

            loggedIn = true;
        } catch (Exception e) {
            System.err.println("Unable to connect to database");
        }
        return loggedIn;
    }
}
