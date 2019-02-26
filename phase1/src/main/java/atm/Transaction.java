package atm;

public class Transaction {

    String Type;
    Account Account;
    Double Amount;

    public Transaction(Account Acc, Double Amount, String Type){
        this.Type = Type;
        this.Amount = Amount;
        this.Account = Acc;

    }

    public Transaction(Double Amount){
        this.Type = "Deposit";
        this.Amount = Amount;
    }
}
