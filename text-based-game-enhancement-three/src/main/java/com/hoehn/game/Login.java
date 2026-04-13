/**Package contains all game files. */
package com.hoehn.game;

import java.util.Scanner;

public final class Login {

    private Login() {}

    /** This method logs the user in.
     *
     * @param scanner gets user input.
     * @return return's the user's username.
     * */
    public static String loginUser(final Scanner scanner) {

        boolean loggedIn = false;
        String userName = null;
        String userPassword;

        while (!loggedIn) {

            System.out.println("Enter user name: ");

            do {
                userName = scanner.nextLine().trim();

                if (userName.isBlank()) {
                    System.out.println("Username cannot be empty");
                    System.out.println("Enter user name: ");
                }

            } while (userName.isBlank());

            System.out.println("Enter Password: ");

            do {
                userPassword = scanner.nextLine().trim();

                if (userPassword.isBlank()) {
                    System.out.println("Password cannot be empty");
                    System.out.println("Enter Password: ");
                }

            } while (userPassword.isBlank());

            loggedIn = DatabaseConnector.login(userName, userPassword);
        }
        return userName;
    }
}
