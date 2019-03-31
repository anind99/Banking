package account;

import atm.ATM;

/***
 * Debt class is a subclass of the Account class and
 * the abstract parent class for debt type accounts
 * Line of Credit(LOC), and Credit Card.
 * The user has a credit limit of $50,000
 * on a Debt account.
 *
 */
public abstract class Debt extends Account{

    protected double creditLimit = 50000;

    /**
     * Debt constructor calls on super (Account class).
     * @param accountNum unique account number
     * @param atm instance of the ATM
     */
    public Debt(int accountNum, ATM atm) {
        super(accountNum, atm);
    }

    /**
     * Adds money to the account and decreases the balance.
     * @param amount dollar amount added into the account
     */
    public void addMoney(double amount){
        this.balance -= amount;
    }

    //Removing money from a debt account will increase its balance

    /**
     * Removes money from the account if credit available and increases the balance.
     * @param amount dollar amount removed from the account
     */
    public void removeMoney(double amount){
        if (checkFundsSufficient(amount)) {
            System.out.println("Transaction declined. This account has reached the maximum credit limit!");
        } else {
            this.balance += amount;
        }
    }

    @Override
    public boolean checkFundsSufficient(double amount) {
        return (balance + amount) >= creditLimit;
    }
}
