package bankmanager;
import account.Account;
import atm.*;

public class TransactionManager extends BankManager{

    public TransactionManager(ATM atm) {
        super(atm);
    }

    public void undoTransaction(User usr, Account acct){
        // Allows Bank Manager to undo any type of transaction.
        if (acct.getLastTransaction() == null){
            System.out.println("No previous transactions");
        } else {
            String transactionType = acct.getLastTransaction().getTransactionType();

            if (transactionType.equalsIgnoreCase("deposit")) {
                undoDeposit(acct);
            } else if (transactionType.equals("withdraw")) {
                undoWithdraw(acct);
            } else if (transactionType.equalsIgnoreCase("transferin")){
                undoTransferIn(usr, acct);
            } else if (transactionType.equalsIgnoreCase("transferout")) {
                undoTransferOut(usr, acct);
            } else if (transactionType.equalsIgnoreCase("paybill")){
                undoPayBill(acct);
            }
        }
    }

    protected void undoDeposit(Account acct) {
        acct.subtractBalance(acct.getLastTransaction().getTransactionAmount());
        acct.removeLastTransactionFromList();
    }

    protected void undoWithdraw(Account acct) {
        acct.addBalance(acct.getLastTransaction().getTransactionAmount());
        acct.removeLastTransactionFromList();
    }

    protected void undoTransferIn(User usr, Account acct) {
        // TransferAct refers to the account that was transferred to.
        Account TransferAct = null;
        for (Account ac2:usr.getAccounts()){
            if (ac2.getAccountNum() == acct.getLastTransaction().getTransactionAccount()){
                TransferAct = ac2;
            }
        }
        if (TransferAct != null) {
            double amount = acct.getLastTransaction().getTransactionAmount();
            acct.subtractBalance(amount);
            TransferAct.addBalance(amount);
            acct.removeLastTransactionFromList();
        }
    }

    protected void undoTransferOut(User usr, Account acct) {
        // TransferAct refers to the account that was transferred from.
        Account TransferAct = null;
        for (Account ac2 : usr.getAccounts()) {
            if (ac2.getAccountNum() == acct.getLastTransaction().getTransactionAccount()) {
                TransferAct = ac2;
            }
        }
        if (TransferAct != null) {
            double amount = acct.getLastTransaction().getTransactionAmount();
            acct.addBalance(amount);
            TransferAct.subtractBalance(amount);
            acct.removeLastTransactionFromList();
        }
    }

    protected void undoPayBill(Account acct) {
        acct.addBalance(acct.getLastTransaction().getTransactionAmount());
        acct.removeLastTransactionFromList();
    }

}