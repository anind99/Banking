package atm;

import account.Account;
import investments.InvestmentPortfolio;
import subscriptions.Subscription;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {

    private String username;
    private String pass;
    protected ArrayList<Account> accounts;
    protected InvestmentPortfolio investmentPortfolio;
    protected ArrayList<Subscription> subscriptions;

    public User(String username, String password, ArrayList<Account> accounts){
        this.username = username;
        this.pass = password;
        this.accounts = accounts;
        this.subscriptions = new ArrayList<>();
        this.investmentPortfolio = new InvestmentPortfolio();
    }

    public double getNetTotal(){
        double sum = 0;
        for (Account acc : accounts){
            if (acc.getType().equals("chequing") || acc.getType().equals("savings") || acc.getType().equals("stock")) {
            sum += acc.getBalance();
            }else{sum -= acc.getBalance();}
        }
        return sum;
    }

    public InvestmentPortfolio getInvestmentPortfolio(){
        return investmentPortfolio;
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

    public ArrayList<Subscription> getSubscriptions(){
        return this.subscriptions;
    }

    public void addSubscription(Subscription s){
        subscriptions.add(s);
    }

    public void removeAllSubscriptions(){
        subscriptions = new ArrayList<>();
    }

    public void removeSubsciption(String name){
        for (Subscription sub: subscriptions){
            if (sub.getName().equalsIgnoreCase(name)){
                subscriptions.remove(sub);
            }
        }
    }

    public void setUsername(String s){
        this.username = s;
    }

    public void setPassword(String s){
        this.pass = s;
    }

    public Date getDateCreated(Account account) {
        return account.getDateCreated().getTime();
    }

    public Transaction getLastTransaction(Account account) {
        return account.getLastTransaction();
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
