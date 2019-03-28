package atm;

import java.util.ArrayList;

public class MutualFundsBroker {
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


    void refillFunds(MutualFund fund) {

    }

    public double calculateBrokerFree(double amount) {
        double fee = amount / 100;
        return fee;
    }

    void sellMutualFunds() {

    }

    public void buyMutualFunds(User user, MutualFund fund, double amount) {
        double total = calculateBrokerFree(amount) + amount;
        if(user.enoughStockBalance(total)){
            if (fund.getValue() < amount){
                refillFunds(fund);}
            buyMutualFundsHelper(user, fund, amount);
        }else{
            System.out.println("Not enough funds in your stock account");
        }
    }

    public void buyMutualFundsHelper(User user, MutualFund fund, double amount){
        ArrayList<Double> investment = new ArrayList<>();
        investment.add(amount);
        investment.add(fund.getValue());
        user.getInvestments().setMutualFundsPortfolio(fund, investment);
        fund.setInvestors(user, investment);
    }

    void calculatePercentageIncrease() {

    }

    void updateMutualFunds() {

    }
}
