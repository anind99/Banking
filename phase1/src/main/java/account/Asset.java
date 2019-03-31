package account;

import atm.ATM;

/**
 * Asset is a child of Account and is an abstract class for all asset accounts: Chequing, Savings, and Stocks
 */
public abstract class Asset extends Account{

    /**
     * Asset constructor calls on super (Account class).
     * @param accountNum unique account number
     * @param atm instance of the ATM
     */
    public Asset(int accountNum, ATM atm) {
        super(accountNum, atm);
    }


    /**
     * Adds money to the account and increases the balance.
     * @param amount dollar amount added into the account
     */
    public void addMoney(double amount){
        balance += amount;
    }
}

