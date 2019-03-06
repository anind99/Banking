package atm;

public class Savings extends Asset {

    public final double interestRate;

    public Savings(int accountNum){
        super(accountNum);
        interestRate = 1.001;
        this.type = "savings";
    }

    //add the interest according the interestRate
    public void addInterest(){balance = (balance * interestRate);}

    //Removing money from an asset account will decrease its balance
    public boolean removeMoney(double amount){
        if(balance - amount >= 0){
                balance -= amount;
                return true;
        }else{return false;}
    }

}
