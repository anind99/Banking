package atm;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Account implements Serializable {

    public String type;
    public final int accountNum;
    protected double balance;
    public Transaction lastTransaction;
    public ArrayList<Transaction> listOfTransactions = new ArrayList<>();
    public Calendar dateCreated;
    private int depositNum;
    private final ATM atm;


    public Account(int accountNum, ATM atm) {
        this.accountNum = accountNum;
        this.atm = atm;
        this.balance = 0;
        this.lastTransaction = null;
        this.dateCreated = this.atm.getDate();
        this.depositNum = 0;
    }

    public double getBalance(){
        return this.balance;
    }

    public void addBalance(double balance) {this.balance += balance;}

    public void subtractBalance(double balance) {this.balance -= balance;}

    public ArrayList<Transaction> getListOfTransactions() {
        return this.listOfTransactions;
    }


    public String getType(){
        return this.type;
    }

    public int getAccountNum() {
        return this.accountNum;
    }

    abstract void addMoney (double amount);

    abstract boolean removeMoney (double amount);

    void appendTransactionToList(Transaction transaction){
        getListOfTransactions().add(transaction);
    }

    void removeLastTransactionFromList() {
        // Remove the last transaction from listOfTransactions and re-initialize lastTransaction to the new last
        // transaction or null otherwise.
        ArrayList<Transaction> lst = getListOfTransactions();
        lst.remove(lst.size() - 1);
        if (lst.size() > 0) {
            setLastTransaction(lst.get(lst.size() - 1));
        } else {
            setLastTransaction(null);
        }
    }

    Transaction getLastTransaction() {
        return this.lastTransaction;
    }

    private void setLastTransaction(Transaction transaction) {
        this.lastTransaction = transaction;
    }

    public void transferIn(double amount, Account accountFrom) {
        boolean removed = accountFrom.removeMoney(amount);
        if(removed){addMoney(amount);
            Transaction transaction = new Transaction(accountFrom.accountNum, amount, "TransferIn");
            this.lastTransaction = transaction;
            this.listOfTransactions.add(transaction);
            System.out.println("\n" + amount + " has been transferred");}
        else{System.out.println("\nThis transaction i;s not possible: insufficient funds");}

    }

    public void transferOut(double amount, Account accountTo) {
        boolean removed = removeMoney(amount);
        if(removed){accountTo.addMoney(amount);
            Transaction transaction =  new Transaction(accountTo.accountNum, amount, "TransferOut");
            this.lastTransaction = transaction;
            this.listOfTransactions.add(transaction);
            System.out.println("\n" + amount + " has been transferred");}
        else{System.out.println("\nThis transaction is not possible: insufficient funds");}


    }

    public void deposit() {
        Double amount = depositReader();
        addMoney(amount);
        Transaction transaction = new Transaction(amount, "deposit");
        this.lastTransaction = transaction;
        this.listOfTransactions.add(transaction);
    }

    private Double depositReader() {
        Double amount;
        try {
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/deposits.txt");
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = r.readLine();
            String firstLine = line;

            int count = 0;
            while (line != null && count < depositNum) {
                count += 1;
                line = r.readLine();
            } amount = depositReaderHelper(line, count, firstLine);
            r.close();
            return amount;
        } catch (IOException e) {
            System.err.println("Problem reading the file deposits.txt");
            return 0.0;
        }
    }

    private double depositReaderHelper(String line, int count, String firstLine){
        double amount;
        if (line != null && count >= depositNum){depositNum += 1;
        }else{line = firstLine;
                depositNum = 1;}

        if (line.contains(".")){
            amount = Double.parseDouble(line);
            System.out.println("\nYou have deposited a cheque for $" + amount);
        }else{ amount = (double) ((Character.getNumericValue(line.charAt(0))) * 5 +
                Character.getNumericValue(line.charAt(1)) * 10 +
                Character.getNumericValue(line.charAt(2)) * 20 +
                Character.getNumericValue(line.charAt(3)) * 50);

            atm.getBills().add_bills(0, Character.getNumericValue(line.charAt(0)));
            atm.getBills().add_bills(1, Character.getNumericValue(line.charAt(1)));
            atm.getBills().add_bills(2, Character.getNumericValue(line.charAt(2)));
            atm.getBills().add_bills(3, Character.getNumericValue(line.charAt(3)));
            System.out.println("\nYou have deposited $" + amount + " in cash");
        }return amount;
    }

    public void withdraw(double amount) {
        if(atm.getBills().get_amount() >= amount) {
            atm.getBills().withdrawBills(amount);
            atm.alertManager();
            removeMoney(amount);
            Transaction transaction = new Transaction(amount, "withdraw");
            this.lastTransaction = transaction;
            System.out.println(this.listOfTransactions);
            this.listOfTransactions.add(transaction);
        }else{System.out.println("\nTransaction not possible: not enough funds in ATM");}

    }

    public void payBill(double amount, String receiver){
        boolean removed = removeMoney(amount);
        if(removed){payBillWriting(amount, receiver);
            System.out.println("You paid " + amount + " to " + receiver);
        }
        else{System.out.println("\nThis transaction is not possible: insufficient funds");}
        this.lastTransaction = new Transaction(receiver, amount);
    }

    //Helper function to Paybill that adds the information of the paid bill to a text file
    public boolean payBillWriting(double amount, String receiver) {
        try {
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/outgoing.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(accountNum + " payed " + amount + " to " + receiver);
            w.close();
            return true;
        } catch (IOException e) {
            System.err.println("Problem writing to the file outgoing.txt");
            return false;
        }
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("Account writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("Account readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("Account readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
