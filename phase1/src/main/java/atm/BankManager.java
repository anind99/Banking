package atm;
import java.util.ArrayList;
import java.io.*;

public class BankManager implements Serializable{
    protected int acct_counter;
    private final ATM atm;

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
            // Creates a new user. This user will have all the account types open.
            ArrayList<Account> accounts = new ArrayList<>();
            boolean contains = false;
            for (User parameter : atm.getListOfUsers()) {
                if (parameter.getUsername().equals(username)) {
                    contains = true;
                }
            }if(!contains){
            User newUser = new User(username, password, accounts);
            create_account(newUser, "Chequing");
            create_account(newUser, "Savings");
            create_account(newUser, "CreditCard");
            create_account(newUser, "LOC");
            System.out.println("New user: " + username + " created");
            atm.addUserToList(newUser);
            }else{ System.out.println("User name already exists, please try a different name");}

        }


        public void create_account(User user, String acct_type){
            if (acct_type.equalsIgnoreCase("chequing")) {
                createAccountHelper(user, createNewChequing(), "chequing");
            }
            else if (acct_type.equalsIgnoreCase("CreditCard")) {
                createAccountHelper(user, createNewCreditCard(), "credit card");
            }
            else if (acct_type.equalsIgnoreCase("LOC")){
                createAccountHelper(user, createNewLOC(), "line of credit");
            }

            else if (acct_type.equalsIgnoreCase("savings")){
                createAccountHelper(user, createNewSavings(), "savings");
            }

            checkForPrimary(user);

        }

        private Chequing createNewChequing() {
            return new Chequing(this.acct_counter, atm);
        }

        private void checkForPrimary(User user) {
            // Checks for primary account. The first chequing account the user makes is always the primary account.
            boolean primary = false;
            for (Account a : user.getAccounts()) {
                if (a.getType().equals("chequing")) {
                    if (((Chequing)a).isPrimary()){primary = true;}
                }
            }if(!primary){
                for (Account a : user.getAccounts()) {
                    if (a.getType().equals("chequing")){
                        ((Chequing)a).setPrimary();
                        break;}}}
        }

        private CreditCard createNewCreditCard() {
            return new CreditCard(this.acct_counter, atm);
        }

        private Savings createNewSavings() {
            return new Savings(this.acct_counter, atm);
        }

        private LOC createNewLOC() {
            return new LOC(this.acct_counter, atm);
        }

        private void createAccountHelper(User user, Account account, String type){
            user.getAccounts().add(account);
            this.acct_counter += 1;
            System.out.println("New " + type + " account created.");
        }

    public void undo_transaction(User usr, Account acct){
        if (acct.getLastTransaction() == null){
            System.out.println("No previous transactions");
        } else {
            String transactionType = acct.getLastTransaction().getTransactionType();

            if (transactionType.equalsIgnoreCase("deposit")) {
                undoDeposit(acct);
            } else if (transactionType.equals("withdraw")) {
                undoWithdraw(acct);
            } else if (transactionType.equalsIgnoreCase("transferin")){
                undoTransferIn(usr, acct);
            } else if (transactionType.equalsIgnoreCase("transferout")) {
                undoTransferOut(usr, acct);
            } else if (transactionType.equalsIgnoreCase("paybill")){
                undoPayBill(acct);
            }
        }
    }

    private void undoDeposit(Account acct) {
            acct.subtractBalance(acct.getLastTransaction().getTransactionAmount());
            acct.removeLastTransactionFromList();
    }

    private void undoWithdraw(Account acct) {
            acct.addBalance(acct.getLastTransaction().getTransactionAmount());
            acct.removeLastTransactionFromList();
    }

    private void undoTransferIn(User usr, Account acct) {
        Account TransferAct = null;
        for (Account ac2:usr.getAccounts()){
            if (ac2.getAccountNum() == acct.getLastTransaction().getTransactionAccount()){
                TransferAct = ac2;
            }
        }
        if (TransferAct != null) {
            double amount = acct.getLastTransaction().getTransactionAmount();
            acct.subtractBalance(amount);
            TransferAct.addBalance(amount);
            if (check_other_acct(usr, acct)) {
                TransferAct.removeLastTransactionFromList();
            }
            acct.removeLastTransactionFromList();
        }
    }

    private void undoTransferOut(User usr, Account acct) {
        Account TransferAct = null;
        for (Account ac2 : usr.getAccounts()) {
            if (ac2.getAccountNum() == acct.getLastTransaction().getTransactionAccount()) {
                TransferAct = ac2;
            }
        }
        if (TransferAct != null) {
            double amount = acct.getLastTransaction().getTransactionAmount();
            acct.addBalance(amount);
            TransferAct.subtractBalance(amount);
            if (check_other_acct(usr, acct)) {
                TransferAct.removeLastTransactionFromList();
            }
            acct.removeLastTransactionFromList();
        }
    }

    private void undoPayBill(Account acct) {
            acct.addBalance(acct.getLastTransaction().getTransactionAmount());
            acct.removeLastTransactionFromList();
    }


    public boolean check_other_acct(User usr, Account acct){
        Account otheract = null;

        String acctTransactionType = acct.getLastTransaction().getTransactionType();
        int acctNum = acct.getAccountNum();
        double acctTransactionAmount = acct.getLastTransaction().getTransactionAmount();

        for (Account ac2 : usr.getAccounts()) {
            if (ac2.getAccountNum() == acctNum){
                otheract = ac2;
            }
        }
        if (otheract == null || otheract.getLastTransaction() == null){
            return false;
        }
        return (otheract.getLastTransaction().getTransactionType().equalsIgnoreCase(acctTransactionType)
                && (otheract.getLastTransaction().getTransactionAccount() == acctNum)
                && (otheract.getLastTransaction().getTransactionAmount() == acctTransactionAmount));
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
