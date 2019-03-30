package atm;

import java.util.ArrayList;
import java.util.Calendar;

public class MutualFundsStocks {
    public ArrayList<Stock> lowRiskStocks = new ArrayList<>();
    public ArrayList<Stock> mediumRiskStocks = new ArrayList<>();
    public ArrayList<Stock> highRiskStocks = new ArrayList<>();
    public ATM atm;
    public  Calendar date;

    public MutualFundsStocks(ATM atm){
        this.atm = atm;
        date = atm.getDate();
        createLowRiskStocks();
        createMediumRiskStocks();
        createHighRiskStocks();
    }

    public void createLowRiskStocks(){
        lowRiskStocks.add(new Stock("MICROSOFT CORP","MSFT",0.0));
        lowRiskStocks.add(new Stock("AMAZON COM INC","AMZN",0.0));
        lowRiskStocks.add(new Stock("APPLE INC","AAPL",0.0));
        lowRiskStocks.add(new Stock("ADOBE INC","ADBE",0.0));
        lowRiskStocks.add(new Stock("FACEBOOK INC","FB",0.0));
        lowRiskStocks.add(new Stock("NETFLIX INC","NFLX",0.0));
        for (Stock stock : lowRiskStocks){
            stock.increaseNumShares(1000);
            stock.updateStock(date);}
    }

    public void createMediumRiskStocks(){
        mediumRiskStocks.add(new Stock("MICROSOFT CORP","MSFT", 0.0));
        mediumRiskStocks.add(new Stock("AMAZON COM INC","AMZN", 0.0));
        mediumRiskStocks.add(new Stock("APPLE INC","AAPL", 0.0));
        mediumRiskStocks.add(new Stock("AURORA CANNABIS INC","ACB",0.0));
        mediumRiskStocks.add(new Stock("HEXO CORP","HEXO",0.0));
        mediumRiskStocks.add(new Stock("CRONOS GROUP INC","CRON",0.0));
        for (Stock stock : mediumRiskStocks){
            stock.increaseNumShares(1000);
            stock.updateStock(date);}
    }

    public void createHighRiskStocks(){
        highRiskStocks.add(new Stock("AURORA CANNABIS INC","ACB",0.0));
        highRiskStocks.add(new Stock("HEXO CORP","HEXO",0.0));
        highRiskStocks.add(new Stock("CRONOS GROUP INC","CRON",0.0));
        highRiskStocks.add(new Stock("CANOPY GROWTH CORPORATION","CGC",0.0));
        highRiskStocks.add(new Stock("CENTURYLINK INC","CTL",0.0));
        highRiskStocks.add(new Stock("ALTRIA GROUP INC","MO",0.0));
        for (Stock stock : highRiskStocks){
            stock.increaseNumShares(1000);
            stock.updateStock(date);}
    }

    public ArrayList<Stock> getLowRiskStocks(){return lowRiskStocks;}

    public ArrayList<Stock> getMediumRiskStocks(){return mediumRiskStocks;}

    public ArrayList<Stock> getHighRiskStocks(){return highRiskStocks;}

}

