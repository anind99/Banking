package interfaces;

import account.*;
import atm.*;

import java.util.ArrayList;
import java.util.*;

public class GeneralInterface {
    Scanner scanner = new Scanner(System.in);
    ATM atm;

    public GeneralInterface(ATM atm) {
        this.atm = atm;
    }

    public void createAccount(User user) {
        String type = selectTypeOfAccount(false, user);
        atm.getBM().createAccount(user, type);
    }

    // TODO: Fix scanner.nextLine() thing, the valid selection keeps popping up, i'm not sure why.
    public String selectTypeOfAccount(boolean transferOut, User user) {
        // Allows users to pick the type of account they want to access and returns their type as a string.

        StringBuilder toPrint = new StringBuilder("Select the type of account: \n 1. Chequing \n" +
                " 2. Line of Credit \n 3. Savings");


        if (transferOut) {
            toPrint.append("\n 4. Stocks");
            System.out.println(toPrint);
        } else {
            toPrint.append("\n 4. Stocks \n 5. Credit Card");
            System.out.println(toPrint);
        }

        String type = null;
        boolean validselection = false;


        while (!validselection) {
            type = scanner.next();

            if (type.equals("1") || type.equals("2") || type.equals("3") || type.equals("4") || (!transferOut && type.equals("5"))) {
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
        } else if (selection.equals("4")){
            toReturn = "stock";
        } else if (!transferOut && selection.equals("5")) {
            toReturn = "creditcard";
        }

        return toReturn;
    }

    private StringBuilder printListOfAccounts(ArrayList<Account> listOfAccounts, boolean summary) {
        // Will return a StringBuilder with the account number, balance, last transaction and date
        // created of the accounts a user has.

        StringBuilder choices = new StringBuilder();

        for (Account i : listOfAccounts) {
            choices.append(i.getAccountNum()).append(", Balance: ").append(i.getBalance());
            if (summary) {
                choices.append(", Last Transaction: ");
                if (i.getLastTransaction() != null) {
                    choices.append(i.getLastTransaction().toString());
                } else {
                    choices.append("No previous transaction.");
                }
                choices.append(", Date Created: ").append(i.getDateCreated().getTime());
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
        String accountNumTo = scanner.next();
        StringBuilder accountNumToB = new StringBuilder(accountNumTo);


        boolean valid = true;
        for(int i = 0; i < accountNumToB.length();i++){
            if(!Character.isDigit(accountNumToB.charAt(i))){valid = false;}}


        if(valid) {
            Account account = null;
            for (Account a : listOfAccounts) {
                if (a.getAccountNum() == Integer.valueOf(accountNumTo)){
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

    public double selectAmount() {
        // Returns the amount a user would like to deposit/transfer.

        System.out.println("Enter the desired amount you would like to transfer: ");
        String amount = scanner.next();
        StringBuilder amountB = new StringBuilder(amount);

        // Checks that the amount entered by the user is valid.
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
                valid = false;}

            if ((amountB.charAt((amountB.length() - 3)) == ',')){valid = false;}

        }else{for (int i = 0; i < amountB.length(); i++) {
            if (!Character.isDigit(amountB.charAt(i))) {
                valid = false;}}}

        if(valid){return Double.valueOf(amount);}

        System.out.println("The amount you entered is not possible, please enter an amount rounded to a whole number or to 2 digits.");
        return selectAmount();
    }

}
