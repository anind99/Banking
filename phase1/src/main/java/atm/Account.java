package atm;
//import
import java.util.Date;

public class Account {

        public String accountTxt;
        public String accountNum;
        protected double balance;
        public Transaction lastTransaction;
        public Date dateCreated;


        public Account(String accountNum, String accountTxt) {
            this.accountNum = accountNum;
            this.accountTxt = accountTxt;
            this.balance = 0;
            this.lastTransaction = null;
            this.dateCreated = new Date();

        }

        public double getBalance(){
            return this.balance;
        }

        public void transferIn(double amount, Account accountFrom) {
//            this.balance += amount;
//            accountFrom.decreaseBalance(amount);
        }

        public void transferOut(double amount, Account accountTo) {
//            this.balance -= amount;
//            accountTo.increaseBalance(amount);
        }

        public void deposit(double amount) {
//            this.increaseBalance(amount);
        }

        public void withdraw(double amount) {
//            this.decreaseBalance(amount);
        }

        public void addMoney (double amount){
        }

        public void removeMoney (double amount){
        }

}
