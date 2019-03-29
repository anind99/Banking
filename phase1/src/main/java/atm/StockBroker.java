package atm;

import account.Account;
import account.Asset;
import bankmanager.BankManager;

import java.util.ArrayList;

public class StockBroker {

    private BankManager BM;
    private ATM atm;

    StockBroker(ATM Atm){
        this.atm = Atm;
    }

    void buyStocks(String symbol, int shares, Account sa, InvestmentPortfolio Iv) {
        boolean bought = false;
        boolean contains = false;
        for (Stock st: Iv.stockPortfolio){
            if (st.symbol.equalsIgnoreCase(symbol)){
                if ((st.currentPrice * shares) <= sa.getBalance()){
                    sa.subtractBalance(st.currentPrice * shares);
                    st.numShares += shares;
                    bought = true;
                }
                contains = true;
            }
        }
        if (!contains){
            bought = buyNewStock(symbol, shares, sa, Iv);
        }
        if (!bought)
            System.out.println("Stocks not purchase because of insufficient funds or invalid symbol");
    }

    private boolean buyNewStock(String symbol, int shares, Account sa, InvestmentPortfolio Iv){

        Stock st = fetchStock(symbol);
        if (st != null){
            if (st.currentPrice * shares <= sa.getBalance()){
                Iv.stockPortfolio.add(st);
                st.numShares = shares;
                sa.subtractBalance(st.currentPrice * shares);
                return true;
            }
        } else {
            System.out.println("There is no stock of symbol: "+symbol);
        }
        return false;
    }


    private Stock fetchStock(String Symbol){
        // not implemented
        // returns a stock of Symbol symbol, that is fetched from API
        Stock st = new Stock("name", Symbol, 0);
        try {
            st.updateStock(atm.getDate());
            return st;
        } catch (Exception e) {
            return null;
        }
    }

    void sellStocks(Asset SA, String stock, int shares, InvestmentPortfolio IV) {
        boolean sold = false;
        for (Stock st: IV.stockPortfolio){
            if (st.name.equalsIgnoreCase(stock)){
                if (shares <= st.numShares) {
                    st.numShares -= shares;
                    SA.addMoney(shares * st.currentPrice);
                    sold = true;
                    break;
                }
            }
        }
        if (!sold){
            System.out.println("Not enough shares, or stock is not owned by user. ");
        }
    }



    void updateStocks(ATM atm) {
        for (User user:atm.getListOfUsers()){
            for (Stock st:user.investments.stockPortfolio){
                st.updateStock(atm.getDate());
            }
        }
    }

    double getTotalStockWorth(User user){
        double total = 0.0;
        for (Stock st: user.investments.stockPortfolio){
            total += st.currentPrice * st.numShares;
        }
        return total;
    }

    void viewUserStocks(User user){
        ArrayList<Stock> Iv = user.investments.stockPortfolio;
        System.out.println("User Currently owns: "+Iv.size()+" types of stocks");
        for (Stock st: Iv){
            System.out.println("Stock: "+st.symbol+" Shares: "+st.numShares);
        }
    }


}

