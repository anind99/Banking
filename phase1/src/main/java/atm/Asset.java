package atm;

public abstract class Asset extends Account{

    public Asset(int accountNum, ATM atm) {

        super(accountNum, atm);
    }

        //Adding money to an asset account will increase its balance
        public void addMoney(double amount){balance += amount;}

        //Removing money from an asset account will decrease its balance this will be implemented in children
        // but if it creases might need to implement is as an abstract method here as well
        //public abstract boolean removeMoney(double amount); { balance -= amount;


    }

