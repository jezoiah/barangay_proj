package src;
import java.util.Scanner;

public class LoginManager {
    private DatabaseManager dbManager;

    public LoginManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Credentials login(Scanner input) {
        final int MAX_ATTEMPTS = 3;
        int attempts = 0;
        boolean userAuthenticated = false;
        String username;
        String password;

        while (attempts < MAX_ATTEMPTS && !userAuthenticated) {
            System.out.print("Enter username: ");
            username = input.nextLine();
            System.out.print("Enter password: ");
            password = input.nextLine();

            userAuthenticated = dbManager.authenticate(username, password);

            if (userAuthenticated) {
                System.out.println("Login successful!");
                return new Credentials(username, password);
            } else {
                attempts++;
                System.out.println("Invalid username or password. Attempts remaining: " + (MAX_ATTEMPTS - attempts));
                System.out.println();
            }
        }

        System.out.println("Terminated. Too many failed attempts.");
        return null;
    }
}