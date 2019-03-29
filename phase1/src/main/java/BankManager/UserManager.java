package BankManager;

import atm.Account;
import atm.User;

import java.util.ArrayList;

public class UserManager {
    public void create_user(String username, String password){
        // Creates a new user. When a new user is created, all account types will be opened for this user.
        ArrayList<Account> accounts = new ArrayList<>();
        boolean contains = false;
        for (User parameter : atm.getListOfUsers()) {
            if (parameter.getUsername().equals(username)) {
                contains = true;
            }
        } if (!contains){
            User newUser = new User(username, password, accounts);
            create_account(newUser, "Chequing");
            create_account(newUser, "Savings");
            create_account(newUser, "CreditCard");
            create_account(newUser, "LOC");
            create_account(newUser, "Stock");
            System.out.println("New user: " + username + " created");
            atm.addUserToList(newUser);
        } else{
            System.out.println("User name already exists, please try a different name");
        }

    }
}
