package atm;

public class UndoTransaction {

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
            if (check_other_acct(usr, acct)) {
                TransferAct.removeLastTransactionFromList();
            }
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
            if (check_other_acct(usr, acct)) {
                TransferAct.removeLastTransactionFromList();
            }
            acct.removeLastTransactionFromList();
        }
    }

    protected void undoPayBill(Account acct) {
        acct.addBalance(acct.getLastTransaction().getTransactionAmount());
        acct.removeLastTransactionFromList();
    }

    private boolean check_other_acct(User usr, Account acct){
        Account otheract = null;

        String acctTransactionType = acct.getLastTransaction().getTransactionType();
        int acctNum = acct.getAccountNum();
        double acctTransactionAmount = acct.getLastTransaction().getTransactionAmount();

        for (Account ac2 : usr.getAccounts()) {
            if (ac2.getAccountNum() == acctNum){
                otheract = ac2;
            }
        }
        if (otheract == null || otheract.getLastTransaction() == null){
            return false;
        }
        return (otheract.getLastTransaction().getTransactionType().equalsIgnoreCase(acctTransactionType)
                && (otheract.getLastTransaction().getTransactionAccount() == acctNum)
                && (otheract.getLastTransaction().getTransactionAmount() == acctTransactionAmount));
    }
}
