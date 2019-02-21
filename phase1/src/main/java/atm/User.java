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

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.pass;
    }

    public void setUsername(String s){
        this.username = s;
    }
    public void setPassword(String s){
        this.pass = s;
    }

}
