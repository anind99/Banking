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

        public static void restock(int index){
            ATM.set_bills(index, 100);
        }

        public User create_user(String username, String password){
            ArrayList accounts = new ArrayList<>();
            User newUser = new User(username, password, accounts);
            create_account(newUser, "credit card");
            create_account(newUser, "loc");
            create_account(newUser, "checking");
            create_account(newUser, "saving");
            //atm.
            return newUser;
        }

        public void undo_transaction(){}

        public void create_account(User user, String acct_type){
            if (actt_type.equalsignorecase("credit card")) {
                CreditCard newCreditCard = new CreditCard(this.acct_counter);
                user.accounts.add(newCreditCard);
                this.acct_counter += 1;
            }
            if (actt_type.equalsignorecase("loc")){
                LOC newLoc = new LOC(this.acct_counter);
                user.accounts.add(newLoc);
                this.acct_counter += 1;
            }
            if (actt_type.equalsignorecase("checking")){
                Checking newChecking = new Checking(this.acct_counter);
                user.accounts.add(newChecking);
                this.acct_counter += 1;
            }
            if (actt_type.equalsignorecase("saving")){
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
