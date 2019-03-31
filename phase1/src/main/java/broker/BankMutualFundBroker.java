package broker;

import atm.ATM;
import atm.User;
import investments.MutualFund;
import investments.Stock;

import java.util.Calendar;

public class BankMutualFundBroker {
    public Calendar date;

    public BankMutualFundBroker(ATM atm){
        this.date = atm.getDate();
    }


    //TODO check is symbol is valid and add valid string name wait for alan to make the list
    //Lets the mutual funds broker buy stocks into a specified mutual fund
    public void buyStocksFund(MutualFund fund, String symbol, int shares){
        boolean found = false;
        for(Stock stock : fund.getStocks()){
            if (stock.getSymbol().equals(symbol)){
                stock.increaseNumShares(shares);
                found = true;}
        }if(!found){
            Stock bought = new Stock(symbol, symbol,0.0);
            bought.setNumShares(shares);
            bought.updateStock(date);
            fund.getStocks().add(bought);
        }
    }

    //TODO check is symbol is valid wait for alan to make the list
    //Lets the mutual funds broker sell stocks from a specified mutual fund
    public void sellStocksFund(MutualFund fund, String symbol, int shares) {
        boolean found = false;
        for (Stock stock : fund.getStocks()) {
            if (stock.getSymbol().equals(symbol)) {
                found = true;
                if (stock.getNumShares() <= shares) {
                    stock.decreaseNumShares(shares);
                } else {System.out.println("You do not own enough shares please try again");}
            }
        }if (!found) {System.out.println("This stock does not exists in this fund");}
    }

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
}
