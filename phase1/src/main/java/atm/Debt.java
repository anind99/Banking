package atm;

public class Debt extends Account{

    public Debt(String accountNum, String accountTxt){
        super(accountNum, accountTxt);
    }

    protected int AccountNumber;
    protected String Tectfile;
    Transaction LastTrnsaction;
    String DateCreated;
    double Balance;
    BankManager Manager;

    public void TransferIn(Account AccIn, int amount){

    }

    //Adding money to a debt account will decrease its balance
    public void addMoney(double amount){
        this.balance -= amount;
    }

    //Removing money from a debt account will increase its balance
    public void removeMoney(double amount){
        this.balance += amount;
    }
}
