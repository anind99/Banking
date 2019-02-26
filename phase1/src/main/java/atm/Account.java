package atm;
//import
public class Account {

        public String accountTxt;
        public String accountNum;
        protected double balance;
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

        public void transferIn(double amount, Account accountFrom) {
            this.balance += amount;
            accountFrom.decreaseBalance(amount);
        }

        public void transferOut(double amount, Account accountTo) {
            this.balance -= amount;
            accountTo.increaseBalance(amount);
        }

        public void deposit(double amount) {
            this.increaseBalance(amount);
        }

        public void withdraw(double amount) {
            this.decreaseBalance(amount);
        }

        public void increaseBalance (double amount){
            this.balance += amount;
        }

        public void decreaseBalance (double amount){
            this.balance -= amount;
        }

}
