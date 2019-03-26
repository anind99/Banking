package atm;

public class StockAccount extends Asset {
    protected double profitmade;
    public StockAccount(int accountNum, ATM atm) {

        super(accountNum, atm);
        this.type = "stock";
        this.profitmade = 0;
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
