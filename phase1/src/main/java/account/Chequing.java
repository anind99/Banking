package account;

import atm.ATM;

public class Chequing extends Asset {

    public boolean primary;

    public Chequing(int accountNum, ATM atm){
        super(accountNum, atm);
        primary = false;
        this.type = "chequing";
    }

    @Override
    public void setPrimary(){
        primary = true;
    }

    @Override
    public boolean isPrimary(){
        return primary;
    }

    //Removing money from an asset account will decrease its balance
    // 0 is considered a positive balance
    public boolean removeMoney(double amount){
        if(balance >= 0 && balance - amount >= -100){
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

}
