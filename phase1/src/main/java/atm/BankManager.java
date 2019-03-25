package atm;
import java.util.ArrayList;
import java.io.*;

public class BankManager implements Serializable{

    private int acct_counter;
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
            for (Account a : user.accounts) {
                if (a.getType().equals("chequing")) {
                    if (((Chequing)a).isPrimary()){primary = true;}
                }
            }if(!primary){
                for (Account a : user.accounts) {
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
            user.accounts.add(account);
            this.acct_counter += 1;
            System.out.println("New " + type + " account created.");
        }

    public void undo_transaction(User usr, Account acct){
        if (acct.lastTransaction == null){
            System.out.println("No previous transactions");
        }
        else if (acct.lastTransaction.Type.equalsIgnoreCase("deposit")){
            undoDeposit(acct);
        }
        else if (acct.lastTransaction.Type.equalsIgnoreCase("withdraw")){
            undoWithdraw(acct);
        }
        else if (acct.lastTransaction.Type.equalsIgnoreCase("transferin")){
            undoTransferIn(usr, acct);
        }

        else if (acct.lastTransaction.Type.equalsIgnoreCase("transferout")) {
            undoTransferOut(usr, acct);
        }
        else if (acct.lastTransaction.Type.equalsIgnoreCase("paybill")){
            undoPayBill(acct);
        }
    }

    private void undoDeposit(Account acct) {
            acct.balance -= acct.lastTransaction.Amount;
            acct.lastTransaction = null;
    }

    private void undoWithdraw(Account acct) {
            acct.balance += acct.lastTransaction.Amount;
            acct.lastTransaction = null;
    }

    private void undoTransferIn(User usr, Account acct) {
        Account TransferAct = null;
        for (Account ac2:usr.accounts){
            if (ac2.accountNum == acct.lastTransaction.Account){
                TransferAct = ac2;
            }
        }
        if (TransferAct != null) {
            acct.balance -= acct.lastTransaction.Amount;
            TransferAct.balance += acct.lastTransaction.Amount;
            if (check_other_acct(usr, acct))
                TransferAct.lastTransaction = null;
            acct.lastTransaction = null;
        }
    }

    private void undoTransferOut(User usr, Account acct) {
        Account TransferAct = null;
        for (Account ac2 : usr.accounts) {
            if (ac2.accountNum == acct.lastTransaction.Account) {
                TransferAct = ac2;
            }
        }
        if (TransferAct != null) {
            acct.balance += acct.lastTransaction.Amount;
            TransferAct.balance -= acct.lastTransaction.Amount;
            if (check_other_acct(usr, acct)) {
                TransferAct.lastTransaction = null;
            }
            acct.lastTransaction = null;
        }
    }

    private void undoPayBill(Account acct) {
        acct.balance += acct.lastTransaction.Amount;
        acct.lastTransaction = null;
    }


    public boolean check_other_acct(User usr, Account acct){
        Account otheract = null;

        for (Account ac2 : usr.accounts) {
            if (ac2.accountNum == acct.lastTransaction.Account){
                otheract = ac2;
            }
        }
        if (otheract == null || otheract.lastTransaction == null){
            return false;
        }
        return (otheract.lastTransaction.Type.equalsIgnoreCase(acct.lastTransaction.Type)
                && (otheract.lastTransaction.Account == acct.accountNum)
                && (otheract.lastTransaction.Amount.equals(acct.lastTransaction.Amount)));
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
