package bankmanager;
import account.Account;
import atm.*;

import java.util.ArrayList;

public class UserManager {

    private ATM atm;

    public UserManager(ATM atm) {
        this.atm = atm;

    }

    /***
     * Creates a new user.
     *
     * @param username the username this user uses to log in
     * @param password the password this user uses to log in
     */
    public User createUser(String username, String password){
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
            return newUser;
        } else{
            System.out.println("User name already exists, please try a different name");
        }
        return null;
    }
}
