package atm;
import java.util.Random;



public class Stock {
    String name;
    String symbol;
    double price;

    //Risk is an int from 1 to 5 that indicates the volatility of a stock.
    // 1: +/- 0.2% daily change
    // 2: +/- 0.5% daily change.
    // 3: +/- 1% daily change.
    // 4: +/- 2% daily change.
    // 5: +/- 5% daily change.

    int risk;


    void updatePrice(){
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
        price += price * base * seed;
    }

}
