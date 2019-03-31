package interfaces;

import atm.*;
import account.*;
import bankmanager.*;

import java.util.*;

public class TransactionInterface extends GeneralInterface{

    public TransactionInterface(ATM atm) {
        super(atm);
    }

    public void displayTransactionMenu(User user) {
        boolean validselection = false;
        boolean goBack = false;

        while (!goBack) {
            printOptions();
            String option = scanner.next();
            switch (option) {
                case "1":
                    deposit(user);
                    break;
                case "2":
                    withdraw(user);
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
                    goBack = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 6.");
                    break;
            }

        }
    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer In");
        System.out.println("4. Transfer Out");
        System.out.println("5. Pay Bills");
        System.out.println("6. Go back to main menu");
        System.out.println("Enter a number: ");
    }

    private void deposit(User user) {
        ArrayList<Account> chequingAccounts = listOfAccounts(user, "chequing");

        for (Account a : chequingAccounts) {
            Chequing account = (Chequing)a;
            if (account.primary) {
                account.deposit();
                break;
            }
        }
    }

    private void withdraw(User user) {
        String type = selectTypeOfAccount(false, user);
        printChoices(user, false, type);

        Account account = selectAccount(user, "withdraw from", listOfAccounts(user, type));
        boolean running = true;

        while (running) {
            System.out.println("Input amount (The amount has to be a multiple of five, no cents allowed): ");
            String amount = scanner.nextLine();
            StringBuilder amountB = new StringBuilder(amount);

            boolean valid = true;
            for(int i = 0; i < amountB.length();i++){
                if(!Character.isDigit(amountB.charAt(i))){valid = false;}}

            if(valid){

                if (Integer.valueOf(amount) % 5 == 0) {
                    account.withdraw(Integer.valueOf(amount));
                    running = false;
                }} else {
                System.out.println("The amount you entered is not possible, please try again.");
            }
        }
    }

    private void transferIn(User user) {
        // Method for users to transfer in.

        System.out.println("Which account do you want to transfer to?");
        String type = selectTypeOfAccount(false, user);
        printChoices(user, false, type);
        Account accountTo = selectAccount(user, "transfer to", listOfAccounts(user, type));

        System.out.println("Which account do you want to transfer out from?");
        String typeTwo = selectTypeOfAccount(true, user);
        printChoices(user, false, typeTwo);
        Account accountFrom = selectAccount(user, "transfer from", listOfAccounts(user, typeTwo));
        double amount = selectAmount();

        accountTo.transferIn(amount, accountFrom);
    }

    private void transferOut(User user) {
        // Method for users to transfer out.

        System.out.println("Which account do you want to transfer out from?");
        String type = selectTypeOfAccount(true, user);
        printChoices(user, false, type);
        Account accountFrom = selectAccount(user, "transfer out from", listOfAccounts(user, type));

        System.out.println("Which account do you want to transfer to?");
        String typeTwo = selectTypeOfAccount(false, user);
        printChoices(user, false, typeTwo);
        Account accountTo = selectAccount(user, "transfer to", listOfAccounts(user, typeTwo));

        double amount = selectAmount();

        accountFrom.transferOut(amount, accountTo);
    }

    private void payBill(User user) {
        // Method for users to pay bills.

        System.out.println("From which account would you like to pay the bill?");
        String type = selectTypeOfAccount(true, user);
        printChoices(user, false, type);
        Account accountFrom = selectAccount(user, "pay the bill from", listOfAccounts(user, type));
        System.out.println("Enter the name of the receiver of the bill: ");
        String receiver = scanner.nextLine();

        double amount = selectAmount();

        accountFrom.payBill(amount, receiver.trim());
    }
}
