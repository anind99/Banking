package atm;

public class CreditCard extends Debt {

    public CreditCard(String accountNum, String accountTxt) {
        super();
    }

    public void transferOut(double amount, Account accountTo) {
        System.out.println("You cannot transfer out from a Credit Card account. Please try again.");
    }

    public void payBills(double amount, String receiver) {
        System.out.println("You cannot transfer out from a Credit Card account. Please try again.");
    }
}