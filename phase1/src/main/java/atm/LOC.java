package atm;

public class LOC extends Debt{

    public LOC(int accountNum, ATM atm) {
        super(accountNum, atm);
        this.type = "LOC";
    }
}
