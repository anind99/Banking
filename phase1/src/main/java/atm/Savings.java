package atm;

public class Savings extends Asset {

    public double interestRate;

    public Savings(String accountNum, String accountTxt){
        super(accountNum, accountTxt);
        interestRate = 1.001;
    }

    public void addInterest(){balance = (balance * interestRate);}



}
