package atm;
//import
public class Account {

        public String accountTxt;
        public String accountNum;
        public double balance;
        public Transaction lastTransaction;
        public String dateCreated;


        public Account(String accountNum, String accountTxt) {
            this.accountNum = accountNum;
            this.accountTxt = accountTxt;
            this.balance = 0;
            this.lastTransaction = null;

        }

        public double getBalance(){
            return this.balance;
        }

        public void transfer_in(double amount, Account acct_from) {
            //this.increase_balance(amount);
            //acct_from.decrease_balance(amount)
        }

        public void increase_balance (double amount){
            this.balance += amount;
        }

        public void decrease_balance (double amount){
            this.balance -= amount;
        }
}
