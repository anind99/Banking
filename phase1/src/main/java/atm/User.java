package atm;

import java.util.ArrayList;

public class User {

    private String username;
    private String pass;
    private ArrayList<Account> accounts;


    public double getNetTotal(){
        double sum = 0;
        for (Account acc : accounts){
            sum += acc.getBalance();
        }
        return sum;
    }

    public void updatePassword(String s){
        this.pass = s;
    }

    public ArrayList<Account> getAccounts(){
        return this.accounts;
    }

}
