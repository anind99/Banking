package atm;

import java.io.*;

public class Transaction implements Serializable {

    String type;
    int account = 0;
    double amount;
    String billName = null;

    /**
     * Constructor for Transfer In/Out
     * type: refers to transaction type
     * account: refers to account which is being transfer in from/ transfered out to
     *
     */
    public Transaction(int Acc, Double Amount, String Type) {
        this.type = Type;
        this.amount = Amount;
        this.account = Acc;
    }

    /**
     * Withdraw/Deposit Constructor
     */
    public Transaction(Double Amount, String Type) {
        this.type = Type;
        this.amount = Amount;
    }

    /**
     * Constructor for pay bill
     * Billname: Billname
     */
    public Transaction(String billname, Double Amount){
        this.billName = billname;
        this.type = "paybill";
        this.amount = Amount;
    }

    public double getTransactionAmount() {
        return this.amount;
    }

    public String getTransactionType() {
        return this.type;
    }

    public int getTransactionAccount() {
        return this.account;
    }

    public String toString() {
        if (this.type.equalsIgnoreCase("transferin")) {
            return "Transferred in " + this.amount +" from account " + this.account;
        } else if (this.type.equalsIgnoreCase("transferout")) {
            return "Transferred out " + this.amount + " from account: " + this.account;
        } else if (this.type.equalsIgnoreCase("withdraw")) {
            return "Withdrew " + this.amount;
        } else if (this.type.equals("deposit")) {
            return "Deposited " + this.amount;
        } else {
            return "Paid " + this.amount + " to " + this.billName;
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
