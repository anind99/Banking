package BankManager;
import atm.*;

import java.util.ArrayList;
import java.io.*;

public class BankManager implements Serializable{
    protected int acct_counter;
    private final ATM atm;
    private final AccountManager accountManager = new AccountManager();
    private final TransactionManager transactionManager = new TransactionManager();
    private final UserManager userManager = new UserManager();

    public BankManager(ATM atm){
        this.atm = atm;
        this.acct_counter = 1000;
            // acct_counter is the variable that provides users with unique account numbers, it will increment by 1
            // each time a new account is created.
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

    protected void addstockaccount(User user){
        boolean added = false;
        for (Account a: user.getAccounts()){
            if (a.type.equalsIgnoreCase("Stock"))
                added = true;

        }
        if (!added){
            int accNum = atm.getBM().acct_counter;
            atm.getBM().acct_counter += 1;
            StockAccount Stocks =  new StockAccount(accNum, atm);
            user.accounts.add(Stocks);
        }
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
