package BankManager;

import atm.*;

public class AccountManager extends BankManager{
    // acct_counter refers to the account number that the Bank Manager sets for each account.
    protected Chequing newChequing(int acct_counter, ATM atm) {
        return new Chequing(acct_counter, atm);
    }

    public void create_account(User user, String acct_type){
        // Creates a new account as specified by the parameter.
        if (acct_type.equalsIgnoreCase("chequing")) {
            createAccountHelper(user, accountManager.newChequing(this.acct_counter, atm), "chequing");
        }
        else if (acct_type.equalsIgnoreCase("CreditCard")) {
            createAccountHelper(user, accountManager.newCreditCard(this.acct_counter, atm), "credit card");
        }
        else if (acct_type.equalsIgnoreCase("LOC")){
            createAccountHelper(user, accountManager.newLOC(this.acct_counter, atm), "line of credit");
        }

        else if (acct_type.equalsIgnoreCase("savings")){
            createAccountHelper(user, accountManager.newSavings(this.acct_counter, atm), "savings");
        } else if (acct_type.equalsIgnoreCase("stock")) {
            createAccountHelper(user, accountManager.newStockAccount(this.acct_counter, atm), "stock");
        }

        accountManager.checkForPrimary(user);

    }

    private void createAccountHelper(User user, Account account, String type){
        // Create account helper method for create_account. Increases acc_counter by 1 and adds the created account
        // to the user.
        user.getAccounts().add(account);
        this.acct_counter += 1;
        System.out.println("New " + type + " account created.");
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

    protected CreditCard newCreditCard(int acct_counter, ATM atm) {
        return new CreditCard(acct_counter, atm);
    }

    protected Savings newSavings(int acct_counter, ATM atm) {
        return new Savings(acct_counter, atm);
    }

    protected LOC newLOC(int acct_counter, ATM atm) {
        return new LOC(acct_counter, atm);
    }

    protected StockAccount newStockAccount(int acct_counter, ATM atm) {
        return new StockAccount(acct_counter, atm);
    }
}
