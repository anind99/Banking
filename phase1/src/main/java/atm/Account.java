package atm;

import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public abstract class Account {


    public int accountNum;
    protected double balance;
    public Transaction lastTransaction;
    public Date dateCreated;


    public Account(int accountNum) {
        this.accountNum = accountNum;
        this.balance = 0;
        this.lastTransaction = null;
        this.dateCreated = new Date();

    }

    public double getBalance(){
        return this.balance;
    }

    abstract void addMoney (double amount);

    abstract boolean removeMoney (double amount);

    public void transferIn(double amount, Account accountFrom) {
        boolean removed = accountFrom.removeMoney(amount);
        if(removed){addMoney(amount);}
        else{System.out.println("This transaction is not possible: insufficient funds");}
    }

    public void transferOut(double amount, Account accountTo) {
        boolean removed = removeMoney(amount);
        if(removed){accountTo.addMoney(amount);}
        else{System.out.println("This transaction is not possible: insufficient funds");}
    }

    public void deposit(int type) {
        amount =

        addMoney(amount);
    }

    public void withdraw(double amount) {
        removeMoney(amount); //override in checking account to allow negative balances
    }

    public void payBill(double amount, String receiver){
        boolean removed = removeMoney(amount);
        if(removed){payBillWriting(amount, receiver);}
        else{System.out.println("This transaction is not possible: insufficient funds");}
    }

    public boolean payBillWriting(double amount, String receiver) {
        try {
            File file = new File("outgoing.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(accountNum + " payed " + amount + " to" + receiver);
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file outgoing.txt");
        } return true;
    }

}
