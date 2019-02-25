package atm;

public class Asset extends Account{
    public Asset(String acct_num, double balance, String date_created, String acct_txt ){
        super (acct_num, balance, date_created, acct_txt);

   }

    public void transfer_in(double amount, Account acct_from) {
        //this.increase_balance(amount);
        //acct_from.decrease_balance(amount)
    }

    public void transfer_out(double amount, Account acct_from) {
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
