package atm;

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


    void refillFunds() {

    }

    public double calculateBrokerFree(double amount) {
        double fee = amount / 100;
        return fee;
    }

    void sellMutualFunds() {

    }

    public void buyMutualFunds(User user, double amount) {


    }

    void calculatePercentageIncrease() {

    }

    void updateMutualFunds() {

    }
}
