package atm;

public class StockAccount extends Asset {
    public StockAccount(int accountNum, ATM atm) {

        super(accountNum, atm);
    }

    public boolean removeMoney(double amount){
        if(balance - amount >= 0){
            balance -= amount;
            return true;
        }else{return false;}
    }

    public void payBill(double amount, String receiver) {
        System.out.println("You cannot pay bills from a Stock account. Please try again.");
    }



}
