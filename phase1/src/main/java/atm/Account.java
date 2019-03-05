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
        this.depositNum = 3;

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

        this.lastTransaction = new Transaction(accountFrom, amount, "transferin");
    }

    public void transferOut(double amount, Account accountTo) {
        boolean removed = removeMoney(amount);
        if(removed){accountTo.addMoney(amount);}
        else{System.out.println("This transaction is not possible: insufficient funds");}

        this.lastTransaction = new Transaction(accountTo, amount, "transferout");
    }

    public void deposit() {
        Double amount = depositReader();
        addMoney(amount);

        this.lastTransaction = new Transaction(amount, "deposit");
    }

    public Double depositReader() {
        Double amount;
        try {
            File file = new File("/Users/isabelkerrebijn/Desktop/deposits.txt"); //FIX
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = r.readLine();
            String firstLine = line;

            int count = 0;
            while (line != null && count < depositNum) {
                count += 1;
                line = r.readLine();
            }

            if (line != null && count >= depositNum) {
                depositNum += 1;
            } else {
                line = firstLine;
                System.out.println(line);
                depositNum = 0;
                System.out.println("boo");
            }

            if (line.contains(".")) {
                amount = Double.valueOf(line);
                System.out.println("You have deposited a cheque for $" + amount);
            }else {

                amount = Double.valueOf((Character.getNumericValue(line.charAt(0))) * 5 +
                        Character.getNumericValue(line.charAt(1)) * 10 +
                        Character.getNumericValue(line.charAt(2)) * 20 +
                        Character.getNumericValue(line.charAt(3)) * 50);
                System.out.println(amount);
                //ATM.add_bills(0, Character.getNumericValue(line.charAt(0)));
                //ATM.add_bills(1, Character.getNumericValue(line.charAt(1)));
                // ATM.set_bills(2, Character.getNumericValue(line.charAt(2)));
                //ATM.set_bills(3, Character.getNumericValue(line.charAt(3)));}
                System.out.println("You have deposited $" + amount + " in cash");
            }r.close();
            return amount;
        } catch (IOException e) {
            System.err.println("Problem reading the file deposits.txt");
            return 0.0;
        }
    }


    public void withdraw(double amount) {
        removeMoney(amount);

        this.lastTransaction = new Transaction(amount, "withdraw");
    }

    public void payBill(double amount, String receiver){
        boolean removed = removeMoney(amount);
        if(removed){payBillWriting(amount, receiver);}
        else{System.out.println("This transaction is not possible: insufficient funds");}

        this.lastTransaction = new Transaction(receiver, amount);
    }

    //Helper function to Paybill that adds the information of the paid bill to a text file
    public boolean payBillWriting(double amount, String receiver) {
        try {
            File file = new File("outgoing.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(accountNum + " payed " + amount + " to " + receiver);
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file outgoing.txt");
        } return true;
    }

}
