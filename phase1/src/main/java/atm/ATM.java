package atm;

import java.io.*;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ATM {

    /** Stores the total amount of the bills in the ATM in an array with the following order:
     [5 dollar bills, 10, dollar bills, 20 dollar bills, 50 dollar bills]. */
    private static int[] bills = new int[4];
    private static ArrayList<User> listOfUsers = new ArrayList<User>();
    private static BankManager BM = new BankManager();

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
        System.out.println(System.getProperty("user.dir"));
        addSavingsInterest();
        while (running){
            String username = BankManagerInterface.displayLoginMenu();
            if (username.equals("user")){
                for (User usr : listOfUsers) {
                    if (usr.getUsername().equals(username)) {
                        UserInterface.displayUserMenu(usr);
                    }
                }

            } else if (username.equals("manager")) {
                BankManagerInterface.displayManagerMenu();
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
        ATM.getListOfUsers().add(u);
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
            if(!fiveBills){w.write("ALERT 5$ bills are low \n");}
            if(!tenBills){w.write("ALERT 10$ bills are low \n");}
            if(!twentyBills){w.write("ALERT 20$ bills are low \n");}
            if(!fiftyBills){w.write("ALERT 50$ bills are low \n");}
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
    protected static ArrayList<User> getListOfUsers(){
        return listOfUsers;
    }

    protected static BankManager getBM(){
        return BM;
    }

}



