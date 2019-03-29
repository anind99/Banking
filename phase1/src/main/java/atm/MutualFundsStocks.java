package atm;

import java.util.ArrayList;
import java.util.Calendar;

public class MutualFundsStocks {
    public ArrayList<Stock> lowRiskStocks = new ArrayList<>();
    public ArrayList<Stock> mediumRiskStocks = new ArrayList<>();
    public ArrayList<Stock> highRiskStocks = new ArrayList<>();
    public ATM atm = new ATM();
    public  Calendar date = atm.getDate();

    public MutualFundsStocks(){
        createLowRiskStocks();
        createMediumRiskStocks();
        createHighRiskStocks();
    }

    public void createLowRiskStocks(){
        lowRiskStocks.add(new Stock("MICROSOFT CORP","MSFT",1000));
        lowRiskStocks.add(new Stock("AMAZON COM INC","AMZN",1000));
        lowRiskStocks.add(new Stock("APPLE INC","AAPL",1000));
        lowRiskStocks.add(new Stock("ADOBE INC","ADBE",1000));
        lowRiskStocks.add(new Stock("FACEBOOK INC","FB",1000));
        lowRiskStocks.add(new Stock("NETFLIX INC","NFLX",1000));
        for (Stock stock : lowRiskStocks){stock.updateStock(date);}
    }

    public void createMediumRiskStocks(){
        mediumRiskStocks.add(new Stock("MICROSOFT CORP","MSFT", 1000));
        mediumRiskStocks.add(new Stock("AMAZON COM INC","AMZN", 1000));
        mediumRiskStocks.add(new Stock("APPLE INC","AAPL", 1000));
        mediumRiskStocks.add(new Stock("AURORA CANNABIS INC","ACB",1000));
        mediumRiskStocks.add(new Stock("HEXO CORP","HEXO",1000));
        mediumRiskStocks.add(new Stock("CRONOS GROUP INC","CRON",1000));
        for (Stock stock : mediumRiskStocks){stock.updateStock(date);}
    }

    public void createHighRiskStocks(){
        highRiskStocks.add(new Stock("AURORA CANNABIS INC","ACB",1000));
        highRiskStocks.add(new Stock("HEXO CORP","HEXO",1000));
        highRiskStocks.add(new Stock("CRONOS GROUP INC","CRON",1000));
        highRiskStocks.add(new Stock("CANOPY GROWTH CORPORATION","CGC",1000));
        highRiskStocks.add(new Stock("CENTURYLINK INC","CTL",1000));
        highRiskStocks.add(new Stock("ALTRIA GROUP INC","MO",1000));
        for (Stock stock : highRiskStocks){stock.updateStock(date);}
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

