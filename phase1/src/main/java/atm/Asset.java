package atm;

public class Asset extends Account{

    public Asset(String accountNum, String accountTxt) {
        super(accountNum, accountTxt);
    }

        //Adding money to an asset account will increase its balance
        public void addMoney(double amount){balance += amount;}

        //Removing money from an asset account will decrease its balance
        public void removeMoney(double amount){ balance -= amount;}
}
