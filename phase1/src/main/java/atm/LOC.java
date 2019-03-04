package atm;

public class LOC extends Debt{
    public String type = "LOC";
    public LOC(int accountNum) {
        super(accountNum);
        this.type = "LOC";
    }
}
