package atm;

public class Checking extends Asset {

    public boolean primary;

    public Checking(String accountNum, String accountTxt){
        super(accountNum, accountTxt);
        primary = false;
    }

    //set this account as the primary account
    public void setPrimary(){primary = true;}

}
