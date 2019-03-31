package account;

import atm.ATM;

/***
 * This is subclass of account class.
 */
public class Debt extends Account{

    protected double maxDebt = 50000;

    public Debt(int accountNum, ATM atm) {
        super(accountNum, atm);
    }

    //Adding money to a debt account will decrease its balance
    public void addMoney(double amount){
        this.balance -= amount;
    }

    //Removing money from a debt account will increase its balance
    public void removeMoney(double amount){
        if (checkFundsSufficient(amount)) {
            System.out.println("This account has hit the maximum amount of debt! Transaction not possible!");
        } else {
            this.balance += amount;
        }
    }

    @Override
    public boolean checkFundsSufficient(double amount) {
        return (balance + amount) >= maxDebt;
    }
}
