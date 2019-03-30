package interfaces;

import atm.*;
import account.*;
import bankmanager.*;

import java.util.*;

public class AccountInterface extends GeneralInterface{

    public AccountInterface(ATM atm) {
        super(atm);
    }

    public void displayAccountMenu(User user) {
        boolean validselection = false;
        boolean goBack = false;

        while (!goBack) {
            printOptions();
            String option = scanner.next();

            switch (option) {
                case "1":
                    createAccount(user);
                case "2":
                    requestJointAccountCreation(user);
                case "3":
                    addUserToExistingAccount(user);
                case "4":
                    summary(user);
                case "5":
                    goBack = true;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 5.");
                    break;
            }
        }

    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Request account creation");
        System.out.println("2. Request joint account creation");
        System.out.println("3. Add user to existing account");
        System.out.println("4. View summary of accounts");
        System.out.println("5. Go back to main menu");
        System.out.println("Enter a number: ");
    }

    public void createAccount(User user) {
        String type = selectTypeOfAccount(false, user);
        atm.getBM().createAccount(user, type);
    }

    private void requestJointAccountCreation(User user1) {
        System.out.println("Enter the username of the user you would like to open an account with: ");
        String username = scanner.next();

        User user2 = findUser(username);

        while (user2 == null) {
            System.out.println("The user name you entered is not valid. Please enter a valid username: ");
            username = scanner.next();

            user2 = findUser(username);
        }

        String type = selectTypeOfAccount(false, user1);
        atm.getBM().createJointAccount(user1, user2, type);
    }

    private void addUserToExistingAccount(User user1) {
        String type = selectTypeOfAccount(false, user1);
        printChoices(user1, false, type);

        Account accountToAddUser = selectAccount(user1, "add user to", listOfAccounts(user1, type));
        System.out.println("Enter the username of the user you would to like to add to this account: ");
        String username = scanner.next();

        User user2 = findUser(username);

        while (user2 == null) {
            System.out.println("The user name you entered is not valid. Please enter a valid username: ");
            username = scanner.next();

            user2 = findUser(username);
        }

        atm.getBM().addExistingUserToAccount(user2, accountToAddUser);
    }

    private void summary(User user) {
        // Method for users to see a summary of their accounts.

        printChoices(user, true, "chequing");
        printChoices(user, true, "LOC");
        printChoices(user, true, "savings");
        printChoices(user, true, "creditcard");
        System.out.println("Your net total is: " + user.getNetTotal());

    }

    private User findUser(String username) {
        for (User user : atm.getListOfUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
