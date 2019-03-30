package interfaces;

import account.*;
import atm.*;
import bankmanager.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class BankManagerInterface {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public BankManagerInterface(ATM atm) {
        this.atm = atm;
    }

    public void displayManagerMenu(BankManager bm){
        boolean loggedOut = false;
        boolean validselection = false;
        while (!validselection){
            printOptions();
            String option = scanner.next();
            switch (option) {
                case "0": {
                    settingDate();
                    break;
                }
                case "1": {
                    creatingUser();
                    validselection = true;
                    break;
                }
                case "2": {
                    creatingAccount();
                    validselection = true;
                    break;
                }
                case "3": {
                    checkingAlerts();
                    validselection = true;
                    break;
                }
                case "4": {
                    restockingMachine(bm);
                    validselection = true;
                    break;

                }
                case "5": {
                    undoingTransaction();
                    validselection = true;
                    break;
                }
                case "6": {
                    validselection = true;
                    loggedOut = true;
                    break;
                }
                case "7":{
                    shutDownSystem();
                }
                default: {
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 7.");
                    break;
                }
            }if (!loggedOut) displayManagerMenu(bm);
        }
    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("0. Set Date");
        System.out.println("1. Create User");
        System.out.println("2. Create account");
        System.out.println("3. Check Alerts");
        System.out.println("4. Restock Machine");
        System.out.println("5. Undo transaction");
        System.out.println("6. Logout");
        System.out.println("7. Turn Off System");
    }

    private void settingDate() {
        boolean condition = false;
        String year = null, month = null, day = null;
        while(!condition){
            condition = true;
            System.out.println("Setting date:");
            System.out.println("Year:");
            year = scanner.next();
            System.out.println("Month:");
            month = scanner.next();
            System.out.println("Day:");
            day = scanner.next();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.parse(year + "-" + month + "-" + day);
            } catch (ParseException e){
                System.out.println("Parse failed. Is the date provided well formed?");
                System.out.println("Try again.");
                condition = false;
            }
        }
        atm.setDate(year + "-" + month + "-" + day);
        System.out.println("Date set, but note that time sensitive operations might not execute immediately " +
                "(such as addSavingsInterest, which happens after the system boots). Also note that the " +
                "date will still increment another day if you restart the system via the manager");
    }


    /***
     * Creates a new user. All account types will be automatically opened for each user.
     *
     */
    private void creatingUser(){
        System.out.println("type the username for the new user");
        String username = scanner.next();
        System.out.println("type the password for the new user");
        String password = scanner.next();
        User user = atm.getBM().createUser(username, password);
        atm.getBM().createAccount(user, "chequing");
        atm.getBM().createAccount(user, "creditcard");
        atm.getBM().createAccount(user, "loc");
        atm.getBM().createAccount(user, "savings");
        atm.getBM().createAccount(user, "stock");
    }

    private void creatingAccount() {
        User user = null;
        boolean created = false;
        int count = 0;
        int count2 = 0;
        while (user == null) {
            if (count != 0) {
                System.out.println("type in the username of the user that would like to create an account: ");
            }
            String username = scanner.nextLine();
            for (User parameter : atm.getListOfUsers()) {
                if (parameter.getUsername().equals(username)) {
                    user = parameter;
                    createAccount(user);
                    created = true;
                    break;
                }
            }
            if (count2 != 0 && !created) {
                System.out.println("The username is not valid, please try again.");
            }
            count += 1;
            count2 += 1;
        }
    }

    void createAccount(User user) {
        String type = selectTypeOfAccount(false, user);
        atm.getBM().createAccount(user, type);
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

    private void checkingAlerts(){
        try {System.out.println(System.getProperty("user.dir"));
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/alerts.txt");
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = "Alerts:";
            System.out.println(line);

            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Problem reading the file alerts.txt");
        }
    }

    private void restockingMachine(BankManager bm){
        System.out.println("Select what type of bill to restock.");
        System.out.println("1. Five dollars, 2. Ten dollars, 3. Twenty dollars, 4. Fifty dollars");
        String dollarType = scanner.next();
        switch (dollarType) {
            case "1":
                bm.restock(1);
                break;
            case "2":
                bm.restock(2);
                break;
            case "3":
                bm.restock(3);
                break;
            case "4":
                bm.restock(4);
                break;
            default:
                System.out.println("There is no option " + dollarType + ". Pick a number from 1 to 4 or quit.");
                break;
        }
    }

    private void undoingTransaction(){
        User user = null;
        int count = 0;
        int count2 = 0;
        while (user == null) {
            if (count2 != 0){
                System.out.println("type in the username of the user that would like to undo their last transaction: ");
            }
            String username = scanner.nextLine();
            System.out.println(username);
            for (User parameter : atm.getListOfUsers()) {
                if (parameter.getUsername().equals(username)) {
                    user = parameter;
                    break;
                }
            } if (count != 0){System.out.println("The username is not valid, please try again.");}
            count += 1;
            count2 += 1;
            //System.out.println("The username is not valid, please try again.");
        }

        Account account = selectAccount(user, "undo its last transaction", user.getAccounts());
        atm.getBM().undoTransaction(user, account);
    }

    Account selectAccount(User user, String action, ArrayList<Account> listOfAccounts) {
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

    private void shutDownSystem(){
        atm.testShutDown();
        System.out.println("System now shutting down");
        System.exit(0);
    }
}
