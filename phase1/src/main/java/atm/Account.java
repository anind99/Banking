package atm;
//import
public class Account {

        public double balance;
        public String accountNum;
        public Transaction lastTransaction;
        public String dateCreated;
        public String accountTxt;



        public Account(String accountNum, String accountTxt, Transaction lastTransaction, double balance){
            this.accountNum = accountNum;
            this.accountTxt = accountTxt;
            this.lastTransaction = lastTransaction;
            this.balance = balance;
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
