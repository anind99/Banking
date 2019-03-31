package broker;

import account.Account;
import account.Asset;
import atm.ATM;
import atm.User;
import investments.InvestmentPortfolio;
import investments.MutualFundsStocks;
import investments.Stock;

import java.io.*;
import java.util.ArrayList;

public class StockBroker implements Serializable {

    /**
     * Class to Implement Functions of Broker related to stocks.
     * It has an attribute function of type ATM, so all the methods
     * can be calibrated to ATM.date.
     *
     * Methods:
     *
     * buyStocks() : Purchases a number of stocks and stores into the user portfolio.
     * buyNewStock(): Helper function to buyStocks(); purchases a stock not already
     * in the user portfolio.
     * fetchStock(): Creates of a new stock of the given symbol.
     * sellStocks(): Sells the stock with the given symbol from user portfolio.
     * getTotalStockWorth(): Prints the total net worth of the user in Stocks.
     * viewUserStocks(): Prints all the Stocks and Shares owned by the user.
     * updateAllStocks(): Updates every stock owned by all the users.
     */

    private ATM atm;
    Broker broker;

    StockBroker(ATM Atm, Broker broker){
        this.atm = Atm;
        this.broker = broker;
    }

    /**
     *buyStocks():
     * Buys stocks of given share amount for a user.
     *
     * @param symbol: the symbol in String of the stock the user wants to purchase.
     * @param Iv : The user's investment portfolio.
     * @param sa : The user's stocks account.
     * @param shares : The number of shares to be purchased.
     */

    public void buyStocks(String symbol, int shares, Account sa, InvestmentPortfolio Iv) {

        boolean bought = false;
        boolean contains = false;
        if (shares > 0) {
            for (Stock st : Iv.getStockPortfolio()) {
                if (st.getSymbol().equalsIgnoreCase(symbol)) {
                    if ((st.getValue() * shares) <= sa.getBalance()) {
                        sa.removeMoney(st.getValue() * shares);
                        st.increaseNumShares(shares);
                        bought = true;
                    }
                    contains = true;
                }
            }
        }
        if (shares <= 0){
            System.out.println("Enter Share amount greater than 0");
        } else if (!contains){
            bought = buyNewStock(symbol, shares, sa, Iv);
        }
        if (!bought){
            System.out.println("Stocks not purchase because of insufficient funds or invalid symbol");}
    }


    /**
     * buyNewStock(): Helper function to buyStocks(), purchases a stock not already owned by User.
     *
     * @param symbol: the symbol in String of the stock the user wants to purchase.
     * @param shares: The number of shares to be purchased.
     * @param sa: The user's stocks account.
     * @param Iv: The user's investment portfolio.
     */

    private boolean buyNewStock(String symbol, int shares, Account sa, InvestmentPortfolio Iv){
        boolean valid = atm.getBroker().checkIfStockIsValid(symbol);
        if (valid){
        Stock st = fetchStock(symbol);
        if (st.getValue() != 0 && shares > 0){
            if (st.getValue() * shares <= sa.getBalance()){
                Iv.getStockPortfolio().add(st);
                st.setNumShares(shares);
                sa.removeMoney(st.getValue() * shares);
                return true;
            }
        } else {
            if (shares <= 0){
                System.out.println("Enter share amount greater than 0");
            }
            else {
                System.out.println("There is no stock of symbol: " + symbol);
            }
        }}
        return false;
    }

    /**
     * fetchStock(): Returns a stock of the given symbol.
     * @param symbol: Symbol of stock.
     * @return Stock object of given symbol
     */


    public Stock fetchStock(String symbol){
        String stockName = atm.getBroker().companyNameFromSymbol(symbol);
        Stock st = new Stock(symbol, stockName, 0);
        st.updateStock(atm.getDate());
        return st;
    }


    /**
     * sellStocks(): sells stocks of given symbol and share amount for user.
     * @param SA: The user's stocks account.
     * @param symbol: Symbol of stock to be sold.
     * @param shares: Number of shares to be sold.
     * @param IV: The user's stock portfolio.
     */

    public void sellStocks(Asset SA, String symbol, int shares, InvestmentPortfolio IV) {
        boolean sold = false;
        for (Stock st: IV.getStockPortfolio()){
            if (st.getSymbol().equalsIgnoreCase(symbol)){
                if (shares <= st.getNumShares()) {
                    st.decreaseNumShares(shares);
                    SA.addMoney(shares * st.getValue());
                    sold = true;
                    break;
                }
            }
        }
        if (!sold){
            System.out.println("Not enough shares, or stock is not owned by user. ");
        }
    }


    /**
     * updateAllStocks(): Updates all the stocks in the atm to their current value.
     * @param atm: atm to be updated.
     */

    public void updateAllStocks(ATM atm) {
        for (User user:atm.getListOfUsers()){
            for (Stock st:user.getInvestmentPortfolio().getStockPortfolio()){
                st.updateStock(atm.getDate());
            }
        }
    }

    /**
     * getTotalStockWorth(): Returns the total money a user owns in stocks.
     * @param user: User that owns stocks.
     * @return Amount of type double, of the user's networth in Stocks.
     */

    public double getTotalStockWorth(User user){
        double total = 0.0;
        for (Stock st: user.getInvestmentPortfolio().getStockPortfolio()){
            total += st.getValue() * st.getNumShares();
        }
        return total;
    }

    /**
     * viewUserStocks(): Prints all the stocks and shares of a user.
     * @param user: user object.
     */

    public void viewUserStocks(User user){
        ArrayList<Stock> Iv = user.getInvestmentPortfolio().getStockPortfolio();
        System.out.println("User Currently owns: "+Iv.size()+" types of stocks");
        for (Stock st: Iv){
            System.out.println("Stock: "+st.getSymbol()+" Shares: "+st.getNumShares());
        }
    }

    public String stocksToString(User user){
        String totalStocks = "";
        for (Stock stock : user.getInvestmentPortfolio().getStockPortfolio()){
            totalStocks += stock.toString();
        } return totalStocks + "Total value of all your stocks:" + getTotalStockWorth(user);
    }

    public boolean checkIfUserHasStock(User user, String symbol) {
        for (Stock stock : user.getInvestmentPortfolio().getStockPortfolio()) {
            if (stock.getSymbol().equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("StockBroker writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("StockBroker readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("StockBroker readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}

