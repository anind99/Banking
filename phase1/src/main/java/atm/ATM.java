package atm;

import java.io.*;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ATM {

    /** Stores the total amount of the bills in the ATM in an array with the following order:
     [5 dollar bills, 10, dollar bills, 20 dollar bills, 50 dollar bills]. */
    private static int[] bills = new int[4];
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
        boolean done;
        try {
            File file = new File("date.txt");
            done = file.delete();
            done = file.createNewFile();
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
                System.out.println("Booting system on date " + line);
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

    public static Calendar getDate() {
        return date;
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
            System.out.println("7. Request Account Creation");
            System.out.println("8. View Summary of Accounts");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");
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
            } else if (option.equals("10")) {
                changePassword(user);
            } else if (option.equals("9")) {
                //Doing nothing works fine here.
                logout = true;
            } else {
                System.out.println("There is no option " + option + ". Pick a number from 1 to 10.");
            }
        }

    }


    private static void changePassword(User user) {
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



    private static void CreateAccount(User usr){
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
                    BM.create_user(username, password);
                    validselection = true;
                }
                case "2": {
                    User user = null;
                    while (user == null) {
                        System.out.println("Type in the username of the user that would like to create an account: ");
                        String username = scanner.nextLine();
                        for (User parameter : listOfUsers) {
                            if (parameter.getUsername().equals(username)) {
                                user = parameter;
                                break;
                            }
                        }
                        System.out.println("The username is not valid, please try again.");
                    }

                    CreateAccount(user);
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
                    User user = null;
                    while (user == null) {
                        System.out.println("Type in the username of the user that would like to undo their last transaction: ");
                        String username = scanner.nextLine();
                        for (User parameter : listOfUsers) {
                            if (parameter.getUsername().equals(username)) {
                                user = parameter;
                                break;
                            }
                        }
                        System.out.println("The username is not valid, please try again.");
                    }

                    Account account = selectAccount(user, "undo its last transaction");
                    BM.undo_transaction(account);

                }
                case "6": {
                    validselection = true;
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

    public static double get_amount(){
       return (bills[0]*5.0 + bills[1]*10.0 + bills[2]*20.0 + bills[3]* 50.0);
    }

    public static void add_bills(int bill, int number){
        bills[bill] += number;
    }

    public static void remove_bills(int bill, int number){
        bills[bill] -= number;
    }

    public static void withdrawBills(double amount){
        int rounded = (int) amount;

        if(rounded / 50 > bills[3]) {
            rounded -= bills[3];
            set_bills(3, 0);
            System.out.println("You have received " + bills[3] + " 50$ bills");
        }else{rounded -= ((rounded / 50)*50);
           remove_bills(3, (rounded / 50));
            System.out.println("You have received " + (rounded / 50) + " 50$ bills");}

        if(rounded / 20 > bills[2]) {
            rounded -= bills[2];
            System.out.println("You have received " + bills[2] + " 20$ bills");
            set_bills(2, 0);
        }else{rounded -= ((rounded / 20)*20);
            remove_bills(2, (rounded / 20));
            System.out.println("You have received " + (rounded / 20) + " 20$ bills");
        }

        if(rounded / 10 > bills[1]) {
            rounded -= bills[1];
            System.out.println("You have received " + bills[1] + " 10$ bills");
            set_bills(1, 0);
        }else{rounded -= ((rounded / 10)*10);
            remove_bills(1, (rounded / 10));
            System.out.println("You have received " + (rounded / 10) + " 10$ bills");}

        remove_bills(5, (rounded / 5));
        System.out.println("You have received " + (rounded / 55) + " 5$ bills");
    }

    public static void addUserToList(User u){
        listOfUsers.add(u);
    }

    /**Alerts the manager when the amount of any denomination goes below 20.*/
    public static void alertManager() {
        boolean fiveBills = true;
        boolean tenBills = true;
        boolean twentyBills = true;
        boolean fiftyBills = true;

        if(bills[0]*5 < 20){fiveBills = false;}
        if(bills[1]*10 < 20){tenBills = false;}
        if(bills[2]*20 < 20){twentyBills = false;}
        if(bills[3]*50 < 20){fiftyBills = false;}

        try {
            //System.out.println(System.getProperty("user.dir"));
            File file = new File(System.getProperty("user.dir") + "/Text Files/alerts.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            if(!fiveBills){w.write("ALERT 5$ bills are low");}
            if(!tenBills){w.write("ALERT 10$ bills are low");}
            if(!twentyBills){w.write("ALERT 20$ bills are low");}
            if(!fiftyBills){w.write("ALERT 50$ bills are low");}
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file alert.txt");
        }
    }


    private void Shutdown(String Dir){
        String Storeloc = Dir + "users.dir";
        File f = new File(Storeloc);

        try {
            for (User usr : listOfUsers) {
                String filename = usr.getUsername() + ".txt";
                File userfile = new File(Storeloc + "/" + filename);
                boolean success;
                    if (!userfile.exists())
                        success = userfile.createNewFile();
                FileWriter Fw = new FileWriter(userfile, false);
                BufferedWriter writer = new BufferedWriter(Fw);
                writer.write("Username," + usr.getUsername());
                writer.write("Password," + usr.getPassword());
                String last;
                for (Account act : usr.accounts) {
                    String header = act.type + "," + act.accountNum + "," + act.balance;
                    if (act.lastTransaction.Type.equals("withdraw") || act.lastTransaction.Type.equals("deposit")) {
                        last = act.lastTransaction.Type + "," + act.lastTransaction.Amount;
                    } else if (act.lastTransaction.Type.equals("TransferIn") || act.lastTransaction.Type.equals("TransferOut")) {
                        last = act.lastTransaction.Type + "," + act.lastTransaction.Account.accountNum + "," + act.lastTransaction.Amount;
                    } else if (act.lastTransaction.Type.equals("Paybill")) {
                        last = "Paybill," + act.lastTransaction.billname + "," + act.lastTransaction.Amount;
                    } else {
                        last = "None";
                    }
                    writer.write(header);
                    writer.write(last);

                }

            }
        } catch(Exception e) {
            System.out.println("error");
        }

    }

    private void Restart(String Dir){

        String Storeloc = Dir + "/users";

        File dir = new File(Storeloc);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                try {
                    FileReader fr = new FileReader(child);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    while (line != null) {
                        if (line.split(",")[0].equals("Username")) {
                            String name = line.split(",")[1];
                            line = br.readLine();
                            String pass = line.split(",")[1];
                            ArrayList<Account> usracts = new ArrayList<Account>();
                            line = br.readLine();
                            if (!line.split(",")[0].equals("Username")) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String acttype = line.split(",")[0];
                                String actbal = line.split(",")[2];
                                String actnum = line.split(",")[1];
                                String Dat = line.split(",")[3];
                                Calendar dat = Calendar.getInstance();
                                dat.setTime(sdf.parse(Dat));
                                line = br.readLine();
                                
                            }
                        }
                    }

                } catch(Exception e){
                    System.out.println("error");
                }
            }
        } else {
            System.out.println("not a directory");
        }

    }
}



