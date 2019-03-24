package atm;

import java.io.*;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;
import java.util.Date;

public class ATM  {
    

    /** Stores the total amount of the bills in the ATM in an array with the following order:
     [5 dollar bills, 10, dollar bills, 20 dollar bills, 50 dollar bills]. */

    private static int[] bills = new int[4];
    private static ArrayList<User> listOfUsers = new ArrayList<User>();
    private static BankManager BM = new BankManager();
    private static Calendar date = Calendar.getInstance();

    public ATM() {
        bills[0] = 100;
        bills[1] = 100;
        bills[2] = 100;
        bills[3] = 100;
    }

    public static void main(String[] arg){
        boolean running = true;
        testBoot();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Booting on " + sdf.format(date.getTime()));
        addSavingsInterest();
        while (running){
            String username = BankManagerInterface.displayLoginMenu();
            if (!username.equals("manager")){
                for (User usr : listOfUsers) {
                    if (usr.getUsername().equals(username)) {
                        UserInterface.displayUserMenu(usr);
                        break;
                    }
                }

            } else if (username.equals("manager")) {
                BankManagerInterface.displayManagerMenu();
            }
        }
    }

    private static void addSavingsInterest(){
        if (date.get(Calendar.DAY_OF_MONTH) == 1){
            for (User user : listOfUsers){
                ArrayList<Account> listOfAccounts = user.getAccounts();
                System.out.println(user.getUsername());
                for (Account account: listOfAccounts){
                    if (account instanceof Savings){
                        ((Savings)account).addInterest();
                    }
                }
            }
        }

    }


    protected static void dateIncrement(){
        boolean done;
        try {
            File file = new File("date.txt");
            done = file.delete();
            done = file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
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

    public static Calendar getDate(){
        return (Calendar) date.clone();
    }


    /** set the number of bills at array index "bill" */
    public static void set_bills(int bill, int number){
        bills[bill - 1] = number;
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
            rounded -= bills[3]*50;
            System.out.println("You have received " + bills[3] + " 50$ bills");
            set_bills(3, 0);
        }else{int prevRounded = (rounded / 50);
            rounded -= ((rounded / 50)*50);
            remove_bills(3, (rounded / 50));
            System.out.println("You have received " + (prevRounded) + " 50$ bills");}

        if(rounded / 20 > bills[2]) {
            rounded -= bills[2]*20;
            System.out.println("You have received " + bills[2] + " 20$ bills");
            set_bills(2, 0);
        }else{int prevRounded = (rounded / 20);
            rounded -= ((rounded / 20)*20);
            remove_bills(2, (rounded / 20));
            System.out.println("You have received " + (prevRounded) + " 20$ bills");
        }

        if(rounded / 10 > bills[1]) {
            rounded -= bills[1]*10;
            System.out.println("You have received " + bills[1] + " 10$ bills");
            set_bills(1, 0);
        }else{int prevRounded = (rounded / 10);
            rounded -= ((rounded / 10)*10);
            remove_bills(1, (rounded / 10));
            System.out.println("You have received " + (prevRounded) + " 10$ bills");}

        remove_bills(0, (rounded / 5));
        System.out.println("You have received " + (rounded / 5) + " 5$ bills");
    }

    public static void addUserToList(User u){
        ATM.getListOfUsers().add(u); }

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
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/alerts.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            if(!fiveBills){w.write("ALERT 5$ bills are low \n");}
            if(!tenBills){w.write("ALERT 10$ bills are low \n");}
            if(!twentyBills){w.write("ALERT 20$ bills are low \n");}
            if(!fiftyBills){w.write("ALERT 50$ bills are low \n");}
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file alert.txt");
        }
    }




    public static ArrayList<User> getListOfUsers(){
        return listOfUsers;
    }


    protected static BankManager getBM(){
        return BM;
    }

    protected static void testShutDown(){
        dateIncrement();
        try {
            File file = new File("serialized.blob");
            file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bills);
            oos.writeObject(listOfUsers);
            oos.writeObject(BM);
            oos.writeObject(date);
            oos.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    protected static void testBoot(){
        boolean bool = false;

        try {
            File file = new File("serialized.blob");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            bills = (int[]) ois.readObject();
            listOfUsers = (ArrayList<User>) ois.readObject();
            BM = (BankManager) ois.readObject();
            date = (Calendar) ois.readObject();
            bool = true;
            ois.close();
        }

        catch (FileNotFoundException e){
            System.out.println("System booting up for the first time!");
            if (bool) {
                System.out.println("....exiting here because this should never happen");
                System.exit(-1);
            }

        }

        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }




}



