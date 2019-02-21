package atm;

public class ATM {

    /** Stores the total amount of the bills in the ATM in an array with the following order:
     [5 dollar bills, 10, dollar bills, 20 dollar bills, 50 dollar bills]. */
    private static int[] bills;

    public ATM() {
        bills = new int[4];}


    /** set the number of bills at array index "bill" */
    public static void set_bills(int bill, int number){
        bills[bill] = number;}


    /** Logs in the user.*/
    public void login(String username, String password) { }


    public void processRequest() {

    }

    public void chooseAccount() {

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