package atm;

public class Asset extends Account{

    public Asset(String accountNum, String accountTxt) {
        super(accountNum, accountTxt);
    }

        public void addMoney(double amount){balance += amount;}

        public void removeMoney(double amount){ balance -= amount;}
}
