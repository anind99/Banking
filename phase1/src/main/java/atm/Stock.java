package atm;
import java.util.Random;



public class Stock {
    String name;
    String symbol;
    double buyPrice;
    double currentPrice;
    double yesterdayPrice;
    int numshares;
    boolean updated;

    // risk might also be obsolete but im leaving it here for now for legacy reasons.
    // Risk is an int from 1 to 5 that indicates the volatility of a stock.
    // 1: +/- 0.2% daily change
    // 2: +/- 0.5% daily change.
    // 3: +/- 1% daily change.
    // 4: +/- 2% daily change.
    // 5: +/- 5% daily change.

    int risk;

    public Stock(String n, String s, double p, int r){
        this.name = n;
        this.symbol = s;
        this.currentPrice = p;
        this.risk = r;
        this.updated = false;
    }



    void updatePrice(){
        // This code should be obsolete by the time I'm done coding the stock market.
        // Make sure to delete by the time we submit!
        Random rand = new Random();
        double seed = 1 - (rand.nextDouble() * 2);
        double base;
        switch(risk){
            case 1: base = 0.002;
            case 2: base = 0.005;
            case 3: base = 0.01;
            case 4: base = 0.025;
            case 5: base = 0.05;
            default: base = 0;
        }
        currentPrice += currentPrice * base * seed;
    }

}
