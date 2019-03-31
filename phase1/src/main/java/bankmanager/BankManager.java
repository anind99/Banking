package bankmanager;
import atm.*;
import account.*;

import java.io.*;

public class BankManager implements Serializable{
    final ATM atm;
    private final AccountManager accountManager;
    private final TransactionManager transactionManager;
    private final UserManager userManager;

    public BankManager(ATM atm){
        this.atm = atm;
            // acct_counter is the variable that provides users with unique account numbers, it will increment by 1
            // each time a new account is created.
        this.accountManager = new AccountManager(atm);
        this.transactionManager = new TransactionManager();
        this.userManager = new UserManager(atm);

    }

    //Bank manager will always add 100 new bills when restocking
    public void restock(int index){
        atm.getBills().setBills(index, 100);
        try {
            //System.out.println(System.getProperty("user.dir"));
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/alerts.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write("Alerts addressed");
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file alerts.txt");
        }
    }

    public void createAccount(User user, String acct_type) {
        accountManager.createAccount(user, acct_type);
    }

    public void createJointAccount(User user1, User user2, String accountType) {
        accountManager.createJointAccount(user1, user2, accountType);
    }

    public void addExistingUserToAccount(User user, Account account) {
        accountManager.addExistingUserToAccount(user, account);
    }

    /***
     * Creates a new user. When the Bank Manager creates a new user, all account types will be open for this user.
     *
     * @param username the username the user uses to log in
     * @param password the password the user uses to log in
     * @return the new user
     * @see User
     */
    public User createUser(String username, String password) {
        User user = userManager.createUser(username, password);

        accountManager.createAccount(user, "chequing");
        accountManager.createAccount(user, "creditcard");
        accountManager.createAccount(user, "loc");
        accountManager.createAccount(user, "savings");
        accountManager.createAccount(user, "stock");

        return user;
    }

    public void undoTransaction(User usr, Account acct) {
        transactionManager.undoTransaction(usr, acct);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException{
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("BM writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("BM readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException{
        System.out.println("BM readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
