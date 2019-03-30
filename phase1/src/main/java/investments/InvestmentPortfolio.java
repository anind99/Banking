package investments;

import java.util.ArrayList;
import java.util.HashMap;

public class InvestmentPortfolio {
    ArrayList <Stock> stockPortfolio = new ArrayList<>();
    HashMap<MutualFund , ArrayList<Double>> mutualFundsPortfolio = new HashMap<>();
    // maps name of the fund: [amount user invested, %owned of the fund through this investment]

    public ArrayList <Stock> getStockPortfolio(){
        return stockPortfolio;
    }

    public void setMutualFundsPortfolio(MutualFund fund, ArrayList<Double> value){
        mutualFundsPortfolio.put(fund, value);
    }

    public HashMap<MutualFund, ArrayList<Double>> getMutualFundPortfolio(){
        return mutualFundsPortfolio;
    }
}
