package account;

import atm.ATM;
import atm.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Abstract class containing all the shared methods between all types of accounts.
 */
public abstract class Account implements Serializable {

    public String type;
    public final int accountNum;
    public double balance;
    public ArrayList<Transaction> listOfTransactions = new ArrayList<>();
    public Calendar dateCreated;
    private final ATM atm;
    private boolean isJoint = false;
    private ReadAndWrite readAndWrite;

    /**
     * Account constructor.
     * @param accountNum  assigns a unique account number to each account
     * @param atm an instance of the ATM
     */
    public Account(int accountNum, ATM atm) {
        this.accountNum = accountNum;
        this.atm = atm;
        this.balance = 0;
        this.dateCreated = this.atm.getDate();
        this.readAndWrite = new ReadAndWrite(atm);
    }

    /**
     * Returns the balance of the account.
     *
     * @return the balance of the account
     */

    public double getBalance(){
        return this.balance;
    }

    /**
     * Returns True if the account is a joint account and False if it is not.
     *
     * @return True if account is joint, False if it is not joint
     */
    public boolean getIsJoint() {
        return isJoint;
    }

    /**
     * Returns the date that the account was created.
     *
     * @return date when account was created
     */
    public Calendar getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Updates the joint status of an account.
     * @param joined a boolean that is true when the account is joint and false otherwise
     */
    public void setIsJoint(boolean joined) {
        this.isJoint = joined;
    }

//    /**
//     * Increases the balance of an account by dollarAmount.
//     * @param dollarAmount the dollar amount to increase the balance by
//     */
//    public void addBalance(double dollarAmount) {
//        this.balance += dollarAmount;
//    }

//    /**
//     * Decreases the balance of an account by dollarAmount.
//     * @param dollarAmount the dollar amount to decrease the balance by
//     */
//    public void subtractBalance(double dollarAmount) {
//        this.balance -= dollarAmount;
//    }

    /**
     * Returns the list of all transactions ever performed using the account.
     * @return listofTransactions  a list of Transaction objects
     * @see Transaction
     */
    public ArrayList<Transaction> getListOfTransactions() {
        return this.listOfTransactions;
    }

    /**
     * Returns the most recent transaction performed using the account.
     * @return the last Transaction object  in the listofTransactions
     * for an account or null if no transactions have been performed yet
     */
    public Transaction getLastTransaction() {
        if (listOfTransactions.size() > 0) {
            return this.listOfTransactions.get(listOfTransactions.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Returns the type of the account (i.e Chequing, Savings, etc.).
     * @return type a String that states the type of the account
     */

    public String getType(){
        return this.type;
    }

    /**
     * Returns the account number.
     * @return accountNum a unique number assigned to each account
     */
    public int getAccountNum() {
        return this.accountNum;
    }

    /**
     * Returns a boolean stating if an account is the primary account of a user.
     * @return primaryStatus a boolean that is true if the account is the user's primary account and false otherwise
     */
    public boolean isPrimary() {
        boolean primaryStatus = false;
        return primaryStatus;
    }

    /**
     * Updates the primaryStatus of an account.
     */
    public void setPrimary() {}

    /**
     *
     * @param amount
     */
    public abstract void addMoney (double amount);

    public abstract boolean removeMoney (double amount);

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
