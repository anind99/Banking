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
            createChequingAccount(user, atm);
        }
        else if (acct_type.equalsIgnoreCase("CreditCard")) {
            createCreditCard(user, atm);
        }
        else if (acct_type.equalsIgnoreCase("LOC")){
            createLOC(user, atm);
        }
        else if (acct_type.equalsIgnoreCase("savings")){
            createSavingsAccount(user, atm);
        } else if (acct_type.equalsIgnoreCase("stock")) {
            createStockAccount(user, atm);
        }
        checkForPrimary(user);
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
                if (a.getType().equalsIgnoreCase("chequing")){
                    a.setPrimary();
                    break;}
            }
        }
    }

    protected void createChequingAccount(User user, ATM atm){
        user.getAccounts().add(new Chequing(acct_counter, atm));
        System.out.println("New chequing account created.");
        acct_counter+=1;
    }

    protected void createSavingsAccount(User user, ATM atm) {
        user.getAccounts().add(new Savings(acct_counter, atm));
        System.out.println("New savings account created.");
        acct_counter+=1;
    }

    protected void createCreditCard(User user, ATM atm) {
        user.getAccounts().add(new CreditCard(acct_counter, atm));
        System.out.println("New credit card created.");
        acct_counter+=1;
    }

    protected void createLOC(User user, ATM atm) {
        user.getAccounts().add(new LOC(acct_counter, atm));
        System.out.println("New Line of Credit created.");
        acct_counter+=1;
    }

    protected void createStockAccount(User user, ATM atm) {
        user.getAccounts().add(new StockAccount(acct_counter, atm));
        System.out.println("New Stock Account created.");
        acct_counter+=1;
    }
}
