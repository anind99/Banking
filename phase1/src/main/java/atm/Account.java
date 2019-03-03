package atm;

import java.io.*;
import java.util.Date;

public abstract class Account {

    public String type = null;
    public int accountNum;
    protected double balance;
    public Transaction lastTransaction;
    public Date dateCreated;
    protected int depositNum;


    public Account(int accountNum) {
        this.accountNum = accountNum;
        this.balance = 0;
        this.lastTransaction = null;
        this.dateCreated = new Date();
        this.depositNum = 0;

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

    public void deposit() {
        double amount = depositReader();
        addMoney(amount);
    }

    public Double depositReader() {
        Double amount = 0.0;
        try {
            File file = new File("outgoing.txt");
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = r.readLine();

            int count = 0;
            while (line != null && count < depositNum) {
                count += 1;
                line = r.readLine();
            }
            if (count >= depositNum) {
                depositNum += 1;
            } else {
                line = r.readLine();
                depositNum = 0;
            }

            if (line.contains(".")) {
                amount = Double.valueOf(line);
            }else{amount = Double.valueOf((Integer.valueOf(line.charAt(0))*5 + Integer.valueOf(line.charAt(1)) * 10 +
                    Integer.valueOf(line.charAt(2))*20 + Integer.valueOf(line.charAt(3))*50));
                ATM.set_bills(0, Integer.valueOf(line.charAt(0)));
                ATM.set_bills(1, Integer.valueOf(line.charAt(1)));
                ATM.set_bills(2, Integer.valueOf(line.charAt(2)));
                ATM.set_bills(3, Integer.valueOf(line.charAt(3)));}
            r.close();
        } catch (IOException e) {
            System.err.println("Problem reading the file deposits.txt");
        }return amount;
    }

    public void withdraw(double amount) {
        removeMoney(amount);
    }

    public void payBill(double amount, String receiver){
        boolean removed = removeMoney(amount);
        if(removed){payBillWriting(amount, receiver);}
        else{System.out.println("This transaction is not possible: insufficient funds");}
    }

    //Helper function to Paybill that adds the information of the paid bill to a text file
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
