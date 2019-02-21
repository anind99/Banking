package atm;

public class BankManager {

        public static String users_txt;

        public BankManager(){
            this.users_txt = "users.txt";
        }


        public static void restock(int index){
           ATM.set_bills(index, 100);

        }

        public String create_user(){
        }

        public void undo_transaction{}

        public void create_account{}

}
