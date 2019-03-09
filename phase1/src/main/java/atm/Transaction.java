package atm;

public class Transaction {

    String Type;
    int Account = 0;
    Double Amount;
    String billname = null;

    public Transaction(int Acc, Double Amount, String Type) {
        /**
         * Constructor for Transfer In/Out
         * Type: refers to transaction type
         * Account: refers to account which is being transfer in from/ transfered out to
         *
         */
        this.Type = Type;
        this.Amount = Amount;
        this.Account = Acc;
    }

    public Transaction(Double Amount, String Type) {
        /**
         * Withdraw/Deposit Constructor
         */
        this.Type = Type;
        this.Amount = Amount;
    }

    public Transaction(String billname, Double Amount){
        /**
         * Constructor for pay bill
         * Billname: Billname
         */
        this.billname = billname;
        this.Type = "paybill";
        this.Amount = Amount;

    }

    public String toString() {
        if (this.Type.equalsIgnoreCase("transferin")) {
            return "Account Number: " + this.Account + "\nType of Transaction: Transfer In";
        } else if (this.Type.equalsIgnoreCase("transferout")) {
            return "Account Number: " + this.Account + "\nType of Transaction: Transfer Out";
        } else if (this.Type.equalsIgnoreCase("withdraw")) {
            return "Account Number: " + this.Account + "\nType of Transaction: Withdrawal";
        } else if (this.Type.equals("deposit")) {
            return "Account Number: " + this.Account + "\nType of Transaction: Deposit";
        } else {
            return "Account Number: " + this.Account + "\nType of Transaction: Pay Bill";
        }
    }

}
