package atm;

public class Debt extends Account{

    public Debt(String accountNum) {
        super(accountNum);
    }

    //Adding money to a debt account will decrease its balance
    public void addMoney(double amount){
        this.balance -= amount;

    }

    //Removing money from a debt account will increase its balance
    public boolean removeMoney(double amount){
        this.balance += amount;
        return true;
    }
}
