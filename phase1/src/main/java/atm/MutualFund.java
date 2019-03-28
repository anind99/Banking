package atm;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class MutualFund {
    public int risk; // 1 is low risk, 2 is medium risk, 3 is high risk
    public String name;
    public ArrayList<Stock> stocks;
    public double sold;

    public MutualFund(int risk, String name, ArrayList<Stock> stocks){
        this.risk = risk;
        this.name = name;
        this.stocks = stocks;
        this.sold = 0.0;
    }

    public double getValue(){
        double total = 0.0;
        for (Stock stock : stocks){
            total += stock.currentPrice;
            return total;
        }

    }
}
