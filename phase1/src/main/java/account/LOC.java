package account;

import atm.ATM;

public class LOC extends Debt{

    public LOC(int accountNum, ATM atm) {
        super(accountNum, atm);
        this.type = "LOC";
    }
}
