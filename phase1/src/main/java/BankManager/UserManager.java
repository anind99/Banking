package BankManager;
import Account.Account;
import atm.*;

import java.util.ArrayList;

public class UserManager extends BankManager{

    public UserManager(ATM atm) {
        super(atm);
    }

    public void createUser(String username, String password){
        // Creates a new user. When a new user is created, all account types will be opened for this user.
        ArrayList<Account> accounts = new ArrayList<>();
        boolean contains = false;
        for (User parameter : atm.getListOfUsers()) {
            if (parameter.getUsername().equals(username)) {
                contains = true;
            }
        } if (!contains){
            User newUser = new User(username, password, accounts);
            System.out.println("New user: " + username + " created");
            atm.addUserToList(newUser);
        } else{
            System.out.println("User name already exists, please try a different name");
        }

    }
}
