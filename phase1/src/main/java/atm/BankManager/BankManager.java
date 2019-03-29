package atm.BankManager;
import atm.*;

import java.util.ArrayList;
import java.io.*;

public class BankManager implements Serializable{
    protected int acct_counter;
    private final ATM atm;
    private final CreateAccount createAccount = new CreateAccount();
    private final UndoTransaction undoTransaction = new UndoTransaction();

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


        public void create_account(User user, String acct_type){
            // Creates a new account as specified by the parameter.
            if (acct_type.equalsIgnoreCase("chequing")) {
                createAccountHelper(user, createAccount.newChequing(this.acct_counter, atm), "chequing");
            }
            else if (acct_type.equalsIgnoreCase("CreditCard")) {
                createAccountHelper(user, createAccount.newCreditCard(this.acct_counter, atm), "credit card");
            }
            else if (acct_type.equalsIgnoreCase("LOC")){
                createAccountHelper(user, createAccount.newLOC(this.acct_counter, atm), "line of credit");
            }

            else if (acct_type.equalsIgnoreCase("savings")){
                createAccountHelper(user, createAccount.newSavings(this.acct_counter, atm), "savings");
            } else if (acct_type.equalsIgnoreCase("stock")) {
                createAccountHelper(user, createAccount.newStockAccount(this.acct_counter, atm), "stock");
            }

            createAccount.checkForPrimary(user);

        }

        private void createAccountHelper(User user, Account account, String type){
            // Create account helper method for create_account. Increases acc_counter by 1 and adds the created account
            // to the user.
            user.getAccounts().add(account);
            this.acct_counter += 1;
            System.out.println("New " + type + " account created.");
        }

    public void undo_transaction(User usr, Account acct){
            // Allows Bank Manager to undo any type of transaction.
        if (acct.getLastTransaction() == null){
            System.out.println("No previous transactions");
        } else {
            String transactionType = acct.getLastTransaction().getTransactionType();

            if (transactionType.equalsIgnoreCase("deposit")) {
                undoTransaction.undoDeposit(acct);
            } else if (transactionType.equals("withdraw")) {
                undoTransaction.undoWithdraw(acct);
            } else if (transactionType.equalsIgnoreCase("transferin")){
                undoTransaction.undoTransferIn(usr, acct);
            } else if (transactionType.equalsIgnoreCase("transferout")) {
                undoTransaction.undoTransferOut(usr, acct);
            } else if (transactionType.equalsIgnoreCase("paybill")){
                undoTransaction.undoPayBill(acct);
            }
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

}
