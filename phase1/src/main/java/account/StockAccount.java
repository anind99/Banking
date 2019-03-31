package account;

import atm.ATM;

/**
 * A child class of {@link Asset} represents a user's stock account used for investment activities.
 * {@see investments}
 */
public class StockAccount extends Asset {
    /**
     * The money made from investing in stocks.
     */
    protected double profitmade;

    /**
     * StockAccount constructor calls on super {@link Asset} and
     * sets initial {@link StockAccount#profitmade} to zero and the
     * {@link Account#type} to stock.
     * @param accountNum the unique account number
     * @param atm instance of {@link ATM}
     */
    public StockAccount(int accountNum, ATM atm) {
        super(accountNum, atm);
        this.type = "stock";
        this.profitmade = 0;
    }

    /**
     * Removes money from the stock account and decreases its {@link Account#balance}.
     * @param amount dollar amount removed from the account
     */
    public void removeMoney(double amount) {
        if (checkFundsSufficient(amount)) {
            this.balance -= amount;
        }else {
            System.out.println("Insufficient funds in Stock Account! Try another amount or account to remove money.");
        }
    }

    public void payBill(double amount, String receiver) {
        System.out.println("\nYou cannot pay bills from a Stock account. Please try again.");
    }
}
