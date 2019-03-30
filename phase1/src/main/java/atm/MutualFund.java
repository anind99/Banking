package atm;

import java.util.ArrayList;
import java.util.HashMap;

public class MutualFund {
    public int risk; // 1 is low risk, 2 is medium risk, 3 is high risk
    public String name;
    public ArrayList<Stock> stocks; // a list of stocks in this mutual fund
    public HashMap<User, ArrayList<Double>> investors = new HashMap<>();
    // maps user: [amount user invested, amount fund was worth at the time of investment]

    public MutualFund(int risk, String name, ArrayList<Stock> stocks){
        this.risk = risk;
        this.name = name;
        this.stocks = stocks;
    }

    public double getValue(){
        double total = 0.0;
        for (Stock stock : stocks) {
            total += stock.getValue();
        }return total;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Stock> getStocks(){
        return stocks;
    }

    public void setInvestors(User user, ArrayList<Double> investment){
        investors.put(user, investment);
        }

    public void setSharesStocks(int num) {
        for (Stock stock : stocks) {
            stock.increaseNumShares(num);
        }
    }

    public int getShares(){
        return stocks.get(0).getNumShares();
    }
}
