package atm;
//import
public class Account {

        public double balance;
        public String acct_num;
        public Transaction last_transaction;
        public String date_created;
        public String acct_txt;



        public Account(String acct_num, double balance, String date_created, String acct_txt){
            this.acct_num = acct_num;
            this.balance = balance;
            this.date_created = date_created;
            this.acct_txt = acct_txt;
            this.last_transaction = null;
        }

        public double view_balance(){
            return this.balance;
        }

        public void transfer_in(double amount, Account acct_outof){

        }
}
