package interfaces;

import atm.*;
import account.*;
import bankmanager.*;

import java.util.Scanner;

public class UpdateProfileInterface {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public UpdateProfileInterface(ATM atm) {
        this.atm = atm;
    }

    public void displayUpdateProfileMenu(User user) {
        boolean validselection = false;
        boolean goBack = false;

        while (!goBack) {
            printOptions();
            String option = scanner.next();

            switch (option) {
                case "1":
                    changePassword(user);
                    break;
                case "2":
                    goBack = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 2.");
                    break;
            }
        }
    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Change password");
        System.out.println("2. Go back to main menu");
        System.out.println("Enter a number: ");
    }

    private void changePassword(User user) {
        // Method for users to change their password.

        System.out.println("type in your new password:");
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
        System.out.println("\nPassword change successful");
    }
}
