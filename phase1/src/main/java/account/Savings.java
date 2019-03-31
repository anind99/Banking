package account;

import atm.ATM;

public class Savings extends Asset {

    public final double interestRate;

    public Savings(int accountNum, ATM atm){
        super(accountNum, atm);
        interestRate = 1.001;
        this.type = "savings";
    }

    //add the interest according the interestRate
    public void addInterest(){balance = (balance * interestRate);}

    //Removing money from an asset account will decrease its balance
    public void removeMoney(double amount){
        if(balance - amount >= 0) {
            balance -= amount;
        }
    }

}
