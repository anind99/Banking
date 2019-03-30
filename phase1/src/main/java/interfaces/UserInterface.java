package interfaces;

import account.*;
import atm.ATM;
import atm.User;

import java.util.*;

public class UserInterface extends ATMInterface{
    private Scanner scanner = new Scanner(System.in);

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
                    createAccount(user);
                    break;
                case "7":
                    requestJointAccountCreation(user);
                case "8":
                    addUserToExistingAccount(user);
                case "9":
                    summary(user);
                    break;
                case "10":
                    changePassword(user);
                    break;
                case "11":

                case "12":

                case "13":
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
        System.out.println("6. Request account creation");
        System.out.println("7. Request joint account creation");
        System.out.println("8. Add user to existing account");
        System.out.println("9. View Summary of Accounts");
        System.out.println("10. Change Password");
        System.out.println("11. Buy/Sell Stocks");
        System.out.println("12. Buy/Sell Mutual Funds");
        System.out.println("13. Logout");
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

    private User findUser(String username) {
        for (User user : atm.getListOfUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
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

    private void changePassword(User user) {
        // Method for users to change their password.

        System.out.println("type in your new password:");
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
