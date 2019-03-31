package account;

import atm.ATM;

public class StockAccount extends Asset {
    protected double profitmade;
    double totalDeposited;

    public StockAccount(int accountNum, ATM atm) {

        super(accountNum, atm);
        this.type = "stock";
        this.profitmade = 0;
    }

    public void removeMoney(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
        }
    }

    public void payBill(double amount, String receiver) {
        System.out.println("\nYou cannot pay bills from a Stock account. Please try again.");
    }
}
