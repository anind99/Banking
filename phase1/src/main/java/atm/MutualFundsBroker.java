package atm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import account.*;


public class MutualFundsBroker {
    public MutualFund lowRiskFund;
    public MutualFund mediumRiskFund;
    public MutualFund highRiskFund;
    public MutualFundsStocks mutualFundsStocks;
    public ATM atm;
    public Calendar date;


    public MutualFundsBroker(ATM atm){
        this.atm = atm;
        date = atm.getDate();
        this.mutualFundsStocks = new MutualFundsStocks(atm);
        this.lowRiskFund = new MutualFund(1, "lowRiskFund1", mutualFundsStocks.getLowRiskStocks());
        this.mediumRiskFund = new MutualFund(2, "mediumRiskFund1", mutualFundsStocks.getMediumRiskStocks());
        this.highRiskFund = new MutualFund(3, "highRiskFund1", mutualFundsStocks.getHighRiskStocks());
    }

// buy more shares of the Stocks in a mutual fund a user wants to invest in so we put all the users money in shares
    public void refillFunds(MutualFund fund, double amount) {
        int num = calculateRefill(fund, amount);
        fund.setSharesStocks(num);
    }

    public int calculateRefill(MutualFund fund, double amount){
        int numStocks = fund.getShares();
        double netWorth = fund.getValue();
        int  increase =  (int) (amount / netWorth) + 1;
        return numStocks * increase;
    }


    public double calculateBrokerFree(double amount){
        double fee = amount / 100;
        return fee;
    }
    //NOT DONE YET
    public void sellMutualFunds(User user, MutualFund fund, double amount){
        double currentInvestment = calculateUserMoney(user, fund);
        if(amount <= currentInvestment){
            for (Account account: user.getAccounts()){
                if (account.getType().equals("stock")){
                    account.addBalance(amount);
                }
            }


        }else{System.out.println("Not enough funds to sell");}


    }
    // WHAT IF THE USER INVESTED IN THE FUND MULTIPLE TIMES??
    double calculateUserMoney(User user, MutualFund fund){
        HashMap<String, ArrayList<Double>> portfolio = user.getInvestments().getMutualFundPortfolio();
        double invested = portfolio.get(fund.getName()).get(0);
        double previousValueFund = portfolio.get(fund.getName()).get(1);
        double currentValueFund = fund.getValue();
        return ((currentValueFund - previousValueFund) / previousValueFund + 1) * invested;
    }


    public void buyMutualFunds(User user, MutualFund fund, double amount){
        double total = calculateBrokerFree(amount) + amount;
        if(user.enoughStockBalance(total)){
            if (fund.getValue() < amount){
                refillFunds(fund, amount);}
            updateFundInvestors(user, fund, amount);
            for (Account account: user.getAccounts()){
                if (account.getType().equals("stock")){
                    account.subtractBalance(amount);
                }
            }
        }else{
            System.out.println("Not enough funds in your stock account");
        }
    }

    public void updateFundInvestors(User user, MutualFund fund, double amount){
        ArrayList<Double> investment = new ArrayList<>();
        investment.add(amount);
        investment.add(fund.getValue());
        user.getInvestments().setMutualFundsPortfolio(fund, investment);
        fund.setInvestors(user, investment);
    }

    void calculatePercentageIncrease() {

    }
//updates the price the fund every day upon ATM restart
    public void updateMutualFunds() {
        for(Stock stock : lowRiskFund.getStocks()){
            stock.updateStock(date);
        }

        for(Stock stock : mediumRiskFund.getStocks()){
            stock.updateStock(date);
        }

        for(Stock stock : highRiskFund.getStocks()){
            stock.updateStock(date);
        }

    }
}
