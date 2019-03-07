package atm;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

public class BankManager {

    private String last, line;
    private int acct_counter;

        public BankManager(){
            //last = null;
            try {
                File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/bankmanager.txt"); //FIX
                FileInputStream is = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader r = new BufferedReader(isr);
                //line = r.readLine();
                while ((line = r.readLine()) != null) {
                    last = line; }
                r.close();
            }catch (FileNotFoundException e){
                System.out.println(
                        "Unable to open file '" +
                                "bankmanager.txt" + "'");
            }
            catch(IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + "bankmanager.txt" + "'");
                ex.printStackTrace();
            }
            this.acct_counter = Integer.parseInt(last);
        }

        //Bank manager will always add 100 new bills when restocking
        public static void restock(int index){
            ATM.set_bills(index, 100);
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
            ArrayList<Account> accounts = new ArrayList<>();
            boolean contains = false;
            for (User parameter : ATM.getListOfUsers()) {
                if (parameter.getUsername().equals(username)) {
                    contains = true;
                }
            }if(!contains){
            User newUser = new User(username, password, accounts);
            create_account(newUser, "CreditCard");
            create_account(newUser, "LOC");
            create_account(newUser, "Chequing");
            create_account(newUser, "Saving");
            System.out.println("New user: " + username + " created");
            ATM.addUserToList(newUser);
            }else{ System.out.println("User name already exists, please try a different name");}

        }


        public void create_account(User user, String acct_type){
            if (acct_type.equalsIgnoreCase("CreditCard")) {
                CreditCard newCreditCard = new CreditCard(this.acct_counter);
                user.accounts.add(newCreditCard);
                this.acct_counter += 1;
                System.out.println("New creditcard account created");
            }
            else if (acct_type.equalsIgnoreCase("LOC")){
                LOC newLoc = new LOC(this.acct_counter);
                user.accounts.add(newLoc);
                this.acct_counter += 1;
                System.out.println("New LOC account created");
            }
            else if (acct_type.equalsIgnoreCase("chequing")){
                Chequing newChequing = new Chequing(this.acct_counter);
                user.accounts.add(newChequing);
                this.acct_counter += 1;
                System.out.println("New chequing account created");
            }
            else if (acct_type.equalsIgnoreCase("saving")){
                Savings newSaving = new Savings(this.acct_counter);
                user.accounts.add(newSaving);
                this.acct_counter += 1;
                System.out.println("New savings account created");
            }


            try{
                File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/bankmanager.txt");
                FileOutputStream is = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(is);
                Writer w = new BufferedWriter(osw);
                w.write(String.valueOf(this.acct_counter));
                w.close();
            }catch(IOException e){e.printStackTrace();}
        }

    public void undo_transaction(User usr, Account acct){
        if (acct.lastTransaction == null){
            System.out.println("No previous transactions");
        }
        else if (acct.lastTransaction.Type.equalsIgnoreCase("deposit")){
            acct.balance -= acct.lastTransaction.Amount;
            acct.lastTransaction = null;
        }
        else if (acct.lastTransaction.Type.equalsIgnoreCase("withdrawal")){
            acct.balance += acct.lastTransaction.Amount;
            acct.lastTransaction = null;
        }
        else if (acct.lastTransaction.Type.equalsIgnoreCase("transferin")){
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

        else if (acct.lastTransaction.Type.equalsIgnoreCase("transferout")) {
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
        else if (acct.lastTransaction.Type.equalsIgnoreCase("paybill")){
            acct.balance += acct.lastTransaction.Amount;
            acct.lastTransaction = null;
        }
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

//        public Date setDate(){
//            Date today;
//            return today;
//        }

}
