package investments;

import account.Account;
import atm.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientFacingBroker {

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
                    account.addMoney(amount);
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
        boolean enoughStockBalance = false;
        for (Account account: user.getAccounts()){
            if (account.getType().equals("stock")){
                enoughStockBalance = account.checkFundsSufficient(total);
                break;
            }
        }
        if(enoughStockBalance){
            if (!possibleToBuy(fund, amount)){
                refillFunds(fund, amount);}
            updateFundInvestors(user, fund, amount);
            for (Account account: user.getAccounts()){
                if (account.getType().equals("stock")){
                    account.removeMoney(amount);
                }
            }
        }else{
            System.out.println("\nNot enough funds in your stock account");
        }
    }

    //Checks the %of the fund that has been bought
    public boolean possibleToBuy(MutualFund fund, double amount) {
        if (fund.getValue() < amount) {
            return false;
        } else {
            double totalPercent = 0.0;
            double percentOfFund = fund.getValue() / amount;
            for (User user : fund.getInvestors().keySet()) {
                totalPercent += fund.getInvestors().get(user).get(1);
            }
            return (totalPercent + percentOfFund) <= 100;
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

    //Calculate the %profit or loss of the user's investments in mutual funds
    public double calculateInvestmentIncrease(User user){
        double invested = 0.0;
        double netWorth = 0.0;
        for (MutualFund fund : user.getInvestments().getMutualFundPortfolio().keySet()){
            invested += user.getInvestments().getMutualFundPortfolio().get(fund).get(0);
            netWorth += (fund.getValue() * (user.getInvestments().getMutualFundPortfolio().get(fund).get(1) / 100));
        } return (netWorth / invested) * 100;
    }

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
        mutualFundInvestments += "\n Your total mutual fund investment increase is " +
                calculateInvestmentIncrease(user) + " $";
        return mutualFundInvestments;
    }
}
