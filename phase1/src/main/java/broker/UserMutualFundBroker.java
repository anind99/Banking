package broker;

import account.Account;
import atm.ATM;
import atm.User;
import investments.MutualFund;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserMutualFundBroker implements Serializable {
    private final BankMutualFundBroker bankMutualFundBroker;

    public UserMutualFundBroker(ATM atm) {
        this.bankMutualFundBroker = new BankMutualFundBroker(atm);
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
                    System.out.println("You have sold " + amount + " $ of your " + fund.getName() + " fund investment");
                    break;
                }
            }
        }else{System.out.println("\nNot enough funds to sell");}


    }
    //calculates how much the user's investment into a certain fund is worth
    double calculateUserMoney(User user, MutualFund fund){
        HashMap<MutualFund, ArrayList<Double>> portfolio = user.getInvestmentPortfolio().getMutualFundPortfolio();
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
                break;}
        }
        if(enoughStockBalance){
            refillToSell(user, fund, amount);
        }else{
            System.out.println("\nNot enough funds in your stock account");
        }
    }

    //tells the bank to buy more stocks into a fund so the user can invest more money
    public void refillToSell(User user, MutualFund fund, double amount){
        if (!possibleToBuy(fund, amount)){
            bankMutualFundBroker.refillFunds(fund, amount);}
        updateFundInvestors(user, fund, amount);
        for (Account account: user.getAccounts()){
            if (account.getType().equals("stock")){
                account.removeMoney(amount);
                System.out.println("You have invested " + amount + " $ into " + fund.getName() + " fund");
                break;
            }
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
        boolean found = findFundInvestors(user, fund, amount);
        if(!found){
            ArrayList<Double> investment = new ArrayList<>();
            investment.add(amount);
            investment.add(percentOfFund);
            user.getInvestmentPortfolio().setMutualFundsPortfolio(fund, investment);
            fund.setInvestors(user, investment);}
    }

    //return if a users have already invested in a fund and updates accordingly
    public boolean findFundInvestors(User user, MutualFund fund, double amount){
        double percentOfFund = amount / fund.getValue() * 100;
        HashMap<MutualFund, ArrayList<Double>> userInvestments = user.getInvestmentPortfolio().getMutualFundPortfolio();
        boolean found = false;
        for (MutualFund userFund : userInvestments .keySet()){
            if(userFund.equals(fund)){
                userInvestments.get(userFund).set(0, userInvestments.get(userFund).get(0) + amount);
                userInvestments.get(userFund).set(1, userInvestments.get(userFund).get(1) + percentOfFund);
                fund.getInvestors().get(user).set(0, fund.getInvestors().get(user).get(0) + amount);
                fund.getInvestors().get(user).set(1, fund.getInvestors().get(user).get(1) + percentOfFund);
                found = true;}}
        return found;
    }

    //Calculate the %profit or loss of the user's investmentPortfolio in mutual funds
    public double calculateInvestmentIncrease(User user){
        double invested = 0.0;
        double netWorth = 0.0;
        for (MutualFund fund : user.getInvestmentPortfolio().getMutualFundPortfolio().keySet()){
            invested += user.getInvestmentPortfolio().getMutualFundPortfolio().get(fund).get(0);
            netWorth += (fund.getValue() * (user.getInvestmentPortfolio().getMutualFundPortfolio().get(fund).get(1) / 100));
        } return (netWorth / invested) * 100;
    }

    // prints the funds the user invested in and how much their investment is worth currently
    public String toString(User user){
        String mutualFundInvestments = "";
        double total = 0.0;
        for (MutualFund fund : user.getInvestmentPortfolio().getMutualFundPortfolio().keySet()){
            double value = fund.getValue() * (user.getInvestmentPortfolio().getMutualFundPortfolio().get(fund).get(1) / 100);
            mutualFundInvestments += "\n Your mutual fund investmentPortfolio are worth the following:\n" + fund.getName()
                    + ":" + value;
            total += value;
        }
        mutualFundInvestments += "\n The total value of your mutual fund investmentPortfolio is $" + total;
        mutualFundInvestments += "\n Your total mutual fund investment increase is " +
                calculateInvestmentIncrease(user) + " $";
        return mutualFundInvestments;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        ois.defaultReadObject();
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
