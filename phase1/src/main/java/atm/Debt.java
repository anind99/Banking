package atm;

public class Debt extends Account{

    protected double maxDebt = -50000;

    public Debt(int accountNum, ATM atm) {
        super(accountNum, atm);
    }

    //Adding money to a debt account will decrease its balance
    public void addMoney(double amount){
        if (checkMaxDebt(this.balance - amount)) {
            System.out.println("This account has hit the maximum amount of debt! Transaction not possible!");
        } else {
            this.balance -= amount;
        }
    }

    //Removing money from a debt account will increase its balance
    public boolean removeMoney(double amount){
        this.balance += amount;
        return true;
    }

    private boolean checkMaxDebt(double balance){
        // Return true if balance has hit the maximum amount of debt and false otherwise.
        return balance <= maxDebt;
    }
}
