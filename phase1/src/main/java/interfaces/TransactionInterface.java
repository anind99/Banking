package interfaces;

import atm.*;
import account.*;

import java.io.*;
import java.util.*;

public class TransactionInterface implements Serializable {
    private ATM atm;
    transient Scanner scanner;
    private GeneralInterfaceMethods general;

    public TransactionInterface(ATM atm) {
        this.atm = atm;
        this.general = new GeneralInterfaceMethods(atm);
    }

    public void displayTransactionMenu(User user) {
        boolean goBack = false;
        scanner = new Scanner(System.in);

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
        ArrayList<Account> chequingAccounts = general.listOfAccounts(user, "chequing");

        for (Account a : chequingAccounts) {
            Chequing account = (Chequing)a;
            if (account.primaryStatus) {
                account.deposit();
                break;
            }
        }
    }

    private void withdraw(User user) {
        String type = general.selectTypeOfAccount(false);
        general.printChoices(user, false, type);

        Account account = general.selectAccount("withdraw from", general.listOfAccounts(user, type));
        boolean running = true;

        while (running) {
            System.out.println("Input amount (The amount has to be a multiple of five, no cents allowed): ");
            scanner = new Scanner(System.in);
            String amount = scanner.next();
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
        String type = general.selectTypeOfAccount(false);
        general.printChoices(user, false, type);
        Account accountTo = general.selectAccount("transfer to", general.listOfAccounts(user, type));

        System.out.println("Which account do you want to transfer out from?");
        String typeTwo = general.selectTypeOfAccount(true);
        general.printChoices(user, false, typeTwo);
        Account accountFrom = general.selectAccount("transfer from", general.listOfAccounts(user, typeTwo));
        double amount = general.selectAmount();

        accountTo.transferIn(amount, accountFrom);
    }

    private void transferOut(User user) {
        // Method for users to transfer out.

        System.out.println("Which account do you want to transfer out from?");
        String type = general.selectTypeOfAccount(true);
        general.printChoices(user, false, type);
        Account accountFrom = general.selectAccount("transfer out from", general.listOfAccounts(user, type));

        System.out.println("Which account do you want to transfer to?");
        String typeTwo = general.selectTypeOfAccount(false);
        general.printChoices(user, false, typeTwo);
        Account accountTo = general.selectAccount("transfer to", general.listOfAccounts(user, typeTwo));

        double amount = general.selectAmount();

        accountFrom.transferOut(amount, accountTo);
    }

    private void payBill(User user) {
        // Method for users to pay bills.

        System.out.println("From which account would you like to pay the bill?");
        String type = general.selectTypeOfAccount(true);
        general.printChoices(user, false, type);
        Account accountFrom = general.selectAccount("pay the bill from", general.listOfAccounts(user, type));
        System.out.println("Enter the name of the receiver of the bill: ");
        scanner = new Scanner(System.in);
        String receiver = scanner.next();
        double amount = general.selectAmount();

        accountFrom.payBill(amount, receiver.trim());
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("TransactionInterface writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("TransactionInterface readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("TransactionInterface readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
