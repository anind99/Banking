package investments;

import java.util.Calendar;
import atm.*;

public class FundsTrader {
    public Calendar date;

    public FundsTrader(ATM atm){
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
}
