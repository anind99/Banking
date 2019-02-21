package atm;

public class BankManager {

        private static String users_txt;

        public BankManager(){
            users_txt = "users.txt";
        }


        public static void restock(int index){
           ATM.set_bills(index, 100);

        }

        public User create_user(){
        }

        public void undo_transaction{}

        public void create_account{}

}
