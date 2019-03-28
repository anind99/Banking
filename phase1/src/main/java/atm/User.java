package atm;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {

    private String username;
    private String pass;
    protected ArrayList<Account> accounts;
    protected InvestmentPortfolio investments;

    public User(String username, String password, ArrayList accounts){
        this.username = username;
        this.pass = password;
        this.accounts = accounts;
    }

    public double getNetTotal(){
        double sum = 0;
        for (Account acc : accounts){
            if (acc.getType().equals("chequing") || acc.getType().equals("savings")){
            sum += acc.getBalance();
            }else{sum -= acc.getBalance();}
        }
        return sum;
    }

    public InvestmentPortfolio getInvestments(){
        return investments;
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public boolean enoughStockBalance(double amount){
        for (Account account : accounts){
            if (account.getType().equals("stock")){
                if (account.getBalance() >= amount){
                    return true;
                }
            }
        }return false;
    }

    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public  String getUsername(){ return username; }

    public String getPassword(){
        return this.pass;
    }

    public void setUsername(String s){
        this.username = s;
    }
    public void setPassword(String s){
        this.pass = s;
    }

    public Date getDateCreated(Account account) {
        return account.dateCreated.getTime();
    }

    public Transaction getLastTransaction(Account account) {
        return account.lastTransaction;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("User writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("User readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("User readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
