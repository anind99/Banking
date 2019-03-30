package account;

import atm.ATM;
import atm.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Account implements Serializable {

    public String type;
    public final int accountNum;
    public double balance;
    public ArrayList<Transaction> listOfTransactions = new ArrayList<>();
    public Calendar dateCreated;
    private final ATM atm;
    private boolean isJoint = false;
    private ReadAndWrite readAndWrite;


    public Account(int accountNum, ATM atm) {
        this.accountNum = accountNum;
        this.atm = atm;
        this.balance = 0;
        this.dateCreated = this.atm.getDate();
        this.readAndWrite = new ReadAndWrite(atm);
    }

    public double getBalance(){
        return this.balance;
    }

    public boolean getIsJoint() {
        return isJoint;
    }

    public Calendar getDateCreated() {
        return this.dateCreated;
    }

    public void setIsJoint(boolean joined) {
        this.isJoint = joined;
    }

    public void addBalance(double balance) {
        this.balance += balance;
    }

    public void subtractBalance(double balance) {
        this.balance -= balance;
    }

    public ArrayList<Transaction> getListOfTransactions() {
        return this.listOfTransactions;
    }

    public Transaction getLastTransaction() {
        if (listOfTransactions.size() > 0) {
            return this.listOfTransactions.get(listOfTransactions.size() - 1);
        } else {
            return null;
        }
    }

    public String getType(){
        return this.type;
    }

    public int getAccountNum() {
        return this.accountNum;
    }

    public boolean isPrimary() {
        return false;
    }

    public void setPrimary() {}

    abstract void addMoney (double amount);

    abstract boolean removeMoney (double amount);

    public void transferIn(double amount, Account accountFrom) {
        boolean removed = accountFrom.removeMoney(amount);
        if(removed){addMoney(amount);
            Transaction transaction = new Transaction(accountFrom.accountNum, amount, "TransferIn");
            this.listOfTransactions.add(transaction);
            System.out.println("\n" + amount + " has been transferred");}
        else{System.out.println("\nThis transaction i;s not possible: insufficient funds");}

    }

    public void transferOut(double amount, Account accountTo) {
        boolean removed = removeMoney(amount);
        if(removed){accountTo.addMoney(amount);
            Transaction transaction =  new Transaction(accountTo.accountNum, amount, "TransferOut");
            this.listOfTransactions.add(transaction);
            System.out.println("\n" + amount + " has been transferred");}
        else{System.out.println("\nThis transaction is not possible: insufficient funds");}


    }

    public void deposit() {
        Double amount = this.readAndWrite.depositReader();
        addMoney(amount);
        Transaction transaction = new Transaction(amount, "deposit");
        this.listOfTransactions.add(transaction);
    }

    public void withdraw(double amount) {
        if(atm.getBills().getTotalAmount() >= amount) {
            atm.getBills().withdrawBills(amount);
            atm.alertManager();
            removeMoney(amount);
            Transaction transaction = new Transaction(amount, "withdraw");
            System.out.println(this.listOfTransactions);
            this.listOfTransactions.add(transaction);
        }else{System.out.println("\nTransaction not possible: not enough funds in ATM");}

    }

    public void payBill(double amount, String receiver){
        boolean removed = removeMoney(amount);
        if(removed){
            this.readAndWrite.payBillWriting(amount, receiver, accountNum);
            System.out.println("You paid " + amount + " to " + receiver);
        }
        else{System.out.println("\nThis transaction is not possible: insufficient funds");}
        this.listOfTransactions.add(new Transaction(receiver, amount));
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("account writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("account readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("account readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
