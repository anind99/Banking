package account;

import atm.ATM;

public class CreditCard extends Debt {

    public CreditCard(int accountNum, ATM atm) {
        super(accountNum, atm);
        this.type = "creditcard";
    }

    public void transferOut(double amount, Account accountTo) {
        System.out.println("You cannot transfer out from a Credit Card account. Please try again.");
    }

    public void payBill(double amount, String receiver) {
        System.out.println("You cannot pay bills from a Credit Card account. Please try again.");
    }
}