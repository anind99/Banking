package atm;

public class Checking extends Asset {

    public boolean primary;

    public Checking(int accountNum){
        super(accountNum);
        primary = false;
    }

    //set this account as the primary account
    public void setPrimary(){primary = true;}

    //Removing money from an asset account will decrease its balance
    // 0 is considered a positive balance
    public boolean removeMoney(double amount){
        if(balance >= 0 && balance - amount >= -100){
            balance -= amount;
            return true;
        }else{return false;}
    }
}
