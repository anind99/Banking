package atm;

public class Transaction {

    String Type;
    Account Account = null;
    Double Amount;

    public Transaction(Account Acc, Double Amount, String Type) {
        this.Type = Type;
        this.Amount = Amount;
        this.Account = Acc;

    }

    public Transaction(Double Amount, String Type) {
        this.Type = Type;
        this.Amount = Amount;
    }
}
