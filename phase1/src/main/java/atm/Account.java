package atm;

import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Account {


    public String accountNum;
    protected double balance;
    public Transaction lastTransaction;
    public Date dateCreated;


    public Account(String accountNum) {
        this.accountNum = accountNum;
        this.balance = 0;
        this.lastTransaction = null;
        this.dateCreated = new Date();

    }

    public double getBalance(){
        return this.balance;
    }

    public void transferIn(double amount, Account accountFrom) {
        addMoney(amount);
        accountFrom.removeMoney(amount);
    }

    public void transferOut(double amount, Account accountTo) {
        removeMoney(amount);
        accountTo.addMoney(amount);
    }

    public void deposit() {
        amount =

        addMoney(amount);
    }

    public void withdraw(double amount) {
        removeMoney(amount);
    }

    public void addMoney (double amount){
    }

    public void removeMoney (double amount){
    }

    public void payBill(double amount, String receiver){
        removeMoney(amount);
        payBillWriting(amount, receiver);
    }

    public void payBillWriting(double amount, String receiver) {
        try {
            File file = new File("outgoing.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(accountNum + " payed " + amount + " to" + receiver);
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file outgoing.txt");
        }
    }

}
