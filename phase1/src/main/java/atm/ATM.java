package atm;

import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    /** Stores the total amount of the bills in the ATM in an array with the following order:
     [5 dollar bills, 10, dollar bills, 20 dollar bills, 50 dollar bills]. */
    private static int[] bills;
    private static ArrayList<User> listOfUsers = new ArrayList<User>();

    public ATM() {
        bills = new int[4];
    }

    public static void main(String[] arg){
        boolean running = true;
        debugSetup();
        while (running){
            String userType = displayLoginMenu();
            if (userType.equals("user")){
                displayUserMenu();
            } else if (userType.equals("Manager")) {
                displayManagerMenu();
            }

        }
    }

    private static void debugSetup(){
        //This function sets up our ATM environment for debugging purposes.
        //Breaking it for merging is fine, but it shouldn't happen.
////        User user1 = new User(String username, String password, ArrayList accounts);
////        user1.setPassword("password");
////        user1.setUsername("user");
//        addUserToList(user1);

    }
    private static void displayUserMenu(User user){
        Scanner scanner = new Scanner(System.in);
        boolean validselection = false;
        while (!validselection){
            System.out.println("Select an option:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer In");
            System.out.println("5. Transfer Out");
            System.out.println("6. Logout");
            String option = scanner.next();
            if (option.equals("1")){
                System.out.println("Type the type of Account: 1 : Savings, 2: Checking, 3: Credit 4: Line of Credit");
                String t = scanner.next();

                System.out.println("Type the password for the new user");
                String password = scanner.next();
                validselection = true;

            } else if (option.equals("2")){

            } else if (option.equals("3")) {

            } else if (option.equals("4")) {

            } else if (option.equals("5")) {

            } else if (option.equals("6")) {
                //Doing nothing works fine here.
            } else {
                System.out.println("There is no option " + option + ". Pick a number from 1 to 6.");
            }
        }

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
            if (option.equals("1")){
                System.out.println("Type the username for the new user");
                String username = scanner.next();
                System.out.println("Type the password for the new user");
                String password = scanner.next();
                validselection = true;

            } else if (option.equals("2")){
                System.out.println("Type the type of Account: 1 : Savings, 2: Checking, 3: Credit 4: Line of Credit");
            } else if (option.equals("3")) {

            } else if (option.equals("4")) {

            } else if (option.equals("5")) {

            } else if (option.equals("6")) {

            } else {
                System.out.println("There is no option " + option + ". Pick a number from 1 to 6.");
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
                        return "user";
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

    public void shutDown() {

    }

    public void restart() {

    }




}