package atm;
//import
public class Account {


        public String acct_num;
        public Transaction last_transaction;
        public String date_created;
        public String acct_txt;



        public Account(String acct_num, String date_created, String acct_txt){
            this.acct_num = acct_num;
            this.date_created = date_created;
            this.acct_txt = acct_txt;
            //this.last_transaction =;
        }


        public float view_balance(){

            return 0;

        }
}
