package atm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    static void displayUserMenu(User user){
        Scanner scanner = new Scanner(System.in);
        boolean validselection = false;
        boolean logout = false;
        while (!validselection && !logout){
            System.out.println("\nSelect an option:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer In");
            System.out.println("5. Transfer Out");
            System.out.println("6. Pay Bills");
            System.out.println("7. Request Account Creation");
            System.out.println("8. View Summary of Accounts");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");
            String option = scanner.next();
            if (option.equals("1")){
                CreateAccount(user);
                validselection = true;
            } else if (option.equals("2")){
                for (Account acc : user.getAccounts()) {acc.deposit();
                    break;
                } /// NEEDS FIXING
            } else if (option.equals("3")) {
                Withdraw(user);
                validselection = true;
            } else if (option.equals("4")) {
                transferIn(user);
                validselection = true;
            } else if (option.equals("5")) {
                transferOut(user);
                validselection = true;
            } else if (option.equals("6")) {
                payBill(user);
                validselection = true;
            } else if (option.equals("7")) {
                CreateAccount(user);
                validselection = true;
            } else if (option.equals("8")) {
                summary(user);
                validselection = true;
            } else if (option.equals("9")) {
                changePassword(user);
                validselection = true;
            } else if (option.equals("10")) {
                //Doing nothing works fine here.
                logout = true;
            } else {
                System.out.println("There is no option " + option + ". Pick a number from 1 to 10.");
            }
        }

    }

    protected static void CreateAccount(User user){
        String type = selectTypeOfAccount(false);
        ATM.getBM().create_account(user, type);
    }

    protected static void Withdraw(User user){
        String type = selectTypeOfAccount(false);
        printChoices(user, false, type);

        Scanner scanner = new Scanner(System.in);
        Account account = selectAccount(user, "withdraw from");
        boolean running = true;

        while (running) {
            System.out.println("Input amount (The amount has to be a multiple of five, no cents allowed): ");
            int amount = scanner.nextInt();
            if (divisibleByFive(amount)) {
                account.withdraw(amount);
                running = false;
            } else {
                System.out.println("the amount you entered is not possible, please try again.");
            }
        }
    }

    private static boolean divisibleByFive(int amount) {
        return (amount % 5 == 0);
    }

    private static void transferIn(User user) {
        // Method for users to transfer in.

        String type = selectTypeOfAccount(false);
        printChoices(user, false, type);
        Account accountTo = selectAccount(user, "transfer to");
        Account accountFrom = selectAccount(user, "transfer from");
        double amount = selectAmount();

        accountTo.transferIn(amount, accountFrom);
    }

    private static void transferOut(User user) {
        // Method for users to transfer out.

        String type = selectTypeOfAccount(true);
        printChoices(user, false, type);
        Account accountFrom = selectAccount(user, "transfer out from");
        Account accountTo = selectAccount(user, "transfer to");
        double amount = selectAmount();

        accountFrom.transferOut(amount, accountTo);
    }

    private static void payBill(User user) {
        // Method for users to pay bills.

        Scanner scanner = new Scanner(System.in);
        String type = selectTypeOfAccount(true);
        printChoices(user, false, type);
        Account accountFrom = selectAccount(user, "pay the bill from");
        System.out.println("Enter the name of the receiver of the bill: ");
        String receiver = scanner.nextLine();

        double amount = selectAmount();

        accountFrom.payBill(amount, receiver.trim());
    }

    private static void changePassword(User user) {
        // Method for users to change their password.

        System.out.println("Type in your new password:");
        Scanner scanner = new Scanner(System.in);
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
    }

    private static void summary(User user) {
        // Method for users to see a summary of their accounts.

        printChoices(user, true, "chequing");
        printChoices(user, true, "loc");
        printChoices(user, true, "savings");
        printChoices(user, true, "creditcard");
        System.out.println("Your net total is: " + user.getNetTotal());

    }

    private static ArrayList<Account> listOfAccounts(User user, String typeOfAccount) {
        // Helper function for printListOfAccounts. This method returns an array list of a certain type of account
        // (taken as a parameter) that a user has.

        ArrayList<Account> accounts = new ArrayList<>();

        for (Account a : user.getAccounts()) {
            if (a.type.equals(typeOfAccount)) {
                accounts.add(a);
            }
        }

        return accounts;
    }

    private static StringBuilder printListOfAccounts(ArrayList<Account> listOfAccounts, boolean summary) {
        // Will return a StringBuilder with the account number, balance, last transaction and date
        // created of the accounts a user has.

        StringBuilder choices = new StringBuilder();

        for (Account i : listOfAccounts) {
            choices.append(i.accountNum).append(", Balance: ").append(i.balance);
            if (summary) {
                choices.append(", Last Transaction: ").append(i.lastTransaction);
                choices.append(", Date Created: ").append(i.dateCreated);
            }
            choices.append("\n");
        }

        return choices;
    }

    private static String selectTypeOfAccount(boolean transferOut) {
        // Allows users to pick the type of account they want to access and returns their type as a string.

        StringBuilder toPrint = new StringBuilder("Select the type of account: \n 1. Chequing \n" +
                "2. Line of Credit \n 3. Savings");


        if (transferOut) {
            System.out.println(toPrint);
        } else {
            toPrint.append("\n + 4. Credit Card");
            System.out.println(toPrint);
        }

        Scanner scanner = new Scanner(System.in);
        String type = null;
        boolean validselection = false;

        while (!validselection) {
            type = scanner.nextLine();

            if (type.equals("1") || type.equals("2") || type.equals("3") || (!transferOut && type.equals("4"))) {
                validselection = true;
            }

            System.out.println("That is not a valid selection. Please try again.");
        }

        return returnTypeOfAccount(type, transferOut);
    }

    private static String returnTypeOfAccount(String selection, boolean transferOut) {
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

    private static void printChoices(User user, boolean summary, String typeOfAccount) {
        // Prints out the accounts a user has.

        ArrayList<Account> accounts = listOfAccounts(user, typeOfAccount);

        if (typeOfAccount.equals("creditcard")) {
            typeOfAccount = "credit card";
        }

        StringBuilder choices = new StringBuilder("Your " + typeOfAccount + " accounts: \n");
        choices.append(printListOfAccounts(accounts, summary));


        System.out.println(choices);
    }

    protected static Account selectAccount(User user, String action) {
        // Allows users to select an account by entering their account number. Returns that account.

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the account number you want to " + action + ": ");
        int accountNumTo = scanner.nextInt();
        Account account = null;

        for (Account a : user.getAccounts()) {
            if (a.accountNum == accountNumTo) {
                account = a;
            }
        }

        if (account != null) {
            return account;
        } else {
            System.out.println("The account number you entered is not valid. Please try again.");
            return selectAccount(user, action);
        }
    }

    protected static double selectAmount() {
        // Returns the amount a user would like to deposit/transfer.

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the desired amount you would like to transfer: ");
        return scanner.nextDouble();

    }
}
