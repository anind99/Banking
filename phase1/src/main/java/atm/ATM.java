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
            displayMenu();

        }
    }

    private static void debugSetup(){
        //This function sets up our ATM environment for debugging purposes.
        //Breaking it for merging is fine, but it shouldn't happen.
        User user1 = new User();
        user1.setPassword("password");
        user1.setUsername("user");
        addUserToList(user1);

    }

    private static void displayMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome. Please login.");
        User loginUser = null;
        boolean loginSuccessful = false;
        while (!loginSuccessful) {
            System.out.println("Username: ");
            String usernameAttempt = scanner.next();
            System.out.println("Password: ");
            String passwordAttempt = scanner.next();
            for (User usr : listOfUsers) {
                if (usr.getUsername().equals(usernameAttempt) && usr.getPassword().equals(passwordAttempt)) {
                    loginUser = usr;
                    loginSuccessful = true;
                }
            }
            if (!loginSuccessful){
                System.out.println("Login Failed, please try again");
            }
        }
        System.out.println("Login Successful. Logging into " + loginUser.getUsername());
    }


    /** set the number of bills at array index "bill" */
    public static void set_bills(int bill, int number){
        bills[bill] = number;
    }


    /** Logs in the user.*/
    public void login(String username, String password) { }


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
            if(bills[i] < 20){BankManager.restock(i);}
        }
    }

    public void shutDown() {

    }

    public void restart() {

    }



}