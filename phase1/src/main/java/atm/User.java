package atm;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private String username;
    private String pass;
    private ArrayList<Account> accounts;

    public User(String username, String password, ArrayList accounts){
        this.username = username;
        this.pass = password;
        this.accounts = accounts;
    }

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

    public String getDateCreated(Account account) {
        return account.dateCreated.toString();
    }

    public Transaction getLastTransaction(Account account) {
        return account.lastTransaction;
    }

}
