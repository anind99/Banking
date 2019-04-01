package atm;

import java.io.*;

public class Transaction implements Serializable {

    String type;
    int accountNum = 0;
    double amount;
    String billPayee = null;

    /**
     * Transaction constructor for Transfer In/Out trasnactions
     *
     * @param acctNum the unique account number for the account user used to do the transaction
     * @param amount the dollar amount of the transaction
     * @param type refers to transaction type
     *
     */
    public Transaction(int acctNum, Double amount, String type) {
        this.type = type;
        this.amount = amount;
        this.accountNum = acctNum;
    }

    /**
     * Transaction constructor for Withdraw/Deposit transactions
     *
     * @param amount the dollar amount of the transaction
     * @param type refers to transaction type

     */
    public Transaction(Double amount, String type) {
        this.type = type;
        this.amount = amount;
    }

    /**
     *Transaction constructor for Pay Bill transactions
     *
     * @param billPayee the person/account the bill is being paid to
     * @param amount the bill's dollar amount
     */
    public Transaction(String billPayee, Double amount){
        this.billPayee = billPayee;
        this.type = "paybill";
        this.amount = amount;
    }

    /**
     *
     * @return the dollar amount of the transaction
     */
    public double getTransactionAmount() {
        return this.amount;
    }

    /**
     *
     * @return type of the transaction
     */
    public String getTransactionType() {
        return this.type;
    }

    /**
     *
     * @return the unique account number from which the transaction was performed
     */
    public int getTransactionAccount() {
        return this.accountNum;
    }

    public String toString() {
        if (this.type.equalsIgnoreCase("transferin")) {
            return "Transferred in " + this.amount +" from account " + this.accountNum;
        } else if (this.type.equalsIgnoreCase("transferout")) {
            return "Transferred out " + this.amount + " from account: " + this.accountNum;
        } else if (this.type.equalsIgnoreCase("withdraw")) {
            return "Withdrew " + this.amount;
        } else if (this.type.equals("deposit")) {
            return "Deposited " + this.amount;
        } else {
            return "Paid " + this.amount + " to " + this.billPayee;
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("Transaction writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("Transaction readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("Transaction readObjectNoData, this should never happen!");
        System.exit(-1);
    }

}
