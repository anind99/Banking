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
        boolean goBack = false;
        Scanner scanner = new Scanner(System.in);

        while (!goBack) {
            printOptions();
            String option = scanner.next();

            switch (option) {
                case "1":
                    createAccount(user);
                    break;
                case "2":
                    requestJointAccountCreation(user);
                    break;
                case "3":
                    addUserToExistingAccount(user);
                    break;
                case "4":
                    summary(user);
                    break;
                case "5":
                    goBack = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 5.");
                    break;
            }
        }
    scanner.close();
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

    private void requestJointAccountCreation(User user1) {
        System.out.println("Enter the username of the user you would like to open an account with: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();

        User user2 = findUser(username);

        while (user2 == null || user2 == user1) {
            System.out.println("The user name you entered is not valid. Please enter a valid username (Press * to quit): ");
            username = scanner.next();

            user2 = findUser(username);
            if (username.equals("*")) {
                break;
            }
        }

        if (!username.equals("*")) {
            String type = selectTypeOfAccount(false, user1);
            atm.getBM().createJointAccount(user1, user2, type);
        }
        scanner.close();
    }

    private void addUserToExistingAccount(User user1) {
        Scanner scanner = new Scanner(System.in);
        String type = selectTypeOfAccount(false, user1);
        printChoices(user1, false, type);

        Account accountToAddUser = selectAccount(user1, "add user to", listOfAccounts(user1, type));
        System.out.println("Enter the username of the user you would to like to add to this account: ");
        String username = scanner.next();

        User user2 = findUser(username);

        while (user2 == null || user2 == user1 ) {
            System.out.println("The user name you entered is not valid. Please enter a valid username (Press * to quit): ");
            username = scanner.next();

            user2 = findUser(username);
            if (username.equals("*")) {
                break;
            }
        }

        if (!username.equals("*")) {
            atm.getBM().addExistingUserToAccount(user2, accountToAddUser);
        }
        scanner.close();
    }

    private void summary(User user) {
        // Method for users to see a summary of their accounts.

        printChoices(user, true, "chequing");
        printChoices(user, true, "loc");
        printChoices(user, true, "savings");
        printChoices(user, true, "creditcard");
        printChoices(user, true, "stock");
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
