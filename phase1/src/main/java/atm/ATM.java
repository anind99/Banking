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


    public void Shutdown(String Dir){
        String Storeloc = Dir + "users.dir";

        for (User usr : listOfUsers) {
            String filename = usr.getUsername() + ".txt";
            File userfile = new File(Storeloc + "/" + filename);
            boolean success = true;
            if (!userfile.exists())
                try {
                    success = userfile.createNewFile();
                } catch (Exception e) {
                    System.out.println("error creating file");
                }

            if (success) {
                try{

                    FileWriter Fw = new FileWriter(userfile, false);
                    BufferedWriter writer = new BufferedWriter(Fw);
                    writer.write("Username," + usr.getUsername());
                    writer.write("Password," + usr.getPassword());
                    String last = "None";

                    for (Account act : usr.accounts) {
                        String header = act.type + "," + act.accountNum + "," + act.balance;
                        if (act.lastTransaction == null){
                            last = "None";
                        }
                        else if (act.lastTransaction.Type.equals("withdraw") || act.lastTransaction.Type.equals("deposit")) {
                            last = act.lastTransaction.Type + "," + act.lastTransaction.Amount;
                        } else if (act.lastTransaction.Type.equals("TransferIn") || act.lastTransaction.Type.equals("TransferOut")) {
                            last = act.lastTransaction.Type + "," + act.lastTransaction.Account + "," + act.lastTransaction.Amount;
                        } else if (act.lastTransaction.Type.equals("Paybill")) {
                            last = "Paybill," + act.lastTransaction.billname + "," + act.lastTransaction.Amount;
                        }
                        writer.write(header);
                        writer.write(last);

                    }

                }
                catch(Exception e) {
                    System.out.println("error writing to file");
                }

            }

        }
    }


    public void Restart(String Dir){

        String Storeloc = Dir + "/users";

        File dir = new File(Storeloc);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                try {
                    FileReader fr = new FileReader(child);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    ArrayList<Account> usracts = new ArrayList<Account>();
                    String name = null;
                    String pass = null;

                    if (line.split(",")[0].equals("Username")) {
                        name = line.split(",")[1];
                        line = br.readLine();
                        if (line.split(",")[0].equals("Password")) {
                            pass = line.split(",")[1];
                        }
                    }

                    line = br.readLine();
                    boolean format = false;
                    if (name != null && pass != null){
                        format = true;
                    }
                    while (line != null && format) {

                        try{

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String acttype = line.split(",")[0];
                            int actbal = Integer.parseInt(line.split(",")[2]);
                            int actnum = Integer.parseInt(line.split(",")[1]);
                            String Dat = line.split(",")[3];
                            Calendar dat = Calendar.getInstance();
                            dat.setTime(sdf.parse(Dat));

                            line = br.readLine();

                            String Type = line.split(",")[0];
                            Transaction t = null;
                            String billname;

                            if (Type.equals("Payblil")) {
                                billname = line.split(",")[1];
                                Double Amount = Double.parseDouble(line.split(",")[2]);
                                t = new Transaction(billname, Amount);
                            } else if (Type.equals("TransferIn") || Type.equals("TransferOut")) {
                                int transfernum = Integer.parseInt(line.split(",")[1]);
                                Double Amount = Double.parseDouble(line.split(",")[2]);
                                t = new Transaction(transfernum, Amount, Type);

                            } else if (Type.equals("Withdraw") || Type.equals("Deposit")) {
                                Double Amount = Double.parseDouble(line.split(",")[1]);
                                t = new Transaction(Amount, Type);
                            }

                            Account userAct;
                            if (acttype.equals("Credit")) {
                                userAct = new CreditCard(actnum);
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            } else if (acttype.equals("LOC")) {
                                userAct = new LOC(actnum);
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            } else if (acttype.equals("Saving")) {
                                userAct = new Savings(actnum);
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            } else if (acttype.equals("Chequing")) {
                                userAct = new Chequing(actnum);
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            }

                        } catch (Exception e){
                            format = false;
                        }
                    }

                    if (format){
                        User usr = new User(name, pass, usracts);
                        listOfUsers.add(usr);
                    } else{
                        System.out.println("Wrong format - file "+child.getName());
                    }

                } catch(Exception e){
                    System.out.println("error reading file: " + child.getName());
                }
            }


        } else {
            System.out.println("not a directory");
        }

    }



    public static ArrayList<User> getListOfUsers(){
        return listOfUsers;
    }


    protected static BankManager getBM(){
        return BM;
    }

}



