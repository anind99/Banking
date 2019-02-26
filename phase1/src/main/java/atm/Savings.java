package atm;

public class Savings extends Asset {

    public double interestRate;

    public Savings(String accountNum){
        super(accountNum);
        interestRate = 1.001;
    }

    //add the interest according the interestRate
    public void addInterest(){balance = (balance * interestRate);}

}
