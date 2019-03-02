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
            this.users_txt = "users.txt";
            try {
                input = new BufferedReader(new FileReader("bankmanager.txt"));
                output = new BufferedWriter(new FileWriter("bankmanager.txt"));
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
        }

        public void create_user(String username, String password){
            ArrayList accounts = new ArrayList<>();
            User newUser = new User(username, password, accounts);
            create_account(newUser, "credit card");
            create_account(newUser, "loc");
            create_account(newUser, "checking");
            create_account(newUser, "saving");
            ATM.addUserToList(newUser);
        }

        public void undo_transaction(){}

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
            if (acct_type.equalsIgnoreCase("checking")){
                Checking newChecking = new Checking(this.acct_counter);
                user.accounts.add(newChecking);
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

        public Date setDate(){
            Date today;
            return today;
        }

}
