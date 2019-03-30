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
        atm.getBills().set_bills(index, 100);
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

    public void create_account(User user, String acct_type) {
        accountManager.createAccount(user, acct_type);
    }

    public void createUser(String username, String password) {
        userManager.createUser(username, password);
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
