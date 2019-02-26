package atm;

public class Checking extends Asset {

    public boolean primary;

    public Checking(String accountNum){
        super(accountNum);
        primary = false;
    }

    //set this account as the primary account
    public void setPrimary(){primary = true;}

}
