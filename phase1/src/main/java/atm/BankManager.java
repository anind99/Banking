package atm;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

public class BankManager {

    public static String users_txt;
    private String last, line;
    private int acct_counter;

        public BankManager(){
            this.users_txt = "users.txt";
            BufferedReader input = new BufferedReader(new FileReader("bankmanager.txt"));
            try {
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
            //String username = ""+ (char)(r.nextInt(26) + 'a') + rand.nextInt(2147483647);
            //String password = "" + (char)(r.nextInt(26) + 'a') + rand.nextInt(1000);
            User newUser = new User(username, password, );
            ArrayList accounts = new ArrayList<>();
            create_account(newUser, "credit card", );
            return newUser;

            //temp value to calm down compiler
        }

        public void undo_transaction(){}

        public void create_account(User user, String acct_type){
            if (actt_type.equalsignorecase("credit card")) {
                CreditCard newCreditCard = new CreditCard(this.acct_counter);


            }
            if (actt_type.equalsignorecase("loc")){

            }

        }

        public Date setDate(){
            Date today;
            return today;
        }

}
