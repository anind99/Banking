package atm;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

public class BankManager {

    public static String users_txt;
    private String last, line;
    private int acct_counter;
    private BufferedReader input;
    private BufferedWriter output;

        public BankManager(){
            this.users_txt = System.getProperty("user.dir") + "/Text Files/users.txt"; //NEED TO FIX!
            try {
                input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/Text Files/bankmanager.txt"));
                output = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/Text Files/bankmanager.txt"));
                while ((line = input.readLine()) != null) {
                    last = line;
                }
                input.close();
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
                File file = new File(System.getProperty("user.dir") + "/Text Files/alerts.txt");
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
            ArrayList accounts = new ArrayList<>();
            User newUser = new User(username, password, accounts);
            create_account(newUser, "credit card");
            create_account(newUser, "loc");
            create_account(newUser, "chequing");
            create_account(newUser, "saving");
            ATM.addUserToList(newUser);
        }



        public void create_account(User user, String acct_type){
            if (acct_type.equalsIgnoreCase("credit card")) {
                CreditCard newCreditCard = new CreditCard(this.acct_counter);
                user.accounts.add(newCreditCard);
                this.acct_counter += 1;
            }
            if (acct_type.equalsIgnoreCase("loc")){
                LOC newLoc = new LOC(this.acct_counter);
                user.accounts.add(newLoc);
                this.acct_counter += 1;
            }
            if (acct_type.equalsIgnoreCase("chequing")){
                Chequing newChequing = new Chequing(this.acct_counter);
                user.accounts.add(newChequing);
                this.acct_counter += 1;
            }
            if (acct_type.equalsIgnoreCase("saving")){
                Savings newSaving = new Savings(this.acct_counter);
                user.accounts.add(newSaving);
                this.acct_counter += 1;
            }

            //update the account counter number in bankmanager.txt
            try{
                output.write(this.acct_counter);
            }catch(IOException e){e.printStackTrace();}
        }

        public void undo_transaction(Account acct){
            if (acct.lastTransaction.Type.equalsIgnoreCase("deposit")){
                acct.balance -= acct.lastTransaction.Amount;
                acct.lastTransaction = null;
            }
            if (acct.lastTransaction.Type.equalsIgnoreCase("withdrawal")){
                acct.balance += acct.lastTransaction.Amount;
                acct.lastTransaction = null;
            }
            if (acct.lastTransaction.Type.equalsIgnoreCase("transferin")){
                acct.balance -= acct.lastTransaction.Amount;
                acct.lastTransaction.Account.balance += acct.lastTransaction.Amount;
                if (check_other_acct(acct))
                    acct.lastTransaction.Account.lastTransaction = null;
                acct.lastTransaction = null;
            }
            if (acct.lastTransaction.Type.equalsIgnoreCase("transferout")){
                acct.balance += acct.lastTransaction.Amount;
                acct.lastTransaction.Account.balance -= acct.lastTransaction.Amount;
                if (check_other_acct(acct)){
                    acct.lastTransaction.Account.lastTransaction = null;
                }
                acct.lastTransaction = null;
            }
            if (acct.lastTransaction.Type.equalsIgnoreCase("paybill")){
                acct.balance += acct.lastTransaction.Amount;
                acct.lastTransaction = null;
            }
        }

        public boolean check_other_acct(Account acct){
            return (acct.lastTransaction.Account.lastTransaction.Type.equalsIgnoreCase(acct.lastTransaction.Type)
                    && (acct.lastTransaction.Account.lastTransaction.Account.accountNum == acct.accountNum)
                    && (acct.lastTransaction.Account.lastTransaction.Amount == acct.lastTransaction.Amount));
        }
//        public Date setDate(){
//            Date today;
//            return today;
//        }

}
