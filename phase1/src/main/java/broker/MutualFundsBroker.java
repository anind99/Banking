package broker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import account.*;
import atm.ATM;
import atm.User;
import investments.*;

public class MutualFundsBroker {

    public MutualFund lowRiskFund;
    public MutualFund mediumRiskFund;
    public MutualFund highRiskFund;
    public MutualFundsStocks mutualFundsStocks;
    public ATM atm;
    public Calendar date;
    public BankMutualFundBroker bankMutualFundBroker;
    public UserMutualFundBroker userMutualFundBroker;


    public MutualFundsBroker(ATM atm){
        this.atm = atm;
        date = atm.getDate();
        this.mutualFundsStocks = new MutualFundsStocks(atm);
        this.lowRiskFund = new MutualFund(1, "lowRiskFund1", mutualFundsStocks.getLowRiskStocks());
        this.mediumRiskFund = new MutualFund(2, "mediumRiskFund1", mutualFundsStocks.getMediumRiskStocks());
        this.highRiskFund = new MutualFund(3, "highRiskFund1", mutualFundsStocks.getHighRiskStocks());
        this.bankMutualFundBroker = new BankMutualFundBroker(atm);
        this.userMutualFundBroker = new UserMutualFundBroker(atm);

    }

    public MutualFund getLowRiskFund() {return lowRiskFund;}

    public MutualFund getMediumRiskFund() {return mediumRiskFund;}

    public MutualFund getHighRiskFund() {return highRiskFund;}

    public void buyMutualFunds(User user, MutualFund mutualFund, double amount) {
        userMutualFundBroker.buyMutualFunds(user, mutualFund, amount);
    }

    public void sellMutualFunds(User user, MutualFund mutualFund, double amount) {
        userMutualFundBroker.sellMutualFunds(user, mutualFund, amount);
    }

    public String toString(User user) {
        return userMutualFundBroker.toString(user);
    }

    public void buyStocksFund(MutualFund fundToBuy, String symbol, Integer valueOf) {
        bankMutualFundBroker.buyStocksFund(fundToBuy, symbol, valueOf);
    }

    public void sellStocksFund(MutualFund fundToSell, String symbol, Integer valueOf) {
        bankMutualFundBroker.buyStocksFund(fundToSell, symbol, valueOf);
    }

    //updates the price the fund every day upon ATM restart
    public void updateMutualFunds() {
        for(Stock stock : lowRiskFund.getStocks()){stock.updateStock(date);}
        for(Stock stock : mediumRiskFund.getStocks()){stock.updateStock(date);}
        for(Stock stock : highRiskFund.getStocks()){stock.updateStock(date);}
    }
}
