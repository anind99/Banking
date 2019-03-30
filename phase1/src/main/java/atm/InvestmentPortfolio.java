package atm;

import java.util.ArrayList;
import java.util.HashMap;

public class InvestmentPortfolio {
    ArrayList <Stock> stockPortfolio = new ArrayList<>();
    HashMap<MutualFund , ArrayList<Double>> mutualFundsPortfolio = new HashMap<>();
    // maps name of the fund: [amount user invested, amount fund was worth at the time of investment]

    public ArrayList <Stock> getStockPortfolio(){
        return stockPortfolio;
    }

    public void setMutualFundsPortfolio(MutualFund fund, ArrayList<Double> value){
        mutualFundsPortfolio.put(fund, value);
    }

    public HashMap<MutualFund, ArrayList<Double>> getMutualFundPortfolio(){
        return mutualFundsPortfolio;
    }

    public double stocksTotalValue() {
        double totalValue = 0.0;
        for (Stock stock : stockPortfolio) {
            totalValue += stock.getValue()*stock.getNumShares();
        }
        return totalValue;
    }

    public String stocksToString(){
        String totalStocks = "";
        for (Stock stock : stockPortfolio){
            totalStocks += stock.toString();
        } return totalStocks + "Total value of all your stocks:" + stocksTotalValue();
    }

}
