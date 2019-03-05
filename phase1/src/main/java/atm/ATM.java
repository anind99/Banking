package atm;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class ATM {

    /** Stores the total amount of the bills in the ATM in an array with the following order:
     [5 dollar bills, 10, dollar bills, 20 dollar bills, 50 dollar bills]. */
    private static int[] bills;
    private static ArrayList<User> listOfUsers = new ArrayList<User>();
    private static BankManager BM;

    private static Calendar date;

    public ATM() {
        bills[0] = 100;
        bills[1] = 100;
        bills[2] = 100;
        bills[3] = 100;
    }

    public static void main(String[] arg){
        boolean running = true;
        debugSetup();
        setup();
        addSavingsInterest();
        while (running){
            String username = displayLoginMenu();
            if (username.equals("user")){
                for (User usr : listOfUsers) {
                    if (usr.getUsername().equals(username)) {
                        displayUserMenu(usr);
                    }
                }

            } else if (username.equals("Manager")) {
                displayManagerMenu();
            }
        }
        dateIncrement();
    }

    private static void debugSetup(){
//        This function sets up our ATM environment for debugging purposes.
//        Breaking it for merging is fine, but it shouldn't happen.
        User user1 = new User("manager", "password", null);
        addUserToList(user1);

    }

    private static void addSavingsInterest(){
        if (date.get(Calendar.DAY_OF_MONTH) == 1){
            for (User user : listOfUsers){
                ArrayList<Account> listOfAccounts = user.getAccounts();
                for (Account account: listOfAccounts){
                    if (account instanceof Savings){
                        ((Savings)account).addInterest();
                    }
                }
            }
        }

    }


    private static void dateIncrement(){
        try {
            File file = new File("date.txt");
            file.delete();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter("date.txt"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date.add(Calendar.DATE, 1);
            writer.write(sdf.format(date.getTime()));
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error, quitting system!");
            System.exit(-1);
        }

    }
    private static void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("date.txt"));
            String line;
            if ((line = reader.readLine()) != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(line));
                date = c;
                reader.close();
                System.out.println("Booting system on" + line);
            }
        }
        catch(FileNotFoundException e){
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("date.txt"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                date = c;
                writer.write(sdf.format(date.getTime()));
                writer.close();

            }
            catch (Exception ee) {
                System.out.println(ee.getMessage());
                System.out.println("error, quitting system!");
                System.exit(-1);
            }
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error, quitting system!");
            System.exit(-1);
        }
    }



    private static void displayUserMenu(User user){
        Scanner scanner = new Scanner(System.in);
        boolean validselection = false;
        boolean logout = false;
        while (!validselection && !logout){
            System.out.println("Select an option:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer In");
            System.out.println("5. Transfer Out");
            System.out.println("6. Pay Bills");
            System.out.println("7. Logout");
            String option = scanner.next();
            if (option.equals("1")){
                CreateAccount(user);
                validselection = true;
            } else if (option.equals("2")){
                user.Deposit();
            } else if (option.equals("3")) {
                Withdraw(user);
                validselection = true;
            } else if (option.equals("4")) {
                printChoices(user);
                Account accountTo = selectAccount(user, "transfer to");
                Account accountFrom = selectAccount(user, "transfer from");
                double amount = selectAmount();

                accountTo.transferIn(amount, accountFrom);
            } else if (option.equals("5")) {
                printChoices(user);
                System.out.println("Note that you cannot transfer out of a credit card account.");
                Account accountFrom = selectAccount(user, "transfer out from");
                Account accountTo = selectAccount(user, "transfer to");
                double amount = selectAmount();

                accountFrom.transferOut(amount, accountTo);
            } else if (option.equals("6")) {
                printChoices(user);
                Account accountFrom = selectAccount(user, "pay the bill from");
                System.out.println("Enter the name of the receiver of the bill: ");
                String receiver = scanner.nextLine();

                double amount = selectAmount();

                accountFrom.payBill(amount, receiver.trim());

            } else if (option.equals("7")) {
                //Doing nothing works fine here.
                logout = true;
            } else {
                System.out.println("There is no option " + option + ". Pick a number from 1 to 6.");
            }
        }

    }

    private static void printChoices(User user) {
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
            } else if (a instanceof Checking) {
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
            choices.append("\n");
        }

        // Add LOC accounts to String.
        for (Account i: locAccounts) {
            choices.append("2. Line of Credit Accounts: \n");
            choices.append(i.accountNum);
            choices.append("\n");
        }

        // Add Chequing Accounts to String.

        for (Account i: chequingAccounts) {
            choices.append("3. Chequing Accounts: \n");
            choices.append(i.accountNum);
            choices.append("\n");
        }

        for (Account i : savingsAccounts) {
            choices.append("4. Savings Accounts: ");
            choices.append(i.accountNum);
            choices.append("\n");
        }

        System.out.println(choices);
    }

    private static Account selectAccount(User user, String action) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the account number you want to " + action + ": ");
        String accountTo = scanner.nextLine();
        int accountNumTo = Integer.parseInt(accountTo.trim());
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

    private static double selectAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the desired amount you would like to transfer: ");
        String num = scanner.nextLine();
        return Double.parseDouble(num);

    }

    private static void Withdraw(User user){
        System.out.println("Choose Accounts by account number: ");
        for (Account acc : user.accounts){
         System.out.println(acc.type +" " + acc.accountNum + " Balance: " + " ");
        }
        Scanner scanner = new Scanner(System.in);
        int account_num = scanner.nextInt();
        for (Account acc: user.accounts){
            if (account_num == acc.accountNum){
                System.out.println("Input amount");
                double amount = scanner.nextDouble();
                acc.withdraw(amount);
                break;
            }
        }
    }



    private static void CreateAccount(User usr){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the type of Account: 1 : Saving, 2: Checking, 3: Credit 4: Line of Credit");
        int t = scanner.nextInt();
        String type = null;
        while (type == null) {
            if (t == 1) {
                type = "Saving";
            } else if (t == 2) {
                type = "Checking";
            } else if (t == 3) {
                type = "Credit Card";
            } else if (t == 4) {
                type = "LOC";
            }
            else{
                System.out.println("Type the type of Account: 1 : Saving, 2: Checking, 3: Credit 4: Line of Credit");
                t = scanner.nextInt();
            }
        }
        BM.create_account(usr, type);
    }


    private static void displayManagerMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean validselection = false;
        while (!validselection){
            System.out.println("Select an option:");
            System.out.println("1. Create User");
            System.out.println("2. Create Account");
            System.out.println("3. Check Alerts");
            System.out.println("4. Restock Machine");
            System.out.println("5. Undo transaction");
            System.out.println("6. Logout");
            String option = scanner.next();
            switch (option) {
                case "1": {
                    System.out.println("Type the username for the new user");
                    String username = scanner.next();
                    System.out.println("Type the password for the new user");
                    String password = scanner.next();
                    validselection = true;
                }
                case "2": {
                    System.out.println("Type the type of Account: 1 : Savings, 2: Checking, 3: Credit 4: Line of Credit");
                }
                case "3": {

                }
                case "4": {
                    System.out.println("Set which dollar bill amount to 100?");
                    System.out.println("1. Five dollars, 2. Ten dollars, 3. Twenty dollars, 4. Fifty dollars 5. Quit menu");
                    String dollarType = scanner.next();
                    if (dollarType.equals("1")) {
                        BankManager.restock(1);
                    } else if (dollarType.equals("2")) {
                        BankManager.restock(2);
                    } else if (dollarType.equals("3")) {
                        BankManager.restock(3);
                    } else if (dollarType.equals("4")) {
                        BankManager.restock(4);
                    } else if (dollarType.equals("5")) {
                        BankManager.restock(5);
                    } else {
                        System.out.println("There is no option " + dollarType + ". Pick a number from 1 to 6.");
                    }

                }
                case "5": {

                }
                case "6": {

                }
                default: {
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 6.");
                }
            }
        }



    }

    private static String displayLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome. Please login.");
            User loginUser;
            System.out.println("Username: ");
            String usernameAttempt = scanner.next();
            System.out.println("Password: ");
            String passwordAttempt = scanner.next();
            if (usernameAttempt.equals("manager") && passwordAttempt.equals("password")) {
                System.out.println("Login Successful. Logging in as bank manager");
                return "manager";
            } else {
                for (User usr : listOfUsers) {
                    if (usr.getUsername().equals(usernameAttempt) && usr.getPassword().equals(passwordAttempt)) {
                        loginUser = usr;
                        System.out.println("Login Successful. Logging into " + loginUser.getUsername());
                        return loginUser.getUsername();
                    }
                }
            }

        System.out.println("Login Failed, please try again");
        return "";
    }


    /** set the number of bills at array index "bill" */
    public static void set_bills(int bill, int number){
        bills[bill] = number;
    }

    public static void add_bills(int bill, int number){
        bills[bill] += number;
    }


    public void processRequest() {

    }

    public void chooseAccount() {

    }
    public static void addUserToList(User u){
        listOfUsers.add(u);
    }

    /**Alerts the manager when the amount of any denomination goes below 20.*/
    public void alertManager() {
        for(int i =0; i<=3; i++){
            if(bills[i] < 20){BankManager.restock(i);} // STILL NEEDS TO SEND A MESSAGE TO THE ALERT FILE
        }
    }

    private void Shutdown(String Dir){
        String Storeloc = Dir + "/users";
        File f = new File(Storeloc);



        for (User usr: listOfUsers){
            String filename = usr.getUsername() + ".txt";
            File userfile = new File(Storeloc +"/" + "filename");
            if ( !userfile.exists() )
                userfile.createNewFile();
            FileWriter Fw = new FileWriter(userfile, false);
            BufferedWriter writer = new BufferedWriter( Fw );
            writer.write("Username,"+usr.getUsername());
            writer.write("Password,"+usr.getPassword());
            String last;
            for (Account act: usr.accounts){
                String header = act.type + "," + act.accountNum + "," + act.balance;
                if (act.lastTransaction.Type.equals("withdraw") || act.lastTransaction.Type.equals("deposit")){
                    last = act.lastTransaction.Type + "," + act.lastTransaction.Amount;
                } else if (act.lastTransaction.Type.equals("TransferIn") || act.lastTransaction.Type.equals("TransferOut")){
                    last = act.lastTransaction.Type + "," + act.lastTransaction.Account.accountNum + "," + act.lastTransaction.Amount;
                } else if (act.lastTransaction.Type.equals("Paybill")){
                    last = "Paybill," + act.lastTransaction.billname + "," +act.lastTransaction.Amount;
                } else{
                    last = "None";
                }
                writer.write(header);
                writer.write(last);

            }

        }

    }

    private void Restart(){

    }
}



