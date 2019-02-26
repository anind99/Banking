package atm;

import java.util.Date;
public class Account {

    public String accountTxt;
    public String accountNum;
    protected double balance;
    public Transaction lastTransaction;
    public Date dateCreated;


    public Account(String accountNum, String accountTxt) {
        this.accountNum = accountNum;
        this.accountTxt = accountTxt;
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

    public void deposit(double amount) {
        addMoney(amount);
    }

    public void withdraw(double amount) {
        removeMoney(amount);
    }

    public void addMoney (double amount){
    }

    public void removeMoney (double amount){
    }

    public void payBill(double amount) {
        removeMoney(amount);


    }

}
