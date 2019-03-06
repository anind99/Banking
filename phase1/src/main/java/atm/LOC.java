package atm;

public class LOC extends Debt{
    public final String type;
    public LOC(int accountNum) {
        super(accountNum);
        this.type = "LOC";
    }
}
