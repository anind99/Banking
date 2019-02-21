package atm;

import java.util.ArrayList;

public class User {

    private String username;
    private String pass;
    private ArrayList<Account> accounts;


    public double getNetTotal(){
    }

    public void updatePassword(String s){
        this.pass = s;
    }

}
