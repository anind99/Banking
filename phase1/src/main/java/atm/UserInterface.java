package atm;

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
                //validselection = true;
            } else if (option.equals("2")){
                for (Account acc : user.getAccounts()) {acc.deposit();
                    break;
                } /// NEEDS FIXING
            } else if (option.equals("3")) {
                Withdraw(user);
                //validselection = true;
            } else if (option.equals("4")) {
                printChoices(user, false);
                Account accountTo = selectAccount(user, "transfer to");
                Account accountFrom = selectAccount(user, "transfer from");
                double amount = selectAmount();

                accountTo.transferIn(amount, accountFrom);
            } else if (option.equals("5")) {
                printChoices(user, false);
                System.out.println("Note that you cannot transfer out of a credit card account.");
                Account accountFrom = selectAccount(user, "transfer out from");
                Account accountTo = selectAccount(user, "transfer to");
                double amount = selectAmount();

                accountFrom.transferOut(amount, accountTo);
            } else if (option.equals("6")) {
                printChoices(user, false);
                Account accountFrom = selectAccount(user, "pay the bill from");
                System.out.println("Enter the name of the receiver of the bill: ");
                String receiver = scanner.nextLine();

                double amount = selectAmount();

                accountFrom.payBill(amount, receiver.trim());

            } else if (option.equals("7")) {
                CreateAccount(user);
            } else if (option.equals("8")) {
                summary(user);
            } else if (option.equals("9")) {
                changePassword(user);
            } else if (option.equals("10")) {
                //Doing nothing works fine here.
                logout = true;
            } else {
                System.out.println("There is no option " + option + ". Pick a number from 1 to 10.");
            }
        }

    }

    protected static void changePassword(User user) {
        System.out.println("Type in your new password:");
        Scanner scanner = new Scanner(System.in);
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
    }

    private static void summary(User user) {
        printChoices(user, true);
        System.out.println("Your net total is: " + user.getNetTotal());

    }

    private static void printChoices(User user, boolean summary) {
        // Prints list of account numbers a user has.

        ArrayList<Account> creditCardAccounts = new ArrayList<>();
        ArrayList<Account> locAccounts = new ArrayList<>();
        ArrayList<Account> chequingAccounts = new ArrayList<>();
        ArrayList<Account> savingsAccounts = new ArrayList<>();

        for (Account a : user.getAccounts()) {
            if (a instanceof CreditCard) {
                creditCardAccounts.add(a);
            } else if (a instanceof LOC) {
                locAccounts.add(a);
            } else if (a instanceof Chequing) {
                chequingAccounts.add(a);
            } else if (a instanceof Savings) {
                savingsAccounts.add(a);
            }
        }

        StringBuilder choices = new StringBuilder("Here are your list of accounts: \n");

        // Add Credit Card accounts to String.
        for (Account i : creditCardAccounts) {
            choices.append("1. Credit Card Accounts: \n");
            choices.append(i.accountNum);
            choices.append(", Balance: ");
            choices.append(i.balance);
            if (summary) {
                choices.append(", Last Transaction: ");
                choices.append(i.lastTransaction);
                choices.append(", Date Created");
                choices.append(i.dateCreated);
            }
            choices.append("\n");
        }

        // Add LOC accounts to String.
        for (Account i: locAccounts) {
            choices.append("2. Line of Credit Accounts: \n");
            choices.append(i.accountNum);
            choices.append(", Balance: ");
            choices.append(i.balance);
            if (summary) {
                choices.append(", Last Transaction: ");
                choices.append(i.lastTransaction);
                choices.append(", Date Created");
                choices.append(i.dateCreated);
            }
            choices.append("\n");
        }

        // Add Chequing Accounts to String.

        for (Account i: chequingAccounts) {
            choices.append("3. Chequing Accounts: \n");
            choices.append(i.accountNum);
            choices.append(", Balance: ");
            choices.append(i.balance);
            if (summary) {
                choices.append(", Last Transaction: ");
                choices.append(i.lastTransaction);
                choices.append(", Date Created");
                choices.append(i.dateCreated);
            }
            choices.append("\n");
        }

        for (Account i : savingsAccounts) {
            choices.append("4. Savings Accounts: ");
            choices.append(i.accountNum);
            choices.append(", Balance: ");
            choices.append(i.balance);
            if (summary) {
                choices.append(", Last Transaction: ");
                choices.append(i.lastTransaction);
                choices.append(", Date Created");
                choices.append(i.dateCreated);
            }
            choices.append("\n");
        }

        System.out.println(choices);
    }

    protected static Account selectAccount(User user, String action) {
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the desired amount you would like to transfer: ");
        Double num = scanner.nextDouble();
        return num;

    }

    protected static void Withdraw(User user){
        System.out.println("Choose Accounts by account number: ");
        for (Account acc : user.accounts){
         System.out.println(acc.type +" " + acc.accountNum + " Balance: " + " ");
        }
        Scanner scanner = new Scanner(System.in);
        int account_num = scanner.nextInt();
        for (Account acc: user.accounts){
            if (account_num == acc.accountNum){
                boolean running = true;
                while (running) {
                    System.out.println("Input amount (The amount has to be a multiple of five, no cents allowed): ");
                    int amount = scanner.nextInt();
                    if (divisibleByFive(amount)) {
                        acc.withdraw(amount);
                        running = false;
                    }
                    else {
                        System.out.println("The amount you entered is not possible, please try again.");
                    }
                }
                break;
            }
        }
    }

    private static boolean divisibleByFive(int amount) {
        if (amount % 5 == 0) {
            return true;
        } else {
            return false;
        }
    }

    protected static void CreateAccount(User usr){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the type of Account: 1 : Saving, 2: Chequing, 3: Credit 4: Line of Credit");
        int t = scanner.nextInt();
        String type = null;
        while (type == null) {
            if (t == 1) {
                type = "Saving";
            } else if (t == 2) {
                type = "Chequing";
            } else if (t == 3) {
                type = "Credit Card";
            } else if (t == 4) {
                type = "LOC";
            }
            else{
                System.out.println("Type the type of Account: 1 : Saving, 2: Chequing, 3: Credit 4: Line of Credit");
                t = scanner.nextInt();
            }
        }
        ATM.getBM().create_account(usr, type);
    }
}
