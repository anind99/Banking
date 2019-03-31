package account;

import atm.ATM;

public class StockAccount extends Asset {


    public StockAccount(int accountNum, ATM atm) {

        super(accountNum, atm);
        this.type = "stock";
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
