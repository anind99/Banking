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
        debugSetup();
        testBoot();
//        setup();
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


    public static void shutdown(){
        String Storeloc = "Text Files/users";
        for (User usr : ATM.getListOfUsers()) {
            String filename = usr.getUsername() + ".txt";
            File userfile = new File(Storeloc + "/" + filename);
            boolean success = true;
            if (!userfile.exists())
                try {
                    success = userfile.createNewFile();
                } catch (Exception e) {
                    System.out.println("error creating file");
                    assert(false);
                }

            if (success) {
                try{
                    FileWriter Fw = new FileWriter(userfile, false);
                    BufferedWriter writer = new BufferedWriter(Fw);
                    writer.write("Username," + usr.getUsername());
                    writer.newLine();
                    writer.write("Password," + usr.getPassword());
                    writer.newLine();
                    System.out.println(usr.getUsername());
                    String last = "None";

                    for (Account act : usr.accounts) {

                        Date date = act.dateCreated.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate=dateFormat.format(date);

                        String header = act.type + "," + act.accountNum + "," + act.balance + ","+formattedDate+",";
                        if (act.lastTransaction == null){
                            last = "None";
                        }
                        else if (act.lastTransaction.Type.equalsIgnoreCase("withdraw") || act.lastTransaction.Type.equalsIgnoreCase("deposit")) {
                            last = act.lastTransaction.Type + "," + act.lastTransaction.Amount + ",";
                        } else if (act.lastTransaction.Type.equalsIgnoreCase("TransferIn") || act.lastTransaction.Type.equalsIgnoreCase("TransferOut")) {
                            last = act.lastTransaction.Type + "," + act.lastTransaction.Account + "," + act.lastTransaction.Amount + ",";
                        } else if (act.lastTransaction.Type.equalsIgnoreCase("Paybill")) {
                            last = "Paybill," + act.lastTransaction.billname + "," + act.lastTransaction.Amount +",";
                        }

                        writer.write(header);
                        writer.newLine();
                        writer.write(last);
                        writer.newLine();



                    }
                    writer.close();
                    assert(true);
                }
                catch(Exception e) {
                    System.out.println("error writing to file");
                    assert(false);
                }

            }

        }

    }



    public static void Restart(){


        String dir = "Text Files/users";
        File directory = new File(dir);
        File[] directoryListing = directory.listFiles();
        User n;
        try {
            for (File child : directoryListing) {
                FileReader fr = new FileReader(child);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                ArrayList<Account> usracts = new ArrayList<>();
                String name = null;
                String pass = null;

                if (line.split(",")[0].equals("Username")) {
                    name = line.split(",")[1];
                    line = br.readLine();
                    System.out.println("done: " +name);
                    if (line.split(",")[0].equals("Password")) {
                        pass = line.split(",")[1];
                        System.out.println("done pass: "+pass);
                    }
                }
                boolean format = false;
                if (name != null && pass != null){
                    format = true;
                }

                while(line != null && format){
                    try{
                        line = br.readLine();
                        boolean infoaquired = false;
                        boolean transaction = false;
                        String acttype = null;
                        Double actbal = null;
                        int actnum = 0;
                        Calendar dat = null;
                        Transaction t = null;
                        boolean primary = false;

                        System.out.println(line.split(",").length);
                        if (line.split(",").length == 4 || line.split(",").length == 5) {
                            if (line.split(",").length == 5){
                                primary = Boolean.getBoolean(line.split(",")[4]);
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            acttype = line.split(",")[0];
                            actbal = Double.parseDouble(line.split(",")[2]);
                            actnum = Integer.parseInt(line.split(",")[1]);
                            String Dat = line.split(",")[3];
                            dat = Calendar.getInstance();
                            dat.setTime(sdf.parse(Dat));
                            System.out.println("done account creation: " + acttype + " Balance: " + actbal + " Num: " + actnum +" "+dat.getTime());
                            infoaquired = true;
                        }
                        line = br.readLine();

                        if (line.split(",").length == 3 || line.split(",").length == 2){
                            String Type = line.split(",")[0];
                            String billname;

                            if (Type.equalsIgnoreCase("Paybill")) {
                                billname = line.split(",")[1];
                                Double Amount = Double.parseDouble(line.split(",")[2]);
                                t = new Transaction(billname, Amount);
                                t.billname = billname;
                                t.Amount = Amount;
                                transaction = true;

                            } else if (Type.equalsIgnoreCase("TransferIn") || Type.equalsIgnoreCase("TransferOut")) {
                                int transfernum = Integer.parseInt(line.split(",")[1]);
                                Double Amount = Double.parseDouble(line.split(",")[2]);
                                t = new Transaction(transfernum, Amount, Type);
                                transaction = true;

                            } else if (Type.equalsIgnoreCase("Withdraw") || Type.equalsIgnoreCase("Deposit")) {
                                Double Amount = Double.parseDouble(line.split(",")[1]);
                                t = new Transaction(Amount, Type);
                                transaction = true;
                            }
                            System.out.println("Transaction creation: " +transaction+" "+Type);

                        }
                        if (transaction && infoaquired){
                            Account userAct;
                            if (acttype.equalsIgnoreCase("CreditCard")) {
                                userAct = new CreditCard(actnum);
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            } else if (acttype.equalsIgnoreCase("LOC")) {
                                userAct = new LOC(actnum);
                                userAct.type = "LOC";
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            } else if (acttype.equalsIgnoreCase("Savings")) {
                                userAct = new Savings(actnum);
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            } else if (acttype.equalsIgnoreCase("Chequing")) {
                                userAct = new Chequing(actnum, primary);
                                userAct.balance = actbal;
                                userAct.dateCreated = dat;
                                userAct.lastTransaction = t;
                                usracts.add(userAct);
                            }
                            System.out.println("done adding account to user array: "+acttype);

                        }

                    }catch(Exception e){
                        System.out.println("end of file reached");
                        format = false;
                    }
                }
                br.close();
                if (name != null && pass != null && usracts.size() != 0){
                    n = new User(name, pass, usracts);
                    System.out.println("user created: "+n.getUsername()+" number of accounts: "+n.getAccounts().size());
                    ATM.listOfUsers.add(n);
                }
            }

        } catch(Exception e){
            System.out.println("could not read");
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



