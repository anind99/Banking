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
     * Adds money to the account and updates the balance accordingly.
     * @param amount dollar amount added into the account
     */
    public abstract void addMoney (double amount);

    /**
     * Removes money from the account and updates the balance accordingly.
     * @param amount
     */
    public abstract void removeMoney (double amount);

    /**
     * Checks if there is sufficient funds in an account.
     * @param amount sufficient amount of funds needed
     * @return a boolean, false if the account's balance is less than the amount we are checking for and true otherwise
     */
    public boolean checkFundsSufficient(double amount){
        if (this.balance < amount){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Transfers money into the account from another account
     * and prints the result of the transaction.
     * @param amount the dollar amount being transferred
     * @param accountFrom the account the money is being transferred from
     */
    public void transferIn(double amount, Account accountFrom) {
        boolean sufficientFunds = accountFrom.checkFundsSufficient(amount);
        if(sufficientFunds){
            addMoney(amount);
            updateTransactionList(new Transaction(accountFrom.accountNum, amount, "TransferIn"));
            System.out.println("\n" + amount + " has been transferred");}
        else{
            System.out.println("\nThis transaction i;s not possible: insufficient funds");
        }
    }

    /**
     * Transfers money out of the account into another account
     * and prints the result of the transaction.
     * @param amount
     * @param accountTo
     */
    public void transferOut(double amount, Account accountTo) {
        boolean sufficientFunds = checkFundsSufficient(amount);
        if(sufficientFunds){
            accountTo.addMoney(amount);
            updateTransactionList(new Transaction(accountTo.accountNum, amount, "TransferOut"));
            System.out.println("\n" + amount + " has been transferred");}
        else{
            System.out.println("\nThis transaction is not possible: insufficient funds");
        }
    }

    /**
     * Deposits money into the account by reading the amount of money being deposited
     * from deposit.txt using an instance of ReadAndWrite class and updates the
     * transaction list of the account.
     * @see ReadAndWrite
     */
    public void deposit() {
        Double amount = this.readAndWrite.depositReader();
        addMoney(amount);
        //updateTransactionList(new Transaction(amount, "deposit"));
    }

    /**
     * Withdraws money from the account if there is enough money
     * in the account and the ATM has enough dollar bills to dispense.
     * @param amount the amount being withdrawn from the account
     */
    public void withdraw(double amount) {
        if(atm.getBills().getTotalAmount() >= amount && checkFundsSufficient(amount)) {
            removeMoney(amount);
            atm.getBills().withdrawBills(amount);
            //atm.getBills().alertManager();
            updateTransactionList(new Transaction(amount, "withdraw"));
            System.out.println(this.listOfTransactions);
        }
        else{
            System.out.println("\nTransaction not possible: not enough funds in ATM");
        }
    }

    /**
     * Pays a bill by transferring money from the account to an external account.
     * <p>
     * Reads the details of the bill to be paid from outgoing.txt using an instance
     * of the atm.ReadAndWrite class and if funds sufficient pays the bill.
     * @param amount the bill amount
     * @param receiver the external account the money is being paid to
     */
    public void payBill(double amount, String receiver){
        boolean sufficientFunds = checkFundsSufficient(amount);
        if(sufficientFunds){
            this.readAndWrite.payBillWriting(amount, receiver, accountNum);
            System.out.println("You paid " + amount + " to " + receiver);
        }
        else{
            System.out.println("\nThis transaction is not possible: insufficient funds");
        }
        updateTransactionList(new Transaction(receiver, amount));
    }

    public void updateTransactionList(Transaction transaction){
        this.listOfTransactions.add(transaction);
    }

    /**
     * Used in serialization to store the Account object.
     * @param oos instance of the ObjectOutputStream class to write the account object
     * @throws IOException caught if writing the object fails
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("account writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Used in serialization to restore the account's information after the ATM is restarted.
     * @param ois instance of the ObjectInputStream class used to read the account object
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("account readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     *
     * @throws ObjectStreamException
     */
    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("account readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
