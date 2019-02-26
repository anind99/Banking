package atm;

public class Asset extends Account{

    public Asset(String accountNum, String accountTxt){
        super(accountNum, accountTxt);

   }

    public void transferIn(double amount, Account acct_from) {
        //this.increase_balance(amount);
        //acct_from.decrease_balance(amount)
    }

    public void transferOut(double amount, Account acct_from) {
        //this.increase_balance(amount);
        //acct_from.decrease_balance(amount)
    }

    public void deposit (double amount) {
        //this.increase_balance(amount);
        //acct_from.decrease_balance(amount)
    }

    public void withdraw (double amount) {
        //this.increase_balance(amount);
        //acct_from.decrease_balance(amount)
    }

}
