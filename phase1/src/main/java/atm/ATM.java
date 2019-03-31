package atm;

import account.*;
import bankmanager.*;
import broker.Broker;
import interfaces.*;
import investments.*;
import subscriptions.Subscriber;
import subscriptions.availableSubscriptions;
import subscriptions.subscription;

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
    private ArrayList<User> listOfUsers;
    private BankManager BM;
    private Calendar date;
    private Interface interfaces;
    private Broker broker;
    private availableSubscriptions subscriptions;
    private Subscriber subscriber;

    public ATM() {
        this.interfaces = new Interface(this);
        this.BM = new BankManager(this);
        this.listOfUsers = new ArrayList<User>();
        this.date = Calendar.getInstance();
        this.date.add(Calendar.YEAR, -3);
        this.broker = new Broker(this, BM);
        this.subscriptions = new availableSubscriptions();
        this.subscriber = new Subscriber(this);
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

    public availableSubscriptions getSubscriptions(){
        return this.subscriptions;
    }

    public Subscriber getSubscriber(){
        return this.subscriber;
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

    public void shutDown(){
        date.add(Calendar.DATE, 1);
        try {
            File file = new File("serialized.blob");
            file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        } catch (IOException e){
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


}