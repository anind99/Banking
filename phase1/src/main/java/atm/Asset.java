package atm;

public class Asset extends Account{

    public Asset(String accountNum, String accountTxt){
        super(accountNum, accountTxt);

   }

    public void transferIn(double amount, Account accountFrom) {
//            balance += amount;
//            accountFrom.decreaseBalance(amount);
    }

    public void transferOut(double amount, Account accountTo) {
//            this.balance -= amount;
//            accountTo.increaseBalance(amount);
    }

    public void deposit(double amount) {
//            this.increaseBalance(amount);
    }

    public void withdraw(double amount) {
//            this.decreaseBalance(amount);
    }

}
