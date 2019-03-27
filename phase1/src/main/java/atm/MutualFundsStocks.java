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
        lowRiskStocks.add(new Stock(0.0, 1000));
        lowRiskStocks.add(new Stock(0.0, 1000));
        lowRiskStocks.add(new Stock(0.0, 1000));
        lowRiskStocks.add(new Stock(0.0, 1000));
        lowRiskStocks.add(new Stock(0.0,1000));
        lowRiskStocks.add(new Stock(0.0,1000));
        for (Stock stock : lowRiskStocks){stock.updatePrice();}
    }

    public void createMediumRiskStocks(){
        mediumRiskStocks.add(new Stock(0.0,1000));
        mediumRiskStocks.add(new Stock(0.0,1000));
        mediumRiskStocks.add(new Stock(0.0,1000));
        mediumRiskStocks.add(new Stock(0.0,1000));
        mediumRiskStocks.add(new Stock(0.0,1000));
        mediumRiskStocks.add(new Stock(0.0,1000));
        for (Stock stock : mediumRiskStocks){stock.updatePrice();}
    }

    public void createHighRiskStocks(){
        highRiskStocks.add(new Stock(0.0,1000));
        highRiskStocks.add(new Stock(0.0,1000));
        highRiskStocks.add(new Stock(0.0,1000));
        highRiskStocks.add(new Stock(0.0,1000));
        highRiskStocks.add(new Stock(0.0,1000));
        highRiskStocks.add(new Stock(0.0,1000));
        for (Stock stock : highRiskStocks){stock.updatePrice();}
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

