package atm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class UserInterface {

    public static void displayUserMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        boolean validselection = false;
        boolean logout = false;
        label:
        while (!validselection && !logout) {
            System.out.println("\nWelcome ATM user, please press * at any point to return to this main menu \n");
            System.out.println("Select an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer In");
            System.out.println("4. Transfer Out");
            System.out.println("5. Pay Bills");
            System.out.println("6. Request Account Creation");
            System.out.println("7. View Summary of Accounts");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            String option = scanner.next();
            switch (option) {
                case "1":
                    deposit(user);
                    break;
                case "2":
                    Withdraw(user);
                    break;
                case "3":
                    transferIn(user);
                    break;
                case "4":
                    transferOut(user);
                    break;
                case "5":
                    payBill(user);
                    break;
                case "6":
                    CreateAccount(user);
                    break;
                case "7":
                    summary(user);
                    break;
                case "8":
                    changePassword(user);
                    break;
                case "9":
                    //Doing nothing works fine here.
                    logout = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 9.");
                    break;
            }
        }

    }

    private static void deposit(User user) {
        ArrayList<Account> chequingAccounts = listOfAccounts(user, "chequing");

        for (Account a : chequingAccounts) {
            Chequing account = (Chequing)a;
            if (account.primary) {
                account.deposit();
                break;
            }
        }
    }

    protected static void CreateAccount(User user) {
        String type = selectTypeOfAccount(false, user);
        ATM.getBM().create_account(user, type);
    }

    protected static void Withdraw(User user) {
        String type = selectTypeOfAccount(false, user);
        printChoices(user, false, type);

        Scanner scanner = new Scanner(System.in);
        Account account = selectAccount(user, "withdraw from");
        boolean running = true;

        while (running) {
            System.out.println("Input amount (The amount has to be a multiple of five, no cents allowed): ");
            String amount = scanner.nextLine();
            StringBuilder amountB = new StringBuilder(amount);

            if (amount.equals("*")) {displayUserMenu(user);}

            boolean valid = true;
            for(int i = 0; i < amountB.length();i++){
                if(!Character.isDigit(amountB.charAt(i))){valid = false;}}

            if(valid){

            if (divisibleByFive(Integer.valueOf(amount))) {
                account.withdraw(Integer.valueOf(amount));
                running = false;
            }} else {
                System.out.println("The amount you entered is not possible, please try again.");
            }
        }
    }

    private static boolean divisibleByFive(int amount) {
        return (amount % 5 == 0);
    }

    private static void transferIn(User user) {
        // Method for users to transfer in.

        System.out.println("Which account do you want to transfer to?");
        String type = selectTypeOfAccount(false, user);
        printChoices(user, false, type);
        Account accountTo = selectAccount(user, "transfer to");

        System.out.println("Which account do you want to transfer out from?");
        String typeTwo = selectTypeOfAccount(true, user);
        printChoices(user, false, typeTwo);
        Account accountFrom = selectAccount(user, "transfer from");
        double amount = selectAmount(user);

        accountTo.transferIn(amount, accountFrom);
    }

    private static void transferOut(User user) {
        // Method for users to transfer out.

        System.out.println("Which account do you want to transfer out from?");
        String type = selectTypeOfAccount(true, user);
        printChoices(user, false, type);
        Account accountFrom = selectAccount(user, "transfer out from");

        System.out.println("Which account do you want to transfer to?");
        String typeTwo = selectTypeOfAccount(false, user);
        printChoices(user, false, typeTwo);
        Account accountTo = selectAccount(user, "transfer to");

        double amount = selectAmount(user);

        accountFrom.transferOut(amount, accountTo);
    }

    private static void payBill(User user) {
        // Method for users to pay bills.

        Scanner scanner = new Scanner(System.in);
        System.out.println("From which account would you like to pay the bill?");
        String type = selectTypeOfAccount(true, user);
        printChoices(user, false, type);
        Account accountFrom = selectAccount(user, "pay the bill from");
        System.out.println("Enter the name of the receiver of the bill: ");
        String receiver = scanner.nextLine();

        double amount = selectAmount(user);

        accountFrom.payBill(amount, receiver.trim());
    }

    private static void changePassword(User user) {
        // Method for users to change their password.

        System.out.println("Type in your new password:");
        Scanner scanner = new Scanner(System.in);
        String newPassword = scanner.nextLine();
        if (!newPassword.equals("*")) {
            user.setPassword(newPassword);
            System.out.println("\nPassword change successful");
        } else {
            System.out.println("\nPassword not changed");
            displayUserMenu(user);
        }
    }

    private static void summary(User user) {
        // Method for users to see a summary of their accounts.

        printChoices(user, true, "chequing");
        printChoices(user, true, "LOC");
        printChoices(user, true, "savings");
        printChoices(user, true, "creditcard");
        System.out.println("Your net total is: " + user.getNetTotal());

    }

    private static ArrayList<Account> listOfAccounts(User user, String typeOfAccount) {
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

    private static StringBuilder printListOfAccounts(ArrayList<Account> listOfAccounts, boolean summary) {
        // Will return a StringBuilder with the account number, balance, last transaction and date
        // created of the accounts a user has.

        StringBuilder choices = new StringBuilder();

        for (Account i : listOfAccounts) {
            choices.append(i.accountNum).append(", Balance: ").append(i.balance);
            if (summary) {
                choices.append(", Last Transaction: ");
                if (i.lastTransaction != null) {
                    choices.append(i.lastTransaction.toString());
                } else {
                    choices.append("No previous transaction.");
                }
                choices.append(", Date Created: ").append(i.dateCreated.getTime());
            }
            choices.append("\n");
        }

        return choices;
    }

    private static String selectTypeOfAccount(boolean transferOut, User user) {
        // Allows users to pick the type of account they want to access and returns their type as a string.

        StringBuilder toPrint = new StringBuilder("Select the type of account: \n 1. Chequing \n" +
                " 2. Line of Credit \n 3. Savings");


        if (transferOut) {
            System.out.println(toPrint);
        } else {
            toPrint.append("\n 4. Credit Card");
            System.out.println(toPrint);
        }

        Scanner scanner = new Scanner(System.in);
        String type = null;
        boolean validselection = false;


        while (!validselection) {
            type = scanner.nextLine();
            if (type.equals("*")) {
                displayUserMenu(user);
            }

            if (type.equals("1") || type.equals("2") || type.equals("3") || (!transferOut && type.equals("4"))) {
                validselection = true;
            } else {
                System.out.println("That is not a valid selection. Please try again.");
            }
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
            toReturn = "LOC";
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
        String accountNumTo = scanner.nextLine();
        StringBuilder accountNumToB = new StringBuilder(accountNumTo);


        boolean valid = true;
        for(int i = 0; i < accountNumToB.length();i++){
            if(!Character.isDigit(accountNumToB.charAt(i))){valid = false;}}


        if(valid) {
        Account account = null;
        for (Account a : user.getAccounts()) {
            if (a.accountNum == Integer.valueOf(accountNumTo)){
                account = a;
            }
        }

        if (account != null) {
            return account;
        } }System.out.println("The account number you entered is not valid. Please try again.");
        return selectAccount(user, action);}




    protected static double selectAmount(User user) {
        // Returns the amount a user would like to deposit/transfer.

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the desired amount you would like to transfer: ");
        String amount = scanner.next();
        StringBuilder amountB = new StringBuilder(amount);

        boolean valid = true;
        if (amountB.length() >= 3) {
            for (int i = 0; i < amountB.length() - 3; i++) {
                if (!Character.isDigit(amountB.charAt(i))) {
                    valid = false;
                }
            }

            for (int i = amountB.length() - 2; i < amountB.length(); i++) {
                if (!Character.isDigit(amountB.charAt(i))) {
                    valid = false;
                }
            }

            if ((!(amountB.charAt((amountB.length() - 3)) == '.') && (!Character.isDigit(amountB.charAt(amountB.length() - 3))))) {
                System.out.println("poop");
                valid = false;}

            if ((amountB.charAt((amountB.length() - 3)) == ',')){valid = false;}

        }else{for (int i = 0; i < amountB.length(); i++) {
            if (!Character.isDigit(amountB.charAt(i))) {
                valid = false;}}}

        if(valid){return Double.valueOf(amount);}

        System.out.println("The amount you entered is not possible, please enter an amount rounded to a whole number or to 2 digits.");
            return selectAmount(user);}
}
