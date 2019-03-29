package bankmanager;

import account.*;
import atm.*;

public class AccountManager extends BankManager{

    public AccountManager(ATM atm){
        super(atm);
    }

    public void create_account(User user, String acct_type){
        // Creates a new account as specified by the parameter.
        if (acct_type.equalsIgnoreCase("chequing")) {
            updateUserAccounts(user, createChequingAccount(this.acct_counter, atm), "chequing");
        }
        else if (acct_type.equalsIgnoreCase("CreditCard")) {
            updateUserAccounts(user, createCreditCard(this.acct_counter, atm), "credit card");
        }
        else if (acct_type.equalsIgnoreCase("LOC")){
            updateUserAccounts(user, createLOC(this.acct_counter, atm), "line of credit");
        }
        else if (acct_type.equalsIgnoreCase("savings")){
            updateUserAccounts(user, createSavingsAccount(this.acct_counter, atm), "savings");
        } else if (acct_type.equalsIgnoreCase("stock")) {
            updateUserAccounts(user, createStockAccount(this.acct_counter, atm), "stock");
        }

        checkForPrimary(user);

    }

    private void updateUserAccounts(User user, Account account, String type){
        // Create account helper method for create_account. Increases acc_counter by 1 and adds the created account
        // to the user.
        user.getAccounts().add(account);
        System.out.println("New " + type + " account created.");
        this.acct_counter += 1;
    }

    protected void checkForPrimary(User user) {
        // Checks for primary account. The first chequing account the user makes is always the primary account.
        boolean primary = false;
        for (Account a : user.getAccounts()) {
            if(a.isPrimary()) {
                primary = true;
            }
        }if(!primary){
            for (Account a : user.getAccounts()) {
                if (a.getType().equals("chequing")){
                    ((Chequing)a).setPrimary();
                    break;}}}
    }

    protected Chequing createChequingAccount(int account_counter, ATM atm){
        return new Chequing(acct_counter, atm);
    }

    protected Savings createSavingsAccount(int acct_counter, ATM atm) {
        return new Savings(acct_counter, atm);
    }

    protected CreditCard createCreditCard(int acct_counter, ATM atm) {
        return new CreditCard(acct_counter, atm);
    }

    protected LOC createLOC(int acct_counter, ATM atm) {
        return new LOC(acct_counter, atm);
    }

    protected StockAccount createStockAccount(int acct_counter, ATM atm) {
        return new StockAccount(acct_counter, atm);
    }
}
