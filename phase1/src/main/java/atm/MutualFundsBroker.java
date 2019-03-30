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

    public MutualFund getLowRiskFund() {return lowRiskFund;}

    public MutualFund getMediumRiskFund() {return mediumRiskFund;}

    public MutualFund getHighRiskFund() {return highRiskFund;}

    // buy more shares of the Stocks in a mutual fund a user wants to invest in so we put all the users money in shares
    public void refillFunds(MutualFund fund, double amount) {
        double oldValue = fund.getValue();
        double newValue = oldValue + amount;
        calculateRefill(fund, newValue);
        updateShareHolders(fund, oldValue, newValue);
    }

    //Calculates how many stocks should be bought for each stock in the fund
    public void calculateRefill(MutualFund fund, double amount){
        double netWorth = fund.getValue();
        int  increase =  (int) (amount / netWorth) + 1;
        for (Stock stock: fund.getStocks()){
            stock.setNumShares(stock.getNumShares() * increase);
        }
    }

    //update % owned of the shareholders
    public void updateShareHolders(MutualFund fund, double oldValue, double newValue){
        double increase = newValue / oldValue;
        for (User shareholder : fund.getInvestors().keySet()){
            double oldPercent = fund.getInvestors().get(shareholder).get(1);
            double newPercent = oldPercent / increase;
            fund.getInvestors().get(shareholder).set(1, newPercent);
            shareholder.getInvestments().getMutualFundPortfolio().get(fund).set(1, newPercent);
        }
    }

    // calculate the broker free for buying this mutual fund
    public double calculateBrokerFree(double amount){
        double fee = amount / 100;
        return fee;
    }

    //allows the user to sell funds
    public void sellMutualFunds(User user, MutualFund fund, double amount){
        double currentInvestment = calculateUserMoney(user, fund);
        if(amount <= currentInvestment){
            double sold = -1 * amount;
            updateFundInvestors(user, fund, sold);
            for (Account account: user.getAccounts()){
                if (account.getType().equals("stock")){
                    account.addBalance(amount);
                }
            }
        }else{System.out.println("\nNot enough funds to sell");}


    }
    //calculates how much the user's investment into a certain fund is worth
    double calculateUserMoney(User user, MutualFund fund){
        HashMap<MutualFund, ArrayList<Double>> portfolio = user.getInvestments().getMutualFundPortfolio();
        double percentOwned = portfolio.get(fund).get(1);
        double fundTotalValue = fund.getValue();
        return (fundTotalValue / 100) * percentOwned;
    }

     //lets a user buy into a mutual fund
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
            System.out.println("\nNot enough funds in your stock account");
        }
    }
    //Stores information about a users purchase in their investment portfolio and stores the users info in the fund's information
    public void updateFundInvestors(User user, MutualFund fund, double amount){
        double percentOfFund = amount / fund.getValue() * 100;
        HashMap<MutualFund, ArrayList<Double>> userInvestments = user.getInvestments().getMutualFundPortfolio();
        boolean found = false;
        for (MutualFund userFund : userInvestments .keySet()){
            if(userFund.equals(fund)){
                userInvestments.get(userFund).set(0, userInvestments.get(userFund).get(0) + amount);
                userInvestments.get(userFund).set(1, userInvestments.get(userFund).get(1) + percentOfFund);
                fund.getInvestors().get(user).set(0, fund.getInvestors().get(user).get(0) + amount);
                fund.getInvestors().get(user).set(1, fund.getInvestors().get(user).get(1) + percentOfFund);
                found = true;}}

        if(!found){
            ArrayList<Double> investment = new ArrayList<>();
            investment.add(amount);
            investment.add(percentOfFund);
            user.getInvestments().setMutualFundsPortfolio(fund, investment);
            fund.setInvestors(user, investment);}
    }

    //calculate the total net worth of mutual funds of a user
    public double getTotalMutualFundWorth(User user){
        double totalWorth = 0.0;
        for (MutualFund fund : user.getInvestments().getMutualFundPortfolio().keySet()){
            totalWorth += fund.getValue() * (user.getInvestments().getMutualFundPortfolio().get(fund).get(1) / 100);
        }return totalWorth;
    }

//updates the price the fund every day upon ATM restart
    public void updateMutualFunds() {
        for(Stock stock : lowRiskFund.getStocks()){stock.updateStock(date);}
        for(Stock stock : mediumRiskFund.getStocks()){stock.updateStock(date);}
        for(Stock stock : highRiskFund.getStocks()){stock.updateStock(date);}
    }

    //TODO
    public double calculateInvestmentIncrease(){}

    public void buyStocksFund(MutualFund fund, Stock symbol, int shares){
        for(Stock stock : fund.getStocks()){
            if (stock.getSymbol().equals(symbol){

            }
        }

    }

    public void sellStocksFund(MutualFund fund, Stock symbol, int shares){}



    // prints the funds the user invested in and how much their investment is worth currently
    public String toString(User user){
        String mutualFundInvestments = "";
        double total = 0.0;
        for (MutualFund fund : user.getInvestments().getMutualFundPortfolio().keySet()){
            double value = fund.getValue() * (user.getInvestments().getMutualFundPortfolio().get(fund).get(1) / 100);
            mutualFundInvestments += "\n Your mutual fund investments are worth the following:\n" + fund.getName()
                    + ":" + value;
            total += value;
        }
        mutualFundInvestments += "\n The total value of your mutual fund investments is $" + total;
        return mutualFundInvestments;
    }
}
