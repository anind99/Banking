package atm;

import BankManager.BankManager;

public class StockBroker {

    private BankManager BM;
    private ATM atm;

    StockBroker(BankManager bm){
        this.BM = bm;
    }

    void buyStocks(String symbol, int shares, StockAccount sa, InvestmentPortfolio Iv) {
        boolean bought = false;
        boolean contains = false;
        for (Stock st: Iv.stockPortfolio){
            if (st.symbol.equalsIgnoreCase(symbol)){
                if ((st.currentPrice * shares) <= sa.balance){
                   sa.balance -= st.currentPrice * shares;
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

    private boolean buyNewStock(String symbol, int shares,  StockAccount sa, InvestmentPortfolio Iv){

            Stock st = fetchStock(symbol);
            if (st != null){
                if (st.currentPrice * shares <= sa.balance){
                    Iv.stockPortfolio.add(st);
                    st.numShares = shares;
                   sa.balance -= st.currentPrice * shares;
                    return  true;
                }
            } else {
                System.out.println("There is no stock of symbol: "+symbol);
            }
        return false;
    }

    void sellStocks(String Symbol, int shares){

    }

    private Stock fetchStock(String Symbol){
        // not implemented
        // returns a stock of Symbol symbol, that is fetched from API
        Stock st = new Stock("name", Symbol, 0, 0);
        try {
            st.updatePrice(atm.getDate());
            return st;
        } catch (Exception e) {
            return null;
        }
    }

    void sellStocks(User user, String stock, int shares) {
        boolean sold = false;
        for (Stock st: user.investments.stockPortfolio){
            if (st.name.equalsIgnoreCase(stock)){
                if (shares <= st.numShares) {
                    st.numShares -= shares;
                    StockAccount SA = getStockAct(user);
                    if (SA == null){
                        BM.create_account(user, "Stock");
                    }
                    SA.addMoney(shares * st.currentPrice);
                    sold = true;
                    break;
                }
            }
        }
        if (!sold){
            System.out.println("Not enough shares, or stock is not owned by user: "+user);
        }
    }

    private StockAccount getStockAct(User user){
        for (Account act: user.getAccounts()){
            if (act instanceof StockAccount){
                return (StockAccount)act;
            }
        }
        return null;
    }

    void updateStocks(ATM atm) {
        for (User user:atm.getListOfUsers()){
            for (Stock st:user.investments.stockPortfolio){
                st.updatePrice(atm.getDate());
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


}
