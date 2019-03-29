package atm.BankManager;

import atm.*;

public class CreateAccount {

    // acct_counter refers to the account number that the Bank Manager sets for each account.
    protected Chequing newChequing(int acct_counter, ATM atm) {
        return new Chequing(acct_counter, atm);
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
