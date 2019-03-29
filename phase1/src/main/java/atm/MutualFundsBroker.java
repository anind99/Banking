package atm;

import java.util.ArrayList;

public class MutualFundsBroker extends Broker{
    public MutualFund lowRiskFund;
    public MutualFund mediumRiskFund;
    public MutualFund highRiskFund;
    public MutualFundsStocks mutualFundsStocks;

    public MutualFundsBroker(){
        this.mutualFundsStocks = new MutualFundsStocks();
        this.lowRiskFund = new MutualFund(1, "lowRiskFund1", mutualFundsStocks.getLowRiskStocks());
        this.mediumRiskFund = new MutualFund(2, "mediumRiskFund1", mutualFundsStocks.getMediumRiskStocks());
        this.highRiskFund = new MutualFund(3, "highRiskFund1", mutualFundsStocks.getHighRiskStocks());
    }

// buy more shares of the Stocks in a mutual fund a user wants to invest in so we put all the users money in shares
    public void refillFunds(MutualFund fund, double amount) {
        int num = calculateRefill(fund, amount);
        fund.setStocks(num);
    }

    public int calculateRefill(MutualFund fund, double amount){
        int numStocks = fund.getShares();
        double netWorth = fund.getValue();
        int  increase =  (int) (amount / netWorth) + 1;
        return numStocks * increase;
    }


    public double calculateBrokerFree(double amount) {
        double fee = amount / 100;
        return fee;
    }

    void sellMutualFunds(User user, MutualFund fund, double amount) {


    }

    public void buyMutualFunds(User user, MutualFund fund, double amount) {
        double total = calculateBrokerFree(amount) + amount;
        if(user.enoughStockBalance(total)){
            if (fund.getValue() < amount){
                refillFunds(fund, amount);}
            updateFundInvestors(user, fund, amount);
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
    void updateMutualFunds() {

    }
}
