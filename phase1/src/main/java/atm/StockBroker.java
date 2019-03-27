package atm;

public class StockBroker {

    private BankManager BM;

    void buyStocks(User user, String symbol, int shares) {

    }

    Stock fetchStock(String Symbol){
        // not implemented
        // returns a stock of Symbol symbol, that is fetched from API
        return null;
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
