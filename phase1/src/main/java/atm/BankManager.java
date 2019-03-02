package atm;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;

public class BankManager {

        public static String users_txt;
        Random rand;

        public BankManager(){
            this.users_txt = "users.txt";
            rand  = new Random();
        }


        public static void restock(int index){
            ATM.set_bills(index, 100);
        }

        public User create_user(String username, String password){
            //String username = ""+ (char)(r.nextInt(26) + 'a') + rand.nextInt(2147483647);
            //String password = "" + (char)(r.nextInt(26) + 'a') + rand.nextInt(1000);
            User newUser = new User(username, password, );
            ArrayList accounts = new ArrayList<>();
            create_account()
            return new User();

            //temp value to calm down compiler
        }

        public void undo_transaction(){}

        public void create_account(User user, String acct_type, String acct_num){
            if (actt_type.equalsignorecase("credit card")){
                CreditCard newCreditCard = new CreditCard(acct_num);
            }
        }

        public Date setDate(){
            Date today;
            return today;
        }

}
