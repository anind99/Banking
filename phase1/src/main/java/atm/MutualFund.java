package atm;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class MutualFund {
    public int risk;
    public String name;
    public ArrayList<Stock> stocks;

    private MutualFund(int risk, String name, ArrayList<Stock> stocks){
        this.risk = risk;
        this.name = name;
        this.stocks = stocks;
    }
}
