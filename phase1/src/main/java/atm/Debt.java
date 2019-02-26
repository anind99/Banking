package atm;

public class Debt extends Account{
    
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
