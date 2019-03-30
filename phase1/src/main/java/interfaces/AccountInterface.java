package interfaces;

import atm.*;
import account.*;
import bankmanager.*;

import java.util.*;

public class AccountInterface {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public AccountInterface(ATM atm) {
        this.atm = atm;
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

    String selectTypeOfAccount(boolean transferOut, User user) {
        // Allows users to pick the type of account they want to access and returns their type as a string.

        StringBuilder toPrint = new StringBuilder("Select the type of account: \n 1. Chequing \n" +
                " 2. Line of Credit \n 3. Savings");


        if (transferOut) {
            System.out.println(toPrint);
        } else {
            toPrint.append("\n 4. Credit Card");
            System.out.println(toPrint);
        }

        String type = null;
        boolean validselection = false;


        while (!validselection) {
            type = scanner.nextLine();

            if (type.equals("1") || type.equals("2") || type.equals("3") || (!transferOut && type.equals("4"))) {
                validselection = true;
            } else {
                System.out.println("That is not a valid selection. Please try again.");
            }
        }

        return returnTypeOfAccount(type, transferOut);
    }

    private String returnTypeOfAccount(String selection, boolean transferOut) {
        // Helper function for selectTypeOfAccount. The function recognizes the selection the user makes and returns
        // the corresponding account type as a string.

        String toReturn = null;

        if (selection.equals("1")) {
            toReturn = "chequing";
        } else if (selection.equals("2")) {
            toReturn = "loc";
        } else if (selection.equals("3")) {
            toReturn = "savings";
        } else if (!transferOut && selection.equals("4")) {
            toReturn = "creditcard";
        }

        return toReturn;
    }

    private StringBuilder printListOfAccounts(ArrayList<Account> listOfAccounts, boolean summary) {
        // Will return a StringBuilder with the account number, balance, last transaction and date
        // created of the accounts a user has.

        StringBuilder choices = new StringBuilder();

        for (Account i : listOfAccounts) {
            choices.append(i.accountNum).append(", Balance: ").append(i.getBalance());
            if (summary) {
                choices.append(", Last Transaction: ");
                if (i.getLastTransaction() != null) {
                    choices.append(i.getLastTransaction().toString());
                } else {
                    choices.append("No previous transaction.");
                }
                choices.append(", Date Created: ").append(i.dateCreated.getTime());
            }
            choices.append("\n");
        }

        return choices;
    }

    public ArrayList<Account> listOfAccounts(User user, String typeOfAccount) {
        // Helper function for printListOfAccounts. This method returns an array list of a certain type of account
        // (taken as a parameter) that a user has.

        ArrayList<Account> accounts = new ArrayList<>();

        for (Account a : user.getAccounts()) {
            if (a.getType().equals(typeOfAccount)) {
                accounts.add(a);
            }
        }

        return accounts;
    }

    public void printChoices(User user, boolean summary, String typeOfAccount) {
        // Prints out the accounts a user has.

        ArrayList<Account> accounts = listOfAccounts(user, typeOfAccount);

        if (typeOfAccount.equals("creditcard")) {
            typeOfAccount = "credit card";
        }

        StringBuilder choices = new StringBuilder("Your " + typeOfAccount + " accounts: \n");
        choices.append(printListOfAccounts(accounts, summary));


        System.out.println(choices);
    }

    public Account selectAccount(User user, String action, ArrayList<Account> listOfAccounts) {
        // Allows users to select an account by entering their account number. Returns that account.

        System.out.println("Enter the account number you want to " + action + ": ");
        String accountNumTo = scanner.nextLine();
        StringBuilder accountNumToB = new StringBuilder(accountNumTo);


        boolean valid = true;
        for(int i = 0; i < accountNumToB.length();i++){
            if(!Character.isDigit(accountNumToB.charAt(i))){valid = false;}}


        if(valid) {
            Account account = null;
            for (Account a : listOfAccounts) {
                if (a.accountNum == Integer.valueOf(accountNumTo)){
                    account = a;
                }
            }

            if (account != null) {
                return account;
            }
        }

        System.out.println("The account number you entered is not valid. Please try again.");
        return selectAccount(user, action, listOfAccounts);
    }
}
