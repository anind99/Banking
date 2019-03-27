package atm;

public class StockBroker {

    private BankManager BM;

    void buyStocks(User user, String symbol, int shares) {
        boolean bought = false;
        boolean contains = false;
        for (Stock st: user.investments.stockPortfolio){
            if (st.symbol.equalsIgnoreCase(symbol)){
                if ((st.currentPrice * shares) <= getStockAct(user).balance){
                    getStockAct(user).balance -= st.currentPrice * shares;
                    st.numShares += shares;
                    bought = true;
                }
                contains = true;
            }
        }
        if (!contains){
            Stock st = fetchStock(symbol);
            if (st != null){
                if (st.currentPrice * shares <= getStockAct(user).balance){
                    user.investments.stockPortfolio.add(st);
                    st.numShares = shares;
                    getStockAct(user).balance -= st.currentPrice * shares;
                    bought = true;
                }
            } else {
                System.out.println("There is no stock of symbol: "+symbol);
            }
        }
        if (!bought)
            System.out.println("Stocks not purchase because of insufficient funds or invalid symbol");
    }


    private Stock fetchStock(String Symbol){
        // not implemented
        // returns a stock of Symbol symbol, that is fetched from API
        Stock st = new Stock("name", Symbol, 0, 0);
        try {
            st.updatePrice();
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
                st.updatePrice();
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
