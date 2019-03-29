package atm;

import java.io.*;

public class Transaction implements Serializable {

    String Type;
    int Account = 0;
    double Amount;
    String billname = null;

    /**
     * Constructor for Transfer In/Out
     * Type: refers to transaction type
     * account: refers to account which is being transfer in from/ transfered out to
     *
     */
    public Transaction(int Acc, Double Amount, String Type) {
        this.Type = Type;
        this.Amount = Amount;
        this.Account = Acc;
    }

    /**
     * Withdraw/Deposit Constructor
     */
    public Transaction(Double Amount, String Type) {
        this.Type = Type;
        this.Amount = Amount;
    }

    /**
     * Constructor for pay bill
     * Billname: Billname
     */
    public Transaction(String billname, Double Amount){
        this.billname = billname;
        this.Type = "paybill";
        this.Amount = Amount;
    }

    public double getTransactionAmount() {
        return this.Amount;
    }

    public String getTransactionType() {
        return this.Type;
    }

    public int getTransactionAccount() {
        return this.Account;
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

    public String toString() {
        if (this.Type.equalsIgnoreCase("transferin")) {
            return "Transferred in " + this.Amount +" from account " + this.Account;
        } else if (this.Type.equalsIgnoreCase("transferout")) {
            return "Transferred out " + this.Amount + " from account: " + this.Account;
        } else if (this.Type.equalsIgnoreCase("withdraw")) {
            return "Withdrew " + this.Amount;
        } else if (this.Type.equals("deposit")) {
            return "Deposited " + this.Amount;
        } else {
            return "Paid " + this.Amount + " to " + this.billname;
        }
    }

}
