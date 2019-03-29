package atm;

import account.*;

import java.util.ArrayList;

public class UserInterface extends Interface{


    public UserInterface(ATM atm) {
        super(atm);
    }

    public void displayUserMenu(User user) {
        boolean validselection = false;
        boolean logout = false;
        while (!logout) {
            printOptions();
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

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer In");
        System.out.println("4. Transfer Out");
        System.out.println("5. Pay Bills");
        System.out.println("6. Request account Creation");
        System.out.println("7. View Summary of Accounts");
        System.out.println("8. Change Password");
        System.out.println("9. Logout");
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

    private void Withdraw(User user) {
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

            if (divisibleByFive(Integer.valueOf(amount))) {
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

    private void changePassword(User user) {
        // Method for users to change their password.

        System.out.println("Type in your new password:");
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
        System.out.println("\nPassword change successful");
    }

    private void summary(User user) {
        // Method for users to see a summary of their accounts.

        printChoices(user, true, "chequing");
        printChoices(user, true, "LOC");
        printChoices(user, true, "savings");
        printChoices(user, true, "creditcard");
        System.out.println("Your net total is: " + user.getNetTotal());

    }
}
