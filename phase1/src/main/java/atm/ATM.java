package atm;

import account.*;
import bankmanager.*;
import interfaces.*;
import investments.*;

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
    private final Interface interfaces;
    private final Broker broker = new Broker(this, BM);

    public ATM() {
        this.interfaces = new Interface(this);
        bills = new Bills(100, 100, 100, 100);
    }

    public Bills getBills() {
        return bills;
    }

    public BankManager getBM(){
        return BM;
    }

    public Broker getBroker() {
        return broker;
    }

    public Calendar getDate(){
        return (Calendar) date.clone();
    }

    public ArrayList<User> getListOfUsers(){
        return listOfUsers;
    }

    public User getUser(String username) {
        for (User usr : listOfUsers) {
            if (usr.getUsername().equals(username)) {
                return usr;
            }
        }
        // This will never happen because we have a previous function that already checks if the user is in the
        // listOfUser in Interface class.
        return null;
    }

    public void run(){
        boolean running = true;
        testBoot();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Booting on " + sdf.format(date.getTime()));
        addSavingsInterest();
        while (running){
            String username = interfaces.displayLoginMenu();
            if (username.equals("manager")) {
                interfaces.displayManagerMenu(BM);
            } else if (username.equals("broker")) {
                interfaces.displayBrokerOrUserChoice(broker);
            } else {
                interfaces.displayUserMenu(getUser(username));
            }
        }
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

    public void setDate(String sdfFormattedDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date.setTime(sdf.parse(sdfFormattedDate));
        } catch (ParseException e){
            System.out.println("Setdate Parse Exception at ATM, this should never happen!");
            System.exit(-1);
        }
    }

    public void addUserToList(User u){
        getListOfUsers().add(u);
    }

    public void testShutDown(){
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