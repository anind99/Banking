package atm;

import java.io.*;
import java.io.File;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ATM implements Serializable {


    /** Stores the total amount of the bills in the ATM in an array with the following order:
     [5 dollar bills, 10, dollar bills, 20 dollar bills, 50 dollar bills]. */

    private Bills bills;
    private ArrayList<User> listOfUsers = new ArrayList<User>();
    private BankManager BM = new BankManager(this);
    private Calendar date = Calendar.getInstance();
    private final UserInterface UserInterface;
    private final BankManagerInterface BankManagerInterface;
    private final Broker broker = new Broker();
    private final BrokerInterface brokerInterface = new BrokerInterface();

    public ATM() {
        this.UserInterface = new UserInterface(this);
        this.BankManagerInterface = new BankManagerInterface(this);
        bills = new Bills(100, 100, 100, 100);
    }

    Bills getBills() {
        return bills;
    }

    BankManager getBM(){
        return BM;
    }

    public void run(){
        boolean running = true;
        testBoot();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Booting on " + sdf.format(date.getTime()));
        addSavingsInterest();
        // Creating a user account for broker.
        BM.create_user("broker", "password");
        while (running){
            String username = BankManagerInterface.displayLoginMenu();
            if (username.equals("manager")) {
                BankManagerInterface.displayManagerMenu(BM, this);
            } else if (username.equals("broker")) {
                brokerInterface.displayBrokerOrUserChoice(broker, this, UserInterface);
            } else {
                UserInterface.displayUserMenu(getUser(username));
            }
        }
    }

    User getUser(String username) {
        for (User usr : listOfUsers) {
            if (usr.getUsername().equals(username)) {
                return usr;
            }
        }
        // This will never happen because we have a previous function that already checks if the user is in the
        // listOfUser in Interface class.
        return null;
    }

    private void addSavingsInterest(){
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

    Calendar getDate(){
        // Has to be package-private.
        return (Calendar) date.clone();
    }

    void setDate(String sdfFormattedDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date.setTime(sdf.parse(sdfFormattedDate));
        } catch (ParseException e){
            System.out.println("Setdate Parse Exception at ATM, this should never happen!");
            System.exit(-1);
        }
    }

    void addUserToList(User u){
        // Has to be package-private.
        getListOfUsers().add(u); }

    /**Alerts the manager when the amount of any denomination goes below 20.*/
    void alertManager() {
        boolean fiveBills = true;
        boolean tenBills = true;
        boolean twentyBills = true;
        boolean fiftyBills = true;

        if(bills.getNumBills(0)*5 < 20){fiveBills = false;}
        if(bills.getNumBills(1)*10 < 20){tenBills = false;}
        if(bills.getNumBills(2)*20 < 20){twentyBills = false;}
        if(bills.getNumBills(3)*50 < 20){fiftyBills = false;}

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

    ArrayList<User> getListOfUsers(){
        // Has to be package-private.
        return listOfUsers;
    }

    void testShutDown(){
        date.add(Calendar.DATE, 1);
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

    private void testBoot(){
        boolean bool = false;

        try {
            File file = new File("serialized.blob");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            bills = (Bills) ois.readObject();
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

//    ArrayList<Stock> getQueriedStocks() {
//
//    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("ATM writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("ATM readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("ATM readObjectNoData, this should never happen!");
        System.exit(-1);
    }

    private void test(){
        //will this push?
    }

}