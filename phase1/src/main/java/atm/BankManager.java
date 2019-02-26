package atm;
import java.util.Random;

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

        public User create_user(){
                // Obtain a number between [0 - 49].
                String username = ""+ (char)(r.nextInt(26) + 'a') + rand.nextInt(2147483647);
                String password = "" + (char)(r.nextInt(26) + 'a') + rand.nextInt(1000);
                return new User();

                //temp value to calm down compiler
        }

        public void undo_transaction(){}

        public void create_account(String username, String password){}

}
