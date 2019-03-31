package bankmanager;

import account.*;
import atm.*;

import java.io.*;


public class AccountManager implements Serializable {

    ATM atm;
    private int acct_counter = 1000;

    public AccountManager(ATM atm){
        this.atm = atm;
    }

    /***
     * Creates an account for user. The type of account is specified by acct_type.
     *
     * @param user the user that wants to create a new account
     * @param acct_type the type of account that user wants to create
     */
    public void createAccount(User user, String acct_type){
        // Creates a new account as specified by the parameter.
        if (acct_type.equalsIgnoreCase("chequing")) {
            createChequingAccount(user, atm);
        }
        else if (acct_type.equalsIgnoreCase("creditcard")) {
            createCreditCard(user, atm);
        }
        else if (acct_type.equalsIgnoreCase("loc")){
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

    /***
     * Creates a new chequing account.
     *
     * @param user the user that wants to create a new chequing account
     * @param atm the ATM that user uses to create the new chequing account
     */
    private void createChequingAccount(User user, ATM atm){
        user.getAccounts().add(new Chequing(acct_counter, atm));
        System.out.println("New chequing account created.");
        acct_counter+=1;
    }

    /***
     * Creates a new savings account for user.
     *
     * @param user the user that wants to create the new savings account
     * @param atm the ATM that user uses to create the new savings account
     */
    private void createSavingsAccount(User user, ATM atm) {
        user.getAccounts().add(new Savings(acct_counter, atm));
        System.out.println("New savings account created.");
        acct_counter+=1;
    }

    /***
     * Creates a new credit card account for user.
     *
     * @param user the user that wants to create the new credit card account
     * @param atm the ATM that user uses to create the credit card account
     */
    private void createCreditCard(User user, ATM atm) {
        user.getAccounts().add(new CreditCard(acct_counter, atm));
        System.out.println("New credit card created.");
        acct_counter+=1;
    }

    /***
     * Creates a new line of credit account for user.
     *
     * @param user the user that wants to create the line of credit account
     * @param atm the ATM that user uses to create the line of credit account
     */
    protected void createLOC(User user, ATM atm) {
        user.getAccounts().add(new LOC(acct_counter, atm));
        System.out.println("New Line of Credit created.");
        acct_counter+=1;
    }

    /***
     * Creates a new stock account for user.
     *
     * @param user the user that wants to create the stock account
     * @param atm the ATM that user uses to create the stock account
     */
    protected void createStockAccount(User user, ATM atm) {
        user.getAccounts().add(new StockAccount(acct_counter, atm));
        System.out.println("New Stock Account created.");
        acct_counter+=1;
    }

    /***
     * Creates a joint account with two users.
     *
     * @param user1 the user that wants to create a joint account
     * @param user2 the other user that wants to create a joint account
     * @param accountType the type of account to be created
     */
    public void createJointAccount(User user1, User user2, String accountType) {
        createAccount(user1, accountType);
        Account account = user1.getAccounts().get(user1.getAccounts().size()-1);
        addExistingUserToAccount(user2, account);
    }

    /***
     * Adds an existing user to an existing account that belongs to another user. Up to two people are allowed to share
     * one account.
     *
     * @param user the user that is to be added to the account
     * @param account the account that is to be joined
     */
    public void addExistingUserToAccount(User user, Account account) {
        if (account.getIsJoint()) {
            System.out.println("Not possible to add a user this account because there are already two users sharing" +
                    "this account!");
        } else {
            user.getAccounts().add(account);
            account.setIsJoint(true);
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("AM writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("AM readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("AM readObjectNoData, this should never happen!");
        System.exit(-1);
    }

}
