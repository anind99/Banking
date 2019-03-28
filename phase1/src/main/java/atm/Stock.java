package atm;
import java.util.Random;



public class Stock {
    String name;
    String symbol;
    double currentPrice;
    double yesterdayPrice;
    int numShares;
    boolean updated;
    double totalSpent;

    // risk might also be obsolete but im leaving it here for now for legacy reasons.
    // Risk is an int from 1 to 5 that indicates the volatility of a stock.
    // 1: +/- 0.2% daily change
    // 2: +/- 0.5% daily change.
    // 3: +/- 1% daily change.
    // 4: +/- 2% daily change.
    // 5: +/- 5% daily change.

    public Stock(String n, String s, double p, int r){
        this.name = n;
        this.symbol = s;
        this.currentPrice = p;
        this.updated = false;
    }

    public double getValue(){
        return currentPrice;
    }



    void updatePrice(){

    }

}
