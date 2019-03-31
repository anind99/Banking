package bankmanager;
import account.Account;
import atm.*;

import java.io.*;
import java.util.ArrayList;

public class TransactionManager implements Serializable {

    public void undoTransaction(User usr, Account acct){
        // Allows Bank Manager to undo any type of transaction.
        if (acct.getLastTransaction() == null){
            System.out.println("No previous transactions");
        } else {
            String transactionType = acct.getLastTransaction().getTransactionType();

            if (transactionType.equalsIgnoreCase("deposit")) {
                System.out.println("Deposit Transactions Cannot be undone!");
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

//    protected void undoDeposit(Account acct) {
//        acct.removeMoney(acct.getLastTransaction().getTransactionAmount());
//        removeLastTransactionFromList(acct);
//    }

    protected void undoWithdraw(Account acct) {
        acct.addMoney(acct.getLastTransaction().getTransactionAmount());
        removeLastTransactionFromList(acct);
    }

    protected void undoTransferIn(User usr, Account acct) {
        // TransferAct refers to the account that was transferred to.
        Account TransferAcct = null;
        for (Account acct2:usr.getAccounts()){
            if (acct2.getAccountNum() == acct.getLastTransaction().getTransactionAccount()){
                TransferAcct = acct2;
            }
        }
        if (TransferAcct != null) {
            double amount = acct.getLastTransaction().getTransactionAmount();
            acct.removeMoney(amount);
            TransferAcct.addMoney(amount);
            removeLastTransactionFromList(acct);
        }
    }

    protected void undoTransferOut(User usr, Account acct) {
        // TransferAct refers to the account that was transferred from.
        Account TransferAcct = null;
        for (Account acct2 : usr.getAccounts()) {
            if (acct2.getAccountNum() == acct.getLastTransaction().getTransactionAccount()) {
                TransferAcct = acct2;
            }
        }
        if (TransferAcct != null) {
            double amount = acct.getLastTransaction().getTransactionAmount();
            acct.addMoney(amount);
            TransferAcct.removeMoney(amount);
            removeLastTransactionFromList(acct);
        }
    }

    protected void undoPayBill(Account acct) {
        acct.addMoney(acct.getLastTransaction().getTransactionAmount());
        removeLastTransactionFromList(acct);
    }

    public void removeLastTransactionFromList(Account account) {
        // Remove the last transaction from listOfTransactions.
        ArrayList<Transaction> lst = account.getListOfTransactions();
        lst.remove(lst.size() - 1);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("BM writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("BM readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("BM readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
