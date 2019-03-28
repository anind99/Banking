package atm;

import java.util.ArrayList;
import java.util.HashMap;

public class InvestmentPortfolio {
    ArrayList <Stock> stockPortfolio = new ArrayList<>();
    HashMap<String, ArrayList<Double>> mutualFundsPortfolio = new HashMap<>();
    // maps name of the fund: [amount user invested, amount fund was worth at the time of investment]

    public ArrayList <Stock> getStockPortfolio(){
        return stockPortfolio;
    }

    public void setMutualFundsPortfolio(MutualFund fund, ArrayList<Double> value){
        mutualFundsPortfolio.put(fund.getName(), value);
    }
}
