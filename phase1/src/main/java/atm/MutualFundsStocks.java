package atm;

import java.util.ArrayList;

public class MutualFundsStocks {
    public ArrayList<Stock> lowRiskStocks = new ArrayList<>();
    public ArrayList<Stock> mediumRiskStocks = new ArrayList<>();
    public ArrayList<Stock> highRiskStocks = new ArrayList<>();

    public MutualFundsStocks(){
        createLowRiskStocks();
        createMediumRiskStocks();
        createHighRiskStocks();
    }

    public void createLowRiskStocks(){
        lowRiskStocks.add(new Stock());
        lowRiskStocks.add(new Stock());
        lowRiskStocks.add(new Stock());
        lowRiskStocks.add(new Stock());
        lowRiskStocks.add(new Stock());
        lowRiskStocks.add(new Stock());
    }

    public void createMediumRiskStocks(){
        mediumRiskStocks.add(new Stock());
        mediumRiskStocks.add(new Stock());
        mediumRiskStocks.add(new Stock());
        mediumRiskStocks.add(new Stock());
        mediumRiskStocks.add(new Stock());
        mediumRiskStocks.add(new Stock());
    }

    public void createHighRiskStocks(){
        highRiskStocks.add(new Stock());
        highRiskStocks.add(new Stock());
        highRiskStocks.add(new Stock());
        highRiskStocks.add(new Stock());
        highRiskStocks.add(new Stock());
        highRiskStocks.add(new Stock());
    }

    public ArrayList<Stock> getLowRiskStocks(){
        return lowRiskStocks;
    }

    public ArrayList<Stock> getMediumRiskStocks(){
        return mediumRiskStocks;
    }

    public ArrayList<Stock> getHighRiskStocks(){
        return highRiskStocks;
    }
}

